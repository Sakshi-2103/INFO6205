package edu.neu.coe.info6205.pq;

import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.PrivateMethodTester;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class PriorityQueueTest {

	private Config config;

	@Before
	public void init() {
		try {
			config = Config.load(PriorityQueueTest.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMax() {

		PriorityQueue<String> pq = new PriorityQueue<>(10, false, Comparator.comparing(String::toString),config);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(false, tester.invokePrivate("getMax"));
	}

	@Test
	public void testUnordered1(){
		String[] binHeap = new String[3];
		binHeap[1] = "A";
		binHeap[2] = "B";
		boolean max = false;
		PriorityQueue<String> pq = new PriorityQueue<>(max, binHeap, 2, Comparator.comparing(String::toString),config);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(max, tester.invokePrivate("unordered", 1, 2));
	}

	@Test
	public void testUnordered2(){
		String[] binHeap = new String[3];
		binHeap[1] = "A";
		binHeap[2] = "B";
		boolean max = true;
		PriorityQueue<String> pq = new PriorityQueue<>(max, binHeap, 2, Comparator.comparing(String::toString),config);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(max, tester.invokePrivate("unordered", 1, 2));
	}

	@Test
	public void testSwimUp(){
		String[] binHeap = new String[3];
		String a = "A";
		String b = "B";
		binHeap[1] = a;
		binHeap[2] = b;
		PriorityQueue<String> pq = new PriorityQueue<>(true, binHeap, 2, Comparator.comparing(String::toString),config);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(a, tester.invokePrivate("peek", 1));
		tester.invokePrivate("swimUp", 2);
		assertEquals(b, tester.invokePrivate("peek", 1));
	}

	@Test
	public void testSink(){
		String[] binHeap = new String[4];
		String a = "A";
		String b = "B";
		String c = "C";
		binHeap[1] = a;
		binHeap[2] = b;
		binHeap[3] = c;
		PriorityQueue<String> pq = new PriorityQueue<>(true, binHeap, 3, Comparator.comparing(String::toString),config);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		tester.invokePrivate("sink", 1);
		assertEquals(c, tester.invokePrivate("peek", 1));
		assertEquals(a, tester.invokePrivate("peek", 3));
	}

	@Test
	public void testGive1() {

		PriorityQueue<String> pq = new PriorityQueue<>(10, Comparator.comparing(String::toString),config);
		String key = "A";
		pq.give(key);
		assertEquals(1, pq.size());
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(key, tester.invokePrivate("peek", 1));
	}

	@Test
	public void testGive2(){
		// Test that we can comfortably give more elements than the the PQ has capacity
		// for
		PriorityQueue<String> pq = new PriorityQueue<>(1, Comparator.comparing(String::toString),config);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		String key = "A";
		pq.give(null); // This will never survive so it might as well be null
		assertEquals(1, pq.size());
		assertNull(tester.invokePrivate("peek", 1));
		pq.give(key);
		assertEquals(1, pq.size());
		assertEquals(key, tester.invokePrivate("peek", 1));
	}

	@Test
	public void testTake1() throws PQException{

		PriorityQueue<String> pq = new PriorityQueue<>(10, Comparator.comparing(String::toString),config);
		String key = "A";
		pq.give(key);
		assertEquals(key, pq.take());

		assertTrue(pq.isEmpty());

	}

	@Test
	public void testTake2() throws PQException {

		PriorityQueue<String> pq = new PriorityQueue<>(10, Comparator.comparing(String::toString),config);
		String a = "A";
		String b = "B";
		pq.give(a);
		pq.give(b);
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(a, tester.invokePrivate("peek", 2));
		assertEquals(b, tester.invokePrivate("peek", 1));
		assertEquals(b, pq.take());
		assertEquals(a, pq.take());

		assertTrue(pq.isEmpty());

	}
	
	@Test
	public void testTake4() throws PQException {

		PriorityQueue<String> pq = new PriorityQueue<>(40, Comparator.comparing(String::toString),config);
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g = "G";
		String h = "H";
		String i = "I";
		String j = "J";
		String k = "K";
		String l = "L";
		String m = "M";
		String n = "N";
		String o = "O";
		String p = "P";
		
		pq.give(b);
		pq.give(c);
		pq.give(d);
		pq.give(e);
		pq.give(f);
		pq.give(g);
		pq.give(h);
		pq.give(i);
		pq.give(j);
		pq.give(k);
		pq.give(l);
		pq.give(m);
		pq.give(n);
		pq.give(o);
		pq.give(p);
		
		int compares = pq.getCompareCount();
		int swaps = pq.getSwapCount();
		
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(p, pq.take());
		
		compares = pq.getCompareCount() - compares;
		swaps = pq.getSwapCount() - swaps;
		
		
		System.out.println("\nTake4 : " + "Comparisons : " + compares + " : Swaps : " + swaps);
	}
	
	@Test
	public void testTake5() throws PQException {

		PriorityQueue<String> pq = new PriorityQueue<>(40, Comparator.comparing(String::toString),config);
	
		String s = "S";
		String j = "J";
		String l = "L";
		String k = "K";
		String d = "D";
		String c = "C";
		String b = "B";
		String a = "A";
		String n = "N";
		String m = "M";
		String f = "F";
		String e = "E";
		String y = "Y";
		String g = "G";
		String z = "Z";
		
		pq.give(s);
		pq.give(j);
		pq.give(l);
		pq.give(k);
		pq.give(d);
		pq.give(c);
		pq.give(b);
		pq.give(a);
		pq.give(n);
		pq.give(m);
		pq.give(f);
		pq.give(e);
		pq.give(y);
		pq.give(g);
		pq.give(z);
		
		int compares = pq.getCompareCount();
		int swaps = pq.getSwapCount();
		
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		assertEquals(z, pq.take());
		
		compares = pq.getCompareCount() - compares;
		swaps = pq.getSwapCount() - swaps;
		
		
		System.out.println("\nTake5 : " + "Comparisons : " + compares + " : Swaps : " + swaps);
	}
	
	@Test
	public void testTake6() throws PQException {

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(40, Comparator.naturalOrder(),config);
		
		pq.give(5);
		pq.give(1);
		pq.give(6);
		pq.give(3);
		pq.give(7);
		pq.give(2);
		pq.give(0);
		pq.give(4);
		
		int compares = pq.getCompareCount();
		int swaps = pq.getSwapCount();
		
		final PrivateMethodTester tester = new PrivateMethodTester(pq);
		pq.take();
		pq.take();
		pq.take();
		pq.take();
		pq.take();
		pq.take();
		pq.take();
		pq.take();
		
		compares = pq.getCompareCount() - compares;
		swaps = pq.getSwapCount() - swaps;
		
		
		System.out.println("\nTake6 : " + "Comparisons : " + compares + " : Swaps : " + swaps);
	}

	@Test(expected = PQException.class)
	public void testTake3() throws PQException {

		PriorityQueue<String> pq = new PriorityQueue<>(10, Comparator.comparing(String::toString),config);
		pq.give("A");
		pq.take();
		pq.take();
	}
}
