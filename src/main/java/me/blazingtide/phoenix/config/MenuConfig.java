package me.blazingtide.phoenix.config;

import lombok.Getter;
import me.blazingtide.phoenix.utils.PhoenixColorTranslator;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class MenuConfig {

    private final File file;
    @Getter
    private FileConfiguration config;

    public MenuConfig(String filePath) {
        file = new File(filePath);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public ItemStack constructItem(String path) {
        var config = getConfig().getConfigurationSection(path);

        var item = new ItemStack(Material.valueOf(config.getString("type")));
        var meta = item.getItemMeta();

        item.setAmount(config.getInt("amount", 1));
        meta.setDisplayName(PhoenixColorTranslator.translateColors(config.getString("name")));
        meta.setLore(config.getStringList("lore").stream().map(PhoenixColorTranslator::translateColors).toList());

//        if (item.getType() == Material.PLAYER_HEAD && config.isSet("base64head")) {
//            var skullMeta = (SkullMeta) meta;
//
//        }

        if (config.isSet("customModelData")) {
            meta.setCustomModelData(config.getInt("customModelData"));
        }
        if (config.isSet("flags")) {
            meta.addItemFlags(config.getStringList("flags").stream().map(ItemFlag::valueOf).toList().toArray(new ItemFlag[0]));
        }

        item.setItemMeta(meta);
        return item;
    }

}
