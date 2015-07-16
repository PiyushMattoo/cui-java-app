package org.sitraka;

import java.io.*;

public class Language implements java.io.Serializable {

  private String name;

  public Language() {

  }

  public Language(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
