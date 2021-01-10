package me.theseems.tomshelby.economypack.api;

import java.util.Collection;
import java.util.Optional;

public interface EconomyManager {
  /**
   * Get global economy provider
   *
   * @return global economy
   */
  EconomyProvider getGlobalProvider();

  /**
   * Get 'local' economy provider
   *
   * @param name to get by
   * @return optional of local economy provider
   */
  Optional<EconomyProvider> getProvider(String name);

  /**
   * Register economy provider
   *
   * @param provider to register
   */
  void registerProvider(EconomyProvider provider);

  /**
   * Unregister provider from the manager
   *
   * @param name to unregister
   */
  void unregisterProvider(String name);

  /**
   * Get all provider's names there are in manager (possible except for global)
   *
   * @return providers
   */
  Collection<String> getProviders();
}
