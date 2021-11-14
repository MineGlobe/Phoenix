package me.blazingtide.phoenix;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

/**
 * I made this class static since I only want 1 instance of the GUI handler at all times.
 * So, each plugin should not be creating their own instances which will prevent overlaping of
 * menus.
 */
@Getter
public class Phoenix {

    /**
     * Since we're going to be using multiple threads at the same time
     * when attempting to update menus for players, Concurrent HashMap will ensure that
     * there are 0 complications.
     */
    private final Map<UUID, Menu> openmenus = Maps.newConcurrentMap();
    private final JavaPlugin plugin;

    private UpdaterThread updater;

    public Phoenix(JavaPlugin plugin) {
        this.plugin = plugin;
        this.updater = new UpdaterThread(this);

        Bukkit.getPluginManager().registerEvents(new PhoenixListener(openmenus), plugin);
        Menu.PHOENIX = this;
    }

}
