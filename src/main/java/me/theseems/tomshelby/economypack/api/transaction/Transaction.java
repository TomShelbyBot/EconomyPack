package me.theseems.tomshelby.economypack.api.transaction;

import me.theseems.tomshelby.economypack.api.EconomyProvider;

import java.util.UUID;

public interface Transaction {
  /**
   * Get transaction status
   * @return status
   */
  TransactionStatus getStatus();

  /**
   * Set transaction status
   * @param status to set
   */
  void setStatus(TransactionStatus status);

  /**
   * Get transaction's economy provider
   * @return provider
   */
  EconomyProvider getProvider();

  /**
   * Get transaction's uuid
   * @return uuid of transaction
   */
  UUID getUuid();
}
