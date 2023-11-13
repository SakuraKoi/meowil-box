package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxItemBuilder;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MeowilBoxSelfHolder implements MeowilBoxHolder {
    @Getter
    private Inventory inventory;

    @Getter
    private Player player;

    public MeowilBoxSelfHolder(Player player) {
        this.player = player;
        var inventory = Bukkit.createInventory(this, 45, Component.text("Petals")); // FIXME text

        // 0-26
        // TODO load inventory

        for (int i = 27; i < 36; i++) {
            inventory.setItem(i, MeowilBoxItemBuilder.createPaddingPane());
        }
        
        // 36-44
        // TODO add buttons
    }

    @Override
    public void saveData() {

    }
}
