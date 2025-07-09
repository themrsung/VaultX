package com.themrsung.mc.util;

import com.themrsung.mc.VaultX;
import com.themrsung.mc.economy.EconomyVX;
import com.themrsung.mc.economy.EconomyX;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;

/**
 * Loader class for VX.
 */
public abstract class VaultLegacyLoader {
    /**
     * Registers economy to server.
     * @param economy The economy
     * @param vx The plugin instance
     */
    public static void registerVXtoVault(EconomyX economy, VaultX vx) {
        vx.getServer().getServicesManager().register(
                Economy.class,
                (EconomyVX) economy,
                vx,
                ServicePriority.Normal
        );
    }

    /**
     * Prevents instantiation.
     * @throws Exception Always
     */
    private VaultLegacyLoader() throws Exception {
        throw new Exception("Cannot instantiate utility class.");
    }
}
