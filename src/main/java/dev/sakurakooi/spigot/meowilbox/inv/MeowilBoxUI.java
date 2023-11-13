package dev.sakurakooi.spigot.meowilbox.inv;

import com.saicone.rtag.RtagItem;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxPetalHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxInventoryUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MeowilBoxUI {
    public static void openPetalsInventory(Player player, ItemStack petalItem) {
        RtagItem tag = RtagItem.of(petalItem);
        var items = MeowilBoxInventoryUtils.getInventory(tag);
        var holder = new MeowilBoxPetalHolder();
        var inventory = Bukkit.createInventory(holder, 27, Component.text("Petals"));
        holder.setInventory(inventory);
        holder.setPetalItem(petalItem);
        items.forEach(inventory::setItem);
        player.openInventory(inventory);
        // TODO handle inv click in inv listener
    }

    public static void openMailBox(Player player) {
        
    }
}
