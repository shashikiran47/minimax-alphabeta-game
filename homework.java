import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class homework {
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("input.txt");
		Scanner sc = new Scanner(f);
		int n = sc.nextInt();
		sc.nextLine();
		int[][] board_values = new int[n+1][n+1]; 
		String board_state_current = new String();
		char[][] board_state = new char[n+1][n+1];
		String mode = sc.nextLine();
		String youplay = sc.nextLine();
		char mysymbol = youplay.charAt(0);
		int depth = sc.nextInt();
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				board_values[i][j] = sc.nextInt();
			}
		}
		Eval evalobj = new Eval(n, mysymbol, board_values);
		sc.nextLine();
		int countPieces = 0;
		for(int i=1;i<=n;i++){
			board_state_current = sc.nextLine();
			for(int j=1;j<=n;j++){
				board_state[i][j] = board_state_current.charAt(j-1);
				if( board_state[i][j] != '.'){
					countPieces++;
				}
			}
		}
		MinMax minmaxobj = new MinMax(evalobj, board_state, mode, depth);
		
		File op = new File("output.txt");
		FileOutputStream fos;
	   	try {
	   	fos = new FileOutputStream(op);
	   	PrintStream ps = new PrintStream(fos);
	   	System.setOut(ps);
	   	} catch (FileNotFoundException e) {
	   	// TODO Auto-generated catch block
	   	e.printStackTrace();
	   	} 
	   	long startTime = System.currentTimeMillis();
	   	minmaxobj.calculate(countPieces);
	   	long endTime   = System.currentTimeMillis();
		double totalTime = (endTime - startTime)/1000;
		//System.out.println("time : " + totalTime);

	}

}
