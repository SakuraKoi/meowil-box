package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Slf4j
public abstract class MeowilBoxGuiHolder implements MeowilBoxHolder {
    @Getter
    private Inventory inventory;

    public MeowilBoxGuiHolder() {
        this.inventory = Bukkit.createInventory(this, 36, getInventoryTitle());

    }

    public abstract Component getInventoryTitle();

    public void fillInventoryBar() {
        for (int i = 27; i < 36; i++) {
            var item = fillCustomButton(i);
            if (item == null)
                item = ItemBuilder.createPaddingPane();
            inventory.setItem(i, item);
        }
    }

    public abstract ItemStack fillCustomButton(int slot);

    public abstract boolean handleButtonClick(int slot);

    public abstract boolean canPickup(int slot);
}
