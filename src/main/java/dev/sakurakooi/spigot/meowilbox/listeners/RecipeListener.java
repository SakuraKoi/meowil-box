package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RecipeListener implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        for (var namespaceKey : MeowilBox.getRegisteredCraftRecipes()) {
            if (!e.getPlayer().hasDiscoveredRecipe(namespaceKey)) {
                e.getPlayer().discoverRecipe(namespaceKey);
            }
        }
    }
}
