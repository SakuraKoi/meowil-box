package dev.sakurakooi.spigot.meowilbox.inv;

import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSelfHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MeowilBoxUI {
    public static void openPetalsInventory(Player player, ItemStack petalItem) {
        var holder = new MeowilBoxPetalHolder(player, petalItem);
        player.openInventory(holder.getInventory());
    }

    public static void openMailBox(Player player) {
        var holder = new MeowilBoxSelfHolder(player);
        player.openInventory(holder.getInventory());
    }
}
