package com.sinosoft.productconfig.intf.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;
import java.util.Map;

import com.sinosoft.common.schema.model.PrpCcoins;
import com.sinosoft.common.schema.model.PrpCitemKind;
import com.sinosoft.common.schema.model.PrpCname;
import com.sinosoft.common.schema.model.PrpCopyAppliSub;
import com.sinosoft.common.schema.model.PrpCopyInsured;
import com.sinosoft.common.schema.model.PrpCopyInsuredIdvList;
import com.sinosoft.common.schema.model.PrpCopyInsuredIdvListId;
import com.sinosoft.common.schema.model.PrpCopyInsuredIdvListSubId;
import com.sinosoft.common.schema.model.PrpCration;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPinsured;
import com.sinosoft.common.schema.model.PrpPitemKind;
import com.sinosoft.common.schema.model.PrpPname;
import com.sinosoft.common.schema.model.PrpTmainIdvListTemp;
import com.sinosoft.common.schema.model.PrpVisaRecord;
import com.sinosoft.productconfig.common.schema.model.UtiImportInfo;
import com.sinosoft.productconfig.common.schema.model.UtiImportMap;
import com.sinosoft.prpins.policy.schema.vo.UserInfo;
public interface ProductPolicyImportService { 

    //此方法返回 UtiImportMap对象数组
    public List<UtiImportMap> getUtiImportMapList(String modeId,String flag,String sql);
    //根据riskCode查询UtiImportMap对象
    public List<UtiImportMap> getUtiImportMapListByRiskCode(String riskCode);
    //此方法返回 UtiImportMap所有对象数组
    public List<UtiImportMap> getUtiImportMapListAll(String modeId,String sql);
    //根据条件查询UtiImportMap所有对象数组 
    public List<UtiImportMap> getUtiImportMapListByFlag(String modeId, String insuredFlag, String sql);
    //此方法根据关系人标志返回 UtiImportMap对象数组
    public List<UtiImportMap> getUtiImportMapListInsured(String riskCode,String comCode,String policyType,String flag,String insuredFlag, String sql);
    //此方法返回 导入正式表表名数组
    public List<String> getMapListGroupByTabName(String modeId);
    //此方法保存 temp表数据
//    public boolean genCinsuredIdvListTemp(String hql, List<String> list);
    //校验所有根据Sql来校验的校验规则
    //public String ImportValidBySql(String proposalNo,String riskCode, String busiType, String bizType, String policyType, String mainPolicyNo, String modeId, Map<String,String> mapOccupationCode);
    public String ImportValidBySql(String batchnumber,String riskCode,  String bizType, String policyType,  String modeId, Map<String,String> mapOccupationCode);
    //查询IdvListTempTemp表
    public Page queryPolicyImportTemp(QueryRule QueryRule,int pageNo,int pageSize);
    
    //modify by liuxi 20130828 begin 
    public Page queryQuotationImportTemp(String totalsql, String sql,int pageNo,int pageSize);
    //modify by fangchuanhui 20110902 reason:增加业务类型查询
    public Page queryPolicyImportT(QueryRule QueryRule,int pageNo,int pageSize);
    public Page queryPolicyImportC(QueryRule QueryRule,int pageNo,int pageSize);
    //modify by fangchuanhui  end 20110902 reason:增加业务类型
    //查询pIdvList表
    public Page queryEndorseImport(QueryRule QueryRule,int pageNo,int pageSize);
    //生成cIdvList表数据
//  public boolean genPrpCIdvListData(List<PrpCinsuredIdvList> prpCinsuredIdvLists);
//  //modify begin add by qincao 20110708 增加生成T表数据方法
//  //生成tIdvList表数据
//  public boolean genPrpTIdvListData(List<PrpTinsuredIdvList> prpTinsuredIdvLists);
    //modify end add by qincao 20110708 增加生成T表数据方法
    //生成cIdvList表或者PIdvList数据
    public boolean genPrpCIdvListData(String sql);
    //通过sql语句生成正式表数据
    public boolean genDataBySql(List<String> listSql);
    //通过sql语句查询被保险人信息 add by sunjiuhua 20110527
    public String[][] getInsuredDataBySql(String strSql, int columns,String proposalNo,String selectedNo);
    //生成pIdvList表数据
//    public boolean genPrpPIdvListData(List<PrpPinsuredIdvList> prpPinsuredIdvLists);
    //生成copyIdvList表数据 add by sunjiuhua 20110707
    public boolean genPrpCopyIdvListData(List<PrpCopyInsuredIdvList> prpCopyInsuredIdvLists);
    //生成pappliSub表数据 add by sunjiuhua 20110713
//    public boolean genPrpPappliSubData(List<PrpPappliSub> prpPappliSubs);
    //生成copyAppliSub表数据 add by sunjiuhua 20110713
//    public boolean genPrpCopyAppliSubData(List<PrpCopyAppliSub> prpCopyAppliSubs);
    //生成pInsured表数据 add by sunjiuhua 20110713
    public void genPrpPinsuredData(PrpPinsured pInsured);
    //生成copyInsured表数据 add by sunjiuhua 20110713
    public void genPrpCopyInsuredData(PrpCopyInsured copyInsured);
    //获取idvListtemp表数据
//  public List<PrpCinsuredIdvListTemp> getIdvListTempData(String proposalNo);
//xuli获取Prptmainidvlisttemp表数据
	public List<PrpTmainIdvListTemp> getIdvListTempData(String batchnumber);
//  //从T表中查询idvListtemp 表数据
//  public List<PrpTinsuredIdvListTemp> getIdvListTempDataByT(String proposalNo);//add by fangchuanhui begin 20110913 reason:从t表中查询数据
    //删除 IdvListTemp 表数据
    public void delIdvListTempData(String proposalNo);
    //删除 IdvListTemp 表数据
//  public void delIdvListTempDataAll(List<PrpCinsuredIdvListTemp> prpCinsuredIdvListTemps);
//  //删除 cIdvList表数据
//  public void delIdvListData(List<PrpCinsuredIdvList> prpCinsuredIdvLists);
//  //删除 tIdvList表数据
//  public void delTIdvListData(List<PrpTinsuredIdvList> prpTinsuredIdvLists);
    //删除 pIdvList表数据
//    public void delEndorseIdvListData(List<PrpPinsuredIdvList> prpPinsuredIdvLists);
    //删除 copyIdvList表数据 add by sunjiuhua 20110708
    public void delCopyIdvLists(List<PrpCopyInsuredIdvList> copyIdvLists);
    //删除 prppitemkind add by sunjiuhua 20110719
    public void delPitemKinds(List<PrpPitemKind> prpPitemKinds);
  //mantis： HAS0051，處理人員：Sam，需求單編號：HAS0051 WS_TA批單核保問題處理
    public void updatePitemKinds(List<PrpPitemKind> prpPitemKinds);
    //根据投保单号获取IdvList表数据
//  public List<PrpCinsuredIdvList> getIdvListData(String proposalNo) ;
    //根据批单申请号获取IdvList表数据
//    public List<PrpPinsuredIdvList> getEndorseIdvListData(String applyNo) ;
    //查询cidvlist表数据量
    public int getInsuredCount(String proposalNo) ;
    //查询pidvlist表数据量
    public int getEndorseInsuredCount(String applyNo) ;
    //按主键查找cidvlist表数据
//  public PrpCinsuredIdvList findCIdvListByPk(PrpCinsuredIdvListId prpCinsuredIdvListId);
    //modify begin add by qincao 20110708 增加查询tidvlist表数据方法
    //按主键查找tidvlist表数据
//  public PrpTinsuredIdvList findTIdvListByPk(PrpTinsuredIdvListId prpTinsuredIdvListId);
    //modify end add by qincao 20110708 增加查询tidvlist表数据方法
    //按sql查找cidvlist表数据 add by sunjiuhua 20110524
//  public List<PrpCinsuredIdvList> findCIdvListBySQL(String selectedNo,String sql);//modify by fangchuanhui begin 20110905 reason:修改方法
    //按sql查找pidvlist表数据 add by sunjiuhua 20110524
//    public List<PrpPinsuredIdvList> findPIdvListBySQL(String sql);
    //按sql查找copyidvlist表数据 add by sunjiuhua 20110907
    public List<PrpCopyInsuredIdvList> findCopyIdvListBySQL(String sql);
    // 按sql查找citemKind表数据 add by sunjiuhua 20110715
    public List<PrpCitemKind> findCItemKindBySQL(String sql);
    // 按sql查找cration表数据 add by sunjiuhua 20110716
    public List<PrpCration> findCRationBySQL(String sql);
    //按主键查找cidvlisttemp表数据
//  public PrpCinsuredIdvListTemp findCIdvListTempByPk(PrpCinsuredIdvListTempId prpCinsuredIdvListTempId);
    //按主键查找pidvlist表数据
//    public PrpPinsuredIdvList findPIdvListByPk(PrpPinsuredIdvListId prpPinsuredIdvListId);
    //按主键查找copyIdvlist表数据 add by sunjiuhua 20110708
    public PrpCopyInsuredIdvList findCopyIdvListByPk(PrpCopyInsuredIdvListId id);
    //按主键查找CIdvlistNature表数据
//  public PrpCinsuredIdvListNature findCIdvNatureByPk(PrpCinsuredIdvListNatureId natureId);
    //modify begin add by qincao 20110708 增加查询TIdvlistNature表数据方法
    //按主键查找TIdvlistNature表数据
//  public PrpTinsuredIdvListNature findTIdvNatureByPk(PrpTinsuredIdvListNatureId natureId);
    //modify end add by qincao 20110708 增加查询TIdvlistNature表数据方法
    //按主键查找PIdvlistNature表数据
//    public PrpPinsuredIdvListNature findPIdvNatureByPk(PrpPinsuredIdvListNatureId natureId);
    //查询投保单下条款
    public Page getRationData(QueryRule queryRule, int pageNo, int pageSize, String busiType);
    //更新PinsuredIdvList表数据
//    public void updatePinsuredIdvList(PrpPinsuredIdvList prppinsuredIdvList);
    //更新copyInsuredIdvList表数据 add by sunjiuhua 20110707
//    public void updateCopyInsuredIdvList(PrpCopyInsuredIdvList prpCopyInsuredIdvList);
    //更新CinsuredIdvList表数据
//  public void updateCinsuredIdvList(PrpCinsuredIdvList prpcinsuredIdvList);
    //modify begin add by qincao 20110711 增加更新TinsuredIdvList表数据方法
    //更新TinsuredIdvList表数据
//  public void updateTinsuredIdvList(PrpTinsuredIdvList prpctinsuredIdvList);
    //modify end add by qincao 20110711 增加更新TinsuredIdvList表数据方法
    
    //add by liufei 团单导入T表改造 begin
    //获取TinsuredIdvList表最大的serialNo
    public int genMaxTIdvListData(String proposalNo);
    //add by fangchuanhui 团单浏览20110903
    public int getMaxIdvListData(String proposalNo,String selectedNo);
    //获取prpTname表最大serialNo
    public int genMaxTNameListData(String proposalNo);;
    //获取PrpTinsured表最大serialNo
    public int genMaxTinsured(String proposalNo);
    //add by liufei 团单导入T表改造 end
    
    //获取CinsuredIdvList表最大的serialNo
    public int genMaxCIdvListData(String proposalNo);
    //获取PinsuredIdvList表最大的serialNo
    public int genMaxPIdvListData(String applyNo);
    //获取prpCname表最大serialNo
    public int genMaxCNameListData(String proposalNo);
    //获取PrpPname表最大serialNo
    public int genMaxPNameListData(String applyNo);
    //获取PrpCinsured表最大serialNo
    public int genMaxCinsured(String proposalNo);
    //获取prpPinsured表最大serialNo
    public int genMaxPinsured(String applyNo);
    //查询cIdvList表
//  public List<PrpCinsuredIdvList> queryCIdvList(QueryRule queryRule);
    //按主键删除cidvlist表数据
//  public void deleteCIdvListByPk(PrpCinsuredIdvListId id);
    //按主键删除pidvlist表数据
//    public void deletePIdvListByPk(PrpPinsuredIdvListId id);
    //按主键删除copyIdvlist表数据 add by sunjiuhua 20110713
    public void deleteCopyIdvListByPk(PrpCopyInsuredIdvListId id);
    // 删除告知信息 add by sunjiuhua 20110708
//    public void deletePIdvListSubByPk(PrpPinsuredIdvListSubId id);
    // 删除告知信息 add by sunjiuhua 20110708
    public void deleteCopyIdvListSubByPk(PrpCopyInsuredIdvListSubId id);
    // 删除占比信息 add by sunjiuhua 20110713
//    public void deletePappliSubs(List<PrpPappliSub> prpPappliSubs);
    // 删除占比信息 add by sunjiuhua 20110713
    public void deleteCopyAppliSubs(List<PrpCopyAppliSub> prpCopyAppliSubs);
    //根据sql删除正式表数据
    public void delImportListDataBySql(String sql);
    //根据主键获取UtiImportInfo表数据
    public UtiImportInfo findUtiImportInfoByPk(String riskCode,String policyType,String comCode);
    //根据No和证件号,serialNo查询
//  public List<PrpCinsuredIdvList> getIdvListDataByIdSerialNo(String proposalNo,String identifyType,String identifyNumber,int serialNo);
    //modify begin add by qincao 20110711 增加查询T表方法
    //根据No和证件号,serialNo查询
//  public List<PrpTinsuredIdvList> getTIdvListDataByIdSerialNo(String proposalNo,String identifyType,String identifyNumber,int serialNo);
    //modify end add by qincao 20110711 增加查询T表方法
    //根据No和证件号,serialNo查询
//    public List<PrpPinsuredIdvList> getPIdvListDataByIdSerialNo(String applyNo,String identifyType, String identifyNumber,int serialNo);
//  根据No和证件号查询
//  public List<PrpCinsuredIdvList> getIdvListDataById(String proposalNo,String insuredCName,String identifyType,String identifyNumber);
    //modify begin add by qincao 20110708 增加查询T表方法
    //根据No和证件号查询
//  public List<PrpTinsuredIdvList> getTIdvListDataById(String proposalNo,String insuredCName,String identifyType,String identifyNumber);
    //modify end add by qincao 20110708 增加查询T表方法
    //根据No和证件号查询
//    public List<PrpPinsuredIdvList> getPIdvListDataById(String applyNo,String identifyType, String identifyNumber);
    //根据proposalNo查询prpCinsuredIdvListTemp表中序号最大值
    public int getMaxSerialNo(String proposalNo);
    //根据proposalNo查询prpCinsuredIdvListTemp表中数据条数
    public int getCountPrpCinsuredIdvList(String proposalNo);
    // 根据relationSerialNo查询关联受益人
//    public List<PrpCinsuredIdvList> findCIdvByRelation(String businessNo, Integer serialNo);
    //modify begin add by qincao 2010708 增加查询T表关联受益人方法
    //根据relationSerialNo查询关联受益人
//    public List<PrpTinsuredIdvList> findTIdvByRelation(String businessNo, Integer serialNo);
    //modify end add by qincao 2010708 增加查询T表关联受益人方法
    // 根据relationSerialNo查询关联受益人
//    public List<PrpPinsuredIdvList> findPIdvByRelation(String businessNo, Integer serialNo);
    // 根据relationSerialNo查询关联受益人 add by sunjiuhua 20110708
    public List<PrpCopyInsuredIdvList> findCopyIdvByRelation(String applyNo, Integer serialNo);
    //根据主键删除CIdvListNature
//    public void deleteCIdvNature(PrpCinsuredIdvListNature nature);
    //modify begin add by qincao 20110708 增加删除TIdvListNature方法
    //根据主键删除TIdvListNature
//    public void deleteTIdvNature(PrpTinsuredIdvListNature nature);
    //modify end add by qincao 20110708 增加删除TIdvListNature方法
    //根据主键删除PIdvListNaure
//    public void deletePIdvNature(PrpPinsuredIdvListNature nature);
    //删除PrpPinsuredIdvList add by sunjiuhua 20110708
//    public void deletePIdvList(PrpPinsuredIdvList pIdvList);
    //删除PrpCopynsuredIdvList add by sunjiuhua 20110708
    public void deleteCopyIdvList(PrpCopyInsuredIdvList copyIdvList);
    //根据insuredFlag获取UtiImportMap对象
    public List<UtiImportMap> getUtiImportMapListByInsured(String modeId, String insuredFlag, String flag, String sql);
//  生成PrpVisaRecord表数据
    public boolean genPrpVisaRecordListData(List<PrpVisaRecord> prpVisaRecordList);
//  获取PrpCcoins数据
    public List<PrpCcoins> getPrpCcoinsList(String proposalNo);
    //获取prpcinsuredIdvList表中的职业代码
    public List<String> getOccupationCode(String proposalNo, String tableName, String policyType);
    //excel数据存储到临时表
    public boolean genTempDataBySql(List<String> listSql, List<List> listData);
    public boolean genDataBySql(String sql1, String sql2);
    //查询cidvlist数据是否已存在数据
    public boolean isHaveItems(String proposalNo);
    //获取职业类别
    public Map<String, String> getDutyType(UserInfo userInfo,String riskCode);
    //根据模板号获取正式表表名
    public List<String> getTableNameByModeId(String modeId);
    //获取prpPname信息
    public List<PrpPname> getPrpPnamesByApplyNo(String applyNo);
    // 获取prpCname信息
    public List<PrpCname> getprpCnameByProposalNo(String proposalNo);
    //获取prpPidvListNature信息
//    public List<PrpPinsuredIdvListNature> getidvListNature(String applyNo);
    
    public void updatePrphead(PrpPhead prpPhead, PrpPhead prpPheadOld);
    
//    public void updateIdvList(List<PrpPinsuredIdvList> prpPinsuredIdvLists);
    
//    public void updateIdvListNature(List<PrpPinsuredIdvListNature> prpPinsuredIdvListNatures);
    //MODIFY BEGIN-ADD-chenyi-20110628-reason：获取职业代码map，中文-代码键值对
    public Map<String, String> getOccupationCodeMap(UserInfo userInfo,String riskCode);
    // 删除后保存前的手动flush add by sunjiuhua 20110715
    public void flushService();
    // 清空session add by sunjiuhua 20111015
    public void clearService();
    //2011-10-19 add by gaopeng
    public List<PrpCopyInsuredIdvList> findPrpCopyInsuredIdvList(String applyNo,Integer relateSerialNo);
//    public List<PrpPinsuredIdvList> findPrpPInsuredIdvList(String applyNo,Integer relateSerialNo);
//    public void delPIdvLists(List<PrpPinsuredIdvList> pIdvLists);
    //20110921 modify by gaopeng 增加跟新prpPHead表方法
    public boolean genPrpHeadListData(PrpPhead prpPhead);
    //2011-10-12 modify by gaopeng
    public void deletePrpPtextByPk(String id);
    //2011-10-22 modify by gaopeng
    public List<PrpCopyInsuredIdvList> findCopyIdvList(String applyNo);
    //add by sucong 20130830
    public Page queryBatchQuotation(String riskCode,String totalsql, String sql, int pageNo, int pageSize);
    //add  by  mjx  火险批次转档查询   20150306
	public Page queryFImportTemp(String totalsql, String sql, int pageNo,int pageSize);
	//mantis： LIA0287，處理人員：Sam，需求單編號：LIA0287 行動裝置保險WS功能開發
	public void repairPrpCplans(String policyNo , String endorseNo , Integer payNo);
}