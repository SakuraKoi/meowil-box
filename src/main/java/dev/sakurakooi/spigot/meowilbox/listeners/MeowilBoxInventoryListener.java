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
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class MeowilBoxInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClickSelf(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxSelfHolder) {
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof MeowilBoxSelfHolder) {
                if (e.getSlot() >= 27) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMoveSelf(InventoryMoveItemEvent e) {
        if (e.getDestination().getHolder() instanceof MeowilBoxSelfHolder) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClickPetal(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxPetalHolder) {
            if (MeowilBoxUtils.isMeowilBoxPetals(e.getCurrentItem())) {
                e.setCancelled(true);
            } else if (e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP) {
                if (MeowilBoxUtils.isMeowilBoxPetals(e.getView().getBottomInventory().getItem(e.getHotbarButton()))) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMovePetal(InventoryMoveItemEvent e) {
        MeowilBoxPetalHolder holder = null;
        if (e.getSource().getHolder() instanceof MeowilBoxPetalHolder) {
            holder = (MeowilBoxPetalHolder) e.getSource().getHolder();
        }
        if (e.getDestination().getHolder() instanceof MeowilBoxPetalHolder) {
            holder = (MeowilBoxPetalHolder) e.getDestination().getHolder();
        }

        if (holder != null) {
            if (MeowilBoxUtils.isMeowilBoxPetals(e.getItem())) {
                e.setCancelled(true);
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
