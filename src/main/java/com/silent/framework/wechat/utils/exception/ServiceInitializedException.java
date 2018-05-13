package com.silent.framework.wechat.utils.exception;

public class ServiceInitializedException extends Exception {

	private static final long serialVersionUID = 4970382311489065158L;

	public ServiceInitializedException(String message) {
		super(message);
	}
	public ServiceInitializedException(Throwable throwable){
		super(throwable);
	}
	public ServiceInitializedException(String message,Throwable throwable){
		super(message,throwable);
	}

}
