package me.blazingtide.phoenix.config;

import com.google.common.collect.Maps;
import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.Button;
import me.blazingtide.phoenix.utils.PhoenixColorTranslator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.function.Consumer;

public abstract class ConfigMenu extends Menu {

    private final MenuConfig config;
    private final Map<String, Consumer<InventoryClickEvent>> actions = Maps.newHashMap();

    public ConfigMenu(Player player, String filePath) {
        super(player, "", 0);

        if (!PHOENIX.getMenuConfigs().containsKey(filePath)) {
            PHOENIX.getPlugin().getLogger().severe("Menu Config for file path " + filePath + " has not been registered! Please register in in the plugin's onEnable.");
            PHOENIX.registerMenuConfig(filePath);
        }

        config = PHOENIX.getMenuConfigs().get(filePath);
        title = PhoenixColorTranslator.translateColors(config.getConfig().getString("title"));
        size = config.getConfig().getInt("size");

        buttons = new Button[size];
        defineActions();
    }

    public ConfigMenu(Player player, JavaPlugin plugin, String location) {
        this(player, plugin.getDataFolder().getPath() + "/" + location);
    }

    public abstract void defineActions();

    public void defineAction(String id, Consumer<InventoryClickEvent> consumer) {
        actions.put(id, consumer);
    }

    public void handleAllActions(String action, InventoryClickEvent event) {

    }

    @Override
    public void draw() {
        for (String key : config.getConfig().getConfigurationSection("items").getKeys(false)) {
            final ItemStack item = config.constructItem("items." + key);

            populator()
                    .slot(config.getConfig().getInt("items." + key + ".slot"))
                    .item(item)
                    .clicked(event -> {
                        final String action = config.getConfig().getString("items." + key + ".clickAction");

                        if (actions.containsKey(action)) {
                            actions.get(action).accept(event);
                        }

                        handleAllActions(action, event);
                    })
                    .create();
        }
    }
}
