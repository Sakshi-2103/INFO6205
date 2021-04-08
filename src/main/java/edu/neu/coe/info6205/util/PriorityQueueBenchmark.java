/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.util;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import edu.neu.coe.info6205.pq.PriorityQueue;


public class PriorityQueueBenchmark {

    public PriorityQueueBenchmark(Config config) {
        this.config = config;
    }

    public static void main(String[] args) throws IOException {
        Config config = Config.load(PriorityQueueBenchmark.class);  
        PriorityQueueBenchmark b = new PriorityQueueBenchmark(config);
        
        b.benchmarkStringSortersInstrumented(4000, 100);
        b.benchmarkStringSortersInstrumented(8000, 200);
        b.benchmarkStringSortersInstrumented(16000, 300);
        b.benchmarkStringSortersInstrumented(32000, 400);
        
        
        b.benchmarkIntSortersInstrumented(4000, 100);
        b.benchmarkIntSortersInstrumented(8000, 200);
        b.benchmarkIntSortersInstrumented(16000, 300);
        b.benchmarkIntSortersInstrumented(32000, 400);
    }

    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i <numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10.
            for(int j = 0; j <word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }
    
    public static Integer[] generateRandomNumbers(int max) {
    	ArrayList<Integer> numbers = new ArrayList<Integer>();   
    	Random randomGenerator = new Random();
    	while (numbers.size() < max) {

    	    int random = randomGenerator .nextInt(max);
    	    if (!numbers.contains(random)) {
    	        numbers.add(random);
    	    }
    	}
    	Integer[] arr = numbers.toArray(new Integer[0]);
    	return arr;
    }



    /**
     * Method to run instrumented string sorter benchmarks.
     * <p>
     * NOTE: this is package-private because it is used by unit tests.
     *
     * @param num s the number source.
     * @param nRuns  the number of runs.
     */
    void benchmarkIntSortersInstrumented(int nums, int nRuns) {
        logger.info("Testing with " + formatWhole(nRuns) + " runs of sorting " + formatWhole(nums) + " numbers");
        	runIntegerPriorityQueueBenchmark(generateRandomNumbers(nums), nRuns,new PriorityQueue<Integer>(nums,Comparator.naturalOrder() ,config));
    }
    
    /**
     * Method to run instrumented string sorter benchmarks.
     * <p>
     * NOTE: this is package-private because it is used by unit tests.
     *
     * @param words  the word source.
     * @param nWords the number of words to be sorted.
     * @param nRuns  the number of runs.
     */
    void benchmarkStringSortersInstrumented(int nWords, int nRuns) {
        logger.info("Testing with " + formatWhole(nRuns) + " runs of sorting " + formatWhole(nWords) + " words");
        	runStringPriorityQueueBenchmark(generateRandomWords(nWords), nWords, nRuns,new PriorityQueue<String>(nWords, Comparator.comparing(String::toString), config));
    }
   
    
    /**
     * Method to run a sorting benchmark using the standard preProcess method of the sorter.
     *
     * @param words       an array of available words (to be chosen randomly).
     * @param nWords      the number of words to be sorted.
     * @param nRuns       the number of runs of the sort to be preformed.
     *                    <p>
     *                    NOTE: this method is public because it is referenced in a unit test of a different package
     */
    public static void runStringPriorityQueueBenchmark(String[] words, int nWords, int nRuns, PriorityQueue<String> pq) {
    	int countWords=0;
    for(int n=1;n<=nRuns;n++)
    {
    	countWords= countWords+pq.consume(words);
    	//System.out.println("The number of compares used to delete words present in PQ in " +n+"th runs is "+pq.consume(words));
    }
    System.out.println("The Average number of compares used to delete all the words present in PQ in " +nRuns+" runs is "+countWords/nRuns);
    System.out.println();
    }
    
    /**
     * Method to run a sorting benchmark using the standard preProcess method of the sorter.
     *
     * @param nums	      an array of available numbers (to be chosen randomly).
     * @param nRuns       the number of runs of the sort to be preformed.
     *                    <p>
     *                    NOTE: this method is public because it is referenced in a unit test of a different package
     */
    public static void runIntegerPriorityQueueBenchmark(Integer[] nums, int nRuns, PriorityQueue<Integer> pq) {
    	int countInt=0;
    for(int n=1;n<=nRuns;n++)
    {
    	countInt= countInt+pq.consume(nums);  
    }
    System.out.println("The Average number of compares used to delete all the numbers present in PQ in " +nRuns+" runs is " +countInt/nRuns);
    System.out.println();
    }

    final static LazyLogger logger = new LazyLogger(PriorityQueueBenchmark.class);

    private final Config config;
}
