package dev.sakurakooi.spigot.meowilbox.inv;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface MeowilBoxHolder extends InventoryHolder {
    void saveData();
}
