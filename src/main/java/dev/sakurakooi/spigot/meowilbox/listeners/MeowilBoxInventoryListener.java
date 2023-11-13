package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSelfHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class MeowilBoxInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickSelf(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxSelfHolder holder) {
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof MeowilBoxSelfHolder) {
                if (e.getSlot() >= 27) {
                    e.setCancelled(true);
                    return;
                }

                InventoryAction action = e.getAction();
                if (action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_SOME || action == InventoryAction.PLACE_ONE || action == InventoryAction.SWAP_WITH_CURSOR) {
                    e.setCancelled(true);
                    return;
                }
                if ((action == InventoryAction.HOTBAR_SWAP || action == InventoryAction.HOTBAR_MOVE_AND_READD) && e.getView().getBottomInventory().getItem(e.getHotbarButton()) != null) {
                    e.setCancelled(true);
                    return;
                }
                Bukkit.getScheduler().runTaskLater(MeowilBox.getInstance(), holder::saveData, 1);
            }
        }
    }

    @EventHandler
    public void onInventoryDragSelf(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof MeowilBoxSelfHolder) {
            e.setCancelled(true);
        }
    }

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

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof MeowilBoxHolder holder) {
            holder.saveData();
        }
    }
}
