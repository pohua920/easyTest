package com.tlg.aps.webService.creditCardBlackList.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.tlg.aps.vo.CreditCardBlackListVo;
import com.tlg.aps.webService.creditCardBlackList.server.CreditCardBlackListService;
import com.tlg.db2.entity.Com580WK;
import com.tlg.db2.service.Com580WKService;
import com.tlg.exception.SystemException;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.WebserviceObjConvert;

public class CreditCardBlackListServiceImpl implements CreditCardBlackListService{

	private Com580WKService com580WKService;

	
	@Override
	public String creditCardBlackListQuery(String str) throws Exception {
		CreditCardBlackListVo creditCardBlackListVo = null;
		String returnStr = "";
		try{
			if(StringUtil.isSpace(str)){
				throw new SystemException("未傳入任何字串");
			}
			

			creditCardBlackListVo = (CreditCardBlackListVo)WebserviceObjConvert.convertBase64StrToObj(str, CreditCardBlackListVo.class);
			//基本檢核
			String cardNo = StringUtil.nullToSpace(creditCardBlackListVo.getCreditCardNo());
			
			if(StringUtil.isSpace(cardNo)){
				throw new SystemException("請輸入信用卡卡號！");
			}
			if(cardNo.length() < 15){
				throw new SystemException("請輸入完整信用卡卡號！");
			}
			
			if(cardNo.length() == 16){
				if(!check16DigitalCard(cardNo)){
					throw new SystemException("信用卡卡號錯誤！");
				}
			}
			if(cardNo.length() == 15){
				if(!checkAECard(cardNo)){
					throw new SystemException("信用卡卡號錯誤！");
				}
			}
			
			String resultCode = "00"; //不在黑名單內
			String resultMsg = "不在黑名單內"; //不在黑名單內
			
			String rocStr = DateUtils.getTaiwanDateString(new Date()).replaceAll("/", "");
			Map<String,String> params = new HashMap<String,String>();
			params.put("wk01", creditCardBlackListVo.getCreditCardNo());
			params.put("geWk02", rocStr);
			params.put("leWk04", rocStr);
			Result result = com580WKService.findCom580WKByParams(params);
			if(result.getResObject() != null){
				//有在黑名單內
				resultCode = "01";
				resultMsg = "此張信用卡在本公司已受管控，請於上班時間洽詢服務人員";
				ArrayList<Com580WK> com580WKList = (ArrayList<Com580WK>)result.getResObject();
				Com580WK com580WK = com580WKList.get(0);
				//迄日相同時
				if(Integer.parseInt(rocStr) == com580WK.getWk04().intValue()){
					String timeStr = DateUtils.getDateTime(new Date(), "");
					if(Integer.parseInt(timeStr) <= com580WK.getWk06().intValue()){
						//有在黑名單內，且迄日為當天可解除管制
						resultCode = "02";
						resultMsg = "此張信用卡在本公司已受管控，請於上班時間洽詢服務人員";
					}else{
						//因為時間已超過會查不到資料，所以不在黑名單內
						resultCode = "00"; //不在黑名單內
						resultMsg = "不在黑名單內"; //不在黑名單內
					}
				}
			}
			creditCardBlackListVo.setResultCode(resultCode);
			creditCardBlackListVo.setResultMsg(resultMsg);
			returnStr = WebserviceObjConvert.convertObjToBase64Str(CreditCardBlackListVo.class, creditCardBlackListVo);
		}catch(SystemException se){
			se.printStackTrace();
			creditCardBlackListVo.setResultCode("99");
			creditCardBlackListVo.setResultMsg("發生異常：" + se.getMessage());
			returnStr = WebserviceObjConvert.convertObjToBase64Str(CreditCardBlackListVo.class, creditCardBlackListVo);
		}catch(Exception e){
			e.printStackTrace();
			creditCardBlackListVo.setResultCode("99");
			creditCardBlackListVo.setResultMsg("發生異常：" + e.getMessage());
			returnStr = WebserviceObjConvert.convertObjToBase64Str(CreditCardBlackListVo.class, creditCardBlackListVo);
		}
		return returnStr;
	}
	
	public boolean check16DigitalCard(String cardNo){
		int sum1 = 0, sum2 = 0;
        for(int i = cardNo.length() - 1, j = 1;i >= 0;i--, j++) {
        	//System.out.println("i = " + i + ", j = " + j + "," + cardNo.charAt(i));
            if(j % 2 == 1){
            	int num = cardNo.charAt(i) - '0';
                sum1 += num;
            }else {
                int temp = (cardNo.charAt(i) - '0') * 2;
                if(temp >= 10){
                	temp = temp - 9;
                }
                sum2 += temp;
            }
        }
        if((sum1 + sum2) % 10 == 0){
            return true;
        }else{
        	return false;
        }
	}
	
	public boolean checkAECard(String cardNo){
		int sum1 = 0, sum2 = 0;
        for(int i = cardNo.length() - 1, j = 1;i >= 0;i--, j++) {
            if(j % 2 == 0){
            	int temp = (cardNo.charAt(i) - '0') * 2;
                if(temp >= 10)
                    temp = temp - 9;
                sum2 = sum2 + temp;
            }else {
            	sum1 = sum1 + (cardNo.charAt(i) - '0');
            }
        }
        if((sum1 + sum2) % 10 == 0){
            return true;
        }else{
        	return false;
        }
	}


	public Com580WKService getCom580WKService() {
		return com580WKService;
	}


	public void setCom580WKService(Com580WKService com580wkService) {
		com580WKService = com580wkService;
	}
	
	
}
