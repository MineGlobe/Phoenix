package me.blazingtide.phoenix.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Pattern;

public class ColorUtils {

    private static Pattern PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String translateColors(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        var matcher = PATTERN.matcher(message);

        while (matcher.find()) {
            var hexCode = message.substring(matcher.start(), matcher.end());

            message = message.replace(hexCode, ChatColor.of(hexCode).toString());
            matcher = PATTERN.matcher(message);
        }

        return message;
    }

}
