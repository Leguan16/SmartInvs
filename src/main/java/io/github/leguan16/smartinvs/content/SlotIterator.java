package io.github.leguan16.smartinvs.content;

import io.github.leguan16.smartinvs.ClickableItem;
import io.github.leguan16.smartinvs.SmartInventory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface SlotIterator {


    /**
     * Available types of the slot iterator
     * Whether the slot iterator is horizontal or vertical
     */
    enum Type {
        HORIZONTAL,
        VERTICAL
    }

    /**
     *
     * @return clickable item
     */
    Optional<ClickableItem> get();

    /**
     *
     * @param item item to be set
     * @return the updated slot iterator
     */
    SlotIterator set(ClickableItem item);

    /**
     *
     * @return updated slot iterator
     */
    SlotIterator previous();

    /**
     *
     * @return updated slot iterator
     */
    SlotIterator next();

    /**
     * Add an item to the blacklist
     * @param row row number of item to be blacklisted
     * @param column column number of item to be blacklisted
     * @return updated slot iterator
     */
    SlotIterator blacklist(int row, int column);

    /**
     * Add an item to the blacklist
     * @param slotPos slot position of item to be blacklisted
     * @return updated slot iterator
     */
    SlotIterator blacklist(SlotPos slotPos);

    /**
     *
     * @return current row
     */
    int row();

    /**
     * Set the current row
     * @param row the new row
     * @return the updated slot iterator
     */
    SlotIterator row(int row);

    /**
     *
     * @return the current row
     */
    int column();

    /**
     * Set the current column
     * @param column the new column
     * @return the updated slot iterator
     */
    SlotIterator column(int column);

    /**
     * returns a boolean if the iterator has already started
     * @return true if already started, false otherwise
     */
    boolean started();

    /**
     * returns a boolean if the iterator has already reached the end
     * @return true if end reached, false otherwise
     */
    boolean ended();

    /**
     * returns a boolean if overriding is allowed
     * @return ture if allowed, false otherwise
     */
    boolean doesAllowOverride();

    /**
     * set if overriding is allowed
     * @param override true to allow overriding, false otherwise
     * @return the updated slot iterator
     */
    SlotIterator allowOverride(boolean override);


    class Impl implements SlotIterator {

        private final InventoryContents contents;
        private final SmartInventory inv;

        private final Type type;
        private boolean started = false;
        private boolean allowOverride = true;
        private int row, column;

        private final Set<SlotPos> blacklisted = new HashSet<>();

        public Impl(InventoryContents contents, SmartInventory inv,
                    Type type, int startRow, int startColumn) {

            this.contents = contents;
            this.inv = inv;

            this.type = type;

            this.row = startRow;
            this.column = startColumn;
        }

        public Impl(InventoryContents contents, SmartInventory inv,
                    Type type) {

            this(contents, inv, type, 0, 0);
        }

        @Override
        public Optional<ClickableItem> get() {
            return contents.get(row, column);
        }

        @Override
        public SlotIterator set(ClickableItem item) {
            if(canPlace())
                contents.set(row, column, item);

            return this;
        }

        @Override
        public SlotIterator previous() {
            if(row == 0 && column == 0) {
                this.started = true;
                return this;
            }

            do {
                if(!this.started) {
                    this.started = true;
                }
                else {
                    switch (type) {
                        case HORIZONTAL -> {
                            column--;
                            if (column == 0) {
                                column = inv.getColumns() - 1;
                                row--;
                            }
                        }
                        case VERTICAL -> {
                            row--;
                            if (row == 0) {
                                row = inv.getRows() - 1;
                                column--;
                            }
                        }
                    }
                }
            }
            while(!canPlace() && (row != 0 || column != 0));

            return this;
        }

        @Override
        public SlotIterator next() {
            if(ended()) {
                this.started = true;
                return this;
            }

            do {
                if(!this.started) {
                    this.started = true;
                }
                else {
                    switch (type) {
                        case HORIZONTAL -> {
                            column = ++column % inv.getColumns();
                            if (column == 0)
                                row++;
                        }
                        case VERTICAL -> {
                            row = ++row % inv.getRows();
                            if (row == 0)
                                column++;
                        }
                    }
                }
            }
            while(!canPlace() && !ended());

            return this;
        }

        @Override
        public SlotIterator blacklist(int row, int column) {
            this.blacklisted.add(SlotPos.of(row, column));
            return this;
        }

        @Override
        public SlotIterator blacklist(SlotPos slotPos) {
            return blacklist(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public int row() { return row; }

        @Override
        public SlotIterator row(int row) {
            this.row = row;
            return this;
        }

        @Override
        public int column() { return column; }

        @Override
        public SlotIterator column(int column) {
            this.column = column;
            return this;
        }

        @Override
        public boolean started() {
            return this.started;
        }

        @Override
        public boolean ended() {
            return row == inv.getRows() - 1
                    && column == inv.getColumns() - 1;
        }

        @Override
        public boolean doesAllowOverride() { return allowOverride; }

        @Override
        public SlotIterator allowOverride(boolean override) {
            this.allowOverride = override;
            return this;
        }

        private boolean canPlace() {
            return !blacklisted.contains(SlotPos.of(row, column)) && (allowOverride || this.get().isEmpty());
        }

    }

}