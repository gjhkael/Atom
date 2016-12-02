package com.ctrip.persistence.enums;

/**
 * @author juntao zhang
 */
public enum MLFlowStatus {
  STOP("停止"),
  RUNNING("运行中"),
  FINISHED("结束");

  MLFlowStatus(String value) {
    this.value = value;
  }

  private String value;


  public String getValue() {
    return value;
  }

  public MLFlowStatus setValue(String value) {
    this.value = value;
    return this;
  }
}
