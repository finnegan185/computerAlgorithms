/* File:	MyMergeSort.java
 * Author:	Zachary Finnegan
 * Date:	4/9/2020
 * Purpose:	This is the class that holds the iterative and recursive sorting
 * 			methods along with the getCount and getTime methods. The main class
 * 			calls this class to do the merge sorts and get the efficiency info.
 * 
 * 			I decided that counting the initial merging operation was the way to go
 * 			to find the efficiency. Counting the times the divide part was always
 * 			the same. Which makes sense. Thus it did NOT make sense to use that
 * 			as the thing to count for efficiency. I could have counted all merge
 * 			operations but that didn't seem like an effective way of getting a 
 * 			more specific efficiency count number as it counts everything and
 * 			isn't representative of the efficiency of the actual merge sorting.
 * 			I think. By counting only the main merge and not the left over merges
 * 			I believe I am getting at the root sort and merge efficiency of the 
 * 			program. The only issue is that I am not 100% sure if a higher
 * 			count number in the main merge is better or worse than having
 * 			a higher count in the left over merges...
 */

package computerAlgorithms;



public class MyMergeSort implements SortInterface {
	private long startTime;
	private long time;
	private int count;
	private int arrayLength;
	
	public void mergeSortStart(int[] array) throws UnsortedException {
		/*
		 * Method that is called by BenchmarkSorts when the recursive method
		 * is requested to be performed. This method calls the actual recursive
		 * sort methods.
		 */
		count = 0;
		arrayLength = array.length;
		startTime = System.nanoTime();
		recursiveMergeSort(array);
	}
	
	public void mergeSortStart(int[] array, int n) throws UnsortedException {
		/*
		 * Method that is called by BenchmarkSorts when the iterative method
		 * is requested to be performed. This method calls the actual iterative
		 * sort methods.
		 */
		count = 0;
		startTime = System.nanoTime();
		iterativeMergeSort(array, n);
	}
	
	
	/* -----------------------------------------------------------------------------------------
	 * RECURSIVEMERGESORT, ITERATIVE MERGESORT AND MERGE WERE TAKEN FROM                       |
	 * GEEKSFORGEEKS.ORG. SPECIFICALLY FROM https://www.geeksforgeeks.org/iterative-merge-sort/|
	 * -----------------------------------------------------------------------------------------
	 */
	
	
	@Override
	public void recursiveMergeSort(int[] array) throws UnsortedException {
		if(array == null) {
			return;
		}
		
		if(array.length > 1) {
			int mid = array.length / 2;
			
			// Split left part
			int[] left = new int[mid];
			for(int i = 0; i < mid; i++) {
				left[i] = array[i];
			}
			
			// Split right part
			int[] right = new int[array.length - mid];
			for(int i = mid; i < array.length; i++) {
				right[i - mid] = array[i];
			}
			recursiveMergeSort(left);
			recursiveMergeSort(right);
			
			int i = 0;
			int j = 0;
			int k = 0;
			
			// Merge left and right arrays
			while(i < left.length && j < right.length) { 
				count++; // I decided the main merging operation was the critical
						 // component to count.
				if(left[i] < right[j]) { 
					array[k] = left[i]; 
	                i++; 
				} else { 
	                array[k] = right[j]; 
	                j++; 
	            } 
	            k++; 
			} 
			
			// Collect remaining elements
			while(i < left.length) {
				array[k] = left[i];
				i++;
				k++;
			}
			while(j < right.length) {
				array[k] = right[j];
				j++;
				k++;
			}
		}
		if(array.length == arrayLength) {
			time = System.nanoTime() - startTime;
			if(!isSorted(array, array.length)) {
				throw new UnsortedException("Array of length " + array.length + " was returned unsorted.");
			}
		}
	}

	@Override
	public void iterativeMergeSort(int[] array, int n) throws UnsortedException {
		int currSize;
		int left;
		
		// Merge subarrays in bottom up manner.
		for(currSize = 1; currSize <= n-1; currSize = 2*currSize) {
			for(left = 0; left < n-1; left += 2*currSize) {
				int mid = Math.min(left + currSize - 1, n-1);
				int right = Math.min(left + 2*currSize - 1, n-1);
				//System.out.println("count = " + count + " left = " + left + " mid = " + mid + " right = " + right);
				// Merge Subarrays
				merge(array, left, mid, right);
			}
		}
		
		//establishes the run time of each iterative mergesort and tests if the array is
		// actually sorted.
		time = System.nanoTime() - startTime;
		if(!isSorted(array, n)) {
			throw new UnsortedException("Array of length " + n + " was returned unsorted.");
		}
	}

	@Override
	public void merge(int[] array, int left, int mid, int right) {
		/*
		 * Method used to merge the broken down arrays in a sorted manner.
		 * Used with the Iterative MergeSort method.
		 */
		int i, j, k;
		int n1 = mid - left + 1;
		int n2 = right - mid;
		//System.out.println("n1 = " + n1 + " n2 = " + n2);
		
		// Temp Arrays
		int leftArr[] = new int[n1];
		int rightArr[] = new int[n2];
		// Copy data to temp arrays
		for(i = 0; i < n1; i++) {
			leftArr[i] = array[left + i];
		}
		for(j = 0; j < n2; j++) {
			rightArr[j] = array[mid + 1 + j];
		}
		//System.out.println("left array = " + Arrays.toString(leftArr));
		//System.out.println("right array = " + Arrays.toString(rightArr));
		
		// Merge temp arrays back into array 
		i = 0;
		j = 0;
		k = left;
		while(i < n1 && j < n2) {
			count++; // I decided the main merging operation was the critical
					 // component to count.
			if(leftArr[i] <= rightArr[j]) {
				array[k] = leftArr[i];
				i++;
			}else {
				array[k] = rightArr[j];
				j++;
			}
			k++;
		}
		
		// Copy remaining elements of left array to array.
		while(i < n1) {
			array[k] = leftArr[i];
			i++;
			k++;
		}
		
		// Copy remaining elements of right array to array.
		while(j < n2) {
			array[k] = rightArr[j];
			j++;
			k++;
		}
		//System.out.println("array = " + Arrays.toString(array));
	}
	
	

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public long getTime() {
		return time;
	}
	
	// Method to test if the 'sorted arrays' are actually sorted.
	static boolean isSorted(int[] array, int n) {  
		if (n == 1 || n == 0) {return true;}
	    	if (array[n - 1] < array[n - 2]) {return false;} 
	       	return isSorted(array, n - 1); 
	}
}
