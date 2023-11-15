package dev.sakurakooi.spigot.meowilbox.inv.holders;

import dev.sakurakooi.spigot.meowilbox.inv.MeowilBoxUI;
import dev.sakurakooi.spigot.meowilbox.storage.MailboxManager;
import dev.sakurakooi.spigot.meowilbox.utils.ItemBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.format.NamedTextColor.DARK_RED;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;

@Slf4j
public class MeowilBoxOtherHolder extends MeowilBoxGuiHolder {
    private final MailboxManager.MeowilBoxStorage storage;
    @Getter
    private final OfflinePlayer player;

    public MeowilBoxOtherHolder(MailboxManager.MeowilBoxStorage storage, OfflinePlayer player) {
        this.storage = storage;
        this.player = player;
        this.postInitialize();
        updatePage();

        // TODO 翻页
    }

    @Override
    public void saveData() {
    }

    @Override
    public Component getInventoryTitle() {
        return Component.text(player.getName() + " 的喵箱").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public void updatePage() {
        for (int i = 0; i < 27; i++) {
            ItemStack item = null;
            if (i < storage.getContents().size()) {
                item = storage.getContents().get(i);
            }
            getInventory().setItem(i, item);
        }
        if (storage.getContents().size() >= 27) {
            getInventory().setItem(28, ItemBuilder.createBlockButton(Component.text("✗ 寄纸箱不能 ✗").color(DARK_RED).decorate(BOLD).decoration(ITALIC, false),
                    Component.text("这只猫猫的喵箱被塞满了!").color(RED).decoration(ITALIC, false)));
        } else {
            getInventory().setItem(28, ItemBuilder.createSendButton());
        }
    }

    @Override
    public ItemStack fillCustomButton(int slot) {
        if (slot == 27)
            return ItemBuilder.createPlayerListButton();
        if (slot == 28)
            return ItemBuilder.createSendButton();

        return null;
    }

    @Override
    public boolean handleButtonClick(@NotNull Player player, int slot) {
        if (slot == 27) {
            MeowilBoxUI.openPlayerListFor(player);
            return true;
        }
        if (slot == 28) {
            if (storage.getContents().size() < 27) {
                MeowilBoxUI.openSendMailFor(player, this.player);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canPickup(int slot) {
        return false;
    }

    @Override
    public boolean canPlaceAt(int slot) {
        return false;
    }
}
