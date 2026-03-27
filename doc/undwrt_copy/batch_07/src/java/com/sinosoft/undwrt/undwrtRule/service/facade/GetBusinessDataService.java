package com.sinosoft.undwrt.undwrtRule.service.facade;

import java.util.List;

import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpQmainProp;
import com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData;

/**
 * 獲取業務數據接口類.
 */
public interface GetBusinessDataService {

	/**
	 * 獲取業務數據.
	 * @param businessNo 業務號
	 * @param businessType 業務類型
	 * @return 業務數據類
	 */
	public BusinessProposalData getBusinessProposalData(String businessNo,
			String businessType);
	/**
	 * 拒保業務獲取報價單和要保書業務數據.
	 * @param businessNo 業務號
	 * @param businessType 業務類型
	 * @return 業務數據類
	 */
	public BusinessProposalData getBusinessData(String businessNo, String businessType);
	/**
	 * 獲取非車業務數據.
	 * @param businessNo 業務號
	 * @param businessType 業務類型
	 * @return 業務數據類
	 * @throws Exception 
	 */
	public BusinessProposalData getUnCarBusinessData(String businessNo, String businessType) throws Exception;
	
	/**
	 * 
	 * @description:TODO
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 * @author wangJun 住火报价单自动核保规则获取业务数据 20150309
	 */
	public BusinessProposalData getLiveFireBusinessData(String businessNo, String businessType) throws Exception;
	
	/**
	 * 
	 * @description:TODO
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 * @author yjm 住火要保书自动核保规则获取业务数据 20150922
	 */
	public BusinessProposalData getTLiveFireBusinessData(String businessNo, String businessType) throws Exception;
	
	/**
	 * 車險要保書自動核保規則獲取業務數據
	 * @param businessNo
	 * @param businessType
	 * @return
	 * @throws Exception
	 */
	public BusinessProposalData getProposalCarAutoBusinessData(String businessNo, String businessType) throws Exception ;
	/**
	 * ADD  BY  MOUJIAXING   TA核保增加TA0A  TA23测试
	 * @param businessNo
	 * @param businessType
	 * @return
	 */
	public String checkUndwrtRules(String businessNo, String businessType);
	/**
	 * 增加四級核保權限校驗
	 * @param businessNo
	 * @return
	 */
	public boolean checkUndwrtRules(String businessNo);
	
	/*
	mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245--- start
	強任費率不一致的的需求
	*/
	/**
	 * 取得車險費率不一致註記 undwrtmark (prptmain/prpqmain)
	 * @param type
	 * @param proposalNo
	 * @return
	 */
	public String getUndwrMark(String type , String proposalNo );
	/* mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 --- end */

	//mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 回寫報價單PRPQMAIN.NOTIFYORNOT ='Y'。
	public void updateNotifyOrNot(String proposalNo )throws Exception;
}
