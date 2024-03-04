package org.frost.carryhelper.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.Utils;

@Command(value = "carryinfo", description = "Shows the carry info")
public class CarryInfo {
    @Main
    public void handle() {
        // username, completed, total, average, paid, rate
        Utils.modMessage("  &c&lCarry Info", false);
        Utils.modMessage("  &9Username &7| &cCompleted &7| &eTotal &7| &aAverage &7| &6Paid &7| &bRate", false);
        Shared.getCarryData().forEach((name, data) -> {
            Utils.modMessage("  &9" + name + " &7| &c" + data.getCompleted() + " &7| &e" + data.getTotalPaid() + " &7| &a" + Utils.formatTime(data.calcSpawnTime()) + " &7| &6" + data.getPaid() + " &7| &b" + data.getRate(), false);
        });
    }
}
