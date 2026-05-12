package com.sinosoft.claimCertify;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.vicp.vissoft.util.CodeUtils;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.AppConfig;

public class CertifyTreeXml {

	/**
	 * 单证参数
	 * @param comCode部门
	 * @param userCode用户
	 * @param businessNo业务号
	 * @return
	 * @throws Exception
	 */
	public String getParamString(String comCode, String userCode, String businessNo, String show) throws Exception {
		if (show == null) {
			show = "";
		}
		String IP = AppConfig.get("sysconst.NewCertify_IP" + show);
		String typeTreeXMLCharset = AppConfig.get("sysconst.NewCertify_typeTreeXMLCharset");
		String pathFormat = AppConfig.get("sysconst.NewCertify_pathFormat");
		String pathPrefix = AppConfig.get("sysconst.NewCertify_pathPrefix");
		StringBuffer buffer = new StringBuffer();
		buffer.append("fileTransServiceUrl=").append(IP).append("/filemanager/services/FileTrans");
		buffer.append("&fileIndexServiceUrl=").append(IP).append("/filemanager/services/FileIndex");
		buffer.append("&userCode=").append(userCode);
		buffer.append("&operatorCode=").append(userCode);
		buffer.append("&comCode=").append(comCode);
		buffer.append("&bussNo=").append(businessNo);
		buffer.append("&codebase=" + IP + "/filemanager/fileupload");
		buffer.append("&property1=");
		buffer.append("&property2=");
		buffer.append("&property3=");
		buffer.append("&property4=");
		buffer.append("&property5=");
		buffer.append("&typeTreeXMLCharset=" + typeTreeXMLCharset);
		buffer.append("&pathFormat=" + pathFormat);
		buffer.append("&pathPrefix=" + pathPrefix);
		return buffer.toString();
	}

	/**
	 * 单证树入口,非车险
	 * @param prpLcertifyCollectDto
	 * @param prpLcertifyDirectDtoList
	 * @return
	 */
	public String getCertifyTree(PrpLcertifyCollect prpLcertifyCollect, List prpLcertifyDirectList) {
		Element sysElement = DocumentHelper.createElement("SysType");
		sysElement.addAttribute("catalog", "true");
		sysElement.addAttribute("code", "claim");
		sysElement.addAttribute("name", "理賠業務");
		Element businessNoElement = sysElement.addElement("BusinessNo");
		businessNoElement.addAttribute("catalog", "true");
		businessNoElement.addAttribute("code", prpLcertifyCollect.getId().getBusinessNo());
		businessNoElement.addAttribute("name", prpLcertifyCollect.getId().getBusinessNo());
		Element subTypeElement = businessNoElement.addElement("SubType");
		subTypeElement.addAttribute("catalog", "true");
		subTypeElement.addAttribute("code", "01");
		subTypeElement.addAttribute("name", "基本訊息");
		if (prpLcertifyDirectList != null && prpLcertifyDirectList.size() > 0) {
			PrpLcertifyDirect newPrpLcertifyDirect = null;
			for (int i = 0; i < prpLcertifyDirectList.size(); i++) {
				newPrpLcertifyDirect = (PrpLcertifyDirect) prpLcertifyDirectList.get(i);
				Element subTypeDetailElement = subTypeElement.addElement("SubTypeDetail");
				subTypeDetailElement.addAttribute("code", newPrpLcertifyDirect.getTypeCode());
				subTypeDetailElement.addAttribute("name", newPrpLcertifyDirect.getTypeName());
			}
		}
		// System.out.println(sysElement.asXML());
		return CodeUtils.byteArrayToHexString(sysElement.asXML().toString().getBytes(), false);
	}

	/**
	 * 单证树入口,车险
	 * @param prpLcertifyCollectDto
	 * @param prpLcertifyDirectDtoList
	 * @param prpLthirdPartyDtoList
	 * @param prpLpersonTraceDtoList
	 * @param nodeType
	 * @return
	 */
	public String getCertifyTree(PrpLcertifyCollect prpLcertifyCollect, List prpLcertifyDirectList, List prpLthirdPartyList, List prpLpersonTraceList, String nodeType) {
		Element sysElement = DocumentHelper.createElement("SysType");
		sysElement.addAttribute("catalog", "true");
		sysElement.addAttribute("code", "claim");
		sysElement.addAttribute("name", "理賠業務");
		Element businessNoElement = sysElement.addElement("BusinessNo");
		businessNoElement.addAttribute("catalog", "true");
		businessNoElement.addAttribute("code", prpLcertifyCollect.getId().getBusinessNo());
		businessNoElement.addAttribute("name", prpLcertifyCollect.getId().getBusinessNo());
		String dateFlag;
		try {
			dateFlag = (String) AppConfig.get("sysconst.NewCertify_Date");
			DateTime dateTime = new DateTime(new DateTime(dateFlag, DateTime.YEAR_TO_DAY));
			PrpLregistService prpLregistService = (PrpLregistService) ServiceFactory.getService("prpLregistService");
			PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLcertifyCollect.getId().getBusinessNo());
			DateTime reportday = new DateTime(new DateTime(prpLregist.getReportDate(), DateTime.YEAR_TO_DAY));
			long betwDate = reportday.getTime() / (1000 * 60 * 60 * 24) - dateTime.getTime() / (1000 * 60 * 60 * 24);
			if (betwDate <= 0) {
				String[] title = { "", "索賠申請", "保險單證", "事故處理單證", "法院提供單證", "保車資料", "財產損失資料", "人傷資料", "車輛盜搶資料", "車輛自燃資料", "駕駛證件", "領取賠款證件", "財車資料", "其他資料" };
				String titleCode = null;
				List typeTreeList = null;
				PrpLcertifyDirect newPrpLcertifyDirect1 = null;
				Map<Integer,List> typeTreeMap = new LinkedHashMap<Integer,List>(prpLcertifyDirectList.size());
				int typeCode = 0;
				for (int y = 0; y < prpLcertifyDirectList.size(); y++) {
					newPrpLcertifyDirect1 = (PrpLcertifyDirect) prpLcertifyDirectList.get(y);
					typeCode = Integer.parseInt(newPrpLcertifyDirect1.getTypeCode().substring(0, 2));
					typeTreeList = typeTreeMap.get(typeCode);
					if(typeTreeList==null){
						typeTreeList = new ArrayList();
					}
					typeTreeList.add(newPrpLcertifyDirect1);
					typeTreeMap.put(typeCode, typeTreeList);
				}
				for(int k : typeTreeMap.keySet()){
					// 取得标题
					if (k > 9) {
						titleCode = "" + k;
					} else {
						titleCode = "0" + k;
					}
					typeTreeList = typeTreeMap.get(k);
					if (k == 7) {
						// 处理多人伤
						getCertifyTreePresonLoss(businessNoElement, titleCode, title[k], typeTreeList, prpLpersonTraceList);
					} else if (k == 12 || k == 5) {
						// 处理多车
						getCertifyTreeCarLoss(businessNoElement, titleCode, title[k], typeTreeList, prpLthirdPartyList);
					} else {
						if(k>12){
							getCertifyTreeNode(businessNoElement, titleCode, title[13], typeTreeList);
						}else{
							getCertifyTreeNode(businessNoElement, titleCode, title[k], typeTreeList);
						}
					}
				}
//				for (int k = 1; k <= 12; k++) {
//					// 取得标题
//					if (k > 9) {
//						titleCode = "" + k;
//					} else {
//						titleCode = "0" + k;
//					}
//					if (prpLcertifyDirectList != null && prpLcertifyDirectList.size() > 0) {
//						typeTreeList = new ArrayList<Object>();
//						for (int y = 0; y < prpLcertifyDirectList.size(); y++) {
//							newPrpLcertifyDirect1 = (PrpLcertifyDirect) prpLcertifyDirectList.get(y);
//							// System.out.println(Integer.parseInt(newPrpLcertifyDirectDto1.getTypeCode().substring(0,
//							// 2)));
//							if (Integer.parseInt(newPrpLcertifyDirect1.getTypeCode().substring(0, 2)) == k) {
//								typeTreeList.add(newPrpLcertifyDirect1);
//							}
//						}
//						if (typeTreeList.size() > 0) {
//
//							if (k == 7) {
//								// 处理多人伤
//								getCertifyTreePresonLoss(businessNoElement, titleCode, title[k], typeTreeList, prpLpersonTraceList);
//							} else if (k == 12 || k == 5) {
//								// 处理多车
//								getCertifyTreeCarLoss(businessNoElement, titleCode, title[k], typeTreeList, prpLthirdPartyList);
//							} else {
//								getCertifyTreeNode(businessNoElement, titleCode, title[k], typeTreeList);
//							}
//
//						}
//					}
//				}
			} else {
				String[] title = { "", "通用單證", "事故處理單證", "查勘資料", "法院/仲裁相關單證", "標的車損資料", "財產損失資料", "人傷亡資料（週七）", "車輛盜搶資料", "三者車損資料", "車輛全部損失資料", "火災、爆炸、自燃等證明", "交強險墊付/預付資料", "領取賠款資料" };
				// System.out.println("gggggggggggggggggggggggggggggggggggggg");
				// for (int k = 20; k <= 32; k++) {
				List typeTreeList = null;
				PrpLcertifyDirect newPrpLcertifyDirect1 = null;
				for (int k = 1; k <= 12; k++) {
					// 取得标题
					/*
					 * String titleCode = "0" + k; if (k > 29) { titleCode = ""
					 * + k; }
					 */
					String titleCode = "" + k;
					if (prpLcertifyDirectList != null && prpLcertifyDirectList.size() > 0) {
						typeTreeList = new ArrayList<Object>();
						for (int y = 0; y < prpLcertifyDirectList.size(); y++) {
							newPrpLcertifyDirect1 = (PrpLcertifyDirect) prpLcertifyDirectList.get(y);
							// System.out.println(Integer.parseInt(newPrpLcertifyDirect1.getTypeCode().substring(0,
							// 2)));
							// if
							// (Integer.parseInt(newPrpLcertifyDirectDto1.getTypeCode().substring(0,
							// 2)) == k) {
							int codeType = Integer.parseInt(newPrpLcertifyDirect1.getTypeCode().substring(0, 2)) - 19;
							if (codeType == k) {
								typeTreeList.add(newPrpLcertifyDirect1);
							}
						}
						if (typeTreeList.size() > 0) {
							// if (k == 7) {
							// // 处理多人伤
							// getCertifyTreePresonLoss(businessNoElement,
							// titleCode, title[k], typeTreeList,
							// prpLpersonTraceDtoList);
							// } else if (k == 12) {
							// // 处理多车
							// getCertifyTreeCarLoss(businessNoElement,
							// titleCode, title[k], typeTreeList,
							// prpLthirdPartyDtoList);
							// } else {
							getCertifyTreeNode(businessNoElement, titleCode, title[k], typeTreeList);
							// }
							//
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CodeUtils.byteArrayToHexString(sysElement.asXML().toString().getBytes(), false);
	}

	/**
	 * 添加节点
	 * @param businessNoElement
	 * @param titleCode
	 * @param title
	 * @param typeTreeList
	 */
	private void getCertifyTreeNode(Element businessNoElement, String titleCode, String title, List typeTreeList) {
		Element subTypeElement = businessNoElement.addElement("SubType");
		subTypeElement.addAttribute("catalog", "true");
		subTypeElement.addAttribute("code", titleCode);
		subTypeElement.addAttribute("name", title);
		PrpLcertifyDirect newPrpLcertifyDirectDto = null;
		Element subTypeDetailElement = null;
		for (int i = 0; i < typeTreeList.size(); i++) {
			newPrpLcertifyDirectDto = (PrpLcertifyDirect) typeTreeList.get(i);
			subTypeDetailElement = subTypeElement.addElement("SubTypeDetail");
			subTypeDetailElement.addAttribute("code", newPrpLcertifyDirectDto.getTypeCode());
			subTypeDetailElement.addAttribute("name", newPrpLcertifyDirectDto.getTypeName());
		}
	}

	/**
	 * 单证环节查看多人伤，没法迭代，再多一层循环我就晕了
	 * @param businessNoElement
	 * @param titleCode
	 * @param title
	 * @param typeTreeList
	 * @param prpLpersonTraceDtoList
	 */
	private void getCertifyTreePresonLoss(Element businessNoElement, String titleCode, String title, List typeTreeList, List prpLpersonTraceDtoList) {
		Element subTypeElement = businessNoElement.addElement("SubType");
		subTypeElement.addAttribute("catalog", "true");
		subTypeElement.addAttribute("code", titleCode);
		subTypeElement.addAttribute("name", title);
		if (prpLpersonTraceDtoList != null && prpLpersonTraceDtoList.size() > 0) {
			PrpLpersonTrace prpLpersonTrace = null;
			List typeTreePersonList = null;
			PrpLcertifyDirect newPrpLcertifyDirect = null;
			Element subTypePersonElement = null;
			PrpLcertifyDirect typeTreePersonDirect = null;
			Element subTypePersonDetailElement = null;
			for (int i = 0; i < prpLpersonTraceDtoList.size(); i++) {
				prpLpersonTrace = (PrpLpersonTrace) prpLpersonTraceDtoList.get(i);
				typeTreePersonList = new ArrayList<Object>();
				for (int j = 0; j < typeTreeList.size(); j++) {
					newPrpLcertifyDirect = (PrpLcertifyDirect) typeTreeList.get(j);
					if (newPrpLcertifyDirect.getId().getLossItemCode().equals("" + prpLpersonTrace.getId().getPersonNo())) {
						typeTreePersonList.add(newPrpLcertifyDirect);
					}
				}
				if (typeTreePersonList.size() > 0) {
					subTypePersonElement = subTypeElement.addElement("SubTypePerson");
					subTypePersonElement.addAttribute("catalog", "true");
					subTypePersonElement.addAttribute("code", "" + prpLpersonTrace.getId().getPersonNo());
					subTypePersonElement.addAttribute("name", " " + prpLpersonTrace.getPersonName());
					for (int j = 0; j < typeTreePersonList.size(); j++) {
						typeTreePersonDirect = (PrpLcertifyDirect) typeTreePersonList.get(j);
						subTypePersonDetailElement = subTypePersonElement.addElement("SubTypePersonDetail");
						subTypePersonDetailElement.addAttribute("code", typeTreePersonDirect.getTypeCode());
						subTypePersonDetailElement.addAttribute("name", typeTreePersonDirect.getTypeName());
					}
				}
			}
		} else {
			PrpLcertifyDirect newPrpLcertifyDirect = null;
			Element subTypeDetailElement = null;
			for (int j = 0; j < typeTreeList.size(); j++) {
				newPrpLcertifyDirect = (PrpLcertifyDirect) typeTreeList.get(j);
				subTypeDetailElement = subTypeElement.addElement("SubTypeDetail");
				subTypeDetailElement.addAttribute("code", newPrpLcertifyDirect.getTypeCode());
				subTypeDetailElement.addAttribute("name", newPrpLcertifyDirect.getTypeName());
			}
		}

	}

	/**
	 * 单证环节查看多车定损，没法迭代，再多一层循环我就晕了
	 * @param businessNoElement
	 * @param titleCode
	 * @param title
	 * @param typeTreeList
	 * @param prpLthirdPartyDtoList
	 */
	private void getCertifyTreeCarLoss(Element businessNoElement, String titleCode, String title, List typeTreeList, List prpLthirdPartyDtoList) {
		Element subTypeElement = businessNoElement.addElement("SubType");
		subTypeElement.addAttribute("catalog", "true");
		subTypeElement.addAttribute("code", titleCode);
		subTypeElement.addAttribute("name", title);
		String carFlagName = "";
		if (prpLthirdPartyDtoList != null && prpLthirdPartyDtoList.size() > 0) {
			PrpLthirdParty prpLthirdParty = null;
			List typeTreeThirdPartyList = null;
			PrpLcertifyDirect newPrpLcertifyDirect = null;
			Element subTypeCarLossElement = null;
			PrpLcertifyDirect typeTreeCarLossDirectDto = null;
			Element subTypeCarLossDetailElement = null;
			for (int i = 0; i < prpLthirdPartyDtoList.size(); i++) {
				prpLthirdParty = (PrpLthirdParty) prpLthirdPartyDtoList.get(i);
				if ("05".equals(titleCode) && prpLthirdParty.getId().getSerialNo() != 1) {
					continue;
				}
				if ("12".equals(titleCode) && prpLthirdParty.getId().getSerialNo() == 1) {
					continue;
				}
				typeTreeThirdPartyList = new ArrayList<Object>();
				for (int j = 0; j < typeTreeList.size(); j++) {
					newPrpLcertifyDirect = (PrpLcertifyDirect) typeTreeList.get(j);
					if (newPrpLcertifyDirect.getId().getLossItemCode().equals("" + prpLthirdParty.getId().getSerialNo())) {
						typeTreeThirdPartyList.add(newPrpLcertifyDirect);
					}
				}
				if (typeTreeThirdPartyList.size() > 0) {
					if (prpLthirdParty.getId().getSerialNo() != 1) {
						carFlagName = "財車:";
					} else {
						carFlagName = "保車:";
					}
					subTypeCarLossElement = subTypeElement.addElement("SubTypeCarLoss");
					subTypeCarLossElement.addAttribute("catalog", "true");
					subTypeCarLossElement.addAttribute("code", "" + prpLthirdParty.getId().getSerialNo());
					subTypeCarLossElement.addAttribute("name", carFlagName + prpLthirdParty.getLicenseNo());
					for (int j = 0; j < typeTreeThirdPartyList.size(); j++) {
						typeTreeCarLossDirectDto = (PrpLcertifyDirect) typeTreeThirdPartyList.get(j);
						subTypeCarLossDetailElement = subTypeCarLossElement.addElement("SubTypeCarLossDetail");
						subTypeCarLossDetailElement.addAttribute("code", typeTreeCarLossDirectDto.getTypeCode());
						subTypeCarLossDetailElement.addAttribute("name", typeTreeCarLossDirectDto.getTypeName());
					}
				}
			}
		} else {
			PrpLcertifyDirect newPrpLcertifyDirectDto = null;
			Element subTypeDetailElement = null;
			for (int j = 0; j < typeTreeList.size(); j++) {
				newPrpLcertifyDirectDto = (PrpLcertifyDirect) typeTreeList.get(j);
				subTypeDetailElement = subTypeElement.addElement("SubTypeDetail");
				subTypeDetailElement.addAttribute("code", newPrpLcertifyDirectDto.getTypeCode());
				subTypeDetailElement.addAttribute("name", newPrpLcertifyDirectDto.getTypeName());
			}
		}

	}
}
