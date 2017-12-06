package bgu.spl.a2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class PromiseTest {
	Promise<Integer> promise;

	@Before
	public void setUp() throws Exception {
		promise = new Promise<Integer>();
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
				Assert.fail("The resolved value should've been equal to 6");
			}
		} catch (Exception exp) {
			Assert.fail("The method should've thrown an IllegalStateException");
		}
	}

	@Test
	public void testIsResolved() {
		Assert.assertEquals("The initial value should be FALSE", false, promise.isResolved());
		try {
			promise.resolve(123);
			Assert.assertEquals("The expected value is TRUE", true, promise.isResolved());
		} catch (Exception e) {
			Assert.fail("Some issue with resolve(): " + e.getMessage());
		}
	}

	@Test
	public void testResolve() {
		final boolean[] testArray = new boolean[1];
		callback callMe = new callback(){
			public void call() {
				testArray[0] = true;
			}
		};
		try {
			promise.subscribe(callMe);
			promise.resolve(666);
			Assert.assertEquals("The call() function didn't do it's job", testArray[0], true);
			try {
				promise.resolve(999);
				Assert.fail("The resolved value can't be changed once defined");		
			}
			catch (IllegalStateException exp){
				int x = promise.get();
				assertEquals(x, 666);
			}
			catch (Exception e) {
				Assert.fail("The wrong exception was thrown. EXPECTED: IllegalStateException");
			}
		}
		catch (Exception e) {
			Assert.fail("Failed to resolve a value");
		}
		
		assertNull("The callback function should've been changed to null", callMe);

	} 

	@Test
	public void testSubscribe() {
		try {
			promise.resolve(123);
			callback callMe = new callback(){
				
				public void call() {}
				
			};
			promise.subscribe(callMe);
			Assert.assertEquals("The callback function should've been changed to null", null, callMe);
		} catch (Exception e) {
			fail("Issue with the resolve() method: " + e.getMessage());
		}
	}
	
}
