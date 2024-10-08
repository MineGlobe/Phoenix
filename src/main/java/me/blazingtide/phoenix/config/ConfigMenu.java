package me.blazingtide.phoenix.config;

import com.google.common.collect.Maps;
import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.Button;
import me.blazingtide.phoenix.utils.PhoenixColorTranslator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
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

    /**
     * Handles all button clicks and provides a reference to the event & configuration section.
     * <p>
     * Return false if click events shouldn't be handled
     *
     * @param reference the reference
     * @return true if click events should be handled
     */
    public boolean handleButtonClick(ConfigButtonReference<InventoryClickEvent, ConfigurationSection> reference) {
        return true;
    }

    public void handleAllActions(String action, InventoryClickEvent event) {

    }

    @Override
    public void draw() {
        final FileConfiguration config = this.config.getConfig();

        for (String key : config.getConfigurationSection("items").getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection("items." + key);
            final ItemStack item = this.config.constructItem("items." + key);

            int[] slots = section.isSet("slots") ?
                    section.getIntegerList("slots").stream().mapToInt(Integer::intValue).toArray() :
                    new int[]{section.getInt("slot")};

            populator()
                    .slot(slots)
                    .item(item)
                    .clicked(event -> {
                        if (!section.isSet("clickAction")) return;

                        final List<String> clickActions = section.getStringList("clickAction");

                        if (clickActions.size() == 0) {
                            clickActions.add(section.getString("clickAction"));
                        }

                        clickActions.forEach(action -> {
                            var result = handleButtonClick(ConfigButtonReference.of(event, section));

                            if (!result) return;

                            if (actions.containsKey(action)) {
                                actions.get(action).accept(event);
                            }

                            handleAllActions(action, event);
                        });
                    }).create();
        }
    }
}
