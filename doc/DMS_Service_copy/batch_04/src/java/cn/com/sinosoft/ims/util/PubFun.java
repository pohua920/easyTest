package cn.com.sinosoft.ims.util;

import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.ims.svr.model.UtiISvr;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;

import com.sinosoft.bpsdriver.domain.getPowerInfo.ExceptCompany;
import com.sinosoft.bpsdriver.domain.getPowerInfo.PermitCompany;
import com.sinosoft.bpsdriver.domain.getPowerInfo.PowerInfo;
import com.sinosoft.dmsdriver.service.server.DictAPIService;

public class PubFun extends GenericDaoHibernate<UtiISvr, String> {

	// 判断是否需要调用接口，进行同步,IMS之间同步
	// liyu IMS间同步用户信息（用户增加、信息修改【修改、漫游/收回、注销/激活】、密码修改用户类型对照）
	// 首先判断当前部署机构是否为总公司；若是总公司则要判断增加的用户的归属机构前两是否为总公司；若是，则不进行同步操作，否则，通过判断用户归属机构前两位与服务机构前两位对比找出同步的IP,PORT,进行同步；
	// 若当前部署机构不为总公司，则同步到总公司IMS
	public InputBean isSyncByIms(String userBelComCode) {
		UtiISvrService utiISvrService = (UtiISvrService) ServiceFactory
				.getService("utiISvrService");
		InputBean inputBean = new InputBean();
		// 得到部署IMS的机构代码
		String deployCom = ReadProperties.getString("deployCom");
		UtiISvr utiISvr = new UtiISvr();
		// 因为不对总公司建立QUEUE，所以只要MAP中不包括总公司的布局机构，就要初始化所有的QUEUE;否则，则只初始化对应的QUEUE
		// 若判断为真，那么则当前布局机构为总公司，则将判断增加用户的机构若在QUEUE配置文件中，则将消息传递过去
		if (IConstants.ComCode_Head.equals(deployCom)) {
			if (!userBelComCode.substring(0, 2).equals(
					deployCom.substring(0, 2))) {
				// StringBuffer hql = new StringBuffer(
				// "select utiISvr from UtiISvr utiISvr where utiISvr.companyCode like '"
				// + userBelComCode.substring(0, 2)
				// + "%' and utiISvr.svrCodeInCompany ='"
				// + IConstants.SvrCode_IMS + "'");
				// utiISvr = (UtiISvr) super.findByHql(hql.toString()).get(0);
				utiISvr = utiISvrService.getUtiISvrByCode(userBelComCode);
				if (utiISvr == null) {
					inputBean.setSync(false);
					inputBean.setSourComCode(deployCom);
					inputBean.setDestComCode(null);
					// throw new BusinessException("4001", "服务不存在。同步失败!");
				} else {
					// utiISvr=(UtiISvr) list.get(0);
					inputBean.setSync(true);
					// inputBean.setUsedJmsConfig(IConstants.JmsConfig_HD);
					inputBean.setSourComCode(deployCom);
					inputBean.setDestComCode(utiISvr.getSvrCodeInCompany());
				}
			}
		} else {
			inputBean.setSync(true);
			// inputBean.setUsedJmsConfig(IConstants.JmsConfig_BC);
			inputBean.setSourComCode(deployCom);
			inputBean.setDestComCode(IConstants.ComCode_Head);
		}
		return inputBean;
	}

	// // 得到文件的路径
	// public String getPath() {
	// String configPath = null;
	// if (configPath == null || configPath.trim().length() == 0) {
	// String actionPathName = FileUtils.getRealPathName(PubFun.class);
	// configPath = actionPathName.substring(0, actionPathName
	// .lastIndexOf("/WEB-INF"));
	// }
	// if (!configPath.endsWith(PlatformUtils.FILE_SEPARATOR)) {
	// configPath += PlatformUtils.FILE_SEPARATOR;
	// }
	// return configPath += "WEB-INF" + PlatformUtils.FILE_SEPARATOR
	// + "config" + PlatformUtils.FILE_SEPARATOR;
	// }

	// // 解析QUEUE.xml文件，返回MAP
	// public Map getQueue() {
	// Map map = new HashMap();
	// try {
	// String readPath = this.getPath();
	// // 解析xml，解析到group时，根据type来生成实体
	// Document document = null;
	// SAXReader reader = new SAXReader();
	//
	// // 找到xml的路径
	// document = reader.read(new File(readPath + "queue.xml"));
	// // 遍历xml得到所有根节点的子节点
	// Element root = document.getRootElement();
	// // 遍历queue
	// for (Iterator iter = root.elementIterator(); iter.hasNext();) {
	// Element element = (Element) iter.next();
	// Attribute comCode = element.attribute("ComCode");
	// Attribute queueName = element.attribute("QueueName");
	// map.put(comCode, queueName);
	// }
	// } catch (DocumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return map;
	// }
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 * @throws ParseException
	 */
	public static Date StrToDate(String str) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		date = format.parse(str);

		return date;
	}
	
	public  String addPowerByList(PowerInfo powerInfo,String company,String risk){
		List<PermitCompany> permitCompanyList = powerInfo.getPermitCompanyList();
		List<ExceptCompany> exceptCompanyList = powerInfo.getExceptCompanyList();
		String riskCodes = powerInfo.getPermitRiskCodes();
		PermitCompany permitCompany = null;
		StringBuffer buffer = new StringBuffer();
		// saaUser.comCodein (,[2]) and 
		String subComCodes = "";
		if (company.length() > 0) {
			//判断允许机构中是否有数据
			if (permitCompanyList.size() > 0) {
				buffer.append(company + " in ('");
				
				for (int i = 0; i < permitCompanyList.size(); i++) {
					permitCompany = permitCompanyList.get(i);
					if (i != 0) {
						buffer.append(",'");
					}
					if ("1".equals(permitCompany.getINCLUDESUBCOM())) {
						buffer.append(permitCompany.getCOMCODE());
						subComCodes = this.getSubCompanyCodes(permitCompany.getCOMCODE());
						if (subComCodes.length() > 0) {
							buffer.append("','");
							buffer.append(subComCodes);
							buffer.append("'");
						}
					}
				}
				buffer.append(")");
			}
			
			if (exceptCompanyList.size() > 0) {
				if (buffer.length() > 0) {
					buffer.append(" and ");
				}
				buffer.append(company + " not in (");
				ExceptCompany exceptCompany = null;
				for (int i = 0; i < exceptCompanyList.size(); i++) {
					exceptCompany = exceptCompanyList.get(i);
					if (i != 0) {
						buffer.append(",");
					}
					if ("1".equals(exceptCompany.getINCLUDESUBCOM())) {
						buffer.append(exceptCompany.getCOMCODE());
						buffer.append(this.getSubCompanyCodes(exceptCompany.getCOMCODE()));
					}else {
						buffer.append(exceptCompany.getCOMCODE());
					}
				}
//				buffer.append(buffer.substring(1));
				buffer.append(")");
			}
		}
		if (risk.length() > 0) {
			buffer.append(" and ");
			if (buffer.length() > 0) {
				buffer.append(risk + " in (");
				buffer.append(riskCodes);
				buffer.append(" ) ");
			}
		}
		return buffer.toString();
	}
	
	public String getSubCompanyCodes(String upperComCode){
		String comCodes = "";
//		String hql = "select t.id.subComCode from PrpDcompanyGrade t where t.id.comCode = ?  and validStatus= ?";
		List list = null;
		try {
			list = DictAPIService.getAllSubCompany("ims", upperComCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrpDcompany p = null;
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				p = (PrpDcompany) list.get(i);
				comCodes += "," + p.getComCode();
			}
		}
//		subCompanyCodes = super.findByHql(hql, upperComCode,"1");
		if(comCodes.length()>1){
			comCodes=comCodes.substring(1);
		}
		return comCodes;
	}
	public static String getComCodeString(List comList) {
		StringBuffer comCodes = new StringBuffer();
		String endComCode = "";
		String com = "";
		Set set = new HashSet();
		for (int i = 0; i < comList.size(); i++) {
			com = (String) comList.get(i);
			if (set.add(com)) {
				comCodes.append(",");
				comCodes.append("'");
				comCodes.append(com);
				comCodes.append("'");
			}
		}
		set.clear();
		// subCompanyCodes = super.findByHql(hql, upperComCode,"1");
		if (comCodes.length() > 1) {
			endComCode = comCodes.substring(1);
		}

		return endComCode;
	}
}
