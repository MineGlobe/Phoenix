package me.blazingtide.phoenix.pagination;

import lombok.Getter;
import lombok.Setter;
import me.blazingtide.phoenix.GUI;
import me.blazingtide.phoenix.button.Button;
import me.blazingtide.phoenix.button.builder.ButtonBuilder;
import me.blazingtide.phoenix.pagination.button.PaginatedButton;
import me.blazingtide.phoenix.pagination.button.PaginationType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public abstract class PaginatedGUI extends GUI {

    public static ItemStack PAGINATED_GUI_FILLER = new ItemStack(Material.AIR);

    protected int maxPage;
    protected int maxElements;
    protected int page = 1;
    private List<Button> elements;

    public PaginatedGUI(Player player, String title) {
        this(player, title, 18);
    }

    public PaginatedGUI(Player player, String title, int size) {
        super(player, title, size);

        elements = getPageButtons();
        maxElements = size - 9;

        maxPage = elements.size() / maxElements + 1;
    }

    public abstract List<Button> getPageButtons();

    @Override
    public void draw() {
        int start = (page - 1) * maxElements;
        int end = start + maxElements;

        int index = 0;
        for (int i = start; i < end; i++) {
            if (elements.size() <= i) {
                continue;
            }
            buttons[index] = elements.get(i);
            index++;
        }

        for (int i = size - 9; i < size; i++) {
            buttons[i] = new ButtonBuilder().withGUI(this).withItem(PAGINATED_GUI_FILLER).build(player);
        }

        if (page <= 1) {
            buttons[size - 9] = new PaginatedButton(player, this, PaginationType.PREVIOUS_PAGE);
        }
        if (page == maxPage) {
            buttons[size - 1] = new PaginatedButton(player, this, PaginationType.NEXT_PAGE);
        }
    }
}