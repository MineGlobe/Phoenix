package me.blazingtide.phoenix

import me.blazingtide.phoenix.button.IButton
import me.blazingtide.phoenix.config.ConfigMenu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

inline fun Menu.button(item: ItemStack, slot: Int, crossinline listener: InventoryClickEvent.() -> Unit): IButton {
    return populator()
        .clicked {
            listener.invoke(it)
        }.item(item)
        .slot(slot)
        .create()
}

inline fun ConfigMenu.defineAction(id: String, crossinline handler: InventoryClickEvent.() -> Unit) {
    defineAction(id) {
        handler.invoke(it)
    }
}