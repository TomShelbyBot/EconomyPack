package me.theseems.tomshelby.economypack.api.transaction.types;

import me.theseems.tomshelby.economypack.api.transaction.Transaction;

import java.math.BigDecimal;

public interface WithdrawTransaction extends Transaction {
  /**
   * Get deposit amount
   * @return amount to be withdrawn
   */
  BigDecimal getWithdrawAmount();

  /**
   * Get user to withdraw to
   * @return to user's id
   */
  Long getTo();
}
