package com.trumpia.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	public static Logger getLogger(Object instance) {
		return LoggerFactory.getLogger(instance.getClass());
	}
	public static Logger getLogger(Class<?> classType) {
		return LoggerFactory.getLogger(classType);
	}
}