package org.frost.carryhelper.hud;

import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.hud.TextHud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.Utils;

import java.util.List;


public class CarryInfoHud extends TextHud {
    public CarryInfoHud() {
        super(true, 50, 50, 1, true, true, 2, 5, 5, new OneColor(0x000000), false, 2, new OneColor(0x0000));
    }

    @Override
    protected void getLines(List<String> lines, boolean example) {
        if (example) {
            lines.add("§cCarry Info");
            lines.add("§9Example 1 §c1§7/2 §e(1d 2h 3m 4s)");
            lines.add("§9Example 2 §c3§7/4 §e(5d 6h 7m 8s)");
            return;
        }

        if (Shared.getCarryData().isEmpty()) {lines.clear(); return;}

        lines.add("§cCarry Info");
        Shared.getCarryData().forEach((name, data) -> {
            lines.add("§9" + name + " §" + (data.getCompleted() > data.getTotalPaid() ? "c" : "7")  + data.getCompleted() + "§7/" + data.getTotalPaid() + " §e(" + Utils.formatTime(data.calcSpawnTime()) + ")");
        });
    }

    @Override
    public void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        if (Shared.getCarryData().isEmpty() && !example) return;
        super.draw(matrices, x, y, scale, example);
    }
}
