package com.willfp.eco.spigot.gui

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.PluginDependent
import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.slot.FillerMask
import com.willfp.eco.core.gui.slot.Slot
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack

class GUITester(plugin: EcoPlugin) : PluginDependent<EcoPlugin>(plugin), Listener {
    private val menu: Menu = Menu.builder(3)
        .setMask(
            FillerMask(
                Material.BLACK_STAINED_GLASS_PANE,
                "111111111",
                "100000001",
                "111111111"
            )
        ).setSlot(
            2, 2,
            Slot.builder()
                .setCaptive()
                .build()
        ).modfiy { builder ->
            run {
                val slot = Slot.builder(ItemStack(Material.RED_STAINED_GLASS_PANE))
                    .setModifier{ player, menu, prev -> run {
                        if (menu.getCaptiveItems(player).isNotEmpty()) {
                            prev.type = Material.GREEN_STAINED_GLASS_PANE
                        }
                    }}
                    .build()
                for (i in 3..8) {
                    builder.setSlot(2, i, slot)
                }
            }
        }.build()

    @EventHandler
    fun test(event: AsyncPlayerChatEvent) {
        if (!event.message.equals("testgui", true)) {
            return
        }

        plugin.scheduler.run { menu.open(event.player) }
    }
}