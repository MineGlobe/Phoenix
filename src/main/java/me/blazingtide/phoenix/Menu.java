package me.blazingtide.phoenix;

import lombok.Getter;
import me.blazingtide.phoenix.button.Button;
import me.blazingtide.phoenix.button.IButton;
import me.blazingtide.phoenix.populator.ButtonPopulator;
import me.blazingtide.phoenix.populator.ButtonPopulatorImpl;
import me.blazingtide.phoenix.utils.InventoryUpdate;
import me.blazingtide.phoenix.utils.PhoenixColorTranslator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Getter
public abstract class Menu {

    /**
     * Basically how long until the GUI autoupdates for the player.
     * <p>
     * Default will be 1 second.
     */
    private static final long REGULAR_UPDATE_TICK = TimeUnit.MILLISECONDS.toSeconds(1);

    protected static Phoenix PHOENIX;

    protected final Player player;
    protected String title;
    protected int size;

    @Deprecated
    protected IButton[] buttons;

    protected long lastTick;

    protected Inventory inventory;

    public Menu(Player player, String title, int size) {
        this.player = player;
        this.title = PhoenixColorTranslator.translateColors(title);
        this.size = size;

        buttons = new Button[size];
    }

    public Menu(Player player, String title, MenuSize size) {
        this(player, title, size.getValue());
    }

    public ButtonPopulator populator() {
        return new ButtonPopulatorImpl().menu(this).player(player);
    }

    /**
     * Inserts a button into the GUI at a specific slot.
     * <p>
     * If the slot is already occupied, it will be replaced.
     * If the slot is out of bounds, it will be ignored.
     * If the button is null, it will be ignored.
     * </p>
     *
     * @param button the button to insert
     * @param slots  the slots to insert the button into
     */
    public void insertButton(IButton button, int... slots) {
        if (button == null) return;

        Arrays.stream(slots).filter(slot -> slot >= 0 && slot < size).forEach(slot -> buttons[slot] = button);
    }

    /**
     * Called whenever the GUI is ready to update.
     * The correct usage would be to return an Optional of ERROR on error
     * and to set the buttons to update the GUI
     * <p>
     * Usage:
     * {@see me.blazingtide.phoenix.populator.ButtonPopulator}
     */
    public abstract void draw();

    /**
     * Called whenever a player clicks an item in their inventory
     * while this GUI is open.
     *
     * @param event which is called.
     */
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    /**
     * Called whenever this GUI is opened.
     *
     * @param event inventory open event
     */
    public void onOpen(InventoryOpenEvent event) {

    }

    /**
     * Called whenever this GUI is closed.
     *
     * @param event inventory close event
     */
    public void onClose(InventoryCloseEvent event) {

    }

    /**
     * Called whenever a player clicks an item in the inventory.
     * called before button's handler
     *
     * @param event inventory drag event
     */
    public void onClickRaw(InventoryClickEvent event) {

    }

    /**
     * Updates the inventory for the player.
     */
    public final void update() {
        clear();
        draw();
        for (int i = 0; i < buttons.length; i++) {
            final IButton button = buttons[i];

            if (button != null) {
                inventory.setItem(i, button.getItem());
            }
        }
        lastTick = System.currentTimeMillis();
    }

    public long getUpdateTick() {
        return REGULAR_UPDATE_TICK;
    }

    /**
     * Opens the inventory for the player
     */
    public final void open() {
        inventory = createInventory();

        update();

        player.openInventory(inventory);

        if (PHOENIX != null) {
            PHOENIX.getOpenMenus().put(player.getUniqueId(), this);
        } else {
            PHOENIX.getPlugin().getLogger().severe("Attempted to open a GUI without having Phoenix initialized.");
        }
    }


    /**
     * Updates the title of the current menu
     */
    public final void updateTitle(String text) {
        this.title = PhoenixColorTranslator.translateColors(text);
        InventoryUpdate.updateInventory(PHOENIX.getPlugin(), this.player, this.title);
    }

    /**
     * Creates an inventory which we can use for the player
     * This method is not final since we want to be able to support
     * multiple inventory types.
     *
     * @return created inventory
     */
    public Inventory createInventory() {
        return Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));
    }

    /**
     * Clears the entire menu
     * However, it doesn't call update method.
     * <p>
     * You'll have to update the menu manually.
     */
    public void clear() {
        Arrays.fill(buttons, null);
    }

    /**
     * Determines weather a GUI should update automatically depending on the
     * update speed.
     *
     * @return weather it should update automatically or not.
     */
    public boolean isAutoUpdating() {
        return false;
    }

    /**
     * Returns the first empty slot in the inventory.
     * <p>
     * Returns -1 if there's no slot empty.
     *
     * @return slot
     */
    public int firstEmpty() {
        for (int i = 0; i < size; i++) {
            if (buttons[i] == null) {
                return i;
            }
        }
        return -1;
    }

}
