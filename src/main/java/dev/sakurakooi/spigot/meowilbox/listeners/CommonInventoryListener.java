package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CommonInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof MeowilBoxHolder holder) {
            holder.saveData();
        }
    }
}
