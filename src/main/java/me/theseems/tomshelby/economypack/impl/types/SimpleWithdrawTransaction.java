package me.theseems.tomshelby.economypack.impl.types;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionStatus;
import me.theseems.tomshelby.economypack.api.transaction.types.WithdrawTransaction;
import me.theseems.tomshelby.economypack.impl.SimpleTransaction;

import java.math.BigDecimal;
import java.util.UUID;

public class SimpleWithdrawTransaction extends SimpleTransaction implements WithdrawTransaction {
  private final BigDecimal amount;
  private final Integer to;

  public SimpleWithdrawTransaction(EconomyProvider provider, BigDecimal amount, Integer to) {
    super(provider, TransactionStatus.QUEUED, UUID.randomUUID());
    this.amount = amount;
    this.to = to;
  }

  /**
   * Get deposit amount
   *
   * @return amount to be deposited
   */
  @Override
  public BigDecimal getWithdrawAmount() {
    return amount;
  }

  /**
   * Get user to deposit to
   *
   * @return to user's id
   */
  @Override
  public Integer getTo() {
    return to;
  }

  @Override
  public String toString() {
    return "SimpleWithdrawTransaction{" +
        "amount=" + amount +
        ", to=" + to +
        "} " + super.toString();
  }
}
