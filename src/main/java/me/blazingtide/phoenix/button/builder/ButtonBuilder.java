package me.blazingtide.phoenix.button.builder;

import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ButtonBuilder {

    private ItemStack item;
    private Menu menu;
    private Consumer<InventoryClickEvent> clickConsumer = event -> {
    };

    public ButtonBuilder withItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public ButtonBuilder withGUI(Menu menu) {
        this.menu = menu;
        return this;
    }

    public ButtonBuilder onClick(Consumer<InventoryClickEvent> consumer) {
        clickConsumer = consumer;

        return this;
    }

    public Button build(Player player) {
        return new Button(player, menu, item, clickConsumer);
    }

}
