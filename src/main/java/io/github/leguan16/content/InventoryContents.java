package io.github.leguan16.content;

import io.github.leguan16.ClickableItem;
import io.github.leguan16.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface InventoryContents {

    /**
     * Returns the inventory
     * @return inventory
     */
    SmartInventory inventory();

    /**
     * Returns pages
     * @return current page
     */
    Pagination pagination();

    /**
     * Returns a SlotIterator
     * @param id id of SlotIterator to get
     * @return SlotIterator of id
     */
    Optional<SlotIterator> iterator(String id);

    /**
     * Creates a new SlotIterator
      * @param id id of SlotIterator
     * @param type type of SlotIterator
     * @param startRow startRow of SlotIterator
     * @param startColumn startColumn of SlotIterator
     * @return a new SlotIterator
     */
    SlotIterator newIterator(String id, SlotIterator.Type type, int startRow, int startColumn);

    /**
     * Creates a new slot iterator
     * @param type type of slot iterator
     * @param startRow starting row of slot iterator
     * @param startColumn starting column of slot iterator
     * @return a new slot iterator
     */
    SlotIterator newIterator(SlotIterator.Type type, int startRow, int startColumn);

    /**
     * Creates a new SlotIterator
     * @param type type of SlotIterator
     * @param startPos starting position of slot iterator
     * @return a new SlotIterator
     */
    SlotIterator newIterator(String id, SlotIterator.Type type, SlotPos startPos);

    /**
     * Creates a new
     * @param type type of slot iterator
     * @param startPos starting position of slot iterator
     * @return a new slot iterator
     */
    SlotIterator newIterator(SlotIterator.Type type, SlotPos startPos);


    /**
     * returns the content of the inventory
     * @return content of the inventory
     */
    ClickableItem[][] all();

    /**
     * Returns the first empty slot pos of the inventory
     * @return the slot pos of the first empty field as an Optional
     */
    Optional<SlotPos> firstEmpty();

    /**
     * Gets the item of a specific position
     * @param row row number
     * @param column column number
     * @return optional of clickable item
     */
    Optional<ClickableItem> get(int row, int column);

    /**
     * Gets the item of a specific position
     * @param slotPos slot pos of the item
     * @return optional of clickable item
     */
    Optional<ClickableItem> get(SlotPos slotPos);

    /**
     * set an item in the inventory
     * @param row row number
     * @param column column number
     * @param item item which should be set at the position
     * @return the updated inventory
     */
    InventoryContents set(int row, int column, ClickableItem item);

    /**
     * set an item in the inventory
     * @param slotPos slot pos of the inventory
     * @param item item which should be set at the position
     * @return the updated inventory
     */
    InventoryContents set(SlotPos slotPos, ClickableItem item);

    /**
     *
     * @param item item which should be added to the inventory
     * @return the updated inventory
     */
    InventoryContents add(ClickableItem item);

    /**
     * fills an inventory with an item
     * @param item item which should be set on each position
     * @return the updated inventory
     */
    InventoryContents fill(ClickableItem item);

    /**
     * fills a row with an item
     * @param row the row which should be filled
     * @param item item which should be set on each position of the row
     * @return the updated inventory
     */
    InventoryContents fillRow(int row, ClickableItem item);

    /**
     * fills a column with an item
     * @param column the column which should be filled
     * @param item item which should be set on each position of the column
     * @return the updated inventory
     */
    InventoryContents fillColumn(int column, ClickableItem item);

    /**
     * fills the border with an item
     * @param item item which should be set on each position of the border
     * @return the updated inventory
     */
    InventoryContents fillBorders(ClickableItem item);

    /**
     * fills the inventory with an item ordered in a rectangle
     * @param fromColumn the column of the position of the first corner of the rectangle
     * @param fromRow the row of the position of the first corner of the rectangle
     * @param toColumn the column of the position of the second corner of the rectangle
     * @param toRow the row of the position of the second corner of the rectangle
     * @param item item which should be set on each position of the rect
     * @return the updated inventory
     */
    InventoryContents fillRect(int fromRow, int fromColumn,
                               int toRow, int toColumn, ClickableItem item);

    /**
     * fills the inventory with an item ordered in a rectangle
     * @param fromPos the slot pos of the first corner of the rectangle
     * @param toPos the slot pos of the second corner of the rectangle
     * @param item item which should be set on each position of the rect
     * @return the updated inventory
     */
    InventoryContents fillRect(SlotPos fromPos, SlotPos toPos, ClickableItem item);

    /**
     * Get a property
     * @param name name of the property
     * @param <T>
     * @return property
     */
    <T> T property(String name);

    /**
     * Get a property
     * @param name name of the property
     * @param <T>
     * @param def
     * @return property
     */
    <T> T property(String name, T def);

    /**
     * Set a property
     * @param name name of the new property
     * @param value value of the property
     * @return the updated inventory
     */
    InventoryContents setProperty(String name, Object value);

    /**
     * Implementation class of InventoryContents
     */
    class Impl implements InventoryContents {

        private final SmartInventory inv;
        private final UUID player;

        private final ClickableItem[][] contents;

        private final Pagination pagination = new Pagination.Impl();
        private final Map<String, SlotIterator> iterators = new HashMap<>();
        private final Map<String, Object> properties = new HashMap<>();

        public Impl(SmartInventory inv, UUID player) {
            this.inv = inv;
            this.player = player;
            this.contents = new ClickableItem[inv.getRows()][inv.getColumns()];
        }

        @Override
        public SmartInventory inventory() { return inv; }

        @Override
        public Pagination pagination() { return pagination; }

        @Override
        public Optional<SlotIterator> iterator(String id) {
            return Optional.ofNullable(this.iterators.get(id));
        }

        @Override
        public SlotIterator newIterator(String id, SlotIterator.Type type, int startRow, int startColumn) {
            SlotIterator iterator = new SlotIterator.Impl(this, inv,
                    type, startRow, startColumn);

            this.iterators.put(id, iterator);
            return iterator;
        }

        @Override
        public SlotIterator newIterator(String id, SlotIterator.Type type, SlotPos startPos) {
            return newIterator(id, type, startPos.getRow(), startPos.getColumn());
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, int startRow, int startColumn) {
            return new SlotIterator.Impl(this, inv, type, startRow, startColumn);
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, SlotPos startPos) {
            return newIterator(type, startPos.getRow(), startPos.getColumn());
        }

        @Override
        public ClickableItem[][] all() { return contents; }

        @Override
        public Optional<SlotPos> firstEmpty() {
            for (int row = 0; row < contents.length; row++) {
                for(int column = 0; column < contents[0].length; column++) {
                    if(this.get(row, column).isEmpty())
                        return Optional.of(new SlotPos(row, column));
                }
            }

            return Optional.empty();
        }

        @Override
        public Optional<ClickableItem> get(int row, int column) {
            if(row >= contents.length)
                return Optional.empty();
            if(column >= contents[row].length)
                return Optional.empty();

            return Optional.ofNullable(contents[row][column]);
        }

        @Override
        public Optional<ClickableItem> get(SlotPos slotPos) {
            return get(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public InventoryContents set(int row, int column, ClickableItem item) {
            if(row >= contents.length)
                return this;
            if(column >= contents[row].length)
                return this;

            contents[row][column] = item;
            update(row, column, item != null ? item.getItem() : null);
            return this;
        }

        @Override
        public InventoryContents set(SlotPos slotPos, ClickableItem item) {
            return set(slotPos.getRow(), slotPos.getColumn(), item);
        }

        @Override
        public InventoryContents add(ClickableItem item) {
            for(int row = 0; row < contents.length; row++) {
                for(int column = 0; column < contents[0].length; column++) {
                    if(contents[row][column] == null) {
                        set(row, column, item);
                        return this;
                    }
                }
            }

            return this;
        }

        @Override
        public InventoryContents fill(ClickableItem item) {
            for(int row = 0; row < contents.length; row++)
                for(int column = 0; column < contents[row].length; column++)
                    set(row, column, item);

            return this;
        }

        @Override
        public InventoryContents fillRow(int row, ClickableItem item) {
            if(row >= contents.length)
                return this;

            for(int column = 0; column < contents[row].length; column++)
                set(row, column, item);

            return this;
        }

        @Override
        public InventoryContents fillColumn(int column, ClickableItem item) {
            for(int row = 0; row < contents.length; row++)
                set(row, column, item);

            return this;
        }

        @Override
        public InventoryContents fillBorders(ClickableItem item) {
            fillRect(0, 0, inv.getRows() - 1, inv.getColumns() - 1, item);
            return this;
        }

        @Override
        public InventoryContents fillRect(int fromRow, int fromColumn, int toRow, int toColumn, ClickableItem item) {
            for(int row = fromRow; row <= toRow; row++) {
                for(int column = fromColumn; column <= toColumn; column++) {
                    if(row != fromRow && row != toRow && column != fromColumn && column != toColumn)
                        continue;

                    set(row, column, item);
                }
            }

            return this;
        }

        @Override
        public InventoryContents fillRect(SlotPos fromPos, SlotPos toPos, ClickableItem item) {
            return fillRect(fromPos.getRow(), fromPos.getColumn(), toPos.getRow(), toPos.getColumn(), item);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T property(String name) {
            return (T) properties.get(name);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T property(String name, T def) {
            return properties.containsKey(name) ? (T) properties.get(name) : def;
        }

        @Override
        public InventoryContents setProperty(String name, Object value) {
            properties.put(name, value);
            return this;
        }

        private void update(int row, int column, ItemStack item) {
            Player currentPlayer = Bukkit.getPlayer(player);

            if (currentPlayer == null) {
                return;
            }

            if(!inv.getManager().getOpenedPlayers(inv).contains(currentPlayer))
                return;

            Inventory topInventory = currentPlayer.getOpenInventory().getTopInventory();
            topInventory.setItem(inv.getColumns() * row + column, item);
        }

    }

}