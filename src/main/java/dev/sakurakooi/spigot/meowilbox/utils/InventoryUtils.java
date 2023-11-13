package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagEditor;
import com.saicone.rtag.item.ItemTagStream;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InventoryUtils {
    public static List<ItemStack> getItemContent(RtagEditor<?> tag) {
        List<Map<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_content");
        return nbtList.stream().map(ItemTagStream.INSTANCE::fromMap).collect(Collectors.toList());
    }

    public static void setItemContent(RtagEditor<?> tag, List<ItemStack> items) {
        List<Map<String, Object>> nbtList = items.stream().map(ItemTagStream.INSTANCE::toMap).toList();
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:item_content");
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, ItemStack> getInventory(RtagEditor<?> tag) {
        List<HashMap<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_inventory");
        return nbtList.stream().collect(Collectors.toMap(map -> (int)map.get("Slot"), map -> ItemTagStream.INSTANCE.fromMap((Map<String, Object>) map.get("Item"))));
    }

    public static void setInventory(RtagEditor<?> tag, Map<Integer, ItemStack> items) {
        List<HashMap<String, Object>> nbtList = items.entrySet().stream().map(entry -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("Slot", entry.getKey());
            map.put("Item", ItemTagStream.INSTANCE.toMap(entry.getValue()));
            return map;
        }).toList();
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:item_inventory");
    }


    public static List<ItemStack> inventoryToList(Inventory inventory, int start, int end) {
        return IntStream.range(start, end).boxed()
                .map(inventory::getItem)
                .filter(Objects::nonNull)
                .toList();
    }

    public static Map<Integer, ItemStack> inventoryToMap(Inventory inventory, int start, int end) {
        return IntStream.range(start, end).boxed()
                .filter(index -> inventory.getItem(index) != null)
                .collect(HashMap::new, (map, index) -> map.put(index, inventory.getItem(index)), HashMap::putAll);
    }
}
