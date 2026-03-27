package cn.com.sinosoft.inf.cross.crossorg;

import ins.framework.common.ServiceFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.inf.cross.webservice.CrossOrgWebServiceClient;
import cn.com.sinosoft.saa.service.facade.CodeService;
import cn.com.sinosoft.saa.service.spring.CodeServiceSpringImpl;

public class CrossOrgSender {

	public CodeService				codeService = (CodeServiceSpringImpl)ServiceFactory.getService("codeService");
	private final SimpleDateFormat	stampFormat			= new SimpleDateFormat("yyyyMMddHHmmss");
	private final SimpleDateFormat	stampFormatInBody	= new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	private final SimpleDateFormat	format				= new SimpleDateFormat("yyyyMMdd");
	// 报送类型 P
	private final String			SENDTYPE			= "P";
	// 子公司代码 我方为财险
	private final String			COMPANYCODE			= "000002";
	// 子公司名称
	private final String			COMPANYNAME			= "中国人民财产保险股份有限公司";
	// 业务类型
	private final String			BUSINESSTYPE		= "D001";

	HashMap<String, String>			areaCodeMap			= new HashMap<String, String>();
	HashMap<String, String>			areaNameMap			= new HashMap<String, String>();

	public CrossOrgSender() {
		areaCodeMap.put("0000", "1"); // 总公司当作北京 东部
		areaCodeMap.put("1100", "1"); // 北京
		areaCodeMap.put("1200", "1"); // 天津
		areaCodeMap.put("1300", "1"); // 河北
		areaCodeMap.put("2100", "1"); // 辽宁
		areaCodeMap.put("2102", "1"); // 大连
		areaCodeMap.put("3100", "1"); // 上海
		areaCodeMap.put("3200", "1"); // 江苏
		areaCodeMap.put("3300", "1"); // 浙江
		areaCodeMap.put("3302", "1"); // 宁波
		areaCodeMap.put("3500", "1"); // 福建
		areaCodeMap.put("3502", "1"); // 厦门
		areaCodeMap.put("3700", "1"); // 山东
		areaCodeMap.put("3702", "1"); // 青岛
		areaCodeMap.put("4400", "1"); // 广东
		areaCodeMap.put("4403", "1"); // 深圳
		areaCodeMap.put("4600", "1"); // 海南

		areaCodeMap.put("1400", "2"); // 山西
		areaCodeMap.put("2200", "2"); // 吉林
		areaCodeMap.put("2300", "2"); // 黑龙江
		areaCodeMap.put("3400", "2"); // 安徽
		areaCodeMap.put("3600", "2"); // 江西
		areaCodeMap.put("4100", "2"); // 河南
		areaCodeMap.put("4200", "2"); // 湖北
		areaCodeMap.put("4300", "2"); // 湖南

		areaCodeMap.put("5000", "3"); // 重庆
		areaCodeMap.put("5100", "3"); // 四川
		areaCodeMap.put("5200", "3"); // 贵州
		areaCodeMap.put("5300", "3"); // 云南
		areaCodeMap.put("5400", "3"); // 西藏
		areaCodeMap.put("6100", "3"); // 陕西
		areaCodeMap.put("6200", "3"); // 甘肃
		areaCodeMap.put("6300", "3"); // 青海
		areaCodeMap.put("6400", "3"); // 宁夏
		areaCodeMap.put("6500", "3"); // 新疆
		areaCodeMap.put("1500", "3"); // 内蒙古
		areaCodeMap.put("4500", "3"); // 广西

		areaNameMap.put("1", "东部地区");
		areaNameMap.put("2", "中部地区");
		areaNameMap.put("3", "西部地区");
	}

	public void sender(String optType, PrpDcompany company) {
		StringBuffer requestHead = new StringBuffer(64);
		requestHead.append(SENDTYPE).append("-"); // 报送类型
		requestHead.append(optType).append("-"); // 操作类型
		requestHead.append(COMPANYCODE).append("-");// 子公司代码
		requestHead.append(company.getComCode()).append("-"); // 机构编号
		requestHead.append(BUSINESSTYPE).append("-"); // 业务类型
		requestHead.append(company.getComCode()).append("-"); // 信息唯一标示
		requestHead.append(stampFormat.format(Calendar.getInstance().getTime())).append(":"); // 时间戳

		CrossOrgSenderPacket packet = new CrossOrgSenderPacket();
		packet.setOrgcod(company.getComCode());
		packet.setComp_cod(COMPANYCODE);
		packet.setComp_nam(COMPANYNAME);
		packet.setArea_cod(areaCodeMap.get(company.getComCode().substring(0, 4)));
		packet.setArea_nam(areaNameMap.get(packet.getArea_cod()));
		packet.setProv_orgcod(getComCode(company, 1));
		packet.setProv_orgname(codeService.translateCode("DMS", CodeService.COMCODE, packet.getProv_orgcod(), "", "C"));
		packet.setCity_orgcod(getComCode(company, 2));
		packet.setCity_orgname(codeService.translateCode("DMS", CodeService.COMCODE, packet.getCity_orgcod(), "", "C"));
		packet.setTown_orgcod(getComCode(company, 3));
		packet.setTown_orgname(codeService.translateCode("DMS", CodeService.COMCODE, packet.getTown_orgcod(), "", "C"));
		packet.setUnder_orgcod(getComCode(company, 4));
		packet.setUnder_orgnam(codeService.translateCode("DMS", CodeService.COMCODE, packet.getUnder_orgcod(), "","C"));
		packet.setOrg_lvl(String.valueOf(company.getComLevel().intValue() - 1));
		packet.setStart_dat(company.getValidDate()==null?null:format.format(company.getValidDate())); // 起始日期
		packet.setEnd_dat(company.getInvalidDate()==null?null:format.format(company.getInvalidDate())); // 终止日期
		packet.setStatus_cod(company.getValidStatus());
		packet.setStatus_nam(packet.getStatus_cod().equals("1") ? "有效" : "无效");
		packet.setCrs_grpcod(packet.getProv_orgcod().substring(0, 6));
		packet.setCrs_grpnam(packet.getProv_orgname());
		packet.setCrs_citycod(packet.getCity_orgcod());
		packet.setCrs_citynam(packet.getCity_orgname());
		packet.setCrs_towncod(packet.getCity_orgcod());
		packet.setCrs_townnam(packet.getTown_orgname());
		packet.setData_upd_typ(optType);
		packet.setDate_send(stampFormatInBody.format(Calendar.getInstance().getTime()));
		packet.setDate_update(stampFormatInBody.format(Calendar.getInstance().getTime()));
		packet.setGbareacod(null);
		packet.setDep_cod(null);
		packet.setDep_name(null);
		packet.setColl_type(COMPANYCODE);
		packet.setCheck_status(null);
		packet.setCheck_date(null);
		packet.setOrgname(company.getComCName());
		
		String requestBody = packet.enCode();
		String requestStr = requestHead + requestBody;
		
		
		Object result = CrossOrgWebServiceClient.getInstance().send(requestStr);
	}

	/**
	 * @param company
	 *            机构对象
	 * @param level
	 *            要获取的机构级别 按照交叉销售接口文档注明： 0总公司 1省分公司 2地市分公司 3县支公司
	 * @return 获取的机构编码
	 */
	private String getComCode(PrpDcompany company, int level) {
		String[] path = company.getUpperPath().split(",", -1);
		if (path.length - 1 < level) {
			return null;
		} else {
			return path[level];
		}
	}
	public static void main(String [] args) {
		CrossOrgSender sender = new CrossOrgSender();
		PrpDcompany prpDcompany = new PrpDcompany();
		prpDcompany.setComCode("43019400");
		prpDcompany.setComCName("测试用机构");
		prpDcompany.setAddressCName("测试用机构地址");
		prpDcompany.setValidDate(Calendar.getInstance().getTime());
		prpDcompany.setInvalidDate(Calendar.getInstance().getTime());
		prpDcompany.setValidStatus("1");
		prpDcompany.setComLevel(new BigDecimal("4"));
		prpDcompany.setUpperComCode("43010000");
		prpDcompany.setUpperPath("00000000,43000000,43010000,43019400");
		sender.sender("U", prpDcompany);
	}
}
