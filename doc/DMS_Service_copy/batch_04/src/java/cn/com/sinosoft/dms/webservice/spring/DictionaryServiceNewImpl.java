package cn.com.sinosoft.dms.webservice.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.dms.model.PrpDcustomer;
import cn.com.sinosoft.dms.webservice.facade.DictionaryNewService;
import cn.com.sinosoft.inf.dict.server.common.DictPage;

public class DictionaryServiceNewImpl extends GenericDaoHibernate implements
DictionaryNewService {

	/**
	 * mantis：CAR0027，處理人員：DP0706，需求單編號：mantis：CAR0027 :
	 * 因DMS 正式機上的CODE與開發版本有所差異故將DNS查詢方法抽出並另外改寫
	 * @author wanglianzhou 新增单位信息
	 * @param
	 * @return DictPage
	 */
	@Override
	public DictPage savePrpDcustomerUnitNew(String systemCode, Map values)
			throws Exception {
		System.out.println("-------------進入單位保存新方法！--------------- ");
		String comCode = (String) values.get("comCode");
		com.sinosoft.dmsdriver.model.PrpDcustomerUnit typeOne = (com.sinosoft.dmsdriver.model.PrpDcustomerUnit) values
				.get("prpDcustomerUnit");
		//暂时注掉----add  by pengxiaohui
		//PrpDcustomerFXQ typeTwo1 = (PrpDcustomerFXQ)values.get("prpDcustomerFXQ");
		cn.com.sinosoft.dms.model.PrpDcustomerUnit type = new cn.com.sinosoft.dms.model.PrpDcustomerUnit();
		//暂时注掉----add  by pengxiaohui
		//cn.com.sinosoft.dms.model.PrpDcustomerFXQ typeTwo = new cn.com.sinosoft.dms.model.PrpDcustomerFXQ();
		PrpDcustomer prpDcustomer = new PrpDcustomer();
		String organizeCode="";
		// add  by  pengxiaohui  ��ʱ������������Ϣ�?ֱ����unit��������ֶ�  2013-11-12
		
		//暂时注掉----add  by pengxiaohui
		
		/*if(typeTwo1.getCustomerType() != null && !"".equals(typeTwo1.getCustomerType())){
			typeTwo.setBusinessRange(typeTwo1.getBusinessRange());
			typeTwo.setBusinessSourceCode(typeTwo1.getBusinessSourceCode());
			typeTwo.setBusinessSourceName(typeTwo1.getBusinessSourceName());
			typeTwo.setCustomerType(typeTwo1.getCustomerType());
			if(null!=typeTwo1.getCustomerCode()&&!"".equals(typeTwo1.getCustomerCode())){
                typeTwo.setCustomerCode(typeTwo1.getCustomerCode());
            }
            String identifyNumber = typeTwo1.getShareHolderIdentifyNumber().substring(1,2);
            System.out.println("dictionaryServiceImpl.java -- -             ------------ ---3544 ==="+identifyNumber);
            if(identifyNumber == "1"){
                typeTwo.setSex("1");
            }
            if(identifyNumber == "2"){
                typeTwo.setSex("2");
            }
            typeTwo.setFlag(typeTwo1.getFlag());typeTwo.setFlag1(typeTwo1.getFlag1());
            typeTwo.setFlag2(typeTwo1.getFlag2());typeTwo.setIdentifyEndDate(typeTwo1.getIdentifyEndDate());
            typeTwo.setIdentifyName(typeTwo1.getIdentifyName());typeTwo.setIdentifyNumber(typeTwo1.getIdentifyNumber());
            typeTwo.setIdentifyStartDate(typeTwo1.getIdentifyStartDate());typeTwo.setLeaderIdentifyEndDate(typeTwo1.getLeaderIdentifyEndDate());
            typeTwo.setLeaderIdentifyName(typeTwo1.getLeaderIdentifyName());typeTwo.setLeaderIdentifyNumber(typeTwo1.getLeaderIdentifyNumber());
            typeTwo.setLeaderIdentifyStartDate(typeTwo1.getLeaderIdentifyStartDate());typeTwo.setLeaderIdentifyType(typeTwo1.getLeaderIdentifyType());
            typeTwo.setLeaderName(typeTwo1.getLeaderName());typeTwo.setOccupationCode(typeTwo1.getOccupationCode());
            typeTwo.setOccupationName(typeTwo1.getOccupationName());typeTwo.setPhoneNumber(typeTwo1.getPhoneNumber());
            typeTwo.setPrincipalIdentifyStartDate(typeTwo1.getPrincipalIdentifyStartDate());
            typeTwo.setPrincipalIdentifyEndDate(typeTwo1.getPrincipalIdentifyEndDate());
            typeTwo.setPrincipalIdentifyName(typeTwo1.getPrincipalIdentifyName());
            typeTwo.setPrincipalIdentifyNumber(typeTwo1.getPrincipalIdentifyNumber());
            typeTwo.setPrincipalIdentifyStartDate(typeTwo.getPrincipalIdentifyStartDate());
            typeTwo.setPrincipalIdentifyType(typeTwo1.getPrincipalIdentifyType());
            typeTwo.setPrincipalName(typeTwo1.getPrincipalName());
            typeTwo.setSex(typeTwo1.getSex());typeTwo.setShareHolderIdentifyEndDate(typeTwo1.getShareHolderIdentifyEndDate());
            typeTwo.setShareHolderIdentifyName(typeTwo1.getShareHolderIdentifyName());
            typeTwo.setShareHolderIdentifyNumber(typeTwo1.getShareHolderIdentifyNumber());
            typeTwo.setShareHolderIdentifyStartDate(typeTwo1.getShareHolderIdentifyStartDate());
            typeTwo.setShareHolderIdentifyType(typeTwo1.getShareHolderIdentifyType());
            typeTwo.setTaxRegisterNumber(typeTwo1.getTaxRegisterNumber());
            typeTwo.setShareHolderName(typeTwo1.getShareHolderName());
		}*/
		
		//-----end  by pengxiaohui 20140612
		if (typeOne != null) {
		    if(null!=typeOne.getCustomerCode()&&!"".equals(typeOne.getCustomerCode())){
                prpDcustomer = (PrpDcustomer) super.get(PrpDcustomer.class,
                        typeOne.getCustomerCode());
                //modify by liudezhen 20160216 start
                if(prpDcustomer == null){
                	prpDcustomer = new PrpDcustomer();
                }
                //modify by liudezhen 20160216 end
            }
			prpDcustomer.setAddressCName(typeOne.getAddressCName());
			prpDcustomer.setAddressEName(typeOne.getAddressEName());prpDcustomer.setArticleCode(typeOne.getArticleCode());
			prpDcustomer.setBlackState(typeOne.getBlackState());prpDcustomer.setCustomerCName(typeOne.getCustomerCName());
			prpDcustomer.setCustomerEName(typeOne.getCustomerEName());prpDcustomer.setCustomerFlag(typeOne.getCustomerFlag());
			prpDcustomer.setCustomerType("2");prpDcustomer.setInputDate(typeOne.getInputDate());
			prpDcustomer.setOperatorCode(typeOne.getOperatorCode());prpDcustomer.setShortHandCode(typeOne.getShortHandCode());
			prpDcustomer.setValidStatus(typeOne.getValidStatus());
			
			/*String str = "select prpDcustomerUnit_seq.nextval from dual ";
			 List templist1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(str).list();			
			 String billNo1 = null ;
	      	 if(templist1 != null && !templist1.isEmpty()){
	      		 billNo1 =((BigDecimal)templist1.get(0)).toString();
	      		 String math="0000000";
	      		 math=math+billNo1;
			if(typeOne.getOrganizeCode()==""){
				 organizeCode = "A" + math.substring(billNo1.length(),math.length());
				prpDcustomer.setOrganizeCode(organizeCode);
		      	 }
			else{
				prpDcustomer.setOrganizeCode(typeOne.getOrganizeCode());
			}
			}*/
			
			if(typeOne.getOrganizeCode()==""){

				String str = "select prpDcustomerUnit_seq.nextval from dual ";
				 List templist1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(str).list();			
				 String billNo1 = null ;
		      	 if(templist1 != null && !templist1.isEmpty()){
		      		 billNo1 =((BigDecimal)templist1.get(0)).toString();
		      		 String math="0000000";
		      		 math=math+billNo1;
				 organizeCode = "A" + math.substring(billNo1.length(),math.length());
				prpDcustomer.setOrganizeCode(organizeCode);
		      	 }
			}
			else{
				prpDcustomer.setOrganizeCode(typeOne.getOrganizeCode());
			}
			
			String customerCode=typeOne.getCustomerCode();
			if(customerCode==null||"".equals(customerCode)){
			String strSqlStatement = "select for_customercode_9.nextval from dual ";
			//List templist = this.getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(strSqlStatement).list();
            List templist = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(strSqlStatement).list();			
			String billNo = null ;
	      	 if(templist != null && !templist.isEmpty()){
	      		 billNo =((BigDecimal)templist.get(0)).toString();
	      	 }
			String iCustomerType = typeOne.getCustomerKind();
			String strCustomerCode ;
			if(null!=typeOne.getCustomerCode()&&!"".equals(typeOne.getCustomerCode())){
                strCustomerCode=typeOne.getCustomerCode();
            }else{
                if (iCustomerType.equals("1")) {
                    strCustomerCode = "5" + comCode.trim().substring(0, 2)
                            + billNo;
                } else {
                    strCustomerCode = "0" + comCode.trim().substring(0, 2)
                            + billNo;
                }
            }   
			prpDcustomer.setCustomerCode(strCustomerCode); // added by wanglz
		    											// 20130424
			}else{
				prpDcustomer.setCustomerCode(typeOne.getCustomerCode());
			}
			// prpDcustomer.setCustomerType(typeOne.getCustomerKind());
			prpDcustomer.setCustomerType("2");
			prpDcustomer.setValidStatus("1");  //暂时写死  wlz
			type.setAccount(typeOne.getAccount());// 银行账号
			type.setAddressCName(typeOne.getAddressCName());// 地址中文名称
			type.setAddressEName(typeOne.getAddressEName());// 地址英文名称
			type.setArticleCode(typeOne.getArticleCode());
			type.setBank(typeOne.getBank());// 银行
			type.setBlackState(typeOne.getBlackState());// 黑名单标志
			type.setComCode(typeOne.getComCode());// 商业机构代码
			type.setCreditLevel(typeOne.getCreditLevel());type.setCustomerCName(typeOne.getCustomerCName());
			type.setCustomerCode(typeOne.getCustomerCode());type.setCustomerEName(typeOne.getCustomerEName());
			type.setCustomerFlag(typeOne.getCustomerFlag());type.setCustomerKind(typeOne.getCustomerKind());
			type.setFaxNumber(typeOne.getFaxNumber());type.setCustomerShortName(typeOne.getCustomerShortName());
			type.setFlag(typeOne.getFlag());type.setHandlerCode(typeOne.getHandlerCode());
			//mantis： XXXXX，處理人員：Sam，需求單編號：CAR0027，新增電子信箱，存檔時也要傳此參數
			type.setEmailAddress(typeOne.getEmailAddress());
			Date date=new Date();
			type.setInputDate(date);//修改時間
//			type.setInputDate(type.getInputDate());
			type.setMobile(typeOne.getMobile());type.setNetAddress(typeOne.getNetAddress());
			type.setNewCustomerCode(typeOne.getNewCustomerCode());type.setOperatorCode(typeOne.getOperatorCode());
			type.setPassword(typeOne.getPassword());type.setPhoneNumber(typeOne.getPhoneNumber());
			type.setLinkerName(typeOne.getLinkerName());
			type.setPostCode(typeOne.getPostCode());type.setShortHandCode(typeOne.getShortHandCode());
			type.setTopLevelFlag(typeOne.getTopLevelFlag());type.setUpdateDate(typeOne.getUpdateDate());
			type.setValidStatus(typeOne.getValidStatus());type.setNewCustomerCode(typeOne.getNewCustomerCode());
			if("".equals(typeOne.getOrganizeCode())||null==typeOne.getOrganizeCode()){
				type.setOrganizeCode(organizeCode);
			}else{
			type.setOrganizeCode(typeOne.getOrganizeCode());
			}
			type.setVerifyNumber(typeOne.getVerifyNumber());
			type.setLoanAccount(typeOne.getLoanAccount());type.setPrincipalName(typeOne.getPrincipalName());
			type.setPrincipalIdentifyType(typeOne.getPrincipalIdentifyType());
			type.setPrincipalIdentifyNumber(typeOne.getPrincipalIdentifyNumber());
			type.setPrincipalIdentifyEndDate(typeOne.getPrincipalIdentifyEndDate());
			type.setPrincipalIdentifyStartDate(typeOne.getPrincipalIdentifyStartDate());
			type.setCreditNumber(typeOne.getCreditNumber());
			type.setCollateralNumber(typeOne.getCollateralNumber());
			type.setLoansBehalfNumber(typeOne.getLoansBehalfNumber());
			type.setLoansDepartment(typeOne.getLoansDepartment());			
			type.setLinkerName(typeOne.getLinkerName());
			type.setBusinessRange(typeOne.getBusinessRange());
			type.setBusinessSource(typeOne.getBusinessSource());
			type.setPrincipalIdentifyNumber(typeOne.getPrincipalIdentifyNumber());
			type.setPrincipalIdentifyEndDate(typeOne.getPrincipalIdentifyEndDate());
			type.setPrincipalIdentifyStartDate(typeOne.getPrincipalIdentifyStartDate());
			type.setCreditNumber(typeOne.getCreditNumber());
			type.setCollateralNumber(typeOne.getCollateralNumber());
			type.setLoansBehalfNumber(typeOne.getLoansBehalfNumber());
			type.setLoansDepartment(typeOne.getLoansDepartment());
			
			//add by 添加字段值   通信地址 end
			type.setPostAddress(typeOne.getPostAddress());
			//add by ����ֶ�ֵ   ͨ�ŵ�ַ end
			type.setUnitCode(typeOne.getUnitCode());
			type.setNationalityAddress(typeOne.getNationalityAddress());
			type.setPhoneAreaNumber(typeOne.getPhoneAreaNumber());
			type.setPhoneExtNumber(typeOne.getPhoneExtNumber());
			type.setLocalNo(typeOne.getLocalNo());
			type.setLocalName(typeOne.getLocalName());
			type.setMobileTelephone(typeOne.getMobileTelephone());
			 //add by yjm MC最低保费维护  20141013 start
            type.setMinimumPreium(typeOne.getMinimumPreium());
            //add by yjm MC最低保费维护  20141013 end
			if (type.getValidStatus() == null) {
				type.setValidStatus("1");
			}
		}
		super.save(prpDcustomer);
		prpDcustomer = (PrpDcustomer) super.get(PrpDcustomer.class,
				prpDcustomer.getCustomerCode());
		type.setPrpDcustomer(prpDcustomer);
		type.setCustomerCode(prpDcustomer.getCustomerCode());
		// add  by  pengxiaohui  暂时注掉  20140612
		/*if(typeTwo.getCustomerType() != null && !"".equals(typeTwo.getCustomerType())){
			typeTwo.setCustomerCode(prpDcustomer.getCustomerCode());
			super.save(typeTwo);
		}*/
		if (type.getNewCustomerCode() == null) {
			type.setNewCustomerCode(prpDcustomer.getCustomerCode());
		}
		DictPage dictPage = new DictPage();

		List<cn.com.sinosoft.dms.model.PrpDcustomerUnit> list = new ArrayList<cn.com.sinosoft.dms.model.PrpDcustomerUnit>();
		try {
			super.save(type);
			list.add(type);
			List<com.sinosoft.dmsdriver.model.PrpDcustomerUnit> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerUnit>();
			for (cn.com.sinosoft.dms.model.PrpDcustomerUnit typeUnit : list) {
				com.sinosoft.dmsdriver.model.PrpDcustomerUnit type1 = new com.sinosoft.dmsdriver.model.PrpDcustomerUnit();
				type1.setAccount(typeUnit.getAccount());type1.setAddressCName(typeUnit.getAddressCName());
				type1.setAddressEName(typeUnit.getAddressEName());
				type1.setArticleCode(typeUnit.getArticleCode());
				type1.setBank(typeUnit.getBank());type1.setBlackState(typeUnit.getBlackState());
				type1.setBusinessRange(typeUnit.getBusinessRange());type1.setBusinessSort(typeUnit.getBusinessSort());
				type1.setBusinessSource(typeUnit.getBusinessSource());
				type1.setCareerRiskGrade(typeUnit.getCareerRiskGrade());type1.setComCode(typeUnit.getComCode());
				type1.setCreditLevel(typeUnit.getCreditLevel());type1.setCustomerCName(typeUnit.getCustomerCName());
				type1.setCustomerCode(typeUnit.getCustomerCode());type1.setCustomerEName(typeUnit.getCustomerEName());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());type1.setCustomerKind(typeUnit.getCustomerKind());
				PrpDcustomer prpDcustomer1 = super.get(PrpDcustomer.class, typeUnit.getCustomerCode());
				type1.setCustomerShortName(typeUnit.getCustomerShortName());type1.setCustomerType(prpDcustomer1.getCustomerType());
				type1.setEmailAddress(typeUnit.getEmailAddress());type1.setEconomyCode(typeUnit.getEconomyCode());
				type1.setEmploySum(typeUnit.getEmploySum());type1.setFatherCode(typeUnit.getFatherCode());
				type1.setFaxNumber(typeUnit.getFaxNumber());type1.setFlag(typeUnit.getFlag());
				type1.setHandlerCode(typeUnit.getHandlerCode());type1.setIndustryCode(typeUnit.getIndustryCode());
				type1.setInputDate(typeUnit.getInputDate());type1.setLeaderName(typeUnit.getLeaderName());
				type1.setLinkerName(typeUnit.getLinkerName());type1.setLowerViewFlag(typeUnit.getLowerViewFlag());
				type1.setMeasureCode(typeUnit.getMeasureCode());type1.setMobile(typeUnit.getMobile());
				type1.setNetAddress(typeUnit.getNetAddress());type1.setNewCustomerCode(typeUnit.getNewCustomerCode());
				type1.setOperatorCode(typeUnit.getOperatorCode());type1.setOrganizeCode(typeUnit.getOrganizeCode());
				type1.setPassword(typeUnit.getPassword());type1.setPhoneNumber(typeUnit.getPhoneNumber());
				type1.setLinkerName(typeUnit.getLinkerName());
				type1.setPossessNature(typeUnit.getPossessNature());type1.setPostAddress(typeUnit.getPostAddress());
				type1.setPostCode(typeUnit.getPostCode());type1.setRegionCode(typeUnit.getRegionCode());
				type1.setRegistFund(typeUnit.getRegistFund());type1.setRevenueCode(typeUnit.getRevenueCode());
				type1.setShareHolderFlag(typeUnit.getShareHolderFlag());type1.setShortHandCode(typeUnit.getShortHandCode());
				type1.setSponsorName(typeUnit.getSponsorName());type1.setTaxIdentifyCode(typeUnit.getTaxIdentifyCode());
				type1.setTopLevelFlag(typeUnit.getTopLevelFlag());type1.setUpdateDate(typeUnit.getUpdateDate());
				type1.setUpdaterCode(typeUnit.getUpdaterCode());type1.setValidStatus(type1.getValidStatus());
				type1.setWordRiskRank(typeUnit.getWordRiskRank());
				if("".equals(typeOne.getOrganizeCode())||null==typeOne.getOrganizeCode()){
					type1.setOrganizeCode(organizeCode);
				}else{
					type1.setOrganizeCode(typeUnit.getOrganizeCode());
				}
				//type1.setOrganizeCode(typeUnit.getOrganizeCode());
				type1.setVerifyNumber(typeUnit.getVerifyNumber());
	            type1.setLoanAccount(typeUnit.getLoanAccount());type1.setPrincipalName(typeUnit.getPrincipalName());
	            type1.setPrincipalIdentifyType(typeUnit.getPrincipalIdentifyType());
	            type1.setPrincipalIdentifyNumber(typeUnit.getPrincipalIdentifyNumber());
	            type1.setPrincipalIdentifyEndDate(typeUnit.getPrincipalIdentifyEndDate());
	            type1.setPrincipalIdentifyStartDate(typeUnit.getPrincipalIdentifyStartDate());
	            
	            type1.setNationalityAddress(typeUnit.getNationalityAddress());
				type1.setPhoneAreaNumber(typeUnit.getPhoneAreaNumber());
				type1.setPhoneExtNumber(typeUnit.getPhoneExtNumber());
				type1.setLocalNo(typeUnit.getLocalNo());
				type1.setLocalName(typeUnit.getLocalName());
				type1.setUnitCode(typeUnit.getUnitCode());
				type1.setCreditNumber(typeUnit.getCreditNumber());
				type1.setCollateralNumber(typeUnit.getCollateralNumber());
	            type1.setLoansBehalfNumber(typeUnit.getLoansBehalfNumber());
	            type1.setLoansDepartment(typeUnit.getLoansDepartment());
	            type1.setMobileTelephone(typeUnit.getMobileTelephone());
	            //add by yjm MC最低保费维护  20141013 start
	            type1.setMinimumPreium(typeUnit.getMinimumPreium());
	            //add by yjm MC最低保费维护  20141013 end
////				added by wanglianzhou 20130603 start
//				type1.setIdentifyNumber(typeTwo.getIdentifyNumber());
//				type1.setSex(typeTwo.getSex());
////				added by wanglianzhou 20130603 end
				list1.add(type1);
			}
			dictPage.setData(list1);
			dictPage.setTotalRecordCount(new Long(1));
			System.out.println("-------------單位保存方法結束--------------------");
			return dictPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// modify add end by renshuo 2011-07-12 reason:增加条款责任互斥条件查询
}
