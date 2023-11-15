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

    /*
    @Getter
    private int currentPage = 1;
    */
    public MeowilBoxSelfHolder(MailboxManager.MeowilBoxStorage storage, OfflinePlayer player) {
        this.storage = storage;
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 36, Component.text(player.getName() + "'s Meowil box").decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));

        setCurrentPage(1);

        for (int i = 27; i < 36; i++) {
            if (i == 27) {
                inventory.setItem(i, ItemBuilder.createPlayerListButton());
            } else {
                inventory.setItem(i, ItemBuilder.createPaddingPane());
            }
        }
    }

    public void setCurrentPage(int page) {
        // this.currentPage = currentPage;

        for (int i = 0, len = Math.min(storage.getContents().size(), 27); i < len; i++) {
            inventory.setItem(i, storage.getContents().get(i));
        }

        /* 翻页先不写了xxx 处理物品存储太麻烦x
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
        }*/
    }

    /*
    private boolean hasPrevPage() {
        return currentPage > 1;
    }

    private boolean hasNextPage() {
        return storage.getContents().size() > currentPage * 27;
    }
    */
    @Override
    public void saveData() {
        storage.setContents(InventoryUtils.inventoryToList(inventory, 0, 27));// FIXME support page
        storage.save();
    }

    public void handleButtonClick(int slot) {
        if (slot == 27) {

        }
        // TODO unimplemented
    }
}
