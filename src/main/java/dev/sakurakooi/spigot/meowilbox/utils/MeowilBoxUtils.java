package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MeowilBoxUtils {
    public boolean isMeowilBox(ItemStack stack) {
        if (stack.getType() == Material.PLAYER_HEAD) {
            RtagItem tag = new RtagItem(stack);
            return tag.hasTag("PublicBukkitValues", "meowilbox:mailbox_mark");
        }
        return false;
    }

    public boolean isMeowilBoxPackage(ItemStack stack) {
        if (stack.getType() == Material.PLAYER_HEAD) {
            RtagItem tag = new RtagItem(stack);
            return tag.hasTag("PublicBukkitValues", "meowilbox:package_content");
        }
        return false;
    }
}
