package com.tlg.db2.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.As400InrcfilDao;
import com.tlg.db2.entity.As400Inrcfil;
import com.tlg.db2.service.As400InrcfilService;
import com.tlg.exception.SystemException;
import com.tlg.msSqlRdm.entity.Inrcfil;
import com.tlg.msSqlRdm.service.InrcfilService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/**
 *mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
 */
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class As400InrcfilServiceImpl implements As400InrcfilService{

	private As400InrcfilDao as400InrcfilDao;
	private InrcfilService inrcfilService;

	@Override
	public int countInrcfil(Map params) throws SystemException, Exception {
		return as400InrcfilDao.count(params);
	}
	
	
	/**
	 * 再保帳單資料拋轉傳回AS400系統
	 */
	@Override
	public Result inrcfilRdmToAs400() throws Exception{
		Result result = new Result();
		
		//查詢松凌再保帳單資料
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		params.put("execute", "Y");
		List<Inrcfil> inrcfils =  inrcfilService.findByParams(params);
		
		if (null == inrcfils || 0 == inrcfils.size()) {//查無資料
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		} 
		
		List<As400Inrcfil> as400Inrcfils = new ArrayList<As400Inrcfil>();
		for(Inrcfil inrcfil : inrcfils){
			now = new Date();
			inrcfil.setTransDate(now);//松凌批次轉檔時間
			
			As400Inrcfil as400Inrcfil = new As400Inrcfil();
			BeanUtils.copyProperties(inrcfil, as400Inrcfil);
			as400Inrcfil.setInrcno(inrcfil.getBatchNo());//批次號
			as400Inrcfil.setInrc09("");
			as400Inrcfil.setInrc10(BigDecimal.ZERO);
			as400Inrcfil.setInrc11(BigDecimal.ZERO);
			if(inrcfil.getInrc21() != null){
				String inrc21 = sdf2.format(inrcfil.getInrc21());
				as400Inrcfil.setInrc21(new BigDecimal(Integer.parseInt(inrc21)-19110000));
			} else {
				as400Inrcfil.setInrc21(BigDecimal.ZERO);
			}
			if(inrcfil.getInrc24() != null){
				String inrc24 = sdf2.format(inrcfil.getInrc24());
				as400Inrcfil.setInrc24(new BigDecimal(Integer.parseInt(inrc24)-19110000));
			} else {
				as400Inrcfil.setInrc24(BigDecimal.ZERO);
			}
			if(inrcfil.getInrc28() != null){
				String inrc28 = sdf2.format(inrcfil.getInrc28());
				as400Inrcfil.setInrc28(inrc28);
			}
			as400Inrcfil.setInrctt(new BigDecimal(sdf.format(now)));//批次轉檔時間
			as400Inrcfils.add(as400Inrcfil);
			
		}
	
	
		//寫入AS400
		for(As400Inrcfil  as400Inrcfil : as400Inrcfils){
			as400InrcfilDao.insert(as400Inrcfil);
		}
		
		
		//更新松凌批次轉檔時間
		for(Inrcfil inrcfil : inrcfils){
			inrcfilService.updateInrcfil(inrcfil);
			
		}
		
		
		//成功訊息
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(as400Inrcfils);
		
		return result;
	}

	public As400InrcfilDao getAs400InrcfilDao() {
		return as400InrcfilDao;
	}

	public void setAs400InrcfilDao(As400InrcfilDao as400InrcfilDao) {
		this.as400InrcfilDao = as400InrcfilDao;
	}


	public InrcfilService getInrcfilService() {
		return inrcfilService;
	}


	public void setInrcfilService(InrcfilService inrcfilService) {
		this.inrcfilService = inrcfilService;
	}

	
	
}
