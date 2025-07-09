package com.themrsung.mc.task;

import com.themrsung.mc.VaultX;

import java.io.IOException;

/**
 * Handles autosave.
 */
public class AutoSaveTask implements Runnable {
    public AutoSaveTask(VaultX vx) {
        this.plugin = vx;
    }

    private final VaultX plugin;

    @Override
    public void run() {
        try {
            plugin.getEconomy().save();
            plugin.getLogger().info("[VaultX] Autosaved data to disk!");
        } catch (IOException e) {
            plugin.getLogger().warning("[VaultX] Error saving data: " + e.getMessage());
        }
    }
}
