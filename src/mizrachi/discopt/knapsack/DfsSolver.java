package mizrachi.discopt.knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DfsSolver extends AbstractKnapsackSolver {

	private List<Item> valuePerWeight;

	@Override
	protected void run() {
		valuePerWeight = new ArrayList<>(items);
		for (int i = 0; i < items; i++) {
			valuePerWeight.add(new Item(i, this.values[i], this.weights[i]));
		}
		Collections.sort(valuePerWeight, Collections.reverseOrder());

		DfsNode firstItem = new DfsNode(0, 0, this.capacity);

	}

	private class DfsNode {
		int currentItem;
		int currentValue;
		int currentCapacity;
		double currentEstimatedValue;
		//TODO we should receive only taken and calculate all values
		int[] currentTaken;

		public DfsNode(int currentItem, int currentValue, int currentCapacity) {
			super();
			this.currentItem = currentItem;
			this.currentValue = currentValue;
			this.currentCapacity = currentCapacity;
			calcEstimatedValue();
		}

		private void calcEstimatedValue() {
			int remainedCapacity = this.currentCapacity;
			for (int i = 0; i <= currentItem; i++) {
				if (DfsSolver.this.taken[i] == 1) {
					this.currentEstimatedValue += DfsSolver.this.values[i];
					remainedCapacity -= DfsSolver.this.weights[i];
				}
			}
			for (Item item : valuePerWeight) {
				if (item.i > currentItem) {
					if (this.currentCapacity + DfsSolver.this.weights[item.i] <= remainedCapacity) {
						this.currentEstimatedValue += DfsSolver.this.values[item.i];
						remainedCapacity -= DfsSolver.this.weights[item.i];
					} else {
						double fractionalValue = (remainedCapacity / (double) DfsSolver.this.weights[item.i]) * DfsSolver.this.values[item.i];
						this.currentEstimatedValue += fractionalValue;
						remainedCapacity = 0;
						break;
					}
				}
			}
		}
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
			return (int) ((this.v / (double) this.w) - (o.v / (double) o.w));
		}
	}
}
