package dev.sakurakooi.spigot.meowilbox;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.listeners.*;
import dev.sakurakooi.spigot.meowilbox.storage.MailboxManager;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class MeowilBox extends JavaPlugin {
    @Getter
    private static MeowilBox instance;
    @Getter
    private static MailboxManager mailboxManager;
    @Getter
    // Map<NamespacedKey RecipeId, Boolean IsHidden>
    private static final Map<NamespacedKey, Boolean> registeredCraftRecipes = new HashMap<>();

    @Getter
    private FileConfiguration configuration;
    @Override
    public void onEnable() {
        instance = this;
        mailboxManager = new MailboxManager();
        registerCraftRecipe();
        Bukkit.getPluginManager().registerEvents(new RecipeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommonInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PetalInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new MailboxInventoryListener(), this);
        Bukkit.getConsoleSender().sendMessage(
                Component.text()
                        .append(Component.text("MeowilBox", NamedTextColor.YELLOW, TextDecoration.BOLD))
                        .append(Component.text(" >> ", NamedTextColor.GRAY))
                        .append(Component.text("Plugin Loaded! Version: v" + this.getPluginMeta().getVersion(), NamedTextColor.GREEN))
                        .append(Component.text(" | ", NamedTextColor.GRAY))
                        .append(Component.text("Powered by.SakuraKooi", NamedTextColor.AQUA))
        );
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof MeowilBoxHolder holder) {
                holder.saveData();
                player.closeInventory();
            }
        }
    }

    // qyl27: Fix due to spigot bug.
    // https://hub.spigotmc.org/jira/browse/SPIGOT-6084
    // https://github.com/PaperMC/Paper/pull/4833
    public void registerCraftRecipe() {
        for (var rl : registeredCraftRecipes.entrySet()) {
            Bukkit.getServer().removeRecipe(rl.getKey(), true);
        }
        registeredCraftRecipes.clear();

        register(Material.ACACIA_PLANKS, "acacia_mailbox");
        register(Material.BAMBOO_PLANKS, "bamboo_mailbox");
        register(Material.BIRCH_PLANKS, "birch_mailbox");
        register(Material.CHERRY_PLANKS, "cherry_mailbox");
        register(Material.COBBLESTONE, "cobblestone_mailbox");
        register(Material.CRIMSON_PLANKS, "crimson_mailbox");
        register(Material.DARK_OAK_PLANKS, "dark_oak_mailbox");
        register(Material.JUNGLE_PLANKS, "jungle_mailbox");
        register(Material.MANGROVE_PLANKS, "mangrove_mailbox");
        register(Material.OAK_PLANKS, "oak_mailbox");
        register(Material.SANDSTONE, "sandstone_mailbox");
        register(Material.SPRUCE_PLANKS, "spruce_mailbox");
        register(Material.STONE, "stone_mailbox");
        register(Material.WARPED_PLANKS, "warped_mailbox");
        register(Material.BRICK, "brick_mailbox");
        register(Material.STONE_BRICKS, "stonebrick_mailbox");

        // Easter egg
        NamespacedKey key = new NamespacedKey(getInstance(), "rainbow_mailbox");
        ShapedRecipe recipe = new ShapedRecipe(key, ItemBuilder.createMailbox(Material.WHITE_WOOL));
        recipe.shape("ROY", "DEG", "PBA");
        recipe.setIngredient('R', Material.RED_WOOL);
        recipe.setIngredient('O', Material.ORANGE_WOOL);
        recipe.setIngredient('Y', Material.YELLOW_WOOL);
        recipe.setIngredient('G', Material.LIME_WOOL);
        recipe.setIngredient('A', Material.LIGHT_BLUE_WOOL);
        recipe.setIngredient('B', Material.BLUE_WOOL);
        recipe.setIngredient('P', Material.PURPLE_WOOL);
        recipe.setIngredient('D', Material.PINK_WOOL);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setGroup("Meowil box");
        Bukkit.getServer().addRecipe(recipe);
        registeredCraftRecipes.put(key, true);
    }

    public static void register(Material material, String name) {
        NamespacedKey key = new NamespacedKey(getInstance(), name);
        ShapedRecipe recipe = new ShapedRecipe(key, ItemBuilder.createMailbox(material));
        recipe.shape("WWW", "WEW", "WWW");
        recipe.setIngredient('W', material);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setGroup("Meowil box");
        Bukkit.getServer().addRecipe(recipe);
        registeredCraftRecipes.put(key, false);
    }
}
