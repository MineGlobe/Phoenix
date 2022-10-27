package me.blazingtide.phoenix

import me.blazingtide.phoenix.button.IButton
import me.blazingtide.phoenix.button.builder.ButtonBuilder
import me.blazingtide.phoenix.config.ConfigMenu
import me.blazingtide.phoenix.pagination.PaginatedMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * Creates and registers a button onto the provided menu
 *
 * @param item The item to be used for the button
 * @param listener The action to be executed when the button is clicked
 */
inline fun Menu.button(item: ItemStack, slot: Int, crossinline listener: InventoryClickEvent.() -> Unit): IButton {
    return populator().clicked {
            listener.invoke(it)
        }.item(item).slot(slot).create()
}

/**
 * Creates but does not register the button onto the provided menu
 *
 * @param item The item to be used for the button
 * @param listener The action to be executed when the button is clicked
 */
fun Menu.button(item: ItemStack, listener: InventoryClickEvent.() -> Unit): IButton {
    return ButtonBuilder().onClick {
            listener.invoke(it)
        }.withItem(item).withGUI(this).build(player)
}

/**
 * Defines an action to be executed when a certain button with a defined action is clicked.
 * Only for config menus.
 *
 * @param id The id of the action
 * @param handler The action to be executed
 */
inline fun ConfigMenu.defineAction(id: String, crossinline handler: InventoryClickEvent.() -> Unit) {
    defineAction(id) {
        handler.invoke(it)
    }
}

interface KMenuCreator {
    fun title(title: String)
    fun size(size: MenuSize) {
        size(size.value)
    }

    fun size(size: Int)

    fun draw(draw: Menu.() -> Unit)

}

interface KPaginatedMenuCreator : KMenuCreator {

    fun providePaginationEntries(draw: Menu.() -> List<IButton>)

    fun drawExtraButtons(draw: Menu.() -> Unit)
    fun nextPageItem(item: ItemStack)
    fun previousPageItem(item: ItemStack)

    fun provideSlots(size: Int): IntArray {
        val toReturn = IntArray(size - 9)

        for (i in toReturn.indices) {
            toReturn[i] = i
        }

        return toReturn
    }

}

class KMenuCreatorImpl : KMenuCreator {

    var title = ""
    var size = MenuSize.ROW_1.value
    var drawFunction: (Menu.() -> Unit)? = null
    override fun title(title: String) {
        this.title = title
    }

    override fun size(size: Int) {
        this.size = size
    }

    override fun draw(draw: Menu.() -> Unit) {
        this.drawFunction = draw
    }

}

/**
 * Creates a new menu
 *
 * @param player The player to create the menu for
 * @param creator The creator to create the menu
 * @return The created menu
 */
inline fun createMenu(player: Player, creator: KMenuCreator.() -> Unit): Menu {
    val creatorImpl = KMenuCreatorImpl()
    creator.invoke(creatorImpl)

    val menu = object : Menu(player, creatorImpl.title, creatorImpl.size) {

        override fun draw() {
            creatorImpl.drawFunction?.invoke(this)
        }

    }

    return menu.also { it.open() }
}

class KPaginatedMenuCreatorImpl : KPaginatedMenuCreator {

    var title = ""
    var size = MenuSize.ROW_1.value
    var drawFunction: (Menu.() -> Unit)? = null
    var drawExtraButtonsFunction: (Menu.() -> Unit)? = null
    var providePaginationEntriesFunction: (Menu.() -> List<IButton>)? = null
    var _nextPageItem: ItemStack? = null
    var _previousPageItem: ItemStack? = null
    override fun title(title: String) {
        this.title = title
    }

    override fun size(size: Int) {
        this.size = size
    }

    override fun draw(draw: Menu.() -> Unit) {
        this.drawFunction = draw
    }

    override fun drawExtraButtons(draw: Menu.() -> Unit) {
        this.drawExtraButtonsFunction = draw
    }

    override fun nextPageItem(item: ItemStack) {
        this._nextPageItem = item
    }

    override fun previousPageItem(item: ItemStack) {
        this._previousPageItem = item
    }


    override fun providePaginationEntries(draw: Menu.() -> List<IButton>) {
        this.providePaginationEntriesFunction = draw
    }

}

/**
 * Creates a new paginated menu
 *
 * @param player The player to create the menu for
 * @param creator The creator to create the menu
 * @return The created menu
 */
inline fun createPaginatedMenu(player: Player, creator: KPaginatedMenuCreator.() -> Unit): PaginatedMenu {
    val creatorImpl = KPaginatedMenuCreatorImpl()
    creator.invoke(creatorImpl)

    val menu = object : PaginatedMenu(player, creatorImpl.title, creatorImpl.size) {

        override fun draw() {
            creatorImpl.drawFunction?.invoke(this)
            creatorImpl.drawExtraButtonsFunction?.invoke(this)
        }

        override fun getPageButtons(): MutableList<IButton> {
            return creatorImpl.providePaginationEntriesFunction?.invoke(this)?.toMutableList() ?: mutableListOf()
        }

        override fun getNextPageItem(): ItemStack {
            return creatorImpl._nextPageItem ?: ItemStack(Material.GREEN_DYE).apply {
                    val meta = itemMeta!!

                    meta.setDisplayName("#31e862&lNext Page")
                    meta.lore = listOf(
                        "&fYou're currently on page #31e862$page/$maxPage", "", "#ffaf2e&l➥ #ffaf2eClick to move pages!"
                    )

                    itemMeta = meta
                }
        }

        override fun getPreviousPageItem(): ItemStack {
            return creatorImpl._previousPageItem ?: ItemStack(Material.YELLOW_DYE).apply {
                    val meta = itemMeta!!

                    meta.setDisplayName("&e&lPrevious Page")
                    meta.lore = listOf(
                        "&fYou're currently on page &e$page/$maxPage", "", "#ffaf2e&l➥ #ffaf2eClick to move pages!"
                    )

                    itemMeta = meta
                }
        }

    }

    return menu.also { it.open() }
}

fun exampleUsage() {

    createMenu(Bukkit.getOnlinePlayers().first()) {
        title("Example Menu")
        size(MenuSize.ROWS_6)

        draw {
            button(ItemStack(Material.PAPER), 3) {}
        }
    }

    createPaginatedMenu(Bukkit.getOnlinePlayers().first()) {
        title("Example Paginated Menu")
        size(MenuSize.ROWS_6)

        providePaginationEntries {
            listOf(button(ItemStack(Material.PAPER)) {})
        }

        drawExtraButtons {
            //Draw your extra buttons like "back" here
        }
    }

}