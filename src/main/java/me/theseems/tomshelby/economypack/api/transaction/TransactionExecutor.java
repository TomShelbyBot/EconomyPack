package me.theseems.tomshelby.economypack.api.transaction;

import java.util.function.Consumer;

public interface TransactionExecutor {
  /**
   * Execute transaction
   *
   * @param transaction to execute
   */
  <T extends Transaction> void execute(T transaction, Consumer<T> callback);

  /**
   * Register handler to the executor
   * @param transactionHandler to register
   * @param <T> of transaction to handle
   */
  <T extends Transaction> void registerHandler(TransactionHandler<T> transactionHandler);
}
