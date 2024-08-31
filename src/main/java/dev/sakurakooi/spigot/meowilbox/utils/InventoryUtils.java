package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagEditor;
import com.saicone.rtag.RtagMirror;
import com.saicone.rtag.item.ItemTagStream;
import com.saicone.rtag.tag.TagBase;
import com.saicone.rtag.tag.TagCompound;
import com.saicone.rtag.tag.TagList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InventoryUtils {
    public static List<ItemStack> getItemContent(RtagEditor<?, ?> tag) {
        List<Map<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_content");
        return nbtList.stream().map(ItemTagStream.INSTANCE::fromMap).collect(Collectors.toList());
    }

    public static void setItemContent(RtagEditor<?, ?> tag, List<ItemStack> items) {
        List<Map<String, Object>> nbtList = items.stream().map(ItemTagStream.INSTANCE::toMap).toList();
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:item_content");
        tag.set(DfuUtils.getDataVersion(), "PublicBukkitValues", "meowilbox:data_version");
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, ItemStack> getInventory(RtagEditor<?, ?> tag) {
        List<HashMap<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_inventory");
        return nbtList.stream().collect(Collectors.toMap(map -> (int) map.get("Slot"), map -> ItemTagStream.INSTANCE.fromMap((Map<String, Object>) map.get("Item"))));
    }

    public static void setInventory(RtagEditor<?, ?> tag, Map<Integer, ItemStack> items) {
        List<HashMap<String, Object>> nbtList = items.entrySet().stream().map(entry -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("Slot", entry.getKey());
            map.put("Item", ItemTagStream.INSTANCE.toMap(entry.getValue()));
            return map;
        }).toList();
        tag.set(nbtList, "PublicBukkitValues", "meowilbox:item_inventory");
        tag.set(DfuUtils.getDataVersion(), "PublicBukkitValues", "meowilbox:data_version");
    }


    public static ArrayList<ItemStack> inventoryToList(Inventory inventory, int start, int end) {
        return IntStream.range(start, end).boxed()
                .map(inventory::getItem)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Map<Integer, ItemStack> inventoryToMap(Inventory inventory, int start, int end) {
        return IntStream.range(start, end).boxed()
                .filter(index -> inventory.getItem(index) != null)
                .collect(HashMap::new, (map, index) -> map.put(index, inventory.getItem(index)), HashMap::putAll);
    }

    public static void checkAndUpdateData(RtagEditor<?, ?> tag) {
        int version;
        if (tag.hasTag("PublicBukkitValues", "meowilbox:data_version")) {
            version = tag.get("PublicBukkitValues", "meowilbox:data_version");
        } else {
            version = 3465; // as we target 1.20.1 before
        }

        if (version < DfuUtils.getDataVersion()) {
            if (tag.hasTag("PublicBukkitValues", "meowilbox:item_content")) {
                List<Map<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_content");
                List<Map<String, Object>> updatedList = nbtList.stream()
                        .map(map -> TagCompound.newTag(RtagMirror.INSTANCE, map))
                        .map(nbtTag -> DfuUtils.update(nbtTag, version))
                        .map(nbtTag -> TagCompound.getValue(RtagMirror.INSTANCE, nbtTag))
                        .toList();
                tag.set(updatedList, "PublicBukkitValues", "meowilbox:item_content");
            } else if (tag.hasTag("PublicBukkitValues", "meowilbox:item_inventory")) {
                List<HashMap<String, Object>> nbtList = tag.get("PublicBukkitValues", "meowilbox:item_inventory");
                var inventory = nbtList.stream()
                        .collect(Collectors.toMap(map -> (int) map.get("Slot"), map -> TagCompound.newTag(RtagMirror.INSTANCE, map.get("Item"))));

                List<HashMap<String, Object>> updatedList = inventory.entrySet().stream()
                        .peek(entry -> entry.setValue(DfuUtils.update(entry.getValue(), version)))
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> TagCompound.getValue(RtagMirror.INSTANCE, entry.getValue())))
                        .entrySet().stream().map(entry -> {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Slot", entry.getKey());
                            map.put("Item", entry.getValue());
                            return map;
                        }).toList();
                tag.set(updatedList, "PublicBukkitValues", "meowilbox:item_inventory");
            }
            tag.set(DfuUtils.getDataVersion(), "PublicBukkitValues", "meowilbox:data_version");
        }
    }

    public static void checkAndUpdateData(Object tagCompound) {
        int version = 3465;
        if (TagCompound.hasKey(tagCompound, "data_version")) {
            version = (int) TagBase.getValue(TagCompound.get(tagCompound, "data_version"));
        }

        if (version < DfuUtils.getDataVersion()) {
            Object list = TagCompound.get(tagCompound, "meowilbox");
            int count = TagList.size(list);
            for (int i = 0; i < count; i++) {
                var nbtItem = TagList.get(list, i);
                var updatedNbtItem = DfuUtils.update(nbtItem, version);
                TagList.set(list, i, updatedNbtItem);
            }

            TagCompound.set(tagCompound, "data_version", TagBase.newTag(DfuUtils.getDataVersion()));
        }
    }
}
