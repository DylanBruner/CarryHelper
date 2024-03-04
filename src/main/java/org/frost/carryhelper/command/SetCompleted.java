package org.frost.carryhelper.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Description;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.Utils;

@Command(value = "setcompleted", description = "Set the amount of completed carries for a player")
public class SetCompleted {
    @Main
    public void handle(@Description(value = "The player to set the completed carries of") String player, @Description("The amount of completed carries to set") int completed) {
        if (Shared.getCarryData(player) == null) {
            Utils.modMessage("§cThat player is not in the carry list!");
            return;
        }

        Shared.getCarryData(player).setCompleted(completed);
        Utils.modMessage("§aSet the amount of completed carries of §e" + player + "§a to §e" + Shared.getCarryData(player).getCompleted());
    }
}
