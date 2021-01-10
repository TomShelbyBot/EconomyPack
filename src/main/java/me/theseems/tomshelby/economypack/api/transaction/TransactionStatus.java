package me.theseems.tomshelby.economypack.api.transaction;

public enum TransactionStatus {
  FAILED(false, "Transaction failed"),
  QUEUED(false, "Transaction is queued"),
  FROZEN(false, "Transaction has been frozen"),
  SUCCEEDED(true, "Transaction succeeded");

  public boolean success;
  public String message;

  TransactionStatus(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
