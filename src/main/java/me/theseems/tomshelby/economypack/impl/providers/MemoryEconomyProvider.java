package me.theseems.tomshelby.economypack.impl.providers;

import me.theseems.tomshelby.economypack.api.EconomyProvider;
import me.theseems.tomshelby.economypack.api.transaction.TransactionExecutor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MemoryEconomyProvider implements EconomyProvider {
  private final Map<Long, BigDecimal> accountMap;
  private final String name;
  private TransactionExecutor executor;

  public MemoryEconomyProvider(String name) {
    this.name = name;
    this.accountMap = new HashMap<>();
  }

  public void setExecutor(TransactionExecutor executor) {
    this.executor = executor;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public TransactionExecutor getTransactionExecutor() {
    return executor;
  }

  @Override
  public void setMoney(Long userId, BigDecimal amount) {
    accountMap.put(userId, amount);
  }

  @Override
  public BigDecimal getMoney(Long userId) {
    if (!accountMap.containsKey(userId))
      return BigDecimal.ZERO;
    return accountMap.get(userId);
  }
}
