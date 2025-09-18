package com.tlg.util;

import java.io.Serializable;

public class Result implements Serializable{

  private Object  resObject = null;

  private Message message   = null; ;

  public Result() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public Object getResObject() {
    return resObject;
  }

  public void setResObject(Object resObject) {
    this.resObject = resObject;
  }

}
