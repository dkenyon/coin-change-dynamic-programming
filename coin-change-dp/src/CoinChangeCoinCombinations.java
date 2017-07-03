import java.util.Arrays;

public class CoinChangeCoinCombinations {

	// the TARGET value to find different combinations of
	private static int TARGET = 31;

	// denomination set of coin values
	private static int[] COIN_VALUES = { 2, 5, 14 };

	// temporary storage to keep track of running totals
	// # of rows is the # of elements in COIN_VALUES
	// # of columns is TARGET
	private static int[][] CACHE;

	public static void main(String[] theArgs) {
		// initialize cache and sort COIN_VALUES
		CACHE = new int[COIN_VALUES.length][TARGET + 1];
		Arrays.sort(COIN_VALUES);

		int solution = solve();
		printCacheToConsole();
		System.out.println("\nSOLUTION: " + solution);

	}

	// populates the cache and returns the solution
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

		// populate array and solve
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

	
	private static void printCacheToConsole() {
		// print indexes
		System.out.print("       ");
		for (int i = 0; i <= TARGET; i++) {
			System.out.printf("%-5d", i);
		}
		System.out.println();
		System.out.print(printBorder());
		
		//print each coin value checked and each row of the cache
		for (int i = 0; i < CACHE.length; i++) {
			System.out.printf("%-2d", COIN_VALUES[i]);
			System.out.printf("%-5s", " |");
			for (int j = 0; j < CACHE[0].length; j++) {
				System.out.printf("%-5d", CACHE[i][j]);
			}
			System.out.print("|");
			System.out.println();
		}
		
		System.out.print(printBorder());
	}

	// creates a string of hyphens ('-') based off the target's size
	private static String printBorder() {
		StringBuilder string = new StringBuilder();
		
		string.append("   +");
		for (int i = 0; i <= TARGET * 5; i++) {
			string.append("-");
		}
		string.append("-------+\n");
		
		return string.toString();
	}
}
