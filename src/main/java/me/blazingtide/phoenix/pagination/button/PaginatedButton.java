package me.blazingtide.phoenix.pagination.button;

import me.blazingtide.phoenix.button.Button;
import me.blazingtide.phoenix.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PaginatedButton extends Button {

    public PaginatedButton(Player player, PaginatedMenu gui, ItemStack item, boolean nextPage) {
        super(player, gui, item, event -> {
            if (nextPage) {
                if (gui.getMaxPage() < gui.getPage()) {
                    gui.setPage(gui.getMaxPage());
                    return;
                }
                gui.setPage(gui.getPage() + 1);
            } else {
                if (gui.getPage() > 1) {
                    gui.setPage(gui.getPage() - 1);
                }
            }
            gui.update();
        });
    }
}