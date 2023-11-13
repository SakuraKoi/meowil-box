package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSelfHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class SelfMailboxInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickSelf(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxSelfHolder holder) {
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof MeowilBoxSelfHolder) {
                if (e.getSlot() >= 27) {
                    handleButtonClick(e.getView().getPlayer(), e.getSlot(), holder);
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

    private void handleButtonClick(HumanEntity player, int slot, MeowilBoxSelfHolder holder) {
        // TODO unimplemented
    }
}
