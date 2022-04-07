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
	
	@Override
	public void solve (int items, int capacity, int[] values, int[] weights) {
		this.items = items;
		this.capacity = capacity;
		this.values = values;
		this.weights = weights;
		this.value = 0;
		this.taken = new int[items];
		Arrays.fill(this.taken, 0);
		
		run();
		print();
	}

	
	abstract protected void run();
	
	private void print() {
		// prepare the solution in the specified output format
        System.out.println(value+" 0");
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");
	}

}
