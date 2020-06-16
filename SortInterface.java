/* File:	SortInterface.java
 * Author:	Zachary Finnegan
 * Date:	4/9/2020
 * Purpose:	Interface of merge sort and efficiency methods implemented
 * 			by MyMergeSort.java
 */

package computerAlgorithms;

public interface SortInterface {
	void recursiveMergeSort(int[] array) throws UnsortedException;
	void iterativeMergeSort(int[] array, int n) throws UnsortedException;
	void merge(int[] array, int l, int m, int r);
	int getCount();
	long getTime();
}
