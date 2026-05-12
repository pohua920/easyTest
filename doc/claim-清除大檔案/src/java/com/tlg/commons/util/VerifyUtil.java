package com.tlg.commons.util;

import ins.framework.common.ServiceFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.app.common.service.facade.PlatConfigRuleService;

//mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
/**
 * 驗證輸入資料用 
 * 如:身分證字號、車號、地址 等...
 * @author bk007
 *
 */
public class VerifyUtil {

	private static final Logger log = LoggerFactory.getLogger(VerifyUtil.class);

	/**
	 * 驗證身分證字號、居留證、法人
	 * @see P:\01.需求變更\理賠\CLM0040.外來人口統一證號格式修正\證號檢核web Service.docx
	 * @param identifyNumber
	 * @return 
	 * result from webService 
	 * JSONObject.isEmpty == true 是異常;
	 * @category mantis： CLM0040，處理人員：BK007 蘇哲，需求單編號：CLM0040 外來人口統一證號格式修正
	 */
	public static final JSONObject verifyIdentifyNumber(final String identifyNumber) {
		log.info("start verifyIdentifyNumber:" + identifyNumber);
		if(StringUtils.isBlank(identifyNumber)){
			return new JSONObject();
		}
		final String url = ((PlatConfigRuleService)ServiceFactory.getService("platConfigRuleService")).getPlatConfigRule("VERIFY_ID_WS_URL", "1").trim();
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url+identifyNumber);
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + method.getStatusLine());
			} else {
				return JSONObject.fromString(new String(method.getResponseBody(),Charset.forName("UTF-8")));
			}
		} catch (HttpException e) {
			log.error("Fatal protocol violation: " + e.getMessage());
		} catch (IOException e) {
			log.error("Fatal transport error: " + e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return new JSONObject();
	}

}
