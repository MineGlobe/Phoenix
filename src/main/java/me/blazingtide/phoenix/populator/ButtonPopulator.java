package me.blazingtide.phoenix.populator;

import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface ButtonPopulator {

    ButtonPopulator menu(Menu menu);

    ButtonPopulator slot(int slot);

    ButtonPopulator player(Player player);

    ButtonPopulator clicked(Consumer<InventoryClickEvent> clickConsumer);

    ButtonPopulator item(ItemStack item);

    IButton create();

}
