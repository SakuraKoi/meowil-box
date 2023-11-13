package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.storage.MailboxManager;
import dev.sakurakooi.spigot.meowilbox.utils.InventoryUtils;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;

@Slf4j
public class MeowilBoxSelfHolder implements MeowilBoxHolder {

    private final MailboxManager.MeowilBoxStorage storage;
    @Getter
    private OfflinePlayer player;

    @Getter
    private Inventory inventory;

    public MeowilBoxSelfHolder(MailboxManager.MeowilBoxStorage storage, OfflinePlayer player) {
        this.storage = storage;
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 36, Component.text(player.getName() + "'s Meowil box").decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));

        // 0-26
        // TODO load inventory

        inventory.setItem(27, ItemBuilder.createPlayerListButton());
        inventory.setItem(28, ItemBuilder.createPaddingPane());
        inventory.setItem(29, ItemBuilder.createPaddingPane());
        if (hasPrevPage()) {
            inventory.setItem(30, ItemBuilder.createPrevPageButton());
        } else {
            inventory.setItem(30, ItemBuilder.createPageStopButton(false));
        }
        inventory.setItem(31, ItemBuilder.createPageButton(getCurrentPage()));
        if (hasNextPage()) {
            inventory.setItem(32, ItemBuilder.createNextPageButton());
        } else {
            inventory.setItem(32, ItemBuilder.createPageStopButton(true));
        }
        inventory.setItem(33, ItemBuilder.createPaddingPane());
        inventory.setItem(34, ItemBuilder.createPaddingPane());
        inventory.setItem(35, ItemBuilder.createPaddingPane());
    }

    private int getCurrentPage() {
        // TODO unimplemented
        return 1;
    }

    private boolean hasPrevPage() {
        // TODO unimplemented
        return true;
    }

    private boolean hasNextPage() {
        // TODO unimplemented
        return false;
    }

    @Override
    public void saveData() {
        storage.setContents(InventoryUtils.inventoryToList(inventory, 0, 27));
        storage.save();
    }

    public void handleButtonClick(int slot) {
        // TODO unimplemented
    }
}
