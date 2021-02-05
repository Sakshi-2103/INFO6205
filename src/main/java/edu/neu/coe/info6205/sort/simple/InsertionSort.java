/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.simple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Timer;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number  elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort() {
        this(new BaseHelper<>(DESCRIPTION));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        for (int i = from; i < to; i++) {
     
        	for(int j=i; j>0 && (helper.compare(xs[j], xs[j-1])) <0; j--)
        	{
        	helper.swap(xs,j-1, j);
        	}
        }
        // TO BE IMPLEMENTED
    }

    /**
     * This is used by unit tests.
     *
     * @param ys  the array to be sorted.
     * @param <Y> the underlying element type.
     */
    public static <Y extends Comparable<Y>> void mutatingInsertionSort(Y[] ys) {
        new InsertionSort<Y>().mutatingSort(ys);
    }

    public static final String DESCRIPTION = "Insertion sort";
    
    /*
     * Random number generator method
     */
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    
    static Integer elements[] = {500,1000,2000,4000,8000,16000,32000}; // Number of elements that are going to be in an array(Size of the array)
    static Integer array[][]= new Integer[4*elements.length][];  // Array to store all the array of all the sizes
    static {
    	
    /*
     * Generating 4 new random array
     */
    for(int i=0,n=0; n <elements.length;i=i+4,n++)
    {
    	Integer[] array1=new Integer[elements[n]];
    	Integer[] array2=new Integer[elements[n]];
    	Integer[] array3=new Integer[elements[n]];
    	Integer[] array4=new Integer[elements[n]];
    	
    	 for (int j=0;j<elements[n];j++)
  	   {
  	       array1[j]=ThreadLocalRandom.current().nextInt(0,100); 
  	       array2[j]=ThreadLocalRandom.current().nextInt(0,100); 
  	       array3[j]=ThreadLocalRandom.current().nextInt(0,100); 
  	       array4[j]=ThreadLocalRandom.current().nextInt(0,100);  
  		  
  	   }
    	 

	   Arrays.sort(array2); // Sort the second array
	   Arrays.sort(array3); // Sort the third array
	   Collections.reverse(Arrays.asList(array3));  // Reverse the third sorted array
	   Arrays.sort(array4, elements[n]/2, elements[n]); //Partially sort the fourth array 
 
	   /*
	    * Put all the array in a single array
	    * Eg : Array of size 500 is 4(Random, Sorted, Reversed, Partial), so that gets stored in array and again 4 array are stored until the length of element
	    */
	   array[i+0] = array1; 
	   array[i+1] = array2;
	   array[i+2] = array3;
	   array[i+3] = array4;
	   
	  
    }  
	   
  }
    
   public static void main(String[] args) {
	   
	  double time[]=new double[4*elements.length];
	  
	  /*
	   * Start the Bench mark timer class to count the time in  millisecs
	   * Time is printed in this order (Random, Sorted, Reverse, Partial) for each size of n (beginning from 0 to element.length).
	   */
	  
		for(int j=0;j<array.length;j++)
		{
			InsertionSort<Integer> i= new InsertionSort<>();
			Benchmark_Timer<Integer[]> benchmark = new Benchmark_Timer<Integer[]>("running",(x)->i.sort(x,true));
			double timer = benchmark.run(array[j], 2);
			time[j]= timer;
			
		}
		
		//Print an array containing all the time taken by different size and array
		// In order- Random, Sorted, Reverse, Partial for n=500,1000,2000,4000,8000,16000,32000 i.e element.length.
		System.out.println(Arrays.toString(time));
	      
   }
}
	



