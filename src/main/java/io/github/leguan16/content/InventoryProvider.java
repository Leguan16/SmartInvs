package io.github.leguan16.content;

import org.bukkit.entity.Player;

public interface InventoryProvider {

    /**
     * initialization method of a new inventory
     * @param player player the inventory gets opened for
     * @param contents content of the inventory
     */
    void init(Player player, InventoryContents contents);

    /**
     * used to update the inventory
     * @param player player the inventory gets opened for
     * @param contents content of the inventory
     */
    default void update(Player player, InventoryContents contents) {}

}
