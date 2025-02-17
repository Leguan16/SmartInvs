package at.noahb.smartinvs.opener;

import com.google.common.collect.ImmutableList;
import at.noahb.smartinvs.InventoryManager;
import at.noahb.smartinvs.SmartInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener {

    private static final List<InventoryType> SUPPORTED = ImmutableList.of(
            InventoryType.FURNACE,
            InventoryType.WORKBENCH,
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            InventoryType.ENCHANTING,
            InventoryType.BREWING,
            InventoryType.ANVIL,
            InventoryType.BEACON,
            InventoryType.HOPPER
    );

    @Override
    public Inventory open(SmartInventory inv, Player player) {
        InventoryManager manager = inv.getManager();
        Inventory handle = Bukkit.createInventory(player, inv.getType(), Component.text(inv.getTitle()));

        manager.getContents(player).ifPresent(inventoryContents -> fill(handle, inventoryContents));

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return SUPPORTED.contains(type);
    }

}
