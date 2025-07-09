package com.themrsung.mc.listener;

import com.themrsung.mc.VaultX;
import com.themrsung.mc.economy.VXPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listens for first join of a player, and initializes their VX account.
 */
public class PlayerAccountInitializationListener implements Listener {
    /**
     * Creates a new listener.
     *
     * @param vx The plugin
     */
    public PlayerAccountInitializationListener(VaultX vx) {
        this.plugin = vx;
    }

    private final VaultX plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        var existing = plugin.getEconomy().getAccount(player.getUniqueId());

        if (existing != null) return; // Player exists, do nothing

        var account = VXPlayer.from(player);
        if (!plugin.getEconomy().addAccount(account)) {
            plugin.getLogger().warning("[VaultX] Failed to create account for " + player.getName() + ".");
            return;
        }

        plugin.getEconomy().issueBalance(account, plugin.getConfig().getDouble("startingBalance", 100000));
        plugin.getEconomy().issuePremiumBalance(account, plugin.getConfig().getLong("startingPremiumBalance", 5));
    }
}
