
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MergeSort extends RecursiveTask<List<Pair>> {

	private static final long serialVersionUID = 1L;

	public static int TH = 100;
	private Pair[] array;
	private int low;
	private int high;

	private int min;
	private int max;

	public MergeSort(Pair[] a, int l, int h, int minN, int maxN) {
		array = a;
		low = l;
		high = h;

		min = minN;
		max = maxN;
	}

	public List<Pair> compute() {
		List<Pair> list = new ArrayList<>();
		// CASO BASE
		if ((high - low) < TH) {
			for (int i = low; i < high; i++) {
				if (array[i].getScore() >= min && array[i].getScore() <= max)
					list.add(array[i]);
			}
		}
		// CASO RECURSIVO
		else {
			int mid = low + ((int) (high - low) / 2);
			MergeSort left = new MergeSort(array, low, mid, min, max);
			MergeSort right = new MergeSort(array, mid, high, min, max);

			left.fork();
			List<Pair> listR = right.compute();
			List<Pair> listL = left.join();

			list.addAll(listR);
			list.addAll(listL);
		}
		return list;
	}
}
