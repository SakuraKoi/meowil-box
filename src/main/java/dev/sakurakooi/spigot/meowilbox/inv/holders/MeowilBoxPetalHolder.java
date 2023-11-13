package dev.sakurakooi.spigot.meowilbox.inv.holders;

import com.saicone.rtag.RtagItem;
import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxInventoryUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.stream.IntStream;

public class MeowilBoxPetalHolder implements MeowilBoxHolder {
    @Setter
    private Inventory inventory;
    @Getter
    @Setter
    private ItemStack petalItem;

    @Override
    public void saveData() {
        RtagItem.edit(petalItem, tag -> {
            MeowilBoxInventoryUtils.setInventory(tag, IntStream.range(0, inventory.getSize()).boxed()
                    .collect(HashMap::new, (m, v) -> m.put(v, inventory.getItem(v)), HashMap::putAll)
            );
        });
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
