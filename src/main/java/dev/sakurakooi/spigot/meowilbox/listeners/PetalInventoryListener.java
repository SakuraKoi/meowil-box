package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PetalInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickPetal(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxPetalHolder holder) {
            if (MeowilBoxUtils.isMeowilBoxPetals(e.getCurrentItem())) {
                e.setCancelled(true);
                return;
            }
            if (e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP) {
                if (MeowilBoxUtils.isMeowilBoxPetals(e.getView().getBottomInventory().getItem(e.getHotbarButton()))) {
                    e.setCancelled(true);
                    return;
                }
            }
            Bukkit.getScheduler().runTaskLater(MeowilBox.getInstance(), holder::saveData, 1); // FIXME: potential item duplicate bug? needs check
        }
    }
}
