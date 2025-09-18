package com.tlg.aps.webService.stakeHolderService.server.impl;

import com.tlg.aps.vo.StakeHolderVo;
import com.tlg.aps.webService.stakeHolderService.server.StakeHolderService;
import com.tlg.exception.SystemException;
import com.tlg.util.StringUtil;
import com.tlg.util.WebserviceObjConvert;

public class StakeHolderServiceImpl implements StakeHolderService{

	private com.tlg.aps.bs.stakeHolderQuery.StakeHolderService stakeHolderService;
	
	@Override
	public String stakeHolderQuery(String str) throws Exception {
		// TODO Auto-generated method stub
		
		if(StringUtil.isSpace(str)){
			throw new SystemException("未傳入任何字串");
		}
		
		StakeHolderVo stakeHolderVo = (StakeHolderVo)WebserviceObjConvert.convertBase64StrToObj(str, StakeHolderVo.class);
		
		stakeHolderVo = stakeHolderService.queryStakeHolder(stakeHolderVo);
		
		String returnStr = WebserviceObjConvert.convertObjToBase64Str(StakeHolderVo.class, stakeHolderVo);
		
		return returnStr;
	}

	public com.tlg.aps.bs.stakeHolderQuery.StakeHolderService getStakeHolderService() {
		return stakeHolderService;
	}

	public void setStakeHolderService(
			com.tlg.aps.bs.stakeHolderQuery.StakeHolderService stakeHolderService) {
		this.stakeHolderService = stakeHolderService;
	}
	
	
}
