package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxUI;
import dev.sakurakooi.spigot.meowilbox.storage.MailboxManager;
import dev.sakurakooi.spigot.meowilbox.utils.InventoryUtils;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class MeowilBoxOtherHolder extends MeowilBoxGuiHolder {
    private final MailboxManager.MeowilBoxStorage storage;
    @Getter
    private OfflinePlayer player;

    /*
    @Getter
    private int currentPage = 1;
    */
    public MeowilBoxOtherHolder(MailboxManager.MeowilBoxStorage storage, OfflinePlayer player) {
        this.storage = storage;
        this.player = player;
        this.postInitialize();
        setCurrentPage(1);
    }

    public void setCurrentPage(int page) {
        // this.currentPage = currentPage;

        for (int i = 0, len = Math.min(storage.getContents().size(), 27); i < len; i++) {
            getInventory().setItem(i, storage.getContents().get(i));
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
        storage.setContents(InventoryUtils.inventoryToList(getInventory(), 0, 27));// FIXME support page
        storage.save();
    }

    @Override
    public Component getInventoryTitle() {
        return Component.text(player.getName() + " 的喵箱");
    }

    @Override
    public ItemStack fillCustomButton(int slot) {
        if (slot == 27)
            return ItemBuilder.createPlayerListButton();
        if (slot == 28)
            return ItemBuilder.createSendButton();

        return null;
    }

    @Override
    public boolean handleButtonClick(@NotNull Player player, int slot) {
        if (slot == 27) {
            MeowilBoxUI.openPlayerListFor(player);
            return true;
        }
        if (slot == 28) {
            MeowilBoxUI.openSendMailFor(player, this.player);
            return true;
        }
        return false;
    }

    @Override
    public boolean canPickup(int slot) {
        return false;
    }

    @Override
    public boolean canPlaceAt(int slot) {
        return false;
    }
}
