package dev.sakurakooi.spigot.meowilbox.listeners;

import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RecipeListener implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        for (var entry : MeowilBox.getRegisteredCraftRecipes().entrySet()) {
            if (!entry.getValue()) {
                var id = entry.getKey();
                if (!e.getPlayer().hasDiscoveredRecipe(id)) {
                    e.getPlayer().discoverRecipe(id);
                }
            }
        }
    }

    @EventHandler
    public void onServerResourcesReload(ServerResourcesReloadedEvent event) {
        MeowilBox.getInstance().registerCraftRecipe();
    }
}
