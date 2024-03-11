//package GameTest;

public class Ship {
    private String name;
    private int size;
    private int[] startCoordinates;
    private boolean isHorizontal;
    private boolean[] hitMarkers;

    public Ship(String name, int size,int row, int col, boolean isHorizontal) {
        this.name = name;
        this.size = size;
        this.startCoordinates = new int[] {row, col};
        this.isHorizontal = isHorizontal;
        this.hitMarkers = new boolean[size];
    }

    public boolean markHit(int row, int col) {
        //calculate hit index based on ship orientation and starting position
       int hitIndex = isHorizontal ? col  - startCoordinates[1] : row - startCoordinates[0];
       if(hitIndex >= 0 && hitIndex < size) {
        if(!hitMarkers[hitIndex]) {
            hitMarkers[hitIndex] = true;
            return true; //hit successful
        }
       }
       return false; //hit not successful
    }
    public boolean isSunk() {
        for(boolean hit : hitMarkers) {
            if(!hit) {
                return false; //if parts not hit ship isnt sunk
            }
        }
        return true; //all parts hit
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
