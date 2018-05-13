package com.silent.framework.wechat.utils.exception;

public class ServiceRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4021595747711018391L;
	public ServiceRuntimeException(Throwable e){
		super(e);
	}
	public ServiceRuntimeException(String message){
		super(message);
	}
	public ServiceRuntimeException(String string, Throwable e) {
		super(string,e);
	}
	public ServiceRuntimeException() {
	}
}
