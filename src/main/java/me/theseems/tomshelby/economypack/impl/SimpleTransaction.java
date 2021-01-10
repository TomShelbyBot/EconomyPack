package me.theseems.tomshelby.economypack.impl;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.Transaction;
import me.theseems.tomshelby.economypack.api.transaction.TransactionStatus;

import java.util.UUID;

public abstract class SimpleTransaction implements Transaction {
  private final EconomyProvider provider;
  private TransactionStatus status;
  private final UUID uuid;

  public SimpleTransaction(EconomyProvider provider, TransactionStatus status, UUID uuid) {
    this.provider = provider;
    this.status = status;
    this.uuid = uuid;
  }

  @Override
  public TransactionStatus getStatus() {
    return status;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "SimpleTransaction{" +
        "status=" + status +
        ", uuid=" + uuid +
        '}';
  }

  @Override
  public EconomyProvider getProvider() {
    return provider;
  }
}
