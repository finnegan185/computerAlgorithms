/* File:	BenchmarkSorts.java
 * Author:	Zachary Finnegan
 * Date: 	4/9/2020
 * Purpose:	This is the main class of Project 1. It creates the unsorted 
 * 			arrays and calls the mergesort methods in MyMergeSort.java.
 * 			It also creates the csv files to hold the array size and 
 * 			efficiency information of each of the 50 arrays per each of the
 * 			10 sizes of arrays. Size,count,time,count,time,count,time
 */

package computerAlgorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class BenchmarkSorts {
	static int[] array;	
	
	public static void main(String args[]) {
		try {
			BufferedWriter iterWriter = new BufferedWriter(new FileWriter("iterMergeSortDataSmol.csv"));
			BufferedWriter recurWriter = new BufferedWriter(new FileWriter("recurMergeSortDataSmol.csv"));
			MyMergeSort sorty = new MyMergeSort();
			//warms up the functions
			warmUp(sorty);
			for(int n = 5; n <= 500; n+=5) {
				iterWriter.append(Integer.toString(n));
				recurWriter.append(Integer.toString(n));

				for(int i = 0; i < 500; i++) {
					createRandArray(n);
					sorty.mergeSortStart(array, n);
					iterWriter.append("," + sorty.getCount());
					iterWriter.append("," + sorty.getTime());
					sorty.mergeSortStart(array);
					recurWriter.append("," + sorty.getCount());
					recurWriter.append("," + sorty.getTime());
				}
				iterWriter.append("\n");
				recurWriter.append("\n");
			}
			iterWriter.flush();
			iterWriter.close();
			recurWriter.flush();
			recurWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(UnsortedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void createRandArray(int n) {
		Random rand = new Random();
		array = new int[n];
		for(int i = 0; i < n; i++) {
			array[i] = rand.nextInt(1000);
		}

	}
	
	private static void warmUp(MyMergeSort sorty) throws UnsortedException {
		/* I found that without running this method the runtimes of the mergesorts
		 * were much higher. I believe this is an effective warm up.  
		 */
		createRandArray(1000);
		for(int i = 0; i < 1000; i++) {
			sorty.mergeSortStart(array);
			sorty.mergeSortStart(array, 1000);
		}
	}
	
	
}
