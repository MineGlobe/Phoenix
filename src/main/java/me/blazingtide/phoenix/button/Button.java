package me.blazingtide.phoenix.button;

import lombok.Getter;
import lombok.Setter;
import me.blazingtide.phoenix.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Getter
public class Button implements IButton {

    private final Player player;
    private final Menu menu;
    private final ItemStack item;
    private final Consumer<InventoryClickEvent> clickConsumer;

    @Setter
    private boolean autoCancelEvent = true;

    public Button(Player player, Menu menu, ItemStack item, Consumer<InventoryClickEvent> clickConsumer) {
        this.player = player;
        this.menu = menu;
        this.item = item;
        this.clickConsumer = clickConsumer;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        clickConsumer.accept(event);
    }
}
