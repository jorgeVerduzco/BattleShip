package Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship  {
    private String name;
    private int size;
    private char symbol;
    private int hits = 0; //number of hits
    private char identifier; // represent ship on board
    public List<int[]> positions = new ArrayList<>(); //tracks coordinates of the ship

    public Ship(String name, int size, char identifier) {
        this.name = name;
        this.size = size;
        this.symbol = name.toUpperCase().charAt(0);
        this.hits = 0;
        this.identifier = identifier;
    }

    public boolean isSunk() {
        return hits == size;
    }

    public void hit() {
        hits++;
    }

    public void addPosition(int row, int col) {
        positions.add(new int[] {row, col});
    }

    public List<int[]> getPositions() {
        return positions;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public char getIdentifier() {
        return identifier;
    }

    public char getSymbol() {
        return symbol;
    }
}
