package com.themrsung.mc;

import com.themrsung.mc.economy.EconomyX;
import com.themrsung.mc.listener.PlayerAccountInitializationListener;
import com.themrsung.mc.task.AutoSaveTask;
import com.themrsung.mc.util.VaultLegacyLoader;
import com.themrsung.mc.util.VaultXConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * <b>Vault Extended</b>
 * Built to be a better economy plugin, but still support legacy Vault API.
 */
public final class VaultX extends JavaPlugin {
    private EconomyX economy;
    private VaultXConfiguration config;
    private boolean legacyEnabled;

    /**
     * Returns the {@link EconomyX economy}.
     *
     * @return The economy
     */
    public @NotNull EconomyX getEconomy() {
        return economy;
    }

    /**
     * Returns whether legacy Vault API support is enabled.
     *
     * @return {@code true} if legacy Vault is supported at this time
     */
    public boolean isLegacyEnabled() {
        return legacyEnabled;
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading VaultX...");

        // Create configuration if absent
        saveDefaultConfig();

        // Load configuration
        loadConfig();
        getLogger().info("Configuration loaded!");

        // Config should be non-null by now, but just in case
        if (config == null) {
            config = new VaultXConfiguration();
        }

        // Try to find legacy Vault API
        var legacyVault = Bukkit.getPluginManager().getPlugin("Vault");
        if (legacyVault != null && legacyVault.isEnabled()) {
            loadVXEconomy(); // Load Vault-compatible economy if found
            legacyEnabled = true;

            getLogger().info("Legacy Vault API found. Using VX economy.");
        } else {
            loadVanillaEconomy(); // VX-only vanilla economy otherwise
            getLogger().info("Vault API not found. Using vanilla economy.");
        }

        // Economy should be non-null by now
        if (economy == null) {
            getLogger().warning("Failed to initialize economy! Shutting down...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register EconomyX to Bukkit
        registerEconomyService();
        getLogger().info("EconomyX registered to services manager!");

        // Attempt to load data
        try {
            economy.load();
            getLogger().info("Data loaded from disk!");
        } catch (IOException e) {
            getLogger().warning("Error loading data: " + e.getMessage());
        }

        // Register autosave task
        getServer().getScheduler().scheduleSyncRepeatingTask(
                this,
                new AutoSaveTask(this),
                5 * 60 * 20,
                60 * 20);

        // Register player first join listener
        getServer().getPluginManager().registerEvents(new PlayerAccountInitializationListener(this), this);

        getLogger().info("Tasks and listeners registered!");

        getLogger().info("VaultX loaded!");
    }

    private void loadConfig() {
        var autoSaveInterval = getConfig().getLong("autoSaveInterval", 6000);
        var startingBalance = getConfig().getDouble("startingBalance", 100000);
        var startingPremiumBalance = getConfig().getLong("startingPremiumBalance", 5);

        config = new VaultXConfiguration(
                autoSaveInterval,
                startingBalance,
                startingPremiumBalance
        );
    }

    private void loadVanillaEconomy() {
        economy = EconomyX.newVanillaInstance(this);
    }

    private void loadVXEconomy() {
        economy = EconomyX.newVXInstance(this);

        VaultLegacyLoader.registerVXtoVault(economy, this);
    }

    private void registerEconomyService() {
        getServer().getServicesManager().register(
                EconomyX.class,
                economy,
                this,
                ServicePriority.Normal
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling VaultX...");

        if (economy == null) {
            getLogger().warning("It looks like VaultX did not initialize properly! Shutting down immediately.");
            return;
        }

        try {
            economy.save();
            getLogger().info("Data saved to disk!");
        } catch (IOException e) {
            getLogger().warning("Error saving data: " + e.getMessage());
        }

        unregisterEconomyService();
        getLogger().info("Economy service unregistered!");

        legacyEnabled = false;
        getLogger().info("Transient variables reset!");

        getLogger().info("VaultX disabled!");
    }

    private void unregisterEconomyService() {
        getServer().getServicesManager().unregister(economy);
    }
}
