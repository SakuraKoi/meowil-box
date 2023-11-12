package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagEditor;
import com.saicone.rtag.item.ItemTagStream;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MeowilBoxInventoryUtils {
    public static List<ItemStack> getInventory(RtagEditor<?> tag) {
        List<Map<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:inv_content");
        return nbtList.stream().map(ItemTagStream.INSTANCE::fromMap).collect(Collectors.toList());
    }

    public static void setInventory(RtagEditor<?> tag, List<ItemStack> items) {
        List<Map<String, Object>> nbtList = items.stream().map(ItemTagStream.INSTANCE::toMap).toList();
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:inv_content");
    }
}
