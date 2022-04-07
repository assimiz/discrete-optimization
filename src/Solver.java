
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mizrachi.discopt.knapsack.BruteForceResolver;
import mizrachi.discopt.knapsack.DynamicProgrammingSolver;
import mizrachi.discopt.knapsack.GreedySolver;
import mizrachi.discopt.knapsack.KnapsackSolver;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to
 * solve the knapsack problem.
 *
 */
public class Solver {

	/**
	 * The main class
	 */
	public static void main(String[] args) {
		try {
			solve(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the instance, solve it, and print the solution in the standard output
	 */
	public static void solve(String[] args) throws IOException {
		String fileName = null;
		KnapsackSolver knapsackSolver = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}

			if (arg.startsWith("-solver=")) {
				String solver = arg.substring(8);
				if (solver.equals("greedy")) {
					knapsackSolver = new GreedySolver();
				} else if (solver.equals("brute")) {
					knapsackSolver = new BruteForceResolver();
				} else if (solver.equals("dynamic")) {
					knapsackSolver = new DynamicProgrammingSolver();
				}
			}
		}
		if (fileName == null) {
			System.err.println("Missing file argument. Use -file=<file_name>");
			return;
		}

		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String[] firstLine = lines.get(0).split("\\s+");
		int items = Integer.parseInt(firstLine[0]);
		int capacity = Integer.parseInt(firstLine[1]);

		int[] values = new int[items];
		int[] weights = new int[items];

		for (int i = 1; i < items + 1; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");

			values[i - 1] = Integer.parseInt(parts[0]);
			weights[i - 1] = Integer.parseInt(parts[1]);
		}
		

		if (knapsackSolver == null) {
			if (items <= 200) {
				knapsackSolver = new DynamicProgrammingSolver();
			}
			
			else {
				knapsackSolver = new GreedySolver();
			}
		}

		knapsackSolver.solve(items, capacity, values, weights);

	}
}