package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskIdReqPacket.SendPrpDriskIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskListResPacket.PrpDriskListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskListResPacket.PrpDriskResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDriskById implements
		DataTransformer<SendPrpDriskIdReqPacket, PrpDriskListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDrisk> list = new ArrayList<PrpDrisk>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDriskIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDriskById */
		
		//TODO 在数据字典接口中添加方法（findPrpDriskById）
		list = dictionaryService.findPrpDriskById(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getRISKCODE());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType =ServiceInfoConst.FINDPRPDRISKBYID;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDriskListResPacket responsePacket = new PrpDriskListResPacket();
		if (list.size() == 0) {
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(
					ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(
					ServiceInfoConst.ERROR_MESSAGE_NULL);
			// BusinessException be = new
			// BusinessException(ServiceInfoConst.ERROR_CODE_NULL,
			// ServiceInfoConst.ERROR_MESSAGE_NULL);
			// throw be;
		} else {
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			// 和上级代码的请求类型
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(
					ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(
					ServiceInfoConst.ERRORMSG_SUCCESS);
			List<PrpDriskResInfo> temp = new ArrayList<PrpDriskResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDrisk prpDrisk = list.get(i);
				PrpDriskResInfo prpDriskResInfo = new PrpDriskResInfo();
				prpDriskResInfo.setRISKCODE(prpDrisk.getRiskCode());
				prpDriskResInfo.setRISKCNAME(prpDrisk.getRiskCName());
				prpDriskResInfo.setRISKTNAME(prpDrisk.getRiskTName());
				prpDriskResInfo.setRISKENAME(prpDrisk.getRiskEName());
				prpDriskResInfo.setRISKATTRIBUTE(prpDrisk.getRiskAttribute());
				prpDriskResInfo.setSALEAREALEVEL(prpDrisk.getSaleAreaLevel());
				prpDriskResInfo.setSALEAREACODE(prpDrisk.getSaleAreaCode());
				prpDriskResInfo.setMATERIALCONTXT(prpDrisk.getMaterialContxt());
				prpDriskResInfo.setCLASSCODE(prpDrisk.getClassCode());
				prpDriskResInfo.setFRAMECODE(prpDrisk.getFrameCode());
				prpDriskResInfo.setRISKSCNAME(prpDrisk.getRiskSCName()); //新增加的字段，上面注释的为删除的字段start...
				prpDriskResInfo.setRISKSENAME(prpDrisk.getRiskSEName());
				prpDriskResInfo.setPOLICYPROCESSFLAG(prpDrisk.getPolicyProcessFlag());
				prpDriskResInfo.setREQUIREDFLAG(prpDrisk.getRequiredFlag());
				if(prpDrisk.getRateUnit()!= null){
					prpDriskResInfo.setRATEUNIT(prpDrisk.getRateUnit().toString());
				}
				else{
					prpDriskResInfo.setRATEUNIT("");
				}
				prpDriskResInfo.setSHORTRATEFLAG(prpDrisk.getShortRateFlag());
				prpDriskResInfo.setCLASSFLAG(prpDrisk.getClassFlag());
				prpDriskResInfo.setRISKFLAG(prpDrisk.getRiskFlag());
				prpDriskResInfo.setENDUPDATERCODE(prpDrisk.getEndUpdaterCode());
				prpDriskResInfo.setPROJECTCODE(prpDrisk.getProjectCode());
				if(prpDrisk.getOperateTimeForHis()!= null ){
					prpDriskResInfo.setOPERATETIMEFORHIS(prpDrisk.getOperateTimeForHis().toString());//新增加的字段，上面注释的为删除的字段end...
				}
				else{
					prpDriskResInfo.setOPERATETIMEFORHIS("");
				}
				
//				prpDriskResInfo.setPROJECT(prpDrisk.getProject());
//				prpDriskResInfo.setDOCUMENTIND(prpDrisk.getDocumentInd());
//				prpDriskResInfo.setRENEWIND(prpDrisk.getRenewInd());
//				prpDriskResInfo.setAUTORENEWIND(prpDrisk.getAutoRenewInd());
//				prpDriskResInfo.setCOUNTERACTIND(prpDrisk.getCounteractInd());
//				prpDriskResInfo.setREINSININD(prpDrisk.getReinsInInd());
//				prpDriskResInfo.setGROUPIND(prpDrisk.getGroupInd());
//				if(null!=prpDrisk.getHesitateBackDays()){
//					prpDriskResInfo.setHESITATEBACKDAYS(prpDrisk.getHesitateBackDays().toString());
//				}
				
//				prpDriskResInfo.setDECLARATIONIND(prpDrisk.getDeclarationInd());
				prpDriskResInfo.setPLANIND(prpDrisk.getPlanInd());
//				prpDriskResInfo.setLOWESTPREMIUMIND(prpDrisk.getLowestPremiumInd());
//				prpDriskResInfo.setLOWESTPREMCURRENCY(prpDrisk.getLowestPremCurrency());
//				if(null!=prpDrisk.getLowestPremium()){
//					prpDriskResInfo.setLOWESTPREMIUM(prpDrisk.getLowestPremium().toString());
//				}
				
//				prpDriskResInfo.setAUTORELATEDCLAUSE(prpDrisk.getAutoRelatedClause());
//				prpDriskResInfo.setOPTIONALCLAUSE(prpDrisk.getOptionalClause());
//				prpDriskResInfo.setESTIMATELOSSINDLEVEL(prpDrisk.getEstimatelossindlev());
//				prpDriskResInfo.setCOMPENSATELEVEL(prpDrisk.getCompensateLevel());
				prpDriskResInfo.setACCOUNTLEVEL(prpDrisk.getAccountLevel());
				prpDriskResInfo.setREINSLEVEL(prpDrisk.getReinsLevel());
				prpDriskResInfo.setMANAGEMENTLEVEL(prpDrisk.getManagementLevel());
				prpDriskResInfo.setSTATLEVEL(prpDrisk.getStatLevel());
//				prpDriskResInfo.setDOCUMENTNUMBER(prpDrisk.getDocumentNumber());
//				prpDriskResInfo.setCONTENTNUMBER(prpDrisk.getContentNumber());
//				prpDriskResInfo.setDYNAMICIND(prpDrisk.getDynamicInd());
//				prpDriskResInfo.setTEMPLATERISKCODE(prpDrisk.getTemplateRiskCode());
				prpDriskResInfo.setCREATORCODE(prpDrisk.getCreatorCode());
				if(null!=prpDrisk.getCreateTime()){
					prpDriskResInfo.setCREATETIME(PubFun.DateToStr(prpDrisk.getCreateTime()));
				}
				prpDriskResInfo.setUPDATERCODE(prpDrisk.getUpdaterCode());
				if(null!=prpDrisk.getUpdateTime()){
					prpDriskResInfo.setUPDATETIME(PubFun.DateToStr(prpDrisk.getUpdateTime()));
				}
				if(null!=prpDrisk.getValidDate()){
					prpDriskResInfo.setVALIDDATE(PubFun.DateToStr(prpDrisk.getValidDate()));
				}
				if(null!=prpDrisk.getInvalidDate()){
					prpDriskResInfo.setINVALIDDATE(PubFun.DateToStr(prpDrisk.getInvalidDate()));
				}
				
				prpDriskResInfo.setVALIDIND(prpDrisk.getValidInd());
				prpDriskResInfo.setTCOL1(prpDrisk.getTcol1());
				prpDriskResInfo.setTCOL2(prpDrisk.getTcol2());
				prpDriskResInfo.setTCOL3(prpDrisk.getTcol3());
				prpDriskResInfo.setREMARK(prpDrisk.getRemark());
				prpDriskResInfo.setFLAG(prpDrisk.getFlag());
				temp.add(prpDriskResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDRISKLIST().setPRPDRISK(
					temp.toArray(new PrpDriskResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDriskListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDriskIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDriskIdReqPacket response = (SendPrpDriskIdReqPacket) joxIn
				.readObject(SendPrpDriskIdReqPacket.class);
		return response;
	}
}
