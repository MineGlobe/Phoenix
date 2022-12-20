package me.blazingtide.phoenix.types;

import me.blazingtide.phoenix.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public abstract class WorkbenchInventory extends Menu {

    public WorkbenchInventory(Player player) {
        super(player, "", 9);
    }

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, InventoryType.WORKBENCH);
    }
}
