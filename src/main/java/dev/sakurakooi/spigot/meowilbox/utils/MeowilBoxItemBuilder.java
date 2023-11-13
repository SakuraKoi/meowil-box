package dev.sakurakooi.spigot.meowilbox.utils;

import com.saicone.rtag.RtagItem;
import com.saicone.rtag.util.SkullTexture;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;

public class MeowilBoxItemBuilder {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static ItemStack createMailbox(Material material) {
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
            case WHITE_WOOL: {
                itemName = "Rainbow Meowil box";
                yield "http://textures.minecraft.net/texture/328cb8a572e3ce993ccec4f69e6f37a859cd5ce0d466b80a123c094935ed4c22";
            }
            default: throw new IllegalArgumentException("Unexpected material: " + material);
        };
        ItemStack item = createCustomHead(texture, itemName, YELLOW, list -> {});
        RtagItem.edit(item, tag -> {
            tag.set(material.name(), "PublicBukkitValues", "meowilbox:mailbox_mark");
        });
        return item;
    }

    public static ItemStack createItemPackage(OfflinePlayer from, List<ItemStack> items, boolean sakura) {
        String texture = sakura ?
                "https://textures.minecraft.net/texture/8b45faf5b17744f1ff2f3acddc89a35823ca2e471d02302e7f59cc458697fb66" :
                "https://textures.minecraft.net/texture/fa85971fb13bf0b78eb9f96b2bdce1a11331373de30d9239e8bc06a2912c4a4";
        ItemStack item = createCustomHead(texture, "包裹", sakura ? TextColor.color(0xff4081) : GREEN, lores -> {
            lores.add(Component.text()
                    .append(Component.text("来自: ").color(GRAY).decoration(ITALIC, false))
                    .append(Component.text(Objects.requireNonNullElse(from.getName(), from.getUniqueId().toString())).color(YELLOW).decoration(ITALIC, false)).asComponent());
            lores.add(Component.text()
                    .append(Component.text("时间: ").color(GRAY).decoration(ITALIC, false))
                    .append(Component.text(dateFormat.format(new Date())).color(YELLOW).decoration(ITALIC, false)).asComponent());
            lores.add(Component.text(""));
            lores.add(Component.text("打破以拆开包裹").color(AQUA).decoration(ITALIC, false).decorate(BOLD));
        });


        RtagItem.edit(item, tag -> {
            tag.set(UUID.randomUUID().toString(), "PublicBukkitValues", "meowilbox:package_mark");
            MeowilBoxInventoryUtils.setItemContent(tag, items);
        });

        return item;
    }

    public static ItemStack createPetals() {
        String texture = "http://textures.minecraft.net/texture/9aef19d2e2a658f33b5c25d1aeae01e83320dd385eceb6a766cfe547ffc03dad";
        ItemStack item = createCustomHead(texture, "樱花手袋", TextColor.color(0xff4081), lores -> {});
        RtagItem.edit(item, tag -> {
            tag.set(UUID.randomUUID().toString(), "PublicBukkitValues", "meowilbox:petals_mark");
            MeowilBoxInventoryUtils.setInventory(tag, new HashMap<>());
        });
        return item;
    }

    public static ItemStack createSendButton() {
        String texture = "http://textures.minecraft.net/texture/a7ed66f5a70209d821167d156fdbc0ca3bf11ad54ed5d86e75c265f7e5029ec1";
        ItemStack item = createCustomHead(texture, "寄信", AQUA, lores -> {});
        RtagItem.edit(item, tag -> {
            tag.set("nya", "PublicBukkitValues", "meowilbox:ui_button");
        });
        return item;
    }

    public static ItemStack createPlayerListButton() {
        String texture = "http://textures.minecraft.net/texture/259e8d4196fea827025c2927a6fcd6e98d030057371238a77ae4cddebce86477";
        ItemStack item = createCustomHead(texture, "玩家列表", AQUA, lores -> {});
        RtagItem.edit(item, tag -> {
            tag.set("nya", "PublicBukkitValues", "meowilbox:ui_button");
        });
        return item;
    }

    public static ItemStack createNextPageButton() {
        String texture = "http://textures.minecraft.net/texture/291ac432aa40d7e7a687aa85041de636712d4f022632dd5356c880521af2723a";
        ItemStack item = createCustomHead(texture, "下一页", AQUA, lores -> {});
        RtagItem.edit(item, tag -> {
            tag.set("nya", "PublicBukkitValues", "meowilbox:ui_button");
        });
        return item;
    }
    public static ItemStack createPrevPageButton() {
        String texture = "http://textures.minecraft.net/texture/7a2c12cb22918384e0a81c82a1ed99aebdce94b2ec2754800972319b57900afb";
        ItemStack item = createCustomHead(texture, "上一页", AQUA, lores -> {});
        RtagItem.edit(item, tag -> {
            tag.set("nya", "PublicBukkitValues", "meowilbox:ui_button");
        });
        return item;
    }
    public static ItemStack createPageStopButton(boolean directionNext) {
        String texture = "http://textures.minecraft.net/texture/bb72ad8369eb6cd8990cec1f54d1778442a108b0186622c5918eb85159e2fb9e";
        ItemStack item = createCustomHead(texture, directionNext ? "下一页" : "上一页", RED, lores -> {
            lores.add(Component.text("已... 已经一点也不剩了~♡").color(LIGHT_PURPLE).decoration(ITALIC, false));
        });
        RtagItem.edit(item, tag -> {
            tag.set("nya", "PublicBukkitValues", "meowilbox:ui_button");
        });
        return item;
    }

















    public static ItemStack createPaddingPane() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(" "));
        item.setItemMeta(meta);
        RtagItem.edit(item, tag -> {
            tag.set("nya", "PublicBukkitValues", "meowilbox:ui_button");
        });
        return item;
    }

    @NotNull
    private static ItemStack createCustomHead(String texture, String itemName, TextColor nameColor, Consumer<ArrayList<Component>> loreHandler) {
        ItemStack head = SkullTexture.getTexturedHead(texture);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.displayName(Component.text(itemName, nameColor).decoration(ITALIC, false));
        ArrayList<Component> lore =new ArrayList<>();
        loreHandler.accept(lore);
        meta.lore(lore);
        head.setItemMeta(meta);
        return head;
    }
}
