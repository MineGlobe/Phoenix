package me.blazingtide.phoenix.populator;

import me.blazingtide.phoenix.GUI;
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
    private GUI menu;

    @Override
    public ButtonPopulator menu(GUI menu) {
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
    public void create() {
        Objects.requireNonNull(player, "Player is null");
        Objects.requireNonNull(menu, "Menu is null");
        Objects.requireNonNull(item, "Item is null");

        menu.getButtons()[slot] = new ButtonBuilder()
                .withGUI(menu)
                .withItem(item)
                .onClick(consumer)
                .build(player);
    }
}
