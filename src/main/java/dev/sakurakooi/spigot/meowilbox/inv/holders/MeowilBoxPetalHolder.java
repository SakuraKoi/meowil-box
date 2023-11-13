package dev.sakurakooi.spigot.meowilbox.inv.holders;

import com.saicone.rtag.RtagItem;
import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxInventoryUtils;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.stream.IntStream;

public class MeowilBoxPetalHolder implements MeowilBoxHolder {
    @Getter
    private Inventory inventory;

    private final Player player;
    private final int heldItemSlot;
    @Getter
    @Setter
    private ItemStack petalItem;

    public MeowilBoxPetalHolder(Player player, int heldItemSlot, ItemStack petalItem) {
        this.player = player;
        this.heldItemSlot = heldItemSlot;
        this.petalItem = petalItem;
        this.inventory = Bukkit.createInventory(this, 27, Component.text("Petals")); // FIXME TEXT
        RtagItem tag = RtagItem.of(petalItem);
        var items = MeowilBoxInventoryUtils.getInventory(tag);
        items.forEach(inventory::setItem);
    }

    @Override
    public void saveData() {
        RtagItem.edit(petalItem, tag -> {
            MeowilBoxInventoryUtils.setInventory(tag, IntStream.range(0, inventory.getSize()).boxed()
                    .filter(index -> inventory.getItem(index) != null)
                    .collect(HashMap::new, (map, index) -> map.put(index, inventory.getItem(index)), HashMap::putAll)
            );
        });
        player.getInventory().setItem(heldItemSlot, petalItem);
    }
}
