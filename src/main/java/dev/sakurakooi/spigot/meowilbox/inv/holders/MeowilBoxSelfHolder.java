package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxItemBuilder;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MeowilBoxSelfHolder implements MeowilBoxHolder {
    @Getter
    private Inventory inventory;

    @Getter
    private Player player;

    public MeowilBoxSelfHolder(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 36, Component.text(player.getName() + "'s Meowil box").decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));

        // 0-26
        // TODO load inventory
        inventory.setItem(0, new ItemStack(Material.DIAMOND)); // FIXME for test

        inventory.setItem(27, MeowilBoxItemBuilder.createPlayerListButton());
        inventory.setItem(28, MeowilBoxItemBuilder.createPaddingPane());
        inventory.setItem(29, MeowilBoxItemBuilder.createPaddingPane());
        if (hasPrevPage()) {
            inventory.setItem(30, MeowilBoxItemBuilder.createPrevPageButton());
        } else {
            inventory.setItem(30, MeowilBoxItemBuilder.createPageStopButton(false));
        }
        inventory.setItem(31, MeowilBoxItemBuilder.createPageButton(getCurrentPage()));
        if (hasNextPage()) {
            inventory.setItem(32, MeowilBoxItemBuilder.createNextPageButton());
        } else {
            inventory.setItem(32, MeowilBoxItemBuilder.createPageStopButton(true));
        }
        inventory.setItem(33, MeowilBoxItemBuilder.createPaddingPane());
        inventory.setItem(34, MeowilBoxItemBuilder.createPaddingPane());
        inventory.setItem(35, MeowilBoxItemBuilder.createPaddingPane());
    }

    private int getCurrentPage() {
        return 1;
    }

    private boolean hasPrevPage() {
        return true;
    }

    private boolean hasNextPage() {
        return false;
    }

    @Override
    public void saveData() {

    }
}
