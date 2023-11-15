package dev.sakurakooi.spigot.meowilbox.inv;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPlayerListHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSendHolder;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ExecutionException;

@Slf4j
public class MeowilBoxUI {
    public static void openPetalsInventory(Player player, int heldItemSlot, ItemStack petalItem) {
        var holder = new MeowilBoxPetalHolder(player, heldItemSlot, petalItem);
        player.openInventory(holder.getInventory());
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1f, 1f);
    }

    public static void openMailBox(Player player) {
        try {
            var holder = MeowilBox.getMailboxManager().getMailbox(player).getHolder();
            holder.setCurrentPage(1);
            player.openInventory(holder.getInventory());
        } catch (ExecutionException e) {
            log.error("An error occurred while loading storage", e);
            player.sendMessage(Component.text("Error: MeowilBox failed load storage!").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        }
    }

    public static void openPlayerListFor(Player player) {
        var holder = new MeowilBoxPlayerListHolder();
        player.openInventory(holder.getInventory());
        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
    }

    public static void openOtherMailBox(Player player, OfflinePlayer offlinePlayer) {
        try {
            var holder = MeowilBox.getMailboxManager().getMailbox(offlinePlayer).getOtherHolder();
            holder.updatePage();
            player.openInventory(holder.getInventory());
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
        } catch (ExecutionException e) {
            log.error("An error occurred while loading storage", e);
            player.sendMessage(Component.text("Error: MeowilBox failed load storage!").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        }
    }

    public static void openSendMailFor(Player player, OfflinePlayer targetPlayer) {
        var holder = new MeowilBoxSendHolder(player, targetPlayer);
        player.openInventory(holder.getInventory());
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1f, 1f);
    }
}
