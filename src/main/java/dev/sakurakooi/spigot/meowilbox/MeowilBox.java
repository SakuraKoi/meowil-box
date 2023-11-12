package dev.sakurakooi.spigot.meowilbox;

import dev.sakurakooi.spigot.meowilbox.listeners.MeowilBoxBlockListener;
import dev.sakurakooi.spigot.meowilbox.listeners.MeowilBoxInventoryListener;
import dev.sakurakooi.spigot.meowilbox.listeners.MeowilBoxRecipeListener;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxItemBuilder;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class MeowilBox extends JavaPlugin {
    @Getter
    private static MeowilBox instance;
    @Getter
    private static final ArrayList<NamespacedKey> registeredCraftRecipes = new ArrayList<>();


    @Override
    public void onEnable() {
        instance = this;
        registerCraftRecipe();
        Bukkit.getPluginManager().registerEvents(new MeowilBoxRecipeListener(), this);
        Bukkit.getPluginManager().registerEvents(new MeowilBoxBlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new MeowilBoxInventoryListener(), this);
        Bukkit.getConsoleSender().sendMessage(
                Component.text()
                        .append(Component.text("MeowilBox", NamedTextColor.YELLOW, TextDecoration.BOLD))
                        .append(Component.text(" >> ", NamedTextColor.GRAY))
                        .append(Component.text("Plugin Loaded! Version: " + this.getPluginMeta().getVersion(), NamedTextColor.GREEN))
                        .append(Component.text(" | ", NamedTextColor.GRAY))
                        .append(Component.text("Powered by.SakuraKooi", NamedTextColor.AQUA))
        );
    }

    @Override
    public void onDisable() {

    }

    private void registerCraftRecipe() {
        registeredCraftRecipes.add(register(Material.ACACIA_PLANKS, "acacia_mailbox"));
        registeredCraftRecipes.add(register(Material.BAMBOO_PLANKS, "bamboo_mailbox"));
        registeredCraftRecipes.add(register(Material.BIRCH_PLANKS, "birch_mailbox"));
        registeredCraftRecipes.add(register(Material.CHERRY_PLANKS, "cherry_mailbox"));
        registeredCraftRecipes.add(register(Material.COBBLESTONE, "cobblestone_mailbox"));
        registeredCraftRecipes.add(register(Material.CRIMSON_PLANKS, "crimson_mailbox"));
        registeredCraftRecipes.add(register(Material.DARK_OAK_PLANKS, "dark_oak_mailbox"));
        registeredCraftRecipes.add(register(Material.JUNGLE_PLANKS, "jungle_mailbox"));
        registeredCraftRecipes.add(register(Material.MANGROVE_PLANKS, "mangrove_mailbox"));
        registeredCraftRecipes.add(register(Material.OAK_PLANKS, "oak_mailbox"));
        registeredCraftRecipes.add(register(Material.SANDSTONE, "sandstone_mailbox"));
        registeredCraftRecipes.add(register(Material.SPRUCE_PLANKS, "spruce_mailbox"));
        registeredCraftRecipes.add(register(Material.STONE, "stone_mailbox"));
        registeredCraftRecipes.add(register(Material.WARPED_PLANKS, "warped_mailbox"));
        registeredCraftRecipes.add(register(Material.BRICK, "brick_mailbox"));
        registeredCraftRecipes.add(register(Material.STONE_BRICKS, "stonebrick_mailbox"));

        // Easter egg
        NamespacedKey key = new NamespacedKey(getInstance(), "rainbow_mailbox");
        ShapedRecipe recipe = new ShapedRecipe(key, MeowilBoxItemBuilder.createMailbox(Material.WHITE_WOOL));
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
    }

    public static NamespacedKey register(Material material, String name) {
        NamespacedKey key = new NamespacedKey(getInstance(), name);
        ShapedRecipe recipe = new ShapedRecipe(key, MeowilBoxItemBuilder.createMailbox(material));
        recipe.shape("WWW", "WEW", "WWW");
        recipe.setIngredient('W', material);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setGroup("Meowil box");
        Bukkit.getServer().addRecipe(recipe);
        return key;
    }
}
