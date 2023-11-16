package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxGuiHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPlayerListHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSendHolder;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.event.inventory.InventoryAction.*;

public class MailboxInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickSelf(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxGuiHolder holder) {
            // TODO potential bug: check MOVE_TO_OTHER_INVENTORY while clicked self inv
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof MeowilBoxGuiHolder) {
                if (holder.handleButtonClick((Player) e.getView().getPlayer(), e.getSlot())) {
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
                    if (action == SWAP_WITH_CURSOR && e.getView().getTopInventory().getHolder() instanceof MeowilBoxPlayerListHolder && e.getSlot() == 34) {
                        if (e.getCursor() != null && e.getCursor().getType() == Material.CHERRY_SAPLING && e.getCursor().getAmount() % 9 == 0) {
                            e.setCancelled(true);
                            e.setCursor(ItemBuilder.createPetals(Math.max(1, e.getCursor().getAmount() / 9)));
                            return;
                        }
                    }
                    if (!holder.canPlaceAt(e.getSlot())) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if ((action == HOTBAR_SWAP || action == HOTBAR_MOVE_AND_READD) && e.getView().getBottomInventory().getItem(e.getHotbarButton()) != null) {
                    e.setCancelled(true);
                    return;
                }
                Bukkit.getScheduler().runTaskLater(MeowilBox.getInstance(), holder::saveData, 1);
            } else if (e.getAction() == MOVE_TO_OTHER_INVENTORY) {
                e.setCancelled(true);
            }
        } else if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxSendHolder holder) {
            InventoryAction action = e.getAction();
            System.out.println(action);
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof MeowilBoxSendHolder) {
                if (action == PLACE_ALL || action == PLACE_SOME || action == PLACE_ONE || action == SWAP_WITH_CURSOR) {
                    if (checkSendItem(e.getCursor())) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (action == HOTBAR_SWAP || action == HOTBAR_MOVE_AND_READD) {
                    var hotbarItem = e.getView().getBottomInventory().getItem(e.getHotbarButton());
                    if (hotbarItem != null && checkSendItem(hotbarItem)) {
                        e.setCancelled(true);
                        return;
                    }
                }
            } else {
                if (action == MOVE_TO_OTHER_INVENTORY) {
                    if (e.getCurrentItem() != null && checkSendItem(e.getCurrentItem())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    private boolean checkSendItem(ItemStack cursor) {
        return cursor.getType() == Material.SHULKER_BOX || MeowilBoxUtils.isMeowilBoxPackage(cursor);
    }

    @EventHandler
    public void onInventoryDragSelf(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof MeowilBoxGuiHolder holder) {
            if (e.getInventorySlots().stream().anyMatch(slot -> !holder.canPlaceAt(slot)))
                e.setCancelled(true);
        } else if (e.getInventory().getHolder() instanceof MeowilBoxSendHolder) {
            if (e.getCursor() != null && checkSendItem(e.getCursor())) {
                e.setCancelled(true);
            } else if (e.getNewItems().values().stream().anyMatch(this::checkSendItem)) {
                e.setCancelled(true);
            }
        }
    }
}
