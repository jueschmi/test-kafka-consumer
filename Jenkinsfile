@Library('jenkins-library@master') _

dockerRegistry = 'https://registry.eu-de.bluemix.net'
dockerRepository = 'sda_se_containers'
dockerPrefix = 'registry.eu-de.bluemix.net'

dockerContainerPrefix = [dockerPrefix, dockerRepository].join('/')

imageTag = ''
url = ''
namespace = ''
branch = ''

pipeline {
    agent none
    stages {
        /*
         * Stage: Prepare Workspace
         *
         * Checks out scm, sets up gradle.properties and replaces placehodlers within
         * feature branch files
         *
         */
        stage('Prepare Workspace') {
            agent {
                label 'master'
            }
            steps {
                prepareGradleWorkspace secretId: 'sdabot-github-token', pathToFile: 'gradle.properties'
                script {
                    (namespace, branch, url, imageTag) = getFeatureBranchVariables()
                }
            }
        }
        /*
         * Stage: Tag Release
         *
         * Finds the current Tag and increments the version by one
         *
         */
        stage('Tag Release') {
            when {
                branch 'master'
            }
            agent {
                label 'master'
            }
            steps {
                javaGradlew gradleCommand: 'createRelease'
            }
        }
        /*
         * Stage: Gradle Java: Service
         *
         * Calls the Gradle Wrapper and builds the Java service.
         *
         * Runs in its own Docker container
         */
        stage('Gradle Java: Service') {
            agent {
                docker {
                    image 'openjdk:8-jdk-alpine'
                }
            }
            steps {
                javaGradlew gradleCommand: 'installDist', clean: true
            }
        }
        /*
         * Stage: Java Gradle: Module Test Service
         *
         * Calls the Gradle wrapper with the "test" task in order
         * to module test the software.
         *
         * Publishes an HTML Report if tests are failing
         *
         * Runs in its own Docker container
         *
         */
        stage('Gradle Java: Module Test Service') {
            agent {
                docker {
                    image 'openjdk:8-jdk-alpine'
                }
            }
            steps {
                javaGradlew gradleCommand: 'test'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
        /*
         * Stage: Java Gradle: Integration Test Service
         *
         * Same as Module Test Service but calling gradlew 'iT'
         *
         */
        stage('Gradle Java: Integration Test Service') {
            agent {
                docker {
                    image 'openjdk:8-jdk-alpine'
                }
            }
            steps {
                javaGradlew gradleCommand: 'integrationTest'
            }
            post {
                always {
                    junit 'build/integTest-results/*.xml'
                }
            }
        }
        /*
         * Run Sonar Scan on all sources for the default branch and publish the results to SonarQube
         */
        stage('Sonar Scan Sources (Publish to SonarQube)') {
            agent {
                label 'master'
            }
            when {
                branch 'develop'
            }
            steps {
                sonarScanBranch project: 'com.sdase.test-kafka-consumer', javaBaseDir: '.'
            }
        }
        /*
         * Run Sonar Scan on all sources for a pull request and annotate the Pull Request on GitHub
         */
        stage('Sonar Scan Sources (Annotate Pull Request)') {
            agent {
                label 'master'
            }
            when {
                changeRequest()
            }
            steps {
                sonarScanPullRequest project: 'com.sdase.test-kafka-consumer', javaBaseDir: '.'
            }
        }
        /*
         * Stage: Build Service Docker
         *
         * Uses the artifacts built by the steps previous to this one
         * and builds a Docker container using a provided Dockerfile.
         * It will also push that container to the nexus.
         *
         */
        stage('Build Services Docker') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            agent {
                label 'master'
            }
            steps {
                buildDockerContainer containerName: dockerContainerPrefix + '/test-kafka-consumer:' + imageTag, buildContext: ''
                pushDockerContainer containerName: dockerContainerPrefix + '/test-kafka-consumer:' + imageTag, registry: dockerRegistry, credentials: 'bluemix-registry'
            }
        }
        /*
         * Stage: Deploy Service to Kubernets
         *
         * Replaces template strings within the provided kubernetes files
         * and uses the containers built before to run them on a given
         * K8S cluster.
         *
         * Note that there is an extra Step "replaceInTemplate" which should
         * be called right before in order to prevent Jenkins from resetting
         * the git repository to checkout state.
         *
         */
        stage('Deploy Services to Kubernetes') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            agent {
                label 'master'
            }
            steps {
                replaceInTemplate namespace: namespace, branch: branch, url: url, repository: dockerRepository, registry: dockerPrefix, imageTag: imageTag, files: ['config.yml', 'kubernetes/kubernetes.yml']
                kubernetesDeployment namespace: namespace, branch: branch, filename: 'kubernetes/kubernetes.yml', tokenId: 'kubernetes-token', caId: 'kubernetes-master-ca', masterUrl: 'kubernetes-master-url', dockerKubernetesSecretId: 'cluster-registry-bluemix'
            }
        }
        /*
         * Stage: Publish release
         *
         * Pushes the Tag to Repository and Uploads the Archive to Nexus
         *
         */
        stage('Publish release') {
            when {
                branch 'master'
            }
            agent {
                label 'master'
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'sdabot-github-token', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/SDA-SE/test-kafka-consumer --tags')
                }
            }
        }
    }
}
