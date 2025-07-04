package com.abdelrahman.financetracker.currencyconverter.domain.plugin

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple registry for managing currency provider plugins
 */
@Singleton
class CurrencyPluginRegistry @Inject constructor() {
    
    private val mutex = Mutex()
    private val plugins = mutableMapOf<String, CurrencyProviderPlugin>()
    private var activeProviderId: String? = null
    
    /**
     * Register a currency provider plugin
     */
    suspend fun registerPlugin(plugin: CurrencyProviderPlugin) {
        mutex.withLock {
            plugins[plugin.id] = plugin
            
            // Set as active if it's the first plugin or no active provider is set
            if (activeProviderId == null) {
                activeProviderId = plugin.id
            }
        }
    }
    
    /**
     * Get all registered plugins
     */
    suspend fun getAllPlugins(): List<CurrencyProviderPlugin> {
        return mutex.withLock {
            plugins.values.toList()
        }
    }
    
    /**
     * Get the currently active plugin
     */
    suspend fun getActivePlugin(): CurrencyProviderPlugin? {
        return mutex.withLock {
            activeProviderId?.let { plugins[it] }
        }
    }
    
    /**
     * Set the active provider
     */
    suspend fun setActiveProvider(providerId: String) {
        mutex.withLock {
            if (plugins.containsKey(providerId)) {
                activeProviderId = providerId
            }
        }
    }
    
    /**
     * Get the active provider ID
     */
    suspend fun getActiveProviderId(): String? {
        return mutex.withLock {
            activeProviderId
        }
    }
} 