//package GameTest;

public class Ship {
    private String name;
    private int size;
    private int[] startCoordinates;
    private int liveCells;
    private boolean isHorizontal;
    private boolean[] hitMarkers;
    private boolean sunk = false;

    public Ship(String name, int size,int row, int col, boolean isHorizontal, int cells) {
        this.name = name;
        this.size = size;
        this.startCoordinates = new int[] {row, col};
        this.isHorizontal = isHorizontal;
        this.hitMarkers = new boolean[size];
        this.liveCells = cells;
    }

    public boolean markHit(int row, int col) {
        //calculate hit index based on ship orientation and starting position
       int hitIndex = isHorizontal ? col  - startCoordinates[1] : row - startCoordinates[0];
       if(hitIndex >= 0 && hitIndex < size) {
        if(!hitMarkers[hitIndex]) {
            hitMarkers[hitIndex] = true;
            liveCells= liveCells-1;
            return true; //hit successful
        }
       }
       return false; //hit not successful
    }
    public void markAsSunk() { // Add this method
        this.sunk = true;
    }
    public boolean isSunk() {
       return sunk; //all parts hit
    }
      public boolean checkAndMarkSunk() {
      //  for (boolean hit : hitMarkers) {
            if (liveCells == 0) {
                markAsSunk();
                return true; // The ship is fully sunk
                
          //  }
        }
         // All parts of the ship have been hit, mark it as sunk
        return false; // The ship is fully sunk
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
