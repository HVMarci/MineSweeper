package Minesweeper;

public class ArrayHelper {
	public int getIndexInArray(Object[] array, Object obj) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == obj) return i;
		}
		
		return -1;
	}
}
