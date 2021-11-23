package me.blazingtide.phoenix.populator;

import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.IButton;
import me.blazingtide.phoenix.button.builder.ButtonBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Consumer;

public class ButtonPopulatorImpl implements ButtonPopulator {

    private int slot;
    private ItemStack item;
    private Consumer<InventoryClickEvent> consumer = ignored -> {
    };
    private Player player;
    private Menu menu;

    @Override
    public ButtonPopulator menu(Menu menu) {
        Objects.requireNonNull(menu);
        this.menu = menu;
        return this;
    }

    @Override
    public ButtonPopulator slot(int slot) {
        this.slot = slot;
        return this;
    }

    @Override
    public ButtonPopulator player(Player player) {
        Objects.requireNonNull(player);
        this.player = player;
        return this;
    }

    @Override
    public ButtonPopulator clicked(Consumer<InventoryClickEvent> clickConsumer) {
        Objects.requireNonNull(clickConsumer);
        this.consumer = clickConsumer;
        return this;
    }

    @Override
    public ButtonPopulator item(ItemStack item) {
        Objects.requireNonNull(item);
        this.item = item;
        return this;
    }


    @Override
    public IButton create() {
        Objects.requireNonNull(player, "Player is null");
        Objects.requireNonNull(menu, "Menu is null");
        Objects.requireNonNull(item, "Item is null");

        return menu.getButtons()[slot] = new ButtonBuilder()
                .withGUI(menu)
                .withItem(item)
                .onClick(consumer)
                .build(player);
    }
}
