package com.tlg.util;

import org.dozer.DozerBeanMapper;

public final class DozerMapper {

	private static DozerBeanMapper instance = new DozerBeanMapper();// 使用預初始化避免並發問題.

	private DozerMapper() {
		
	}

	public static DozerBeanMapper getInstance() {
		return instance;
	}
}
