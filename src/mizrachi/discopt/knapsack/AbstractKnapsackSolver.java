package mizrachi.discopt.knapsack;

import java.util.Arrays;

public abstract class AbstractKnapsackSolver implements KnapsackSolver {

	//in
	protected int items;
	@SuppressWarnings("unused")
	protected int capacity;
	@SuppressWarnings("unused")
	protected int[] values;
	@SuppressWarnings("unused")
	protected int[] weights;
	
	//out
	protected int value;
	protected int[] taken;
	

	protected boolean debug = false;
	
	@Override
	public void solve (int items, int capacity, int[] values, int[] weights) {
		this.items = items;
		this.capacity = capacity;
		this.values = values;
		this.weights = weights;
		this.value = 0;
		this.taken = new int[items];
		Arrays.fill(this.taken, 0);
		
		double startTime = System.currentTimeMillis();
		run();
		print();
		double endTime = System.currentTimeMillis();
		if (debug) {
			System.out.println("Computation time[s] = " + (endTime - startTime) / 1000);
		}
	}

	
	abstract protected void run();
	abstract protected boolean isOptimal();
	
	private void print() {
		// prepare the solution in the specified output format
		System.out.println(value + (isOptimal() ? " 1" : " 0"));
		for (int i = 0; i < items; i++) {
			System.out.print(taken[i] + " ");
		}
		System.out.println("");
	}

}
