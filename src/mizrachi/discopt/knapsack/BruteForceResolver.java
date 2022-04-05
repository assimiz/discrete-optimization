package mizrachi.discopt.knapsack;
import java.util.Arrays;

public class BruteForceResolver extends AbstractKnapsackSolver {

	@Override
	protected void run() {
		
		this.value = 0;
		
		for (int j = 0; j < Math.pow(2, items); j++) {
			int[] take = toBinaryArray(j);
			
			int val = 0;
			int weight = 0;
			for (int i = 0; i < items; i++) {
				weight += take[i] * weights[i];
				val += take[i] * values[i];
			}
			
			if (val > value && weight <= this.capacity) {
				this.value = val;
				this.taken = take;
			}
		}

	}
	
	

	// Function converting decimal to binary
	private int[] toBinaryArray(int num) {
		// Creating and assigning binary array size
		int[] binary = new int[items];
		Arrays.fill(binary, 0);
		
		int i = items - 1;

		// Number should be positive
		while (num > 0) {
			binary[i--] = num % 2;
			num = num / 2;
		}
		
		return binary;
	}

}
