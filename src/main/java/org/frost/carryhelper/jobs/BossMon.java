package org.frost.carryhelper.jobs;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frost.carryhelper.data.Config;
import org.frost.carryhelper.data.Shared;
import org.frost.carryhelper.util.RenderUtils;
import org.frost.carryhelper.util.Utils;

import java.awt.*;
import java.util.HashMap;

public class BossMon {
    private final HashMap<String, Long> mithrilCoatWarnTimes = new HashMap<>();

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        if (Shared.getCarryData().isEmpty()) return; // if we're not carrying anyone, ignore it
        if (Minecraft.getMinecraft().thePlayer.ticksExisted % 20 != 0) return; // only check every second

        Minecraft.getMinecraft().theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityArmorStand).forEach(entity -> {
            String name = StringUtils.stripControlCodes(entity.getName());
            if (name.contains("Spawned by:")) {
                onBossFound(name.split("Spawned by:")[1].trim(), entity);
            }
        });
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent e){
        if (!Config.boxBosses || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;

        Shared.getCarryData().forEach((name, data) -> {
            if (data.getBossData().getLastSeen() == -1) return;
            Entity target = data.getBossData().getTaggedEntity();
            if (target != null && !target.isDead)
                RenderUtils.drawEntityBox(target, Config.bossColor.toJavaColor(), Config.boxWidth, e.partialTicks);

            if (data.getBossData().getOwner() != null && !data.getBossData().getOwner().isDead)
                RenderUtils.drawEntityBox(data.getBossData().getOwner(), Config.bossColor.toJavaColor(), Config.boxWidth, e.partialTicks);
        });
    }

    private void onBossFound(String spawner, Entity entity) {
        Shared.CarryData cd = Shared.getCarryData(spawner);
        if (cd == null) return; // if the person isn't someone we're carrying, ignore it

        // if the last seen time was more than 5 seconds ago, increment the completed count
        if (System.currentTimeMillis() - cd.getBossData().getLastSeen() > 5000) {
            cd.setCompleted(cd.getCompleted() + 1);
            if (cd.getBossData().getLastSeen() != -1) {
                cd.addTime(System.currentTimeMillis() - cd.getBossData().getLastSeen());
            }

            // find the closest non-armorstand entity to the entity
            Entity target = Utils.getClosestEntity(entity.posX, entity.posY, entity.posZ, 10, entity1 -> !(entity1 instanceof EntityArmorStand));
            if (target != null) {
                cd.getBossData().setTaggedEntity(target);
            }

            // find the owner
            Entity owner = Utils.getClosestEntity(entity.posX, entity.posY, entity.posZ, 10, entity1 -> entity1.getName().equals(cd.getUsername()));
            if (owner != null) {
                cd.getBossData().setOwner((EntityPlayer) owner);
            }

            // send a message to the player
            int total = (int) (cd.getPaid() / cd.getRate());
            double average = cd.getTimes().stream().mapToLong(Long::longValue).average().orElse(0);
            if (Config.logToMe) {
                String message = "&e" + cd.getUsername() + " &" + (cd.getCompleted() > total ? "c" : "6") + cd.getCompleted() + "&r/&6" + total + " &r(&6" + Utils.formatTime((long) average) + "&r)";
                Utils.modMessage(message);
            }

            if (Config.showBossTitle){
                Utils.showTitle("§e" + cd.getUsername() + " §aspawned", "", 10, 40, 10);
                mithrilCoatWarnTimes.put(cd.getUsername(), System.currentTimeMillis()); // give the spawn alert time to display
            }

            if (Config.logToParty) {
                String simpleMessage = Config.partyLogFormat
                        .replace("$player", cd.getUsername())
                        .replace("$completed", String.valueOf(cd.getCompleted()))
                        .replace("$total", String.valueOf(total))
                        .replace("$average", Utils.formatTime((long) average));

                Minecraft.getMinecraft().thePlayer.sendChatMessage(simpleMessage);
            }
        }

        if (!cd.isWearingMithrilCoat() && Config.mithrilCoatWarning && System.currentTimeMillis() - mithrilCoatWarnTimes.getOrDefault(cd.getUsername(), 0L) > 3500) {
            Utils.loop(3, 200, () -> Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1, 1));
            Utils.showTitle("§c§lWARNING", "§e" + cd.getUsername() + " §cis not wearing a mithril coat!", 10, 40, 10);
            mithrilCoatWarnTimes.put(cd.getUsername(), System.currentTimeMillis());
        }

        cd.getBossData().setLastSeen(System.currentTimeMillis());
    }
}