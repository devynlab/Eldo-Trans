package io.devynlab.eldotrans.user.enums;

public enum Gender {
  MALE("Male"),
  FEMALE("Female"),
  NA("N/a"),
  NOT_SPECIFIED("Not Specified");

  private String name;

  Gender(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
