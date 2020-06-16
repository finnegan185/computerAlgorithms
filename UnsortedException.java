/* File:	UnsortedException.java
 * Author:	Zachary Finnegan
 * Date:	4/9/2020
 * Purpose:	Custom exception to be thrown when an array is unsorted.
 */

package computerAlgorithms;

public class UnsortedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UnsortedException(String message) {
		super(message);
	}

}
