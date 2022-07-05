package me.theseems.tomshelby.economypack.api.transaction.types;

import me.theseems.tomshelby.economypack.api.transaction.Transaction;

import java.math.BigDecimal;

public interface DepositTransaction extends Transaction {
  /**
   * Get deposit amount
   * @return amount to be deposited
   */
  BigDecimal getDepositAmount();

  /**
   * Get user to deposit to
   * @return to user's id
   */
  Long getTo();
}
