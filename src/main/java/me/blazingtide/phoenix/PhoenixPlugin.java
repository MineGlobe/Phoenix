package me.blazingtide.phoenix;

import org.bukkit.plugin.java.JavaPlugin;

public class PhoenixPlugin extends JavaPlugin {

    private static Phoenix phoenix;

    public static Phoenix getPhoenix() {
        return phoenix;
    }

    @Override
    public void onEnable() {
        phoenix = new Phoenix(this);
    }
}
