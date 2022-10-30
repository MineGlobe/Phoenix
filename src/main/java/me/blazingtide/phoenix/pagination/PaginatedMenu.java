package me.blazingtide.phoenix.pagination;

import lombok.Getter;
import lombok.Setter;
import me.blazingtide.phoenix.Menu;
import me.blazingtide.phoenix.button.IButton;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public abstract class PaginatedMenu extends Menu {

    protected int maxPage;
    private int maxElements;
    protected int page = 1;
    private List<IButton> elements;

    public PaginatedMenu(Player player, String title) {
        this(player, title, 18);
    }

    public PaginatedMenu(Player player, String title, int size) {
        super(player, title, size);

        elements = getPageButtons();
        maxElements = getSlots().length;

        maxPage = elements.size() / maxElements + 1;
    }

    public abstract List<IButton> getPageButtons();

    @Override
    public void draw() {
        int start = (page - 1) * maxElements;
        int end = start + maxElements;
        final int[] slots = getSlots();

        final AtomicInteger index = new AtomicInteger();


        if (elements.size() >= start) {
            elements
                    .stream()
                    .skip(start)
                    .limit(end)
                    .forEachOrdered(button -> {
                        if (button != null) {
                            insertButton(button, slots[index.getAndIncrement()]);
                        }
                    });

        }

        if (shouldDrawPaginatedButtons()) {
            drawPaginationButtons();
        }
    }

    protected void drawPaginationButtons() {
        populator()
                .slot(size - 9)
                .item(getPreviousPageItem())
                .clicked(event -> {
                    var oldPage = page;
                    page = Math.max(1, page - 1);

                    if (oldPage != page) {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 1, 1);
                        update();
                    }
                }).create();

        populator()
                .slot(size - 1)
                .item(getNextPageItem())
                .clicked(event -> {
                    var oldPage = page;

                    page = Math.min(maxPage, page + 1);

                    if (oldPage != page) {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 1, 1);
                        update();
                    }
                }).create();

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