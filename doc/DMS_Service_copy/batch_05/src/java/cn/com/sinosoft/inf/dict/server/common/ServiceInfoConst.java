package cn.com.sinosoft.inf.dict.server.common;
import java.io.Serializable;

/**
 * 数据字典请求类型编码规则：
 *  1：编码首字母必须是D开头数字结尾。
 *  2: 并且编码中的数字依次递增.
 *  3: D77以后的接口必须用xstream 实现报文对象的转换
 *  4：编码中的数字不得大于Integer.MAX_VALUE
 * @author zhuhe changed by hualimin
 */
public class ServiceInfoConst implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 成功返回信息代码 */
	public static final String RESPONSECODE_SUCCESS = "1";
	/** 失败返回信息代码 */
	public static final String RESPONSECODE_FAIL = "0";
	/** 成功返回错误信息代码 */
	public static final String ERRORCODE_SUCCESS = "0000";
	/** 成功返回错误信息内容 */
	public static final String ERRORMSG_SUCCESS = "成功";

	/** 不成功返回错误信息代码 */
	public static final String ERRORCODE_FAIL = "0001";
	/** 不成功返回错误信息内容 */
	public static final String ERRORMSG_FAIL = "不成功";

	/** 未查询到相关信息代码 */
	public static final String ERROR_CODE_NULL = "9100";

	/** 未查询到相关信息内容 */
	public static final String ERROR_MESSAGE_NULL = "未查询到相关信息！";

	/** 没有子代码的错误代码 */
	public static final String ERROR_CODE_NOSUBCODE = "9105";
	/** 该代码为五级代码，没有子代码 */
	public static final String ERROR_MESSAGE_NOSUBCODE = "该代码为五级代码，没有子代码！";
	/** 数据库错误代码 */
	public static final String ERROR_CODE_SQL = "9106";
	/** 数据库异常信息 */
	public static final String ERROR_MESSAGE_SQL = "数据库操作失败，重复数据或数据无效。";
	
	/** JOX解析异常代码 */
	public static final String ERROR_CODE_JOX = "9200";
	/** JOX解析异常信息 */
	public static final String ERROR_MESSAGE_JOX = "XML解析异常";
	/** Exception,最外层异常捕获后返回信息 */
	public static final String ERROR_CODE_EXC = "9201";
	/** Exception,最外层异常捕获后返回信息 */
	public static final String ERROR_MESSAGE_EXC = "数据处理失败，请检查是否符合接口规范！";

	/** Exception,数据量过大 */
	public static final String TOOBIGCODE = "9300";
	/** Exception,数据量过大 */
	public static final String TOOBIGMSG = "数据量过大，尽量不要超过1000条数据！";

	
	/** 代码翻译 D21 */
	public static final String TRANSLATECODE = "D21";
	/** 代码类型翻译 D22 */
	public static final String CODETYPETRANSLATE = "D22";
	/** 获取代码类型 D23 */
	public static final String GETPRPDTYPE = "D23";
	/** 获取一条代码记录 D24 */
	public static final String GETPRPDCODE = "D24";
	/** 获取指定代码的直接上级代码 D25 */
	public static final String GETUPPERCODE = "D25";
	/** 获取指定代码的直接下级代码 D26 */
	public static final String GETSUBCODE = "D26";
//	/** 获取指定查询条件的结果数 D27 */
//	public static final String GETCOUNT = "D27";
	/** 获得指定机构信息 */
	public static final String GETPRPDCOMPANY = "D28";
	/** 获得上级机构 */
	public static final String GETUPPERPRPDCOMPANY = "D29";
	/** 获得直接下级机构 */
	public static final String GETSUBCOMCODE = "D30";
	/** 获得所有下级机构 */
	public static final String GETALLSUBCOMCODE = "D31";
	/** 获取兑换率 */
	public static final String GETPRPDEXCH = "D32";
	/** 兑换货币 */
	public static final String EXCHANGE = "D33";
	/** 获得指定金融机构 */
	public static final String GETPRPDBANK = "D34";
	/** 获得指定渠道 */
	public static final String GETPRPDAGENT = "D35";
	/** 获得指定经销商 */
	public static final String GETPRPDDEALER = "D36";
//	/** 获得指定司机代码 */
//	public static final String GETPRPDDRIVER = "D37";
//	/** 获得指定飞机代码 */
//	public static final String GETPRPDPLANE = "D38";
	/** 获得指定港口代码 */
	public static final String GETPRPDPORT = "D39";
//	/** 获得指定船舶代码 */
//	public static final String GETPRPDSHIP = "D40";
	/**获得指定代码类型的所有代码*/
	public static final String GETPRPDCODELIST = "D41";
//	/**通过条件获得prpDcode的信息*/
//	public static final String FINDCODEBYCONDITION = "D42";
	/**通过条件获得prpDcompany的信息*/
	public static final String FINDCOMPANYBYCONDITION = "D43";
	/**通过id获得PrpDclass信息*/
	public static final String FINDPRPDCLASSBYID = "D44";
//	/**通过id获得PrpDengage信息*/
//	public static final String FINDPRPDENGAGEBYID = "D45";
	/**通过id获得PrpDframe信息*/
	public static final String FINDPRPDFRAMEBYID = "D46";
	/**通过id获得PrpDitem信息*/
	public static final String FINDPRPDITEMBYID = "D47";
	/**通过id获得PrpDitemType信息*/
	public static final String FINDPRPDITEMTYPEBYID = "D48";
	/**通过id获得PrpDkind信息*/
	public static final String FINDPRPDKINDBYID = "D49";
	/**通过id获得PrpDlimit信息*/
	public static final String FINDPRPDLIMITBYID = "D50";
//	/**通过id获得PrpDmaterialInfo信息*/
//	public static final String FINDPRPDMATERIALINFOBYID = "D51";
	/**通过id获得PrpDplan信息*/
	public static final String FINDPRPDPLANBYID = "D52";
	/**通过id获得PrpDrisk信息*/
	public static final String FINDPRPDRISKBYID = "D53";
	/**通过id获得PRPDRISKCLAUSEKIND信息*/
	public static final String FINDPRPDRISKCLAUSEKINDBYID = "D54";
	/**通过id获得PRPDRISKENGAGE信息*/
	public static final String FINDPRPDRISKENGAGEBYID = "D55";
	/**通过id获得PRPDRISKITEM信息*/
	public static final String FINDPRPDRISKITEMBYID = "D56";
	/**通过id获得PRPDRISKLIMIT信息*/
	public static final String FINDPRPDRISKLIMITBYID = "D57";
	/**通过id获得PRPDRISKSHORTRATE信息*/
	public static final String FINDPRPDRISKSHORTRATEBYID = "D58";
	/**通过id获得PRPDSHORTRATE信息*/
//	public static final String FINDPRPDSHORTRATEBYID = "D59";
//	***********************************************************
	/**通过condition获得prpDclass的信息*/
	public static final String FINDPRPDCLASSBYCONDITION = "D60";
	/**通过condition获得PrpDengage信息*/
//	public static final String FINDPRPDENGAGEBYCONDITION = "D61";
//	/**通过condition获得PrpDframe信息*/
	public static final String FINDPRPDFRAMEBYCONDITION = "D62";
	/**通过condition获得PrpDitem信息*/
	public static final String FINDPRPDITEMBYCONDITION = "D63";
	/**通过condition获得PrpDitemType信息*/
	public static final String FINDPRPDITEMTYPEBYCONDITION = "D64";
	/**通过condition获得PrpDkind信息*/
	public static final String FINDPRPDKINDBYCONDITION = "D65";
	/**通过condition获得PrpDlimit信息*/
	public static final String FINDPRPDLIMITBYCONDITION = "D66";
//	/**通过condition获得PrpDmaterialInfo信息*/
//	public static final String FINDPRPDMATERIALINFOBYCONDITION = "D67";
	/**通过condition获得PrpDplan信息*/
	public static final String FINDPRPDPLANBYCONDITION = "D68";
//	/**通过condition获得PrpDrisk信息*/
//	public static final String FINDPRPDRISKBYCONDITION = "D69";
	/**通过condition获得PRPDRISKCLAUSEKIND信息*/
	public static final String FINDPRPDRISKCLAUSEKINDBYCONDITION = "D70";
	/**通过condition获得PRPDRISKENGAGE信息*/
	public static final String FINDPRPDRISKENGAGEBYCONDITION = "D71";
	/**通过condition获得PRPDRISKITEM信息*/
	public static final String FINDPRPDRISKITEMBYCONDITION = "D72";
	/**通过condition获得PRPDRISKLIMIT信息*/
	public static final String FINDPRPDRISKLIMITBYCONDITION = "D73";
	/**通过condition获得PRPDRISKSHORTRATE信息*/
	public static final String FINDPRPDRISKSHORTRATEBYCONDITION = "D74";
	/**通过condition获得PRPDSHORTRATE信息*/
//	public static final String FINDPRPDSHORTRATEBYCONDITION = "D75";
	/**通过condition获得PRPDCODECOM信息*/
	public static final String FINDPRPDCODECOMBYCONDITION = "D76";
//	***********************************************************
	/****获得代码名称 分页************/
	public static final String GETCODE = "D77";
	/****获得代码和代码名称 分页************/
	public static final String GETCODEANDNAME = "D78";
	/**获得机构，分页*/
	public static final String GETCOMPANY = "D79";
	/**通过riskcode获得codeCode*/
	public static final String GETCODEWITHRISK = "D80";
	/**通过riskcode获得prpdcode*/
	public static final String GETPRPDCODEWITHRISK = "ALL80";
	/**通过comcode获得codeCode*/
	public static final String GETCODEWITHCOM = "D81";
	/**获得特约条款*/
	public static final String GETRISKENGAGE = "D82";
	/***获得纳税机构*/
	public static final String GETTAXAUTHORITIES = "D83";
	/**获得险种risk*/
	public static final String GETRISK = "D84";
	/**获得险类*/
	public static final String GETCLASS = "D85";
	/**获取 PrpDtreatyReten*/
	public static final String GETPRPDTREATYRETEN = "D86";
//	**************************************************************
	/****************产品创新引擎数据同步接口******start*******/
	public static final String SYNCHRORISKDATA = "D87";
	
	public static final String SYNCHROFRAMEDATA = "D88";

	public static final String SYNCHROPLANDATA = "D89";

	public static final String SYNCHROCLASSDATA = "D90";
	/*************产品创新引擎数据同步接口*********end******/
	public static final String GETRISKBYCONDITION = "D91";
	/*****获取打印类型**/
	public static final String GETPRINTTYPE = "D92";
	
	/**获取系统配置*/
	public static final String GETSERVICEINFOBYCODE="D93";
	/**获取系统地址*/
	public static final String GETURLBYCODE = "D94";
	/**分页获取PrpDagent*/
	public static final String GETAGENT = "D95";
	/**通过主键获得PrpDriskClause*/
	public static final String GETRISKCLAUSE = "D96";
	
	public static final String GETRISKCLAUSEKIND = "D97";
	
	public static final String GETPRPDCLAUSEKIND = "D9797";//added by yuyiqiang 20130226查询条款险别的关系
	
	public static final String GETACCOUNTINFO = "D98";
	
	public static final String GETREINSURER = "D99";
	
	public static final String GETCOINS = "D100";
	
	/** 限额免赔额 */
	public static final String PRPDRISKLIMIT = "D101";
	
	/** 渠道信息 */
	public static final String SYNCHROAGENTDATA = "D102";
	
	/** 国管局项目PICC联系人信息 */
	public static final String PRPDSETTLEMENTLKR = "D103";
	
	/** 国管局项目一级预算单位 */
	public static final String PRPDSETTLEMEMTBYR = "D104";
	
	/** 根据主键获取PrpDstatistics表数据 */
	public static final String GETPRPDSTATISTICS = "D105";
	
	/** 更新PrpDstatistics表数据 */
	public static final String UPDATEPRPDSTATISTICS = "D106";
	
	/** PrpDcode表代码新老转换 */
	public static final String CODETRANSFORM = "D107";
	
	/** 产品代码老转换 */
	public static final String RISKTRANSFORM = "D108";
	
	/** 代码批量翻译 */
	public static final String CODETRANSLATE = "D109";

	/** 获取币别 */
	public static final String GETPRPDCURRENCY = "D110";
	
	/** 巨灾 PrpDdisaster*/
	public static final String PRPDDISASTER = "D111";
	
	/** PrpDtype */
	public static final String PRPDTYPE = "D112";
	
	/** PrpDclass */
	public static final String PRPDCLASS = "D113";
	
	/** PrpDkind */
	public static final String PRPDKIND = "D114";
	
	/** PrpDlimit */
	public static final String PRPDLIMIT = "D115";
	
	/** 获取一条旧代码记录 D116 */
	public static final String GETPRPDOLDCODE = "D116";
	
	/** 获取方案相关信息 D117 */
	public static final String GETPLANINFO = "D117";
	
	/** 获得银行信息 */
	public static final String GETBANK = "D118";
	
	/** PrpDcrossOrg */
	public static final String GETPRPDCROSSORG="D119";
	
	/** 获得产品标的信息 */
	public static final String GETPRPDRISKITEM="D120";
	
	/** 获取港口信息 D121 */
	public static final String GETPRPDPORTS = "D121";
	
	/** 获取短期费率信息 D122 */
	public static final String GETSHORTRATE = "D122";
	
	/**获得机构，分页*/
	public static final String GETCOMPANYS = "D123";
	
	/**分页获取PrpDagent*/
	public static final String GETAGENTBYCODE = "D124";
	
	/** PrpDcontractManage */
	public static final String GETCONTRACTMANAGE="D125";
	
	/** PrpDplan */
	public static final String GETPLAN="D126";
	
	/** PrpDidentifier */
	public static final String GETIDENTITY="D127";
	
	/**分页获取PrpDproject*/
	public static final String GETPROJECTS = "D128";
	
	/** PrpDcurrencyAndExchRateVo */
	public static final String PrpDcurrencyAndExchRate="D129";
	
	/** GetPlanWhetherHasFixed */
	public static final String GetPlanWhetherHasFixed = "D130";
	
	/**分页获取PrpDresource */
	public static final String GETRESOURCE = "D131";
	
	/** 分页获取PrpDSimpleTreaty */
	public static final String GETSIMPLETREATY = "D132";
	
	/** 国民经济代码的分层功能，需要添加的接口 */
	public static final String GETTRADECODES = "D133";
	
	/** 获取短期费率信息 D134 */
	public static final String NEWGETSHORTRATE = "D134";
	
	/** 新旧标的代码转换 D135*/
	public static final String GETRISKITEM = "D135";
	
	/** 新旧限额/免赔代码转换 D136*/
	public static final String GETRISKILIMIT = "D136";
	
	/** 新旧特约代码转换 D137*/
	public static final String GETREVERRISKIENGAGE = "D137";
	
	/** 批量数据字典代码进行旧代码转换 D138*/
	public static final String REVERSECODETYPEANDCODE = "D138";
	
	/** 批量获取系统地址*/
	public static final String GETSERVICEINFOBYCODES = "D139";
	
	/** limitflag = '2'的限额代码的翻译*/
	public static final String  TRANSLATELIMIT = "D140";
	
	/** 获取险别配置信息表*/
	public static final String  GETPRPDCODEKIND = "D141";
	
	/**工作日计算*/
	public static final String COUNTWORKDAY = "D142";
	
	/** 审批系统获取标的接口*/
	public static final String GETITEM = "D143";
	
	/** 获取海外代理人详细信息接口*/
	public static final String GETIDENTITYDESC= "D144";
	
	/** 查询社保地方政策资料 add by chenyi-2011-05-13 */
	public static final String GETINFOMATION = "D145";
	/** 查询社保地方政策资料 add by renshuo-2011-05-13 */
	public static final String GETRISKCLAUSEKINDSUB="D146";
	/**通过riskcode获得codeCode add by renshuo-2011-05-24 */
	public static final String GETSUBCODEWITHRISK = "D147";
	/** add by liufei 根据方案代码获取短期费率信息 D148*/
	public static final String GETSHORTRATERATION = "D148";
	
		/**通过HQL获得PRPDCODE表信息 add by guyanqing-2011-06-24 */
	public static final String GETPRPDBYCONDITON ="D149";
	public static final String GETRISKCLAUSEKINDRELATION="D150";// modify add by renshuo 2011-07-12 reason:增加条款责任互斥依赖关系代码
	
	/**同步废止产品信息 add by guyanqing-2011-09-28 */
	public static final String SYNCHROREVISERISKDATA = "D151";
	
	/**同步废止条款信息 add by guyanqing-2011-09-28 */
	public static final String SYNCHROREVISECLAUSEDATA = "D152";

	/**同步废止条款信息 add by guyanqing-2011-09-28 */
	public static final String SYNCHROMODIFYRISKDATA = "D153";
	
	/**同步备案信息*/
	public static final String SYNCHROMODIFYCLAUSEREPORTDATA="D164";
	

	public static final String GETRATIONRATE = "D154";
	/** 获取方案相关信息 D155 增加个性信息 */
	public static final String GETPLANINFONEW="D155";
	
	/** 个人客户信息查询 add by wanglianzhou 20130410*/
	public static final String GETPRPDCUSTOMERIDV = "D157";
	
	/** 企业客户信息查询 add by wanglianzhou 20130418*/
	public static final String GETPRPDCUSTOMERUNIT = "D158";
	
	/** 保存个人客户信息 add by wanglianzhou 20130423*/
	public static final String SAVEPRPDCUSTOMERIDV = "D159";
	
	/** 保存企业客户信息 add by wanglianzhou 20130423*/
	public static final String SAVEPRPDCUSTOMERUNIT = "D160";
	
	/**險別对应商品代码信息查询 */
	public static final String GETPRPDKINDPRODUCT="D161";
	
	/**险别对应適用車型信息查詢*/
	public static final String ALLOWCARKIND="D162";
	
	/**險別对应文案号信息查询 */
	public static final String GETPRPDKINDREPORT="D163";
	
	/**要保人扩展信息查询*/
	public static final String GETPRPDCUSTOMERFXQ="D164";
	
	/**保存要保人扩展信息*/
	public static final String SAVEPRPDCUSTOMERFQL="D165";
	
	/**同步套装商品信息　add by fengyang 20140401*/
	public static final String SYNCHROPRODUCTSETDATA="D166";
	/** 船舶信息查询 add by fengyang 20140520*/
	public static final String GETPRDITEMSHIP = "D167";
	/** 保存船舶信息 add by fengyang 20140520*/
	public static final String SAVEPRPDITEMSHIP = "D168";
	/** 飞机信息查询 add by fengyang 20140524*/
	public static final String GETPRDITEMPLANE = "D169";
	/** 保存飞机信息 add by fengyang 20140526*/
	public static final String SAVEPRPDPLANE = "D170";
	/**保存文案号 add by  mjx  20150225*/
	public static final String SAVECOPYNUMBER = "D171";
	/**保存文案号 add by  mjx  20150302*/
	public static final String SAVEORUPDATEOCCUPATION = "D172";
	/**保存特約及附加條款 add by  yjm  20150331*/
	public static final String SAVEENGAGEMAINTENANCE = "D173";
	/**保存條款 add by  yjm  20150331*/
	public static final String SAVECLAUSEMAINTENANCE = "D174";
	
	/** mantis：CAR0027，處理人員：DP0706，需求單編號：mantis：CAR0027 :因DMS 正式機上的程式與開發版本有所差異故將DNS查詢方法抽出並另外改寫*/
	public static final String SAVEPRPDCUSTOMERUNITNEW = "D175";
}
