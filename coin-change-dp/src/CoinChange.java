import java.util.Arrays;

/**
 * Solves for and prints to the console two variants of the class coin change dynamic programming problem.
 * Also prints 2D cache array to help check work.
 * Note: As of Jul 2017, min coin method is bugged if there is no coin value for 1.
 * Edit the variables VALUE and COIN_VALUES
 * @author dkenyon
 *
 */
public class CoinChange {
	
	// the value to find different combinations/min number of coins for
	private static int VALUE = 12;

	// denomination set of coin values
	private static int[] COIN_VALUES = { 1, 2, 5 };

	// temporary storage to keep track of running totals
	private static int[][] CACHE = new int[COIN_VALUES.length][VALUE + 1];;

	// main
	public static void main(String[] theArgs) {
		// set of coin values in ascending order
		Arrays.sort(COIN_VALUES);

		solveMaxCombinations();
		solveMinCoins();
	}

	// solves the task of finding the different number of
	// coin combinations to get the VALUE coin value.
	// populates the cache and returns the solution.
	private static int solveMaxCombinations() {
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
		for (int row = 1; row < CACHE.length; row++) {
			currentCoin = COIN_VALUES[row];
			for (int col = 0; col < CACHE[0].length; col++) {
				if (currentCoin > col) {
					CACHE[row][col] = CACHE[row - 1][col];
				} else {
					CACHE[row][col] = CACHE[row - 1][col] + CACHE[row][col - currentCoin];
					if (CACHE[row][col] > solution) {
						solution = CACHE[row][col];
					}
				}
			}
		}
		System.out.println("Max number of combinations to make " + VALUE + " with coin values " + Arrays.toString(COIN_VALUES) + ": " + solution + "\n");
		printCacheToConsole();	
		System.out.println("\n\n");
		return solution;
	}

	// solves the task of finding the minimum number of
	// coins to get the VALUE coin value.
	// populates the cache and returns the solution.
	// BUG 2017-07-03: does not work right if there is no 1-value coin
	private static int solveMinCoins() {
		int solution = Integer.MAX_VALUE;
		int currentCoin = COIN_VALUES[0];

		// initialize first row
		for (int col = 0; col < CACHE[0].length; col++) {
			if (col % currentCoin == 0) {
				CACHE[0][col] = col / currentCoin;
				solution = col / currentCoin;
			} else {
				CACHE[0][col] = CACHE[0][col];
			}
		}

		// populate array and solve
		for (int row = 1; row < CACHE.length; row++) {
			currentCoin = COIN_VALUES[row];
			for (int col = 0; col < CACHE[0].length; col++) {
				if (currentCoin > col) {
					CACHE[row][col] = CACHE[row - 1][col];
				} else {
					CACHE[row][col] = Math.min(CACHE[row - 1][col], CACHE[row][col - currentCoin] + 1);
				}
			}
		}

		solution = CACHE[CACHE.length - 1][CACHE[0].length - 1];
		System.out.println("Min number of coins to make " + VALUE + " with coin values " + Arrays.toString(COIN_VALUES) + ": " +solution + "\n");
		printCacheToConsole();
		System.out.println("\n\n");
		return solution;
	}

	// prints the 2D cache array to the console
	private static void printCacheToConsole() {
		// print indexes
		System.out.print("       ");
		for (int row = 0; row <= VALUE; row++) {
			System.out.printf("%-5d", row);
		}
		System.out.println();
		System.out.print(printBorder());

		// print each coin value checked and each row of the cache
		for (int row = 0; row < CACHE.length; row++) {
			System.out.printf("%-2d", COIN_VALUES[row]);
			System.out.printf("%-5s", " |");
			for (int col = 0; col < CACHE[0].length; col++) {
				System.out.printf("%-5d", CACHE[row][col]);
			}
			System.out.print("|");
			System.out.println();
		}

		System.out.print(printBorder());
	}

	// creates a string of hyphens ('-') based off the VALUE's size
	private static String printBorder() { 
		StringBuilder string = new StringBuilder();

		string.append("   +");
		for (int row = 0; row <= VALUE * 5; row++) {
			string.append("-");
		}
		string.append("-------+\n");

		return string.toString();
	}
}
