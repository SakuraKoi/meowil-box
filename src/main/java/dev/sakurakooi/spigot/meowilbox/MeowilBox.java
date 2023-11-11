package dev.sakurakooi.spigot.meowilbox;

import dev.sakurakooi.spigot.meowilbox.listeners.MeowilBoxRecipeListener;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxRecipeUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

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
    }

    @Override
    public void onDisable() {

    }

    private void registerCraftRecipe() {
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.ACACIA_PLANKS, "acacia_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.BAMBOO_PLANKS, "bamboo_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.BIRCH_PLANKS, "birch_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.CHERRY_PLANKS, "cherry_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.COBBLESTONE, "cobblestone_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.CRIMSON_PLANKS, "crimson_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.DARK_OAK_PLANKS, "dark_oak_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.JUNGLE_PLANKS, "jungle_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.MANGROVE_PLANKS, "mangrove_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.OAK_PLANKS, "oak_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.SANDSTONE, "sandstone_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.SPRUCE_PLANKS, "spruce_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.STONE, "stone_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.WARPED_PLANKS, "warped_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.BRICK, "brick_mailbox"));
        registeredCraftRecipes.add(MeowilBoxRecipeUtils.register(Material.STONE_BRICKS, "stonebrick_mailbox"));
    }
}
