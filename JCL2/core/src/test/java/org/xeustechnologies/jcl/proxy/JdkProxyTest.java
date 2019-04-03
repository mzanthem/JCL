package org.xeustechnologies.jcl.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * jdk dynamic proxy test
 * @author MSH8244
 *
 */
public class JdkProxyTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		IUserService target = new UserServiceImpl();
		
		ProxyProvider jdkProxyProvider = new JdkProxyProvider();
		
		IUserService proxyObject = (IUserService) jdkProxyProvider.createProxy(target, 
				target.getClass().getSuperclass(), 
				target.getClass().getInterfaces(), 
				JdkProxyTest.class.getClassLoader()
		);
		
		proxyObject.sayHello();
	}

}
