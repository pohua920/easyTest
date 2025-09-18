package com.tlg.aps.webService.claimBlockChainService.client.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.webService.claimBlockChainService.client.vo.TokenVo;
import com.tlg.prpins.entity.PrpLuser;
import com.tlg.prpins.service.PrpLuserService;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

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
			
			PrpLuserService prpLuserService = (PrpLuserService)AppContext.getBean("prpLuserService");
			Map<String, String> params = new HashMap<String, String>();
			params.put("usercode", userCode);
			Result result = prpLuserService.findPrpLuserByParams(params);
			if(result.getResObject() == null){
				throw new Exception("無法取得使用者資料");
			}
			ArrayList<PrpLuser> prpLuserList = (ArrayList<PrpLuser>)result.getResObject();
			PrpLuser prpLuser = prpLuserList.get(0);
			TokenVo tvo = new TokenVo();
			tvo.setEmployeeId(prpLuser.getEmail());
			tvo.setExtension(prpLuser.getExt());
			tvo.setName(prpLuser.getUsername());
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
}
