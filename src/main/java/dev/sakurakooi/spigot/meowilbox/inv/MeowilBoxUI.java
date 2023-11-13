package dev.sakurakooi.spigot.meowilbox.inv;

import com.saicone.rtag.RtagItem;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxInventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MeowilBoxUI {
    public static void openPetalsInventory(Player player, ItemStack petalItem) {
        RtagItem tag = RtagItem.of(petalItem);
        var items = MeowilBoxInventoryUtils.getItemContent(tag);
        // TODO unimplemented
    }
}
