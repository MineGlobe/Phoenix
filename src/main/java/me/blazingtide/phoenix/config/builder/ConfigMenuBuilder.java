package me.blazingtide.phoenix.config.builder;

import me.blazingtide.phoenix.MenuSize;
import me.blazingtide.phoenix.config.ConfigMenu;
import org.bukkit.inventory.ItemStack;

public interface ConfigMenuBuilder {

    ConfigMenuBuilder title(String title);

    ConfigMenuBuilder size(MenuSize size);

    ConfigMenuBuilder items(ItemStack... items);

}
