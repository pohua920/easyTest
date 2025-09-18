package com.tlg.aps.bs.reinsInwardService.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardNo;
import com.tlg.prpins.service.ReinsInwardNoService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;


@Transactional(value="prpinsTransactionManager", propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
public class ReinsInwardNoServiceImpl {
	
	private ReinsInwardNoService reinsInwardNoService;

	
	public synchronized String getReinsInwardNo(String type, String poins)
			throws SystemException, Exception {

		if(StringUtil.isSpace(type) || StringUtil.isSpace(poins)){
			throw new SystemException("請傳入類型及出單險種！");
		}
		String currentNo = "";
		//年度
		String year = new SimpleDateFormat("yyyy").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		params.put("year", year);
		params.put("type", type);
		params.put("poins", poins);
		Result result = this.reinsInwardNoService.findReinsInwardNoByParams(params);
		if(result.getResObject() == null){
			currentNo = "1";
			//回傳1號，並新增2號回去
			ReinsInwardNo reinsInwardNo = new ReinsInwardNo();
			reinsInwardNo.setNo("2");
			reinsInwardNo.setPoins(poins);
			reinsInwardNo.setType(type);
			reinsInwardNo.setYear(year);
			
			result = this.reinsInwardNoService.insertReinsInwardNo(reinsInwardNo);
			if(result.getResObject() == null){
				throw new SystemException("取號失敗(1)");
			}
			return currentNo;
		}
		ArrayList<ReinsInwardNo> reinsInwardNoList = (ArrayList<ReinsInwardNo>)result.getResObject();
		ReinsInwardNo reinsInwardNo = (ReinsInwardNo)reinsInwardNoList.get(0); //正常應該只有1筆
		currentNo = reinsInwardNo.getNo();
		
		//更新
		reinsInwardNo.setNo(String.valueOf(Integer.parseInt(currentNo) + 1));
		this.reinsInwardNoService.updateReinsInwardNo(reinsInwardNo);
		if(result.getResObject() == null){
			throw new SystemException("取號失敗(2)");
		}
		return currentNo;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public ReinsInwardNoService getReinsInwardNoService() {
		return reinsInwardNoService;
	}


	public void setReinsInwardNoService(ReinsInwardNoService reinsInwardNoService) {
		this.reinsInwardNoService = reinsInwardNoService;
	}

}
