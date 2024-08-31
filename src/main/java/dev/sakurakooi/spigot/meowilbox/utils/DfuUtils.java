package dev.sakurakooi.spigot.meowilbox.utils;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.saicone.rtag.util.EasyLookup;
import com.saicone.rtag.util.ServerInstance;
import dev.sakurakooi.spigot.meowilbox.MeowilBox;
import lombok.Getter;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DfuUtils {
    @Getter
    private static int dataVersion;

    private static DataFixer dfu;

    private static DSL.TypeReference dataFixerTypesItemStack;
    private static DynamicOps nbtOps;

    public static void initialize() throws ReflectiveOperationException {
        // net/minecraft/server/MinecraftServer
        EasyLookup.addNMSClass("server.MinecraftServer");
        // net/minecraft/util/datafix/fixes/References
        EasyLookup.addNMSClass("util.datafix.fixes.References");
        // net/minecraft/nbt/NbtOps
        EasyLookup.addNMSClass("nbt.NbtOps");

        dataVersion = ServerInstance.DATA_VERSION;
        MeowilBox.getInstance().getLogger().info("Initializing DFU with data version: " + dataVersion);

        // MinecraftServer.getServer().getFixerUpper();
        var clazzMinecraftServer = EasyLookup.classById("MinecraftServer");
        var methodGetServer = EasyLookup.findMethod(clazzMinecraftServer, true, "getServer", clazzMinecraftServer);
        var instanceMinecraftServer = methodGetServer.invoke(null);
        var methodGetFixerUpper = EasyLookup.findMethod(clazzMinecraftServer, false, "getFixerUpper", DataFixer.class);
        dfu = (DataFixer) methodGetFixerUpper.invoke(instanceMinecraftServer);

        var clazzFixesReferences = EasyLookup.classById("References");
        var fieldDataFixerTypesItemStack = EasyLookup.findField(clazzFixesReferences, true, "ITEM_STACK", DSL.TypeReference.class);
        dataFixerTypesItemStack = (DSL.TypeReference) fieldDataFixerTypesItemStack.get(null);

        var clazzNbtOps = EasyLookup.classById("NbtOps");
        var fieldNbtOpsInstance = EasyLookup.findField(clazzNbtOps, true, "INSTANCE", clazzNbtOps);
        nbtOps = (DynamicOps) fieldNbtOpsInstance.get(null);
    }

    // MinecraftServer.getServer().fixerUpper.update(DataConverterTypes.ITEM_STACK, new Dynamic(DynamicOpsNBT.INSTANCE, savedStack), version, CraftMagicNumbers.INSTANCE.getDataVersion()).getValue();
    public static Object update(Object nbtTagCompound, int version) {
        return dfu.update(dataFixerTypesItemStack, new Dynamic(nbtOps, nbtTagCompound), version, dataVersion).getValue();
    }
}
