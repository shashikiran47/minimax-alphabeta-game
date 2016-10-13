
public class Eval {
	private int[][] board_values;
	private int n;
	private char mysymbol;
	private char opponentsymbol;
	Eval(int size, char symbol, int[][] board_val){
		board_values = board_val;
		n = size;
		mysymbol = Character.toUpperCase(symbol);
		if(symbol == 'X'){
			opponentsymbol = 'O';
		}
		else{
			opponentsymbol = 'X';
		}
	}
	
	public  int eval(char[][] state){
		int myvalue = 0;
		int opponentvalue = 0;
		int gamevalue;
		for(int i=1;i<=n;i++ ){
			for(int j=1;j<=n;j++){
				if(state[i][j] == mysymbol){
					myvalue += board_values[i][j];
				}
				else if(state[i][j] == opponentsymbol){
					opponentvalue += board_values[i][j];
				}
			}
		}
		gamevalue = myvalue - opponentvalue;
		return gamevalue;
	}

	public char getOpponentsymbol() {
		return opponentsymbol;
	}

	public int getN() {
		return n;
	}

	public char getMysymbol() {
		return mysymbol;
	}
}
