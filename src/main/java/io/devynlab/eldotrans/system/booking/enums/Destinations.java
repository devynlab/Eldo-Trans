package io.devynlab.eldotrans.system.booking.enums;

public enum Destinations {
  ELDORET("Eldoret, along Uganda road at the National Oil Petroleum Station near the old Ukwala Supermarket"),
  NAIROBI("Nairobi, at the Mfangano Lane near Afya Center");
  private String name;

  Destinations(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
