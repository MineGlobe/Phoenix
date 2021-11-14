package me.blazingtide.phoenix.button;

import me.blazingtide.phoenix.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IButton {

    Player getPlayer();

    Menu getMenu();

    ItemStack getItem();

    void onClick(InventoryClickEvent event);

    boolean isAutoCancelEvent();
    
    default void cloneFor(int... slots) {
        for (int slot : slots) {
            getMenu().populator()
                    .slot(slot)
                    .item(getItem())
                    .clicked(this::onClick)
                    .create();
        }
    }
    
}
