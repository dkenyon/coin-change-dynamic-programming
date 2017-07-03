import java.util.Arrays;

public class CoinChange {

	/*
	 * +-------------------------------+ 
	 * | CHANGE THESE TWO VALUES BELOW |
	 * +-------------------------------+
	 */

	// the TARGET value to find different combinations of
	private static int TARGET = 98; 
	// denomination set of coin values
	private static int[] COIN_VALUES = {1, 5, 10, 25 }; 

	/*
	 * +-------------------------------+ 
	 * | CHANGE THESE TWO VALUES ABOVE |
	 * +-------------------------------+
	 */

	private static int[][] CACHE; // temporary storage to keep track of running
									// totals

	public static void main(String[] theArgs) {
		CACHE = new int[COIN_VALUES.length][TARGET + 1];
		Arrays.sort(COIN_VALUES);

		int solution = solve();
		printCACHE();
		System.out.println("\nSOLUTION: " + solution);


	}

	private static int solve() {
		int solution = 0;
		int currentCoin = COIN_VALUES[0];

		// initialize first row
		for (int col = 0; col < CACHE[0].length; col++) {
			if (col % currentCoin == 0) {
				CACHE[0][col] = 1;
				solution = 1;
			}
		}

		for (int i = 1; i < CACHE.length; i++) {
			currentCoin = COIN_VALUES[i];
			for (int j = 0; j < CACHE[0].length; j++) {
				if (currentCoin > j) {
					CACHE[i][j] = CACHE[i - 1][j];
				} else {
					CACHE[i][j] = CACHE[i - 1][j] + CACHE[i][j - currentCoin];
					if (CACHE[i][j] > solution) {
						solution = CACHE[i][j];
					}
				}
			}
		}

		return solution;
	}

	private static void printCACHE() {
		System.out.print("    ");
		for (int i = 0; i <= TARGET; i++) {
			System.out.printf("%-5d", i);
		}
//		System.out.print("\t <- Coin value");
		System.out.println();
		printBorder();
		
		for (int i = 0; i < CACHE.length; i++) {
			System.out.print(COIN_VALUES[i]);
			System.out.printf("%-3s", " |");
			//System.out.printf("%-4s", (COIN_VALUES[i] + " | "));
			for (int j = 0; j < CACHE[0].length; j++) {
				System.out.printf("%-5d", CACHE[i][j]);
			}
			System.out.printf("%-3s", "|");
			System.out.println();
		}
		printBorder();
		
//		System.out.println("^\n|Coin values");

	}

	private static void printBorder() {
		System.out.print("   ");
		for (int i = 0; i <= TARGET * 5; i++) {
			System.out.print("-");
		}
		System.out.print("----- \n");
	}
}
