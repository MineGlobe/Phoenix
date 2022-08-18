package me.blazingtide.phoenix;

import org.bukkit.plugin.java.JavaPlugin;

public class PhoenixPlugin extends JavaPlugin {

    private Phoenix phoenix;

    @Override
    public void onEnable() {
        phoenix = new Phoenix(this);
    }
}
