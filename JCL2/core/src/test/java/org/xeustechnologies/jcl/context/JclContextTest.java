package org.xeustechnologies.jcl.context;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JclContextTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 单例测试
	 */
	@Test
	public void test() {
		JclContext ctx1 = JclContext.getInstance();
		System.out.println(ctx1);
		
		JclContext ctx2 = JclContext.getInstance();
		System.out.println(ctx2);
		
		assertEquals(ctx1, ctx2);
	}
	

}
