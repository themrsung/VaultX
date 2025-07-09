package com.themrsung.mc;

import com.themrsung.mc.economy.EconomyVX;
import com.themrsung.mc.economy.EconomyX;
import com.themrsung.mc.task.AutoSaveTask;
import net.milkbowl.vault.economy.Economy;
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

    /**
     * Returns the {@link EconomyX economy}.
     *
     * @return The economy
     */
    public @NotNull EconomyX getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading VaultX...");

        // Try to find legacy Vault API
        var legacyVault = Bukkit.getPluginManager().getPlugin("Vault");
        if (legacyVault != null && legacyVault.isEnabled()) {
            loadVXEconomy(); // Load Vault-compatible economy if found
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

        getLogger().info("VaultX loaded!");
    }

    private void loadVanillaEconomy() {
        economy = EconomyX.newVanillaInstance(this);

        getServer().getServicesManager().register(
                Economy.class,
                (EconomyVX) economy,
                this,
                ServicePriority.Normal
        );
    }

    private void loadVXEconomy() {
        economy = EconomyX.newVXInstance(this);
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

        try {
            economy.save();
            getLogger().info("Data saved to disk!");
        } catch (IOException e) {
            getLogger().warning("Error saving data: " + e.getMessage());
        }

        if (economy instanceof EconomyVX vx) {
            getServer().getServicesManager().unregister(vx);
            getLogger().info("VX economy unregistered!");
        }

        getLogger().info("VaultX disabled!");
    }
}
