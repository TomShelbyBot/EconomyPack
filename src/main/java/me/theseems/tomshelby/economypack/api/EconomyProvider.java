package me.theseems.tomshelby.economypack.api;

import me.theseems.tomshelby.economypack.api.transaction.TransactionExecutor;

import java.math.BigDecimal;

public interface EconomyProvider {
  /**
   * Get name of the economy
   * @return economy's name
   */
  String getName();

  /**
   * Get transaction executor
   *
   * @return transaction executor
   */
  default TransactionExecutor getTransactionExecutor() {
    return EconomyApi.getManager().getGlobalProvider().getTransactionExecutor();
  }

  /**
   * Set money amount to user
   * @param userId to set to
   * @param amount to set
   */
  void setMoney(Integer userId, BigDecimal amount);

  /**
   * Get user's money
   * @param userId to get amount of money of
   * @return money
   */
  BigDecimal getMoney(Integer userId);
}
