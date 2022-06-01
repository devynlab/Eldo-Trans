package io.devynlab.eldotrans.system.vehicle.enums;

public enum CarModels {
  VOXY("Toyota Voxy"),
  NOAH("Toyota Noah");
  private String name;

  CarModels(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
