package com.tlg.commons.util.api.rest.blockChain.util;

import ins.framework.common.ServiceFactory;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.service.facade.PrpLuserService;
import com.tlg.commons.util.api.rest.blockChain.vo.TokenVo;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class TokenUtil {

	
	public static String generateToken(String userCode){
		String tokenStr = "";
		try{
			if("".equals(userCode) || userCode == null){
				throw new Exception("無法取得使用者帳號");
			}
			
			PrpLuserService prpLuserService = ((PrpLuserService)ServiceFactory.getService("prpLuserService"));
			PrpLuser prpLuser = prpLuserService.findPrpLuserByUserCode(userCode);
			if(prpLuser == null){
				throw new Exception("無法取得使用者帳號");
			}			
			TokenVo tvo = new TokenVo();
			tvo.setEmployeeId(prpLuser.getEmail());
			tvo.setExtension(prpLuser.getExt());
			tvo.setName(prpLuser.getUserName());
			tvo.setPhone(prpLuser.getTel());
			ObjectMapper objectMapper = new ObjectMapper();
			StringWriter jsonStr = new StringWriter();  
			objectMapper.writeValue(jsonStr, tvo);
			tokenStr = Base64.encodeBase64String(jsonStr.toString().getBytes("UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return tokenStr;
	}
	//mantis：CLM0277_TEST ，處理人員： DP0713 ，需求單編號：區塊鏈生成 user資訊的 不送
	public static void main(String []arg) throws JsonGenerationException, JsonMappingException, IOException{
		String tokenStr = null;
		TokenVo tvo = new TokenVo();
		tvo.setEmployeeId("CE020@ctbcins.com");
		tvo.setExtension("5060");
		tvo.setName("楚成業");
		tvo.setPhone("0223700789");
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter jsonStr = new StringWriter();  
		objectMapper.writeValue(jsonStr, tvo);
		tokenStr = Base64.encodeBase64String(jsonStr.toString().getBytes("UTF-8"));
		System.out.println("tokenStr:"+tokenStr);
	}
}
