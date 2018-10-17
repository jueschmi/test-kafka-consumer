package com.sda.workbench.kafka.consumer.document.rest.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class SoRKey {

  @ApiModelProperty(value = "List of SoRKey elements.", example = "...")
  private List<SorKeyElement> sorKeyElements;

  public SoRKey(List<SorKeyElement> sorKeyElements) {
    this.sorKeyElements = sorKeyElements;
  }

  public List<SorKeyElement> getSorKeyElements() {
    return sorKeyElements;
  }

  public void setSorKeyElements(List<SorKeyElement> sorKeyElements) {
    this.sorKeyElements = sorKeyElements;
  }
}
