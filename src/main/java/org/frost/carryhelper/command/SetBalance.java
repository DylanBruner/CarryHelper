package org.frost.carryhelper.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Description;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.Utils;

@Command(value = "setbalance", description = "Set the balance of a player")
public class SetBalance {
    @Main
    public void handle(@Description(value = "The player to set the balance of") String player, @Description("The balance to set") String balance) {
        if (Shared.getCarryData(player) == null) {
            Utils.modMessage("§cThat player is not in the carry list!");
            return;
        }

        Shared.getCarryData(player).setPaid(Utils.parseValue(balance));
        Utils.modMessage("§aSet the balance of §e" + player + "§a to §e" + Utils.formatValue(Shared.getCarryData(player).getPaid()));
    }
}
