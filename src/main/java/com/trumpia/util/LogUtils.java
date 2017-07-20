package com.trumpia.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

	public static Logger getLogger(Object instance) {
		return LogManager.getLogger(instance.getClass());
	}
	public static Logger getLogger(Class<?> classType) {
		return LogManager.getLogger(classType);
	}
}