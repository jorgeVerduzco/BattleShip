package BattleshipTest;

public class Ship {
    private String name;
    private int size;
    private int[] startCoordinates;
    private int hits = 0;
    private boolean isHorizontal;
    private boolean[] hitMarkers;
    private boolean sunk = false;

    public Ship(String name, int size,int row, int col, boolean isHorizontal) {
        this.name = name;
        this.size = size;
        this.startCoordinates = new int[] {row, col};
        this.isHorizontal = isHorizontal;
        this.hitMarkers = new boolean[size];
    }

    public boolean markHit(int row, int col) {
        int index = isHorizontal ? col - startCoordinates[1] : row - startCoordinates[0];
        if(index >= 0 && index < size && !hitMarkers[index]) {
            hitMarkers[index] = true;
            hits++;
            checkAndMarkSunk();
            return true;
        }
        return false;

    }
    public void markAsSunk() { // Add this method
        this.sunk = true;
    }
    public boolean isSunk() {
       return hits == size;
    }
      public boolean checkAndMarkSunk() {
        for(boolean hit : hitMarkers) {
            if(!hit) return false;
        }
        sunk = true;
        return true;
    
    }
   

    public String getName() {
        return name;
    }

    public String getType() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int[] getStartCoordinates() {
        return startCoordinates;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    
}