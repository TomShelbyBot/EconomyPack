package me.theseems.tomshelby.economypack.api.transaction;

public abstract class TransactionHandler<T extends Transaction> {
  public abstract void execute(T transaction);
}
