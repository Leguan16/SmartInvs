package at.noahb.smartinvs.content;

import at.noahb.smartinvs.ClickableItem;

import java.util.Arrays;

public interface Pagination {

    /**
     * Get all items of a specific page
     * @return an array of items in the current page
     */
    ClickableItem[] getPageItems();

    /**
     * Gets the current page
     * @return the id the current page
     */
    int getPage();

    /**
     * Changes the page
     * @param page the page to be switched to
     * @return the new page
     */
    Pagination page(int page);

    /**
     * checks if it's the first page
     * @return ture if it is the first page, false otherwise
     */
    boolean isFirst();

    /**
     * checks if it's the last page
     * @return ture if it is the last page, false otherwise
     */
    boolean isLast();

    /**
     * resets the page number
     * @return the updated pagination
     */
    Pagination first();

    /**
     * sets the page number to the previous page
     * @return the updated pagination
     */
    Pagination previous();

    /**
     * sets the page number to the next page
     * @return the updated pagination
     */
    Pagination next();

    /**
     * sets the page number to the last page
     * @return the updated pagination
     */
    Pagination last();

    /**
     * adds the page to the iterator
     * @param iterator the iterator to add the page to
     * @return the pagination
     */
    Pagination addToIterator(SlotIterator iterator);

    /**
     * Sets items in a page
     * @param items the items to be set
     * @return the updated pagination
     */
    Pagination setItems(ClickableItem... items);

    /**
     * Sets the amount of how many items should be per page
     * @param itemsPerPage the amount of items per page
     * @return the updated pagination
     */
    Pagination setItemsPerPage(int itemsPerPage);


    class Impl implements Pagination {

        private int currentPage;

        private ClickableItem[] items = new ClickableItem[0];
        private int itemsPerPage = 5;

        @Override
        public ClickableItem[] getPageItems() {
            return Arrays.copyOfRange(items,
                    currentPage * itemsPerPage,
                    (currentPage + 1) * itemsPerPage);
        }

        @Override
        public int getPage() {
            return this.currentPage;
        }

        @Override
        public Pagination page(int page) {
            this.currentPage = page;
            return this;
        }

        @Override
        public boolean isFirst() {
            return this.currentPage == 0;
        }

        @Override
        public boolean isLast() {
            int pageCount = (int) Math.ceil((double) this.items.length / this.itemsPerPage);
            return this.currentPage >= pageCount - 1;
        }

        @Override
        public Pagination first() {
            this.currentPage = 0;
            return this;
        }

        @Override
        public Pagination previous() {
            if(!isFirst())
                this.currentPage--;

            return this;
        }

        @Override
        public Pagination next() {
            if(!isLast())
                this.currentPage++;

            return this;
        }

        @Override
        public Pagination last() {
            this.currentPage = this.items.length / this.itemsPerPage;
            return this;
        }

        @Override
        public Pagination addToIterator(SlotIterator iterator) {
            for(ClickableItem item : getPageItems()) {
                iterator.next().set(item);

                if(iterator.ended())
                    break;
            }

            return this;
        }

        @Override
        public Pagination setItems(ClickableItem... items) {
            this.items = items;
            return this;
        }

        @Override
        public Pagination setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
            return this;
        }

    }

}
