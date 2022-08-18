package me.blazingtide.phoenix.config;

import com.google.common.collect.Maps;
import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.utils.PhoenixColorTranslator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

public class ConfigMenu extends Menu {

    private final MenuConfig config;
    private final Map<String, Consumer<InventoryClickEvent>> actions = Maps.newHashMap();

    public ConfigMenu(Player player, String filePath) {
        super(player, "", 0);

        config = new MenuConfig(filePath);
        title = PhoenixColorTranslator.translateColors(config.getConfig().getString("title"));
        size = config.getConfig().getInt("size");
    }

    public void defineAction(String id, Consumer<InventoryClickEvent> consumer) {
        actions.put(id, consumer);
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
                    })
                    .create();
        }
    }
}
