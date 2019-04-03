package org.xeustechnologies.jcl.proxy;

public class UserServiceImpl implements IUserService {

	@Override
	public void sayHello() {
		System.out.println("hello");
	}

	@Override
	public void add(String name) {
		System.out.println("add user to db: " + name + " ");
	}

}
