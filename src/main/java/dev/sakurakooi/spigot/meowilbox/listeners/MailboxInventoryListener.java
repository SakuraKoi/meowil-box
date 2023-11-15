package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxGuiHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSelfHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import static org.bukkit.event.inventory.InventoryAction.*;

public class MailboxInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickSelf(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxGuiHolder holder) {
            // TODO potential bug: check MOVE_TO_OTHER_INVENTORY while clicked self inv
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof MeowilBoxGuiHolder) {
                if (holder.handleButtonClick(e.getSlot())) {
                    e.setCancelled(true);
                    return;
                }

                InventoryAction action = e.getAction();
                if (action == PICKUP_ALL || action == PICKUP_SOME || action == PICKUP_HALF || action == PICKUP_ONE || action == DROP_ALL_SLOT || action == DROP_ONE_SLOT || action == MOVE_TO_OTHER_INVENTORY || action == COLLECT_TO_CURSOR) {
                    if (!holder.canPickup(e.getSlot())) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (action == PLACE_ALL || action == PLACE_SOME || action == PLACE_ONE || action == SWAP_WITH_CURSOR) {
                    e.setCancelled(true);
                    return;
                }
                if ((action == HOTBAR_SWAP || action == HOTBAR_MOVE_AND_READD) && e.getView().getBottomInventory().getItem(e.getHotbarButton()) != null) {
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
}
