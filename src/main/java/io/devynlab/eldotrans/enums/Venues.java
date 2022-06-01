package io.devynlab.eldotrans.enums;

public enum Venues {
  ELDORET("AIC Chebisas Boys High School, ELDORET"),
  KISUMU("Kasagam Secondary School, KISUMU"),
  NAIROBI("Upperhil High School, NAIROBI"),
  MOMBASA("Khadija Secondary School, MOMBASA"),
  KERUGOYA("Ngaru Girls High School, KERUGOYA");
  private String name;

  Venues(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
