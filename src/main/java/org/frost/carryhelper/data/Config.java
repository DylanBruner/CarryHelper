package org.frost.carryhelper.data;

import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import org.frost.carryhelper.CarryHelper;
import org.frost.carryhelper.hud.CarryInfoHud;

public class Config extends cc.polyfrost.oneconfig.config.Config {
    @HUD(name = "Carry Info", category = "HUD")
    public static CarryInfoHud carryInfoHud = new CarryInfoHud();

    @Switch(name = "Mithril Coat Warning", description = "Warns you when the spawner isn't wearing a mithril coat", category = "General")
    public static boolean mithrilCoatWarning = true;

    @Switch(name = "Log to Me", description = "Log boss spawns to your chat", category = "General")
    public static boolean logToMe = true;

    @Switch(name = "Log to Party", description = "Log boss kills to the party chat", category = "General")
    public static boolean logToParty = false;

    @Text(name = "Format", description = "The party chat format, $player, $completed, $total, $average", category = "General")
    public static String partyLogFormat = "/pc $player - $completed/$total";

    @Switch(name = "Show Boss Title", description = "Show a title when a boss is spawned", category = "General")
    public static boolean showBossTitle = true;

    @Switch(name = "Box Spawners", description = "Draw a esp box on the spawners", category = "General")
    public static boolean boxSpawners = true;

    @Color(name = "Spawner Color", description = "The color of the esp box", category = "General")
    public static OneColor spawnerColor = new OneColor(0x00FF00);

    @Switch(name = "Box Bosses", description = "Draw a esp box on the bosses", category = "General")
    public static boolean boxBosses = true;

    @Color(name = "Boss Color", description = "The color of the esp box", category = "General")
    public static OneColor bossColor = new OneColor(0xFF0000);

    @Slider(name = "Box Width", description = "The width of the esp box", category = "General", min = 1, max = 10)
    public static int boxWidth = 2;

    public Config() {
        super(new Mod(CarryHelper.NAME, ModType.UTIL_QOL), CarryHelper.MODID + ".json");
        initialize();
    }
}

