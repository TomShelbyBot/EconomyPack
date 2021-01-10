package me.theseems.tomshelby.economypack.api.transaction.types;

import me.theseems.tomshelby.economypack.api.transaction.Transaction;

import java.math.BigDecimal;

public interface TransferTransaction extends Transaction {
  /**
   * Get transfer amount
   * @return amount to be transferred
   */
  BigDecimal getAmount();

  /**
   * Get user to transfer from
   * @return from user's id
   */
  Integer getFrom();

  /**
   * Get user to transfer to
   * @return to user's id
   */
  Integer getTo();
}
