package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.util.SkullTexture;
import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MeowilBoxRecipeUtils {
    public static NamespacedKey register(Material material, String name) {
        NamespacedKey key = new NamespacedKey(MeowilBox.getInstance(), name);
        ShapedRecipe recipe = new ShapedRecipe(key, createMailbox(material));
        recipe.shape("WWW", "WEW", "WWW");
        recipe.setIngredient('W', material);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setGroup("Meowil box");
        Bukkit.getServer().addRecipe(recipe);
        return key;
    }

    private static ItemStack createMailbox(Material material) {
        String itemName;
        String texture = switch (material) {
            case ACACIA_PLANKS: {
                itemName = "Acacia Meowil box";
                yield "https://textures.minecraft.net/texture/60708e8d5d114d900abe6ba4c6b3cf966e6fcc925ab9d631d001d3c016948a51";
            }
            case BAMBOO_PLANKS: {
                itemName = "Bamboo Meowil box";
                yield "https://textures.minecraft.net/texture/44756a8bcd1852cb721335010918e37c26239cf0c7e4f3109138759ed85319e";
            }
            case BIRCH_PLANKS: {
                itemName = "Birch Meowil box";
                yield "https://textures.minecraft.net/texture/58404a0277ddb038316ee7ac1f76f64dad8a4b66a546a1dc604712a569894808";
            }
            case CHERRY_PLANKS: {
                itemName = "Cherry Meowil box";
                yield "https://textures.minecraft.net/texture/8216b3e4ca9ecac63e8255eff0ae2e7b6cf7c9a60979a0f6951836e9d7ff54c7";
            }
            case COBBLESTONE: {
                itemName = "Cobblestone Meowil box";
                yield "https://textures.minecraft.net/texture/1c4c983284ea10104db69c5e7addad5e5ed1b78e5075b38fb62ae3eba41cbaaf";
            }
            case CRIMSON_PLANKS: {
                itemName = "Crimson Meowil box";
                yield "https://textures.minecraft.net/texture/131e4c8861c885de8a85e42b3a7140d46f5b8140b081f2ad06e9d5895f566f87";
            }
            case DARK_OAK_PLANKS: {
                itemName = "Dark Oak Meowil box";
                yield "https://textures.minecraft.net/texture/4c72387e744a8bea3636803ced6f58a15ae586e8c15cf63d3d9945b5a23cee1c";
            }
            case JUNGLE_PLANKS: {
                itemName = "Jungle Meowil box";
                yield "https://textures.minecraft.net/texture/6cec5ac7b419d7ffbff2ce9b83b79008a2a33532759bc2d0dc447696b78a44a9";
            }
            case MANGROVE_PLANKS: {
                itemName = "Mangrove Meowil box";
                yield "https://textures.minecraft.net/texture/f421357fb086e59f4270eb0eec1e1268918d5f6658c3d72421c5b6ef56213ac5";
            }
            case OAK_PLANKS: {
                itemName = "Oak Meowil box";
                yield "https://textures.minecraft.net/texture/a1a43f1aab0b061fb9b96553ad7fae1e70d83ecabbc7452a1d21e61e73f99c3d";
            }
            case SANDSTONE: {
                itemName = "Sandstone Meowil box";
                yield "https://textures.minecraft.net/texture/a6a974c913f27a52bd80fed96a8f57736f38865108683b7b786c6712b3f68786";
            }
            case SPRUCE_PLANKS: {
                itemName = "Spruce Meowil box";
                yield "https://textures.minecraft.net/texture/3bb83651a97995b470a0df228a98d5f4c1e154a97921f2982746057bc6eeb624";
            }
            case STONE: {
                itemName = "Stone Meowil box";
                yield "https://textures.minecraft.net/texture/e38fd27dff4020d945336c81345d34fc5cb18ffb0eb16bf8ecf88be2f00f0b8a";
            }
            case WARPED_PLANKS: {
                itemName = "Warped Meowil box";
                yield "https://textures.minecraft.net/texture/69a6e310e351a14af215dd2b28e587a707c0038ab3cbb34125e5f37e35c719bf";
            }
            case BRICK: {
                itemName = "Brick Meowil box";
                yield "https://textures.minecraft.net/texture/7c17470fff9d918c184fdc33ace3570e6bc8299bb00d40ea42919d7ac303afde";
            }
            case STONE_BRICKS: {
                itemName = "Stonebrick Meowil box";
                yield "https://textures.minecraft.net/texture/6bed4a8357b2e931d4d07e8595c5bcb9c2ed6b0ee0b883f0565d8405459d8875";
            }
            default: throw new IllegalArgumentException("Unexpected material: " + material);
        };
        ItemStack head = SkullTexture.getTexturedHead(texture);
        ItemMeta meta = head.getItemMeta();
        meta.displayName(Component.text(itemName).color(TextColor.color(0xffff55)));
        meta.lore(List.of(Component.text("Meowil Box").color(TextColor.color(0xff4081)).decorate(TextDecoration.BOLD)));
        head.setItemMeta(meta);
        return head;
    }
}
