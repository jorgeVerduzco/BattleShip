package Game;

public class BattleshipGame {
	public static void main(String[] args){

		BattleshipModel model = new BattleshipModel();
	    BattleshipView view = new BattleshipView();
	    BattleshipController controller = new BattleshipController(model,view);
        
        
    }
}
