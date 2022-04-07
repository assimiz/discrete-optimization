package mizrachi.discopt.knapsack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicProgrammingSolver extends AbstractKnapsackSolver {

	Map<List<Integer>, Integer> solved = new HashMap<>();
	
	@Override
	protected void run() {
		this.value = optimize(this.capacity, this.items - 1);
		this.taken = new int[items];
		Arrays.fill(this.taken, 0);
		//first item of the list is the capacity, second is the item
		int currentCapacity = this.capacity;
		int currentItem = this.items - 1;
		while (currentItem != -1) {
			List<Integer> kjTuple = Arrays.asList(currentCapacity, currentItem);
			List<Integer> kjTupleWithoutCurrentItem = Arrays.asList(currentCapacity, currentItem - 1);
			if (solved.get(kjTuple).intValue() != solved.get(kjTupleWithoutCurrentItem).intValue()) {
				currentCapacity -= this.weights[currentItem];
				this.taken[currentItem] = 1;
			}
			currentItem -= 1;
		}
		
	}
	
	public int optimize(int k, int j) {
		List<Integer> kjTuple = Arrays.asList(k, j);
		if (solved.containsKey(kjTuple)) {
			return solved.get(kjTuple);
		}
		int result;
		if (j == -1) {
			result = 0;
		}
		
		else if (this.weights[j] <= k) {
			result = Math.max(optimize(k, j-1), this.values[j] + optimize(k - weights[j], j-1));
		}
		
		else {
			result = optimize(k, j-1);
		}
		
		solved.put(kjTuple, result);		
		return result;
	}
}
