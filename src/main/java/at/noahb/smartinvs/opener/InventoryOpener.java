package at.noahb.smartinvs.opener;

import at.noahb.smartinvs.ClickableItem;
import at.noahb.smartinvs.SmartInventory;
import at.noahb.smartinvs.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public interface InventoryOpener {

    /**
     * Opens the inventory for a player
     * @param inv the inventory the player should be opened
     * @param player the player the inventory should be opened
     * @return the inventory which gets opened
     */
    Inventory open(SmartInventory inv, Player player);

    /**
     * Checks if the inventory type is supported
     * @param type type of inventory
     * @return true if the inventory type is supported, false otherwise
     */
    boolean supports(InventoryType type);

    default void fill(Inventory handle, InventoryContents contents) {
        ClickableItem[][] items = contents.all();

        for(int row = 0; row < items.length; row++) {
            for(int column = 0; column < items[row].length; column++) {
                if(items[row][column] != null)
                    handle.setItem(9 * row + column, items[row][column].getItem());
            }
        }
    }

}
