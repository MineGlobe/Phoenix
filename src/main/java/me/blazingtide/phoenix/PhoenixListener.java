package me.blazingtide.phoenix;

import lombok.AllArgsConstructor;
import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.IButton;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class PhoenixListener implements Listener {

    private final Map<UUID, Menu> menus;

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        final HumanEntity player = event.getPlayer();

        //We can assume that the player's open GUI is the same GUI that we store in the Map.
        if (menus.containsKey(player.getUniqueId())) {
            menus.get(player.getUniqueId()).onOpen(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final HumanEntity player = event.getPlayer();

        //We can assume that the player's open GUI is the same GUI that we store in the Map.
        if (menus.containsKey(player.getUniqueId())) {
            menus.remove(player.getUniqueId()).onClose(event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final HumanEntity player = event.getWhoClicked();

        if (menus.containsKey(player.getUniqueId())) {
            final Menu menu = menus.get(player.getUniqueId());

            menu.onClickRaw(event);

            if (player.getOpenInventory() == null || event.getClickedInventory() == null) {
                return;
            }

            if (event.getClickedInventory().equals(player.getOpenInventory().getTopInventory())) {
                IButton button = menu.getButtons()[event.getRawSlot()];

                if (button != null) {
                    if (button.isAutoCancelEvent()) {
                        event.setCancelled(true);
                    }
                    button.onClick(event);
                }
            } else if (event.getClickedInventory().equals(player.getOpenInventory().getBottomInventory()) && event.getCurrentItem() != null) {
                menu.onPlayerInventoryClick(event);
            }
        }
    }

}
