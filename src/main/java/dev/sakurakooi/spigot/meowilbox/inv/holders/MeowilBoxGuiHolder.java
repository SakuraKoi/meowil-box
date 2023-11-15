package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Slf4j
public abstract class MeowilBoxGuiHolder implements MeowilBoxHolder {
    private Inventory inventory;

    public void postInitialize() {
        this.inventory = Bukkit.createInventory(this, 36, getInventoryTitle());
        fillInventoryBar();
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

    public abstract void updatePage();

    public abstract ItemStack fillCustomButton(int slot);

    public abstract boolean handleButtonClick(@NotNull Player player, int slot);

    public abstract boolean canPickup(int slot);

    public abstract boolean canPlaceAt(int slot);

    public @NotNull Inventory getInventory() {
        if (this.inventory == null)
            throw new IllegalStateException("Code bug detected: inventory not initialized, call postInitialize() before use");

        return this.inventory;
    }
}
