package dev.sakurakooi.spigot.meowilbox;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxHolder;
import dev.sakurakooi.spigot.meowilbox.listeners.*;
import dev.sakurakooi.spigot.meowilbox.storage.MailboxManager;
import dev.sakurakooi.spigot.meowilbox.utils.DfuUtils;
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
    private final Map<NamespacedKey, Boolean> craftingRecipes = new HashMap<>();

    @Getter
    private FileConfiguration configuration;
    @Override
    public void onEnable() {
        instance = this;
        try {
            DfuUtils.initialize();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unexpected error caught while initializing DFU", e);
        }
        mailboxManager = new MailboxManager();
        registerCraftingRecipe();
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
        unregisterCraftingRecipe();

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
    public void registerCraftingRecipe() {
        registerRecipe(Material.ACACIA_PLANKS, "acacia_mailbox");
        registerRecipe(Material.BAMBOO_PLANKS, "bamboo_mailbox");
        registerRecipe(Material.BIRCH_PLANKS, "birch_mailbox");
        registerRecipe(Material.CHERRY_PLANKS, "cherry_mailbox");
        registerRecipe(Material.COBBLESTONE, "cobblestone_mailbox");
        registerRecipe(Material.CRIMSON_PLANKS, "crimson_mailbox");
        registerRecipe(Material.DARK_OAK_PLANKS, "dark_oak_mailbox");
        registerRecipe(Material.JUNGLE_PLANKS, "jungle_mailbox");
        registerRecipe(Material.MANGROVE_PLANKS, "mangrove_mailbox");
        registerRecipe(Material.OAK_PLANKS, "oak_mailbox");
        registerRecipe(Material.SANDSTONE, "sandstone_mailbox");
        registerRecipe(Material.SPRUCE_PLANKS, "spruce_mailbox");
        registerRecipe(Material.STONE, "stone_mailbox");
        registerRecipe(Material.WARPED_PLANKS, "warped_mailbox");
        registerRecipe(Material.BRICK, "brick_mailbox");
        registerRecipe(Material.STONE_BRICKS, "stonebrick_mailbox");

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
        registerRecipe(recipe, true);
    }

    public void unregisterCraftingRecipe() {
        for (var rl : craftingRecipes.entrySet()) {
            Bukkit.getServer().removeRecipe(rl.getKey(), true);
        }
        craftingRecipes.clear();
    }

    private void registerRecipe(Material material, String name) {
        NamespacedKey key = new NamespacedKey(getInstance(), name);
        ShapedRecipe recipe = new ShapedRecipe(key, ItemBuilder.createMailbox(material));
        recipe.shape("WWW", "WEW", "WWW");
        recipe.setIngredient('W', material);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setGroup("Meowil box");
        registerRecipe(recipe, false);
    }

    private void registerRecipe(ShapedRecipe recipe, boolean hidden) {
        Bukkit.getServer().addRecipe(recipe);
        craftingRecipes.put(recipe.getKey(), hidden);
    }
}
