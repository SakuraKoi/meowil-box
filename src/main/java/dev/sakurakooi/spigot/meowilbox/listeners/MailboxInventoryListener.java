package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxGuiHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

import static org.bukkit.event.inventory.InventoryAction.*;

public class MailboxInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClickSelf(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof MeowilBoxGuiHolder holder) {
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
                        if (e.getCursor().getType() == Material.CHERRY_SAPLING && e.getCursor().getAmount() % 9 == 0 && e.getCursor().getAmount() <= 54) {
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
        }

        checkBlacklistPlace(e, () -> e.getView().getTopInventory().getHolder() instanceof MeowilBoxSendHolder,
                () -> e.getClickedInventory().getHolder() instanceof MeowilBoxSendHolder);
        checkBlacklistPlace(e, () -> e.getView().getTopInventory().getHolder() instanceof MeowilBoxPetalHolder,
                () -> e.getClickedInventory().getHolder() instanceof MeowilBoxPetalHolder);
        checkBlacklistPlace(e, () -> e.getView().getTopInventory().getType() == InventoryType.SHULKER_BOX,
                () -> e.getClickedInventory().getType() == InventoryType.SHULKER_BOX);
    }

    private void checkBlacklistPlace(InventoryClickEvent e, Supplier<Boolean> applyCheckCondition, Supplier<Boolean> currentInventoryCondition) {
        if (applyCheckCondition.get()) {
            InventoryAction action = e.getAction();
            if (e.getClickedInventory() != null && currentInventoryCondition.get()) {
                if (action == PLACE_ALL || action == PLACE_SOME || action == PLACE_ONE || action == SWAP_WITH_CURSOR) {
                    if (checkBlacklistItemPlace(e.getCursor())) {
                        e.setCancelled(true);
                        return;
                    }
                }

                if ((action == HOTBAR_SWAP || action == HOTBAR_MOVE_AND_READD)) {
                    var hotbarItem = e.getView().getBottomInventory().getItem(e.getHotbarButton());
                    if (hotbarItem != null && checkBlacklistItemPlace(hotbarItem)) {
                        e.setCancelled(true);
                        return;
                    }
                }
            } else if (e.getAction() == MOVE_TO_OTHER_INVENTORY) {
                if (e.getCurrentItem() != null && checkBlacklistItemPlace(e.getCurrentItem())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    private boolean checkBlacklistItemPlace(ItemStack cursor) {
        return cursor.getType().name().endsWith("SHULKER_BOX") || MeowilBoxUtils.isMeowilBoxPackage(cursor) || MeowilBoxUtils.isMeowilBoxPetals(cursor);
    }

    @EventHandler
    public void onInventoryDragSelf(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof MeowilBoxGuiHolder holder) {
            if (e.getInventorySlots().stream().anyMatch(slot -> !holder.canPlaceAt(slot))) {
                e.setCancelled(true);
                return;
            }
        }

        List<Inventory> inventoryList = e.getRawSlots().stream().map(slot -> e.getView().getInventory(slot)).distinct().toList();
        for (Inventory inv : inventoryList) {
             if (inv.getHolder() instanceof MeowilBoxSendHolder || inv.getHolder() instanceof MeowilBoxPetalHolder ||
                     inv.getType() == InventoryType.SHULKER_BOX) {
                if (e.getCursor() != null && checkBlacklistItemPlace(e.getCursor())) {
                    e.setCancelled(true);
                    return;
                }

                if (e.getNewItems().values().stream().anyMatch(this::checkBlacklistItemPlace)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
