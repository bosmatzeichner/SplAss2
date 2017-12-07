package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class PromiseTest {
	Promise<Integer> promise;

	@Before
	public void setUp() throws Exception {
		this.promise = new Promise<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		promise = null;
		assertNull(promise);
	}

	@Test
	public void testGet() {
		assertNull("promise hasn't resolved yet", promise.get());
		// testResolve();
		promise.resolve(1);
		int resolveVal = promise.get();
		assertEquals(1, resolveVal);
	}

	@Test
	public void testIsResolved() {
		assertEquals(false, promise.isResolved());
		// testResolve();
		promise.resolve(1);
		assertEquals(true, promise.isResolved());
	}

	@Test
	public void testResolve() {

		final boolean[] callAssure = new boolean[1];
		callback call = () -> {
			callAssure[0] = true;
		};
		// testSubscribe();
		promise.subscribe(call);
		assertEquals("the call function unreasonably executed", false, callAssure[0]);
		try {
			promise.resolve(5);
			try {
				promise.resolve(6);
				Assert.fail();
			} catch (IllegalStateException exp) {
				int x = promise.get();
				assertEquals(x, 5);
			} catch (Exception exp) {
				Assert.fail();
			}
		} catch (Exception exp) {
			Assert.fail();
		}
		assertEquals("the call function has not executed by resolved", true, callAssure[0]);
	}

	@Test
	public void testSubscribe() {
		// testResolve();
		promise.resolve(1);
		boolean[] callAssure = new boolean[1];
		callback call = () -> {
			callAssure[0] = true;
		};
		promise.subscribe(call);
		assertEquals("the call function has not executed by subscribe", true, callAssure[0]);
	}

}
