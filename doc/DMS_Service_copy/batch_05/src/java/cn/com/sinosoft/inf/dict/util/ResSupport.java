package cn.com.sinosoft.inf.dict.util;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;

import com.wutka.dtd.DTD;
import com.wutka.dtd.DTDParser;

/**
 * 资源类，集成应用需要的多种资源。
 * 
 * @version 2009-07-17 李子扬 初始化版本 changed by ain 适用于bps服务器端
 */
public class ResSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final ResSupport instance = new ResSupport();

	// Req文件名称列表
	private HashMap<String, String> resDtdNameList = new HashMap<String, String>();

	// Req DTD类型列表
	private HashMap<String, DTD> reqDtdTypeList = new HashMap<String, DTD>();

	private Log log = LogFactory.getLog(this.getClass());

	/** 获取实例 */
	public static ResSupport getInstance() {
		return instance;
	}

	/** 构造函数 */
	private ResSupport() {
		try {
			this.init();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/** 初始化 */
	public synchronized void init() throws Exception {
		// 文件名称列表
		this.initReqDtdNameList();

		// DTD类型列表
		this.initReqDtdTypeList();
	}

	/** 初始化文件名称列表 */
	private synchronized void initReqDtdNameList() {
		// 清理
		this.resDtdNameList.clear();
		this.resDtdNameList = new HashMap<String, String>();

		// 添加

		// 21 - 代码翻译
		this.resDtdNameList.put(ServiceInfoConst.TRANSLATECODE,
				"TraslateCodeResPacket");
		this.resDtdNameList.put(ServiceInfoConst.CODETYPETRANSLATE,
				"CodeTypeTranslateResPacket");
		this.resDtdNameList.put(ServiceInfoConst.EXCHANGE,//
				"ExchangeResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETALLSUBCOMCODE,
				"GetPrpDcompanyListResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.GETCOUNT, "GetCountResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDAGENT,
				"GetPrpDagentResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDBANK,
				"GetPrpDbankResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDCODE,
				"GetPrpDcodeResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDCOMPANY,
				"GetPrpDcompanyResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDDEALER,
				"GetPrpDdealerResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.GETPRPDDRIVER,
//				"GetPrpDdriverResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDEXCH,
				"GetPrpDexchResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.GETPRPDPLANE,
//				"GetPrpDplaneResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDPORT,
				"GetPrpDportResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.GETPRPDSHIP,
//				"GetPrpDshipResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDTYPE,
				"GetPrpDtypeResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETSUBCODE,
				"GetSubCodeResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETSUBCOMCODE,
				"GetPrpDcompanyListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDCOMPANYBYCONDITION,
				"GetPrpDcompanyListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETUPPERCODE,
				"GetUpperCodeResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETUPPERPRPDCOMPANY,
				"GetPrpDcompanyResPacket");
		this.resDtdNameList.put(ServiceInfoConst.GETPRPDCODELIST,
				"GetPrpDcodeListResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.FINDCODEBYCONDITION,
//				"GetPrpDcodeListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDCLASSBYID,
				"PrpDclassListResPacket");

//		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDENGAGEBYID,
//				"PrpDengageListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDFRAMEBYID,
				"PrpDframeListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDITEMBYID,
				"PrpDitemListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDITEMTYPEBYID,
				"PrpDitemTypeListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDKINDBYID,
				"PrpDkindListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDLIMITBYID,
				"PrpDlimitListResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDMATERIALINFOBYID,
//				"PrpDmaterialInfoListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDPLANBYID,
				"PrpDplanListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKBYID,
				"PrpDriskListResPacket");

		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKCLAUSEKINDBYID,
				"PrpDriskClauseKindListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKENGAGEBYID,
				"PrpDriskEngageListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKITEMBYID,
				"PrpDriskItemListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKLIMITBYID,
				"PrpDriskLimitListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKSHORTRATEBYID,
				"PrpDriskShortRateListResPacket");
		// ********************************************************************
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDCLASSBYCONDITION,
				"PrpDclassListResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDENGAGEBYCONDITION,
//				"PrpDengageListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDFRAMEBYCONDITION,
				"PrpDframeListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDITEMBYCONDITION,
				"PrpDitemListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDITEMTYPEBYCONDITION,
				"PrpDitemTypeListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDKINDBYCONDITION,
				"PrpDkindListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDLIMITBYCONDITION,
				"PrpDlimitListResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDMATERIALINFOBYCONDITION,
//				"PrpDmaterialInfoListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDPLANBYCONDITION,
				"PrpDplanListResPacket");
//		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKBYCONDITION,
//				"PrpDriskListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKCLAUSEKINDBYCONDITION,
				"PrpDriskClauseKindListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKENGAGEBYCONDITION,
				"PrpDriskEngageListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKITEMBYCONDITION,
				"PrpDriskItemListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKLIMITBYCONDITION,
				"PrpDriskLimitListResPacket");
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDRISKSHORTRATEBYCONDITION,
				"PrpDriskShortRateListResPacket");
		/**by hualimin  2009-10-22 start*/
		this.resDtdNameList.put(ServiceInfoConst.FINDPRPDCODECOMBYCONDITION,
		"PrpDcodeComListResPacket");
		/**by hualimin  2009-10-22 end*/
		// *********************************************************************
		// this.resDtdNameList.put(ServiceInfoConst.FAIL_CODE, "UserException");
		/** 类似上面添加其他的文件名称 */
	}

	/** 初始化DTD类型列表 */
	private synchronized void initReqDtdTypeList() throws Exception {
		// 清理
		this.reqDtdTypeList.clear();

		// 循环添加
		String dtdPath = "cn/com/sinosoft/inf/dict/resource/dtd/";
		String fileExt = ".dtd";

		for (String key : this.resDtdNameList.keySet()) {
			// 文件名
			StringBuilder fullName = new StringBuilder();
			fullName.append(dtdPath).append(this.resDtdNameList.get(key))
					.append(fileExt);
			String fileName = fullName.toString();
			log.debug(key);
			log.debug(fileName);

			// 对应dtd文件的URL
			URL url = ResSupport.class.getClassLoader().getResource(fileName);

			// 得到对象
			DTDParser dtdParser = new DTDParser(url);

			// 放入列表
			this.reqDtdTypeList.put(key, dtdParser.parse());
		}
	}

	/** 根据类型获取DTD对象 */
	public DTD getReqDtdObject(String requestType) throws Exception {
		return (DTD) this.reqDtdTypeList.get(requestType);
	}
}