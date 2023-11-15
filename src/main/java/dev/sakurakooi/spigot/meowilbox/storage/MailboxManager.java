package dev.sakurakooi.spigot.meowilbox.storage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalNotification;
import com.saicone.rtag.item.ItemObject;
import com.saicone.rtag.tag.TagCompound;
import com.saicone.rtag.tag.TagList;
import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxOtherHolder;
import dev.sakurakooi.spigot.meowilbox.inv.holders.MeowilBoxSelfHolder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class MailboxManager {
    private File dataDir;
    private LoadingCache<UUID, MeowilBoxStorage> storage = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofHours(1))
            .removalListener(this::onUnload)
            .build(new MeowilBoxLoader());

    private void onUnload(RemovalNotification<UUID, MeowilBoxStorage> removalNotification) {
        Objects.requireNonNull(removalNotification.getValue()).save();
    }

    public MailboxManager() {
        dataDir = new File(MeowilBox.getInstance().getDataFolder(), "data");
        dataDir.mkdirs();
    }

    public MeowilBoxStorage getMailbox(OfflinePlayer player) throws ExecutionException {
        return storage.get(player.getUniqueId());
    }

    private class MeowilBoxLoader extends CacheLoader<UUID, MeowilBoxStorage> {
        @Override
        public MeowilBoxStorage load(UUID uuid) {
            File file = new File(dataDir, uuid.toString() + ".nbt");
            if (file.exists()) {
                Object tagCompound = TagCompound.DATA.fromFile(file);
                Object list = TagCompound.get(tagCompound, "meowilbox");
                int count = TagList.size(list);
                ArrayList<ItemStack> contents = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    Object nmsItem = ItemObject.newItem(TagList.get(list, i));
                    contents.add(ItemObject.asBukkitCopy(nmsItem));
                }
                return new MeowilBoxStorage(uuid, file, contents);
            } else {
               return new MeowilBoxStorage(uuid, file, new ArrayList<>());
            }
        }
    }

    public static class MeowilBoxStorage {
        @Getter
        private MeowilBoxSelfHolder holder;
        @Getter
        private MeowilBoxOtherHolder otherHolder;
        private File dataFile;
        @Getter @Setter
        private ArrayList<ItemStack> contents;

        public MeowilBoxStorage(UUID owner, File dataFile, ArrayList<ItemStack> contents) {
            this.dataFile = dataFile;
            this.contents = contents;
            this.holder = new MeowilBoxSelfHolder(this, Bukkit.getOfflinePlayer(owner));
            this.otherHolder = new MeowilBoxOtherHolder(this, Bukkit.getOfflinePlayer(owner));
        }

        public void save() {
            Object list = TagList.newTag();
            for (ItemStack item : contents) {
                Object nmsItem = ItemObject.asNMSCopy(item);
                TagList.add(list, ItemObject.save(nmsItem));
            }

            Object tagCompound = TagCompound.newTag();
            TagCompound.set(tagCompound,"meowilbox", list);
            TagCompound.DATA.toFile(tagCompound, dataFile);
        }
    }
}
