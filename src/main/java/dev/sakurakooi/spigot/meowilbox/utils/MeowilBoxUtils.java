package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagBlock;
import com.saicone.rtag.RtagItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MeowilBoxUtils {
    public static boolean isMeowilBox(ItemStack stack) {
        if (stack == null)
            return false;
        if (stack.getType() == Material.PLAYER_HEAD) {
            RtagItem tag = new RtagItem(stack);
            return tag.hasTag("PublicBukkitValues", "meowilbox:mailbox_mark");
        }
        return false;
    }

    public static boolean isMeowilBoxPackage(ItemStack stack) {
        if (stack == null)
            return false;
        if (stack.getType() == Material.PLAYER_HEAD) {
            RtagItem tag = new RtagItem(stack);
            return tag.hasTag("PublicBukkitValues", "meowilbox:package_mark");
        }
        return false;
    }

    public static boolean isMeowilBoxPetals(ItemStack stack) {
        if (stack == null)
            return false;
        if (stack.getType() == Material.PLAYER_HEAD) {
            RtagItem tag = new RtagItem(stack);
            return tag.hasTag("PublicBukkitValues", "meowilbox:petals_mark");
        }
        return false;
    }

    public static int getMeowilBoxPetalsSize(ItemStack stack) {
        Objects.requireNonNull(stack);
        if (stack.getType() == Material.PLAYER_HEAD) {
            RtagItem tag = new RtagItem(stack);
            return Objects.requireNonNullElse(tag.get("PublicBukkitValues", "meowilbox:petals_size"), 27);
        }
        throw new IllegalArgumentException("stack is not a MeowilBoxPetals");
    }

    public static boolean isMeowilBox(Block block) {
        if (block == null)
            return false;
        if (block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
            RtagBlock tag = new RtagBlock(block);
            return tag.hasTag("PublicBukkitValues", "meowilbox:mailbox_mark");
        }
        return false;
    }

    public static boolean isMeowilBoxPackage(Block block) {
        if (block == null)
            return false;
        if (block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
            RtagBlock tag = new RtagBlock(block);
            return tag.hasTag("PublicBukkitValues", "meowilbox:package_mark");
        }
        return false;
    }
}
