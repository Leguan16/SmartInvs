package fr.minuskube.inv.content;

public record SlotPos(int row, int column) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        SlotPos slotPos = (SlotPos) obj;

        return row == slotPos.row && column == slotPos.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;

        return result;
    }

    public static SlotPos of(int row, int column) {
        return new SlotPos(row, column);
    }

}
