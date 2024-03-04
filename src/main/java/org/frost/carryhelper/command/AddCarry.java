package org.frost.carryhelper.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Description;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.frost.carryhelper.CarryHelper;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.Utils;

@Command(value = "addcarry", description = "Add a player to the carry list")
public class AddCarry {
    @Main
    private void handle(String player, String rate) {
        Shared.CarryData cd = Shared.getCarryData(player);
        if (cd != null) {
            cd.setRate(Utils.parseValue(rate));
            Utils.modMessage("Changed &6" + player + "&r's rate to &6" + Utils.formatValue(cd.getRate()));
        } else {
            Shared.addCarryData(player, Utils.parseValue(rate));
            Utils.modMessage("Added &6" + player + "&r to the carry list with a rate of &6" + Utils.formatValue(Shared.getCarryData(player).getRate()));
        }
    }
}