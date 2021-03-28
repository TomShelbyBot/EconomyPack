package me.theseems.tomshelby.economypack.impl.executors;

import me.theseems.tomshelby.economypack.api.transaction.Transaction;
import me.theseems.tomshelby.economypack.api.transaction.TransactionExecutor;
import me.theseems.tomshelby.economypack.api.transaction.TransactionHandler;
import me.theseems.tomshelby.economypack.api.transaction.TransactionStatus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SimpleTransactionExecutor implements TransactionExecutor {
  private final Map<Type, List<TransactionHandler>> handlers;
  private final Map<Transaction, Consumer<Transaction>> map;

  public SimpleTransactionExecutor() {
    this.handlers = new HashMap<>();
    this.map = new ConcurrentHashMap<>();
  }

  public void launch() {
    ScheduledExecutorService service = Executors.newScheduledThreadPool(8);
    service.scheduleAtFixedRate(this::run, 0, 1, TimeUnit.NANOSECONDS);
  }

  // Get executor handling transaction type
  private Type getType(TransactionHandler executor) {
    return (((ParameterizedType) executor.getClass().getGenericSuperclass())
        .getActualTypeArguments()[0]);
  }

  public void run() {
    map.forEach(
        (transaction, transactionConsumer) -> {
          Type type = transaction.getClass().getGenericInterfaces()[0];
          if (!handlers.containsKey(type)) {

            TransactionStatus status = TransactionStatus.FAILED;
            status.message = "Unknown transaction type: " + type.getTypeName();
            transaction.setStatus(status);

            transactionConsumer.accept(transaction);
            map.remove(transaction);
            return;
          }

          try {
            for (TransactionHandler transactionHandler : handlers.get(type)) {
              transactionHandler.execute(transaction);
            }
            transaction.setStatus(TransactionStatus.SUCCEEDED);
          } catch (Exception e) {
            TransactionStatus status = TransactionStatus.FAILED;
            status.message = e.getMessage();
            transaction.setStatus(status);
          }

          transactionConsumer.accept(transaction);
          map.remove(transaction);
        });
  }

  /**
   * Execute transaction
   *
   * @param transaction to execute
   */
  @Override
  public <T extends Transaction> void execute(T transaction, Consumer<T> callback) {
    map.put(transaction, (Consumer<Transaction>) callback);
  }

  /**
   * Register handler to the executor
   *
   * @param transactionHandler to register
   */
  @Override
  public <T extends Transaction> void registerHandler(TransactionHandler<T> transactionHandler) {
    Type type = getType(transactionHandler);
    if (!handlers.containsKey(type)) {
      handlers.put(type, new ArrayList<>());
    }

    handlers.get(type).add(transactionHandler);
  }
}
