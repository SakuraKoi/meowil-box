package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagEditor;
import com.saicone.rtag.item.ItemTagStream;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MeowilBoxInventoryUtils {
    public static List<ItemStack> getItemContent(RtagEditor<?> tag) {
        List<Map<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_content");
        return nbtList.stream().map(ItemTagStream.INSTANCE::fromMap).collect(Collectors.toList());
    }

    public static void setItemContent(RtagEditor<?> tag, List<ItemStack> items) {
        List<Map<String, Object>> nbtList = items.stream().map(ItemTagStream.INSTANCE::toMap).toList();
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:item_content");
    }

    public static Map<Integer, ItemStack> getInventory(RtagEditor<?> tag) {
        Map<Integer, Map<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_inventory");
        return nbtList.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ItemTagStream.INSTANCE.fromMap(e.getValue())));
    }

    public static void setInventory(RtagEditor<?> tag, Map<Integer, ItemStack> items) {
        Map<Integer, Map<String, Object>> nbtList = items.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ItemTagStream.INSTANCE.toMap(e.getValue())));
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:item_inventory");
    }
}
