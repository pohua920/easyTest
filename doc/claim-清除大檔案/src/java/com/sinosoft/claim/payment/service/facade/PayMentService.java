package com.sinosoft.claim.payment.service.facade;

import java.util.Map;

/**
 * 收付接口
 * @author 中科软
 */
public abstract interface PayMentService {

	/**
	 * 数据传输
	 * @param paramString1 参数1
	 * @param paramString2 参数2
	 * @param infoMap 传入参数
	 * @throws Exception
	 */
	public abstract void transData(String paramString1, String paramString2, Map<?, ?> infoMap) throws Exception;

	/**
	 * 数据传输
	 * @param paramString1 参数1
	 * @param paramString2 参数2
	 * @throws Exception
	 */
	public abstract void send(String paramString1, String paramString2) throws Exception;
}