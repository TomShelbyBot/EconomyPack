package me.theseems.tomshelby.economypack.api;

public class EconomyApi {
  private static EconomyManager manager;

  public EconomyApi() {
  }

  public static void setManager(EconomyManager manager) {
    EconomyApi.manager = manager;
  }

  public static EconomyManager getManager() {
    return manager;
  }
}
