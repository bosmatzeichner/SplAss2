package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

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

	/*
	 * @Test public void testGetVersion() {
	 * Assert.assertEquals("The initial value should be 0", 0,
	 * verMonitor.getVersion()); verMonitor.inc();
	 * Assert.assertEquals("The initial value should be 1", 1,
	 * verMonitor.getVersion()); }
	 * 
	 * @Test public void testInc() { verMonitor.inc();
	 * Assert.assertEquals("The initial value should be 1", 1,
	 * verMonitor.getVersion()); }
	 */
	@Test
	public void testAwait() {
		final boolean[] testArray = new boolean[1];
		// System.out.println(testArray[0]);
		Thread testThread = new Thread(new Runnable() {
			public void run() {
				try {
					verMonitor.await(0);
				} catch (InterruptedException e) {
					// testArray[0] = true;
					System.out.println("!!! " + testArray[0]);
				}
				testArray[0] = true;

			}
		});

		testThread.start();
		verMonitor.inc();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

		assertEquals(true, testArray[0]);
	}

}
