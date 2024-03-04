package org.frost.carryhelper.util;

import cc.polyfrost.oneconfig.libs.checker.nullness.qual.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;

import java.util.Comparator;
import java.util.function.Predicate;

public class Utils {
    private static final String PREFIX = "§7[§cCarryHelper§7] §r";

    public static void showTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title == null && subtitle == null) return;

        title = title == null ? "" : title.replaceAll("(?<!&)&(?=[^&])", "§");
        subtitle = subtitle == null ? "" : subtitle.replaceAll("(?<!&)&(?=[^&])", "§");

        Minecraft.getMinecraft().ingameGUI.displayTitle(title, null, fadeIn, stay, fadeOut);
        Minecraft.getMinecraft().ingameGUI.displayTitle(null, subtitle, fadeIn, stay, fadeOut);
        Minecraft.getMinecraft().ingameGUI.displayTitle(null, null, fadeIn, stay, fadeOut);
    }

    public static void modMessage(String message, boolean prefix) {
        message = message.replaceAll("(?<!&)&(?=[^&])", "§");
        if (Minecraft.getMinecraft().thePlayer == null)
            System.out.println((prefix ? PREFIX : "") + message);
        else
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText((prefix ? PREFIX : "") + message));
    }

    public static void modMessage(String message) {
        modMessage(message, true);
    }

    public static void modMessage(ChatComponentText message, boolean prefix) {
        if (Minecraft.getMinecraft().thePlayer == null)
            System.out.println(message.getUnformattedText());
        else
            Minecraft.getMinecraft().thePlayer.addChatMessage(prefix ? new ChatComponentText(PREFIX).appendSibling(message) : message);
    }

    public static void modMessage(ChatComponentText message) {
        modMessage(message, true);
    }

    public static String stripColor(String message) {
        return message.replaceAll("(?<!&)&(?=[^&])", "");
    }

    public static String formatTime(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return (days > 0 ? days + "d " : "") + (hours % 24 > 0 ? hours % 24 + "h " : "") + (minutes % 60 > 0 ? minutes % 60 + "m " : "") + seconds % 60 + "s";
    }

    public static double parseValue(String value) {
        double v = Double.parseDouble(value.substring(0, value.length() - 1));
        if (value.endsWith("k")) {
            return v * 1000;
        } else if (value.endsWith("m")) {
            return v * 1000000;
        } else if (value.endsWith("b")) {
            return v * 1000000000;
        } else {
            return Double.parseDouble(value);
        }
    }

    public static String formatValue(double value) {
        if (value < 1000) {
            return String.format("%.0f", value);
        } else if (value < 1000000) {
            return String.format("%.1f", value / 1000) + "k";
        } else if (value < 1000000000) {
            return String.format("%.1f", value / 1000000) + "m";
        } else {
            return String.format("%.1f", value / 1000000000) + "b";
        }
    }

    public static Entity getClosestEntity(double x, double y, double z, double range, Predicate<Entity> filter) {
        return Minecraft.getMinecraft().theWorld.loadedEntityList.stream().filter(filter).filter(entity -> entity.getDistanceSq(x, y, z) < range * range).min(Comparator.comparingDouble(e -> e.getDistanceSq(x, y, z))).orElse(null);
    }

    // convert codes like &c to actual colors
    public static int formatCodeToColor(char c) {
        switch (c) {
            case '0':
                return 0x000000;
            case '1':
                return 0x0000AA;
            case '2':
                return 0x00AA00;
            case '3':
                return 0x00AAAA;
            case '4':
                return 0xAA0000;
            case '5':
                return 0xAA00AA;
            case '6':
                return 0xFFAA00;
            case '7':
                return 0xAAAAAA;
            case '8':
                return 0x555555;
            case '9':
                return 0x5555FF;
            case 'a':
                return 0x55FF55;
            case 'b':
                return 0x55FFFF;
            case 'c':
                return 0xFF5555;
            case 'd':
                return 0xFF55FF;
            case 'e':
                return 0xFFFF55;
            default:
                return 0xFFFFFF;
        }
    }

    public static void loop(int times, int delay, Runnable runnable) {
        new Thread(() -> {
            for (int i = 0; i < times; i++) {
                try {
                    runnable.run();
                } catch (Exception ignored) {}
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }
}
