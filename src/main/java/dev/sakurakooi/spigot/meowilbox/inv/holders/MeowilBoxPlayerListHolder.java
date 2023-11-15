package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxUI;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class MeowilBoxPlayerListHolder extends MeowilBoxGuiHolder {
    private final ArrayList<OfflinePlayer> players;
    private int page = 0;

    public MeowilBoxPlayerListHolder() {
        players = new ArrayList<>(Arrays.asList(Bukkit.getOfflinePlayers()));
        postInitialize();
    }

    @Override
    public void saveData() {

    }

    @Override
    public void postInitialize() {
        super.postInitialize();
        setCurrentPage(0);
    }

    private void setCurrentPage(int page) {
        this.page = page;
        updatePage();
    }

    @Override
    public Component getInventoryTitle() {
        return Component.text("喵窝").color(NamedTextColor.BLUE).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public void updatePage() {
        getInventory().setItem(33, page > 0 ? ItemBuilder.createPrevPageButton() : ItemBuilder.createPageStopButton(false));
        getInventory().setItem(34, ItemBuilder.createPageButton(page + 1));
        getInventory().setItem(35, page < (players.size() / 27) ? ItemBuilder.createNextPageButton() : ItemBuilder.createPageStopButton(true));

        int start = page * 27;
        int end = Math.min(start + 27, players.size());
        for (int i = start; i < end; i++) {
            getInventory().setItem(i - start, ItemBuilder.createPlayerHead(players.get(i)));
        }
    }

    @Override
    public ItemStack fillCustomButton(int slot) {
        if (slot == 27)
            return ItemBuilder.createMyMailboxButton();
        // set 33 & 34 & 35 in setCurrentPage
        return null;
    }

    @Override
    public boolean handleButtonClick(@NotNull Player player, int slot) {
        if (slot < 27 ) {
            if (slot + page * 27 < players.size()) {
                MeowilBoxUI.openOtherMailBox(player, players.get(slot + page * 27));
                return true;
            }
        }
        if (slot == 27) {
            MeowilBoxUI.openMailBox(player, false);
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            return true;
        }
        if (slot == 33) {
            if (page > 0) {
                setCurrentPage(page - 1);
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            }
            return true;
        }
        if (slot == 35) {
            if (page < (players.size() / 27)) {
                setCurrentPage(page + 1);
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            }
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
