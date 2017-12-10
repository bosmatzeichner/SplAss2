package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


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
		try {
			promise.get();
		} catch (IllegalStateException exp) {
			promise.resolve(6);
			try {
				int temp = promise.get();
				assertEquals(temp, 6);
			} catch (Exception e) {
				fail("The resolved value should've been equal to 6");
			}
		} catch (Exception exp) {
			fail("The method should've thrown an IllegalStateException");
		}
	}

	@Test
	public void testIsResolved() {
		assertEquals("The initial value should be FALSE", false, promise.isResolved());
		try {
			promise.resolve(123);
			assertEquals("The expected value is TRUE", true, promise.isResolved());
		} catch (Exception e) {
			fail("Some issue with resolve(): " + e.getMessage());
		}
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
				fail();
			} catch (IllegalStateException exp) {
				int x = promise.get();
				assertEquals(x, 5);
			} catch (Exception exp) {
				fail();
			}
		} catch (Exception exp) {
			fail();
		}
		assertEquals("the call function has not executed by resolved", true, callAssure[0]);
	}

	@Test
	public void testSubscribe() {
		try {
			promise.resolve(1);
			boolean[] callAssure = new boolean[1];
			callback call = () -> {
				callAssure[0] = true;
			};
			promise.subscribe(call);
			assertEquals("the call function has not executed by subscribe", true, callAssure[0]);
		} catch (Exception e) {
			fail("Some issue with resolve(): " + e.getMessage());
		}
	}

}