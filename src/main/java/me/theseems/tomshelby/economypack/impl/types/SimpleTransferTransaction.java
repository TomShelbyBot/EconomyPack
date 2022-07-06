package me.theseems.tomshelby.economypack.impl.types;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionStatus;
import me.theseems.tomshelby.economypack.api.transaction.types.TransferTransaction;
import me.theseems.tomshelby.economypack.impl.SimpleTransaction;

import java.math.BigDecimal;
import java.util.UUID;

public class SimpleTransferTransaction extends SimpleTransaction implements TransferTransaction {
  Long from;
  Long to;
  BigDecimal amount;

  public SimpleTransferTransaction(EconomyProvider provider, Long from, Long to, BigDecimal amount) {
    super(provider, TransactionStatus.QUEUED, UUID.randomUUID());
    this.from = from;
    this.to = to;
    this.amount = amount;
  }

  /**
   * Get transfer amount
   *
   * @return amount to be transferred
   */
  @Override
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Get user to transfer from
   *
   * @return from user's id
   */
  @Override
  public Long getFrom() {
    return from;
  }

  /**
   * Get user to transfer to
   *
   * @return to user's id
   */
  @Override
  public Long getTo() {
    return to;
  }
}
