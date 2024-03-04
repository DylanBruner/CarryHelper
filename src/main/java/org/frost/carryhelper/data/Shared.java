package org.frost.carryhelper.data;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shared {
    private static final HashMap<String, CarryData> carryData = new HashMap<>();

    public static CarryData getCarryData(String username) {
        return carryData.getOrDefault(username, null);
    }

    public static void addCarryData(String username, double rate) {
        carryData.put(username, new CarryData(username, rate));
    }

    public static void removeCarryData(String username) {
        carryData.remove(username);
    }

    public static HashMap<String, CarryData> getCarryData() {
        return carryData;
    }

    public static class CarryData {
        private final String username;
        private final BossData bossData;
        private double paid;
        private double rate;
        private int completed;
        private List<Long> times;


        public CarryData(String username, double rate) {
            this.username = username;
            this.rate = rate;
            this.paid = 0;
            this.completed = 0;
            this.times = new ArrayList<>();
            this.bossData = new BossData();
        }

        public boolean isWearingMithrilCoat() {
            EntityPlayer entity = Minecraft.getMinecraft().theWorld.playerEntities.stream().filter(player -> player.getName().equals(username)).findFirst().orElse(null);
            return entity != null && entity.getEquipmentInSlot(3) != null && entity.getEquipmentInSlot(3).getItem() == net.minecraft.init.Items.chainmail_chestplate;
        }

        public int getTotalPaid() {
            return (int) (paid / rate);
        }

        public long calcSpawnTime() {
            return (long) times.stream().mapToLong(Long::longValue).average().orElse(0);
        }

        public String getUsername() {return username;}
        public double getPaid() {return paid;}
        public double getRate() {return rate;}
        public int getCompleted() {return completed;}
        public List<Long> getTimes() {return times;}
        public BossData getBossData() {return bossData;}

        public void setPaid(double paid) {this.paid = paid;}
        public void setRate(double rate) {this.rate = rate;}
        public void setCompleted(int completed) {this.completed = completed;}
        public void setTimes(List<Long> times) {this.times = times;}
        public void addTime(long time) {this.times.add(time);}
    }

    public static class BossData {
        private long lastSeen = -1;
        private Entity taggedEntity;
        private EntityPlayer owner;

        public BossData() {
        }

        public long getLastSeen() {return lastSeen;}
        public Entity getTaggedEntity() {return taggedEntity;}
        public EntityPlayer getOwner() {return owner;}

        public void setLastSeen(long lastSeen) {this.lastSeen = lastSeen;}
        public void setTaggedEntity(Entity taggedEntity) {this.taggedEntity = taggedEntity;}
        public void setOwner(EntityPlayer owner) {this.owner = owner;}
    }
}