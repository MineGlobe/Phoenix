package me.blazingtide.phoenix

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

inline fun Menu.button(item: ItemStack, slot: Int, crossinline listener: InventoryClickEvent.() -> Unit) {
    populator()
        .clicked {
            listener.invoke(it)
        }.item(item)
        .slot(slot)
        .create()
}
