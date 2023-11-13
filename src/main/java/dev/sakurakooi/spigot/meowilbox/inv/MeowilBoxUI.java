package dev.sakurakooi.spigot.meowilbox.inv;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ExecutionException;

@Slf4j
public class MeowilBoxUI {
    public static void openPetalsInventory(Player player, int heldItemSlot, ItemStack petalItem) {
        var holder = new MeowilBoxPetalHolder(player, heldItemSlot, petalItem);
        player.openInventory(holder.getInventory());
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
}
