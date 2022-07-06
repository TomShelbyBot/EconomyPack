package me.theseems.tomshelby.economypack.impl.types;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionStatus;
import me.theseems.tomshelby.economypack.api.transaction.types.DepositTransaction;
import me.theseems.tomshelby.economypack.impl.SimpleTransaction;

import java.math.BigDecimal;
import java.util.UUID;

public class SimpleDepositTransaction extends SimpleTransaction implements DepositTransaction {
  private final BigDecimal amount;
  private final Long to;

  public SimpleDepositTransaction(EconomyProvider provider, BigDecimal amount, Long to) {
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
  public BigDecimal getDepositAmount() {
    return amount;
  }

  /**
   * Get user to deposit to
   *
   * @return to user's id
   */
  @Override
  public Long getTo() {
    return to;
  }

  @Override
  public String toString() {
    return "SimpleDepositTransaction{"
        + "amount="
        + amount
        + ", to="
        + to
        + "} "
        + super.toString();
  }
}
