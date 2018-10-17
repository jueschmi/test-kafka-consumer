package com.sda.workbench.kafka.consumer.document.rest.model;

public class SorKeyElement {

  private String key;
  private String value;

  public SorKeyElement() {
  }

  public SorKeyElement(String key, String value) {
    this.key = key;
    this.setValue(value);
  }

  public String getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
