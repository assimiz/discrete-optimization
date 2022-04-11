package mizrachi.discopt.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DfsSolver extends AbstractKnapsackSolver {

	private List<Item> valuePerWeight;
	private int[] optimalTaken;
	private int optimalValue;
	
	private int[] currentTaken;
	
	//In variables
	private int currentItem; //current items index

	//Calculated variables
	private int currentValue; //the value of the items taken so far
	private int currentCapacity; //the current capacity remained for next items
	private double currentEstimatedValue; //the best estimated value that can be achieved by this branch	

	@Override
	protected void run() {	
		//init data structures
		this.currentTaken = new int[this.items];
		this.optimalTaken = this.currentTaken;
		this.optimalValue = 0;
		//calculate currentEstimatedValue
		calcEstimatedValue();
				
		init();
		dfs(true);
		init();
		dfs(false);
		
		this.value = this.optimalValue;
		this.taken = this.optimalTaken;
	}
	
	private void calcEstimatedValue() {
		valuePerWeight = new ArrayList<>(items);
		for (int i = 0; i < items; i++) {
			valuePerWeight.add(new Item(i, this.values[i], this.weights[i]));
		}
		Collections.sort(valuePerWeight, Collections.reverseOrder());
		int remainedCapacity = this.capacity;
		for (Item item : valuePerWeight) {
			if (remainedCapacity >= this.weights[item.i]) {
				this.currentEstimatedValue += this.values[item.i];
				remainedCapacity -= this.weights[item.i];
			} else {
				double fractionalValue = (remainedCapacity / (double) this.weights[item.i]) * this.values[item.i];
				this.currentEstimatedValue += fractionalValue;
				remainedCapacity = 0;
				break;
			}
		}		
	}
	
	private void init() {
		this.currentItem = 0;
		Arrays.fill(this.currentTaken, 0);
		this.currentValue = 0;
		this.currentCapacity = this.capacity;
	}
	
	@Override
	protected boolean isOptimal() {
		return true;
	}
	
	private void dfs(boolean take) {
		//if this is the last item - return
		if (debug) {
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("Current item = " + currentItem);
			System.out.print("Current taken = ");
			for (int i = 0; i < items; i++) {
				System.out.print(currentTaken[i] + " ");
			}
			System.out.println();
			System.out.println("Current value = " + currentValue);
			System.out.println("Current capacity = " + currentCapacity);
			System.out.println("Estimated value = " + currentEstimatedValue);
			System.out.println("Optimal = " + optimalValue);			
		}
		if (this.currentItem == this.items) {
			return;
		}		
		if (take) {
			//updates item taken
			this.currentTaken[currentItem] = 1;
			this.currentValue += this.values[currentItem];
			this.currentCapacity -= this.weights[currentItem];
			
			if (this.currentCapacity < 0) {
				return;
			}
			//update optimal value
			if (this.currentValue > this.optimalValue) {
				this.optimalTaken = Arrays.copyOf(this.currentTaken, items);
				this.optimalValue = this.currentValue;
			}
		}
		
		else {
			//TODO update this.currentEstimatedValue
		}
		
		this.currentItem += 1;
		dfs(true);
		
		//restore item not taken		
		if (this.currentItem < this.items) {
			this.currentTaken[currentItem] = 0;
			this.currentValue -= this.values[currentItem];
			this.currentCapacity += this.weights[currentItem];
		}
		
		if (this.currentEstimatedValue > this.optimalValue) {
			dfs(false);
		}
		this.currentItem -= 1;
	}

	private class Item implements Comparable<Item> {
		int i;
		int v;
		int w;

		public Item(int i, int v, int w) {
			super();
			this.i = i;
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(Item o) {
			double diff = (this.v / (double) this.w) - (o.v / (double) o.w);
			if (diff == 0) {
				return 0;
			} else if (diff > 0) {
				return 1;
			} else {
				return -1;
			}
		}

		@Override
		public String toString() {
			return "Item [i=" + i + ", v=" + v + ", w=" + w + ", v/w=" + v /(double) w + "]";
		}
	}
}
