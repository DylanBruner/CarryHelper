package org.frost.carryhelper.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Description;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.Utils;

@Command(value = "removecarry", description = "Remove a player from the carry list")
public class RemoveCarry {
    @Main
    private void handle(String player) {
        if (Shared.getCarryData().remove(player) != null) {
            Utils.modMessage("Removed &6" + player + "&r from the carry list");
        } else {
            Utils.modMessage("&6" + player + "&r is not on the carry list");
        }
    }
}
