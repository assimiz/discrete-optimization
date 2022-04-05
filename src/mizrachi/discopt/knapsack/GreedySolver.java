package mizrachi.discopt.knapsack;

public class GreedySolver extends AbstractKnapsackSolver {

	@Override
	protected void run() {
		// // a trivial greedy algorithm for filling the knapsack
		// it takes items in-order until the knapsack is full
		this.value = 0;
		int weight = 0;
		this.taken = new int[items];

		for (int i = 0; i < items; i++) {
			if (weight + weights[i] <= capacity) {
				taken[i] = 1;
				value += values[i];
				weight += weights[i];
			} else {
				taken[i] = 0;
			}
		}

	}

}
