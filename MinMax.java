import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class MinMax {
	char[][] board_state;
	int n;
	char mysymbol;
	char opponentsymbol;
	String mode;
	int depth;
	Eval evalobj;
	MinMax(Eval obj, char[][] state, String mode, int depth){
		n = obj.getN();
		mysymbol = obj.getMysymbol();
		opponentsymbol = obj.getOpponentsymbol();
		board_state = state;
		evalobj = obj;
		this.mode = mode;
		this.depth = depth;
	}
	
	public void calculate(int countPieces){
		if(mode.equalsIgnoreCase("MINIMAX")){
			minimax(countPieces);
		}
		else if(mode.equalsIgnoreCase("ALPHABETA") ){
			alphabeta(countPieces);
		}
		/*else{
			return competition();
		}*/
	}

	private String competition() {
		return null;
	}

	private void alphabeta(int countPieces) {
		String val;
		//int resval;
		val = maximum_1_alphabeta(board_state,-9999999, 9999999, depth, countPieces);
		String[] tokens = val.split("\\+");
		
		char y = (char) (Integer.parseInt(tokens[2]) + 64);
		System.out.print(y+tokens[1]+" "+tokens[0]+"\n");
		String mov = tokens[1]+"+"+tokens[2];
		char[][] b = result(board_state, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[0], mysymbol);
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				System.out.print(b[i][j]);
			}
			System.out.print("\n");
		}
	}

	private String maximum_1_alphabeta(char[][] board_state2, int alpha, int beta,
			int depth2, int countPieces) {
		Map<String, HashMap<Integer, LinkedHashSet<Integer>>> actions = PossibleActions(board_state2,mysymbol);
		Map<Integer, String> valmap = new HashMap<Integer, String>();
		int max = -9999999;
		int val;
		String act = null;
		for(String action : actions.keySet()){
			for( int i : actions.get(action).keySet()){
				for(int j : actions.get(action).get(i)){
					val = minimum_alphabeta(result(board_state2, i,j, action, mysymbol),alpha, beta, depth2-1, countPieces+1);
					if(valmap.get(val) == null){
						act = action + "+" + i + "+" + j;
						valmap.put(val, act);
					}
					if(val > max){
						max = val;
					}
					if(max >= beta){
						
						return act;
					}
					if(max > alpha){
						alpha = max;
					}
				}
			}
		}
		return valmap.get(max);
	}

	private int maximum_alphabeta(char[][] board_state2, int alpha, int beta, int d, int countPieces) {
		//terminal state check
		if(d==0 || countPieces == (n*n)){
			return evalobj.eval(board_state2);
		}
		Map<String, HashMap<Integer, LinkedHashSet<Integer>>> actions = PossibleActions(board_state2,mysymbol);
		int max = -9999999;
		int val;
		for(String action : actions.keySet()){
			for( int i : actions.get(action).keySet()){
				for(int j : actions.get(action).get(i)){
					val = minimum_alphabeta(result(board_state2, i,j, action, mysymbol),alpha, beta, d-1, countPieces+1);
					if(val > max){
						max = val;
					}
					if(max >= beta){
						return max;
					}
					if(max > alpha){
						alpha = max;
					}
				}
			}
		}
		
		return max;
	}
	
	private int minimum_alphabeta(char[][] result, int alpha, int beta, int d, int countPieces) {
		if(d==0 || countPieces == (n*n)){
			int value = evalobj.eval(result);
			return value;
		}
		Map<String, HashMap<Integer, LinkedHashSet<Integer>>> actions = PossibleActions(result,opponentsymbol);
		int min = 9999999;
		int val;
		for(String action : actions.keySet()){
			for( int i : actions.get(action).keySet()){
				for(int j : actions.get(action).get(i)){
					val = maximum_alphabeta(result(result, i,j, action, opponentsymbol),alpha,beta, d-1, countPieces+1);
					if(val < min){
						min = val;
				}
					if(min <= alpha){
						return min;
					}
					if( min < beta){
						beta = min;
					}
			}
		}
		}
		return min;
	}
	
	
	private void minimax(int countPieces) {
		Map<String, HashMap<Integer, LinkedHashSet<Integer>>> actions = PossibleActions(board_state,mysymbol);
		int value;
		int max = -9999999;
		String action = null;
		for(String action1 : actions.keySet()){
			for( int i : actions.get(action1).keySet()){
				for(int j : actions.get(action1).get(i)){
					value = minimum(result(board_state, i,j, action1, mysymbol), depth-1, countPieces+1);
					if(value > max){
						max = value;
						action = action1+"+"+i+"+"+j;
					}
				}
			}
		}
		
		System.out.println("val :" + max);
		String[] tokens = action.split("\\+");
		
		char y = (char) (Integer.parseInt(tokens[2]) + 64);
		System.out.print(y+tokens[1]+" "+tokens[0]+"\n");
		char[][] b = result(board_state, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[0], mysymbol);
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				System.out.print(b[i][j]);
			}
			System.out.print("\n");
		}
	}
	
	private int minimum(char[][] result, int d, int countPieces) {
		if(d==0 || countPieces == (n*n)){
			int value = evalobj.eval(result);
			return value;
		}
		Map<String, HashMap<Integer, LinkedHashSet<Integer>>> actions = PossibleActions(result,opponentsymbol);
		int min = 9999999;
		int val;
		for(String action : actions.keySet()){
			for( int i : actions.get(action).keySet()){
				for(int j : actions.get(action).get(i)){
					val = maximum(result(result, i,j, action, opponentsymbol), d-1, countPieces+1);
					if(val < min){
						min = val;
				}
			}
		}
		}
		return min;
	}
	
	private int maximum(char[][] board_state2, int d, int countPieces) {
		//terminal state check
		if(d==0 || countPieces == (n*n)){
			return evalobj.eval(board_state2);
		}
		Map<String, HashMap<Integer, LinkedHashSet<Integer>>> actions = PossibleActions(board_state2,mysymbol);
		int max = -9999999;
		int val;
		for(String action : actions.keySet()){
			for( int i : actions.get(action).keySet()){
				for(int j : actions.get(action).get(i)){
					val = minimum(result(board_state2, i,j, action, mysymbol), d-1, countPieces+1);
					if(val > max){
						max = val;
					}
				}
			}
		}
		
		return max;
	}

	private char[][] result(char[][] board_state2, int i, int j, String type, char player_symbol) {
		char[][] intermediate_state = new char[n+1][n+1];
		for(int x=1;x<=n;x++){
			for(int y=1;y<=n;y++){
				intermediate_state[x][y] = board_state2[x][y];
			}
		}

		char opponentsymbol = 'X';
		if(player_symbol == 'X'){
			opponentsymbol = 'O';
		}
		if(type.equalsIgnoreCase("Stake")){
			intermediate_state[i][j] = player_symbol;
		}
		else{
			intermediate_state[i][j] = player_symbol;
			if((i-1)>0 && (intermediate_state[i-1][j] == opponentsymbol)){
				intermediate_state[i-1][j] = player_symbol;
			}
			if((i+1) < n+1 && (intermediate_state[i+1][j] == opponentsymbol)){
				intermediate_state[i+1][j] = player_symbol;
			}
			if((j-1) > 0 && (intermediate_state[i][j-1] == opponentsymbol)){
				intermediate_state[i][j-1] = player_symbol;
			}
			if((j+1) < n+1 && (intermediate_state[i][j+1] == opponentsymbol)){
				intermediate_state[i][j+1] = player_symbol;
			}
		}

		return intermediate_state;
	}

	
	private Map<String, HashMap<Integer, LinkedHashSet<Integer>>> PossibleActions(char[][] state, char symbol){
		Map<String,HashMap<Integer, LinkedHashSet<Integer>>> actions = new LinkedHashMap<String, HashMap<Integer, LinkedHashSet<Integer>>>();
		HashMap<Integer, LinkedHashSet<Integer>> raids = new HashMap<Integer, LinkedHashSet<Integer>>();
		char oppsymbol = 'X';
		if(symbol == 'X'){
			oppsymbol = 'O';
		}
		HashMap<Integer, LinkedHashSet<Integer>> stakes = new HashMap<Integer, LinkedHashSet<Integer>>();
		for(int i=1;i<=n;i++){
			LinkedHashSet<Integer> cols = new LinkedHashSet<Integer>();
			for(int j=1;j<=n;j++){
				if( state[i][j] == '.'){
					cols.add(j);
				}
				else if( state[i][j] == symbol){
					if((i-1)>0 && (state[i-1][j] == '.')){
						if(validity(state, i-1,j,oppsymbol)){
							if( raids.get(i-1)==null){
								raids.put(i-1, new LinkedHashSet<Integer>());
							}
							raids.get(i-1).add(j);
							}
						}
					if((j-1) > 0 && (state[i][j-1] == '.')){
						if(validity(state, i,j-1,oppsymbol)){
							if( raids.get(i)==null){
								raids.put(i, new LinkedHashSet<Integer>());
							}
							raids.get(i).add(j-1);
						}
					}
					if((j+1) < n+1 && (state[i][j+1] == '.')){
						if(validity(state, i,j+1,oppsymbol)){
							if( raids.get(i)==null){
								raids.put(i, new LinkedHashSet<Integer>());
							}
							raids.get(i).add(j+1);
						}
					}
					
					if((i+1) < n+1 && (state[i+1][j] == '.')){
						if(validity(state, i+1,j,oppsymbol)){
							if( raids.get(i+1)==null){
								raids.put(i+1, new LinkedHashSet<Integer>());
							}
							raids.get(i+1).add(j);
						}
					}
				}
			}
			stakes.put(i, cols);
		}
		actions.put("Stake", stakes);
		actions.put("Raid", raids);
		for(int key : actions.get("Raid").keySet()){
			for(int posraid : actions.get("Raid").get(key)){
				if(actions.get("Stake").get(key).contains(posraid)){
					actions.get("Stake").get(key).remove(posraid);
				}
			}
		}
		return actions;
		
	}
	
	private boolean validity(char[][] state, int i, int j, char oppsymbol){
		boolean valid = false;
		if((i-1)>0 && (state[i-1][j] == oppsymbol)){
			valid = true;
		}
		if((i+1) < n+1 && (state[i+1][j] == oppsymbol)){
			valid = true;
		}
		if((j-1) > 0 && (state[i][j-1] == oppsymbol)){
			valid = true;
		}
		if((j+1) < n+1 && (state[i][j+1] == oppsymbol)){
			valid = true;
		}
		return valid;
	}
	
	/*private result(char[][] board_state2, int i, int j, String type, char player_symbol) {
		if(type.equalsIgnoreCase("Stake"))){
			
		}
	}*/
	
	

}
