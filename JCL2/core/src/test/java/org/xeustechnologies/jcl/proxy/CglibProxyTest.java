package org.xeustechnologies.jcl.proxy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CglibProxyTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		IUserService userService = new UserServiceImpl();
		
		ProxyProvider cglibProxyProvider = new CglibProxyProvider();
	}

}
