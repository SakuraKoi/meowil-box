package dev.sakurakooi.spigot.meowilbox.listeners;

import com.saicone.rtag.RtagBlock;
import com.saicone.rtag.RtagItem;
import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxInventoryUtils;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxItemBuilder;
import dev.sakurakooi.spigot.meowilbox.utils.MeowilBoxUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class MeowilBoxBlockListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.isCancelled())
            return;
        if (MeowilBoxUtils.isMeowilBox(e.getItemInHand())) {
            RtagItem item = new RtagItem(e.getItemInHand());
            RtagBlock.edit(e.getBlockPlaced(), tag -> {
                tag.set(item.get("PublicBukkitValues", "meowilbox:mailbox_mark"), "PublicBukkitValues", "meowilbox:mailbox_mark");
            });
        }
        if (MeowilBoxUtils.isMeowilBoxPackage(e.getItemInHand())) {
            RtagItem item = new RtagItem(e.getItemInHand());
            RtagBlock.edit(e.getBlockPlaced(), tag -> {
                tag.set("nya", "PublicBukkitValues", "meowilbox:package_mark");
                tag.set(item.get("PublicBukkitValues", "meowilbox:inv_content"), "PublicBukkitValues", "meowilbox:inv_content");
            });
        }
        if (MeowilBoxUtils.isMeowilBoxPetals(e.getItemInHand())) {
            e.setCancelled(true);
            openPetals(e.getPlayer(), e.getItemInHand());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (MeowilBoxUtils.isMeowilBox(e.getBlock())) {
            e.setDropItems(false);
            RtagBlock tag = new RtagBlock(e.getBlock());
            Material material = Material.valueOf(tag.get("PublicBukkitValues", "meowilbox:mailbox_mark"));
            Bukkit.getScheduler().runTaskLater(MeowilBox.getInstance(), () -> {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation().toCenterLocation(), MeowilBoxItemBuilder.createMailbox(material));
            }, 1);
        }
        if (MeowilBoxUtils.isMeowilBoxPackage(e.getBlock())) {
            e.setDropItems(false);
            RtagBlock tag = new RtagBlock(e.getBlock());
            var items = MeowilBoxInventoryUtils.getInventory(tag);
            Bukkit.getScheduler().runTaskLater(MeowilBox.getInstance(), () -> {
                items.forEach(item -> {
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation().toCenterLocation(), item);
                });
                e.getBlock().getWorld().playSound(e.getBlock().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1f, 1f);
            }, 1);
        }
    }


    @EventHandler
    public void onBlockClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            if (MeowilBoxUtils.isMeowilBox(e.getClickedBlock())) {
                e.setCancelled(true);
                openMailBox(e.getPlayer());
                e.getPlayer().getWorld().dropItem(e.getClickedBlock().getLocation().toCenterLocation(), MeowilBoxItemBuilder.createItemPackage(e.getPlayer(), Collections.singletonList(MeowilBoxItemBuilder.createPetals()), false));
                e.getPlayer().getWorld().dropItem(e.getClickedBlock().getLocation().toCenterLocation(), MeowilBoxItemBuilder.createItemPackage(e.getPlayer(), Collections.singletonList(MeowilBoxItemBuilder.createPetals()), true));
            }
        }
    }

    private void openMailBox(Player player) {
        // TODO
    }

    private void openPetals(Player player, ItemStack itemInHand) {
        // TODO
    }

}
