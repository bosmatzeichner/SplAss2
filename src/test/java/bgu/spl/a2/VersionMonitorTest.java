package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VersionMonitorTest {
	VersionMonitor verMonitor;

	@Before
	public void setUp() throws Exception {
		verMonitor = new VersionMonitor();
	}

	@After
	public void tearDown() throws Exception {
		verMonitor = null;
		assertNull(verMonitor);
	}

	@Test
	public void testGetVersion() {
		assertEquals("The initial value should be 0", 0, verMonitor.getVersion());
		verMonitor.inc();
		assertEquals("The initial value should be 1", 1, verMonitor.getVersion());
	}

	@Test
	public void testInc() {
		verMonitor.inc();
		assertEquals("The initial value should be 1", 1, verMonitor.getVersion());
	}

	@Test
	public void testAwait() {
		final boolean[] testArray = new boolean[2];
		Thread testThread1 = new Thread(() -> {
			try {
				verMonitor.await(0);
				fail("await function should have throw exception");
			} catch (InterruptedException e) {
				testArray[0] = true;
			}
		});
		Thread testThread2 = new Thread(() -> {
			try {
				verMonitor.await(3);
				testArray[1] = true;
			} catch (InterruptedException e) {
				fail("await function threw an undemend exception");
			}
		});
		
				
		///////////////test await (current version number)/////////////////
		
		testThread1.start();
		try {
			Thread.sleep(100);
			assertEquals("testThread1 should be waiting", testThread1.getState(), Thread.State.WAITING);
			verMonitor.inc();
			try {
				Thread.sleep(100);
				assertEquals("testThread1 shouldn't be waiting anymore", testThread1.getState(),
						Thread.State.TERMINATED);
				assertEquals("testThread1 function has not been completed", true, testArray[0]);
				assertEquals("testThread1 should be waiting", testThread1.getState(), Thread.State.TERMINATED);
			} catch (InterruptedException e) {
				fail("an undemend exception has been thrown while put the main thread asleep");
			}
		} catch (InterruptedException e) {
			fail("an undemend exception has been thrown while put the main thread asleep");
		}
		
		
		////////test await (an unequal number tocurrent version number)////////
		
		testThread2.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		assertEquals("testThread2 should not be waiting at all", testThread2.getState(), Thread.State.TERMINATED);
		assertEquals("testThread2 function has not been completed", true, testArray[1]);

		////////////////////////////////////////////////
		System.out.println(
				"thred 2 state is: " + testThread2.getState() + "  ,  thread1 state is:  " + testThread1.getState());

	}
}