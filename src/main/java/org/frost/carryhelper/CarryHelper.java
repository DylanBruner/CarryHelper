package org.frost.carryhelper;

import net.minecraftforge.common.MinecraftForge;
import org.frost.carryhelper.command.*;
import org.frost.carryhelper.data.Config;
import net.minecraftforge.fml.common.Mod;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.frost.carryhelper.jobs.BossMon;

@Mod(modid = CarryHelper.MODID, name = CarryHelper.NAME, version = CarryHelper.VERSION)
public class CarryHelper {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @Mod.Instance(MODID)
    public static CarryHelper INSTANCE;
    public static Config config;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new Config();
        CommandManager.INSTANCE.registerCommand(new AddCarry());
        CommandManager.INSTANCE.registerCommand(new RemoveCarry());
        CommandManager.INSTANCE.registerCommand(new SetBalance());
        CommandManager.INSTANCE.registerCommand(new SetCompleted());
        CommandManager.INSTANCE.registerCommand(new CarryInfo());

        MinecraftForge.EVENT_BUS.register(new BossMon());
    }
}