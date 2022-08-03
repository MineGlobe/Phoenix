package me.blazingtide.phoenix.pagination;

import lombok.Getter;
import lombok.Setter;
import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public abstract class PaginatedMenu extends Menu {

    private int maxPage;
    private int maxElements;
    protected int page = 1;
    private List<Button> elements;

    public PaginatedMenu(Player player, String title) {
        this(player, title, 18);
    }

    public PaginatedMenu(Player player, String title, int size) {
        super(player, title, size);

        elements = getPageButtons();
        maxElements = getSlots().length;

        maxPage = elements.size() / maxElements + 1;
    }

    public abstract List<Button> getPageButtons();

    @Override
    public void draw() {
        clear();

        int start = (page - 1) * maxElements;
        int end = start + maxElements;
        final int[] slots = getSlots();

        int index = 0;
        for (int i = start; i < end; i++) {
            if (elements.size() <= i) {
                continue;
            }
            buttons[slots[index]] = elements.get(i);
            index++;
        }

        if (shouldDrawPaginatedButtons()) {
            drawPaginationButtons();
        }
    }

    protected void drawPaginationButtons() {
        populator()
                .slot(size - 9)
                .item(getPreviousPageItem())
                .clicked(event -> page = Math.max(1, page - 1)).create();

        populator()
                .slot(size - 1)
                .item(getNextPageItem())
                .clicked(event -> page = Math.min(maxPage, page - 1));
    }

    public boolean shouldDrawPaginatedButtons() {
        return true;
    }

    protected int[] getSlots() {
        final int[] toReturn = new int[size - 9];

        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = i;
        }

        return toReturn;
    }

    public abstract ItemStack getNextPageItem();

    public abstract ItemStack getPreviousPageItem();

}