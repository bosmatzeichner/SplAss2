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

	@Test
	public void testGetVersion() {
		Assert.assertEquals("The initial value should be 0", 0, verMonitor.getVersion());
		verMonitor.inc();
		Assert.assertEquals("The initial value should be 1", 1, verMonitor.getVersion());
	}

	@Test
	public void testInc() {
		verMonitor.inc();
		Assert.assertEquals("The initial value should be 1", 1, verMonitor.getVersion());
	}

	@Test
	public void testAwait() {
		
		fail("Not yet implemented");
	}

}
