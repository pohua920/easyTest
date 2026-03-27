package com.sinosoft.claim.schema.service.spring;
/**
 * 支付帳户信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDpaymentAccount;
import com.sinosoft.claim.schema.model.PrpLaccount;
import com.sinosoft.claim.schema.service.facade.PrpDpaymentAccountService;
import com.sinosoft.claim.schema.service.facade.PrpLaccountService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

public class PrpDpaymentAccountServiceSpringImpl extends GenericDaoHibernate<PrpDpaymentAccount, String> implements PrpDpaymentAccountService{

	private PrpLaccountService prpLaccountService;
    /**
     * 插入一条数据
     * @param prpdPaymentAccountDto prpdPaymentAccountDto
     * @throws Exception
     */
	@Override
	public void save(PrpDpaymentAccount prpDpaymentAccount) throws Exception {
		super.save(prpDpaymentAccount);
	}

    /**
     * 采用批方式插入多条数据
     * @param collection collection
     * @throws Exception
     */
    @Override
    public void insertAll(List<PrpDpaymentAccount> list)
            throws Exception{
    		super.saveAll(list);
    }

    /**
     * 按主键删除一条数据
     * @param accountCode accountCode
     * @throws Exception
     */
    public void delete(String accountCode)
            throws Exception{
    	super.deleteByPK(PrpDpaymentAccount.class, accountCode);
    }

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param prpdPaymentAccountDto prpdPaymentAccountDto
     */
    @Override
    public void update(PrpDpaymentAccount prpdPaymentAccountDto){
    	if(prpdPaymentAccountDto != null){
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			try {
				session.merge(prpdPaymentAccountDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

    /**
     * 按主键查找一条数据
     * @param accountCode accountCode
     * @return PrpdPaymentAccountDto
     * @throws Exception
     */
    public PrpDpaymentAccount findByPrimaryKey(String accountCode)
            throws Exception{
    	return super.get(PrpDpaymentAccount.class,accountCode);
    }

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public List<PrpDpaymentAccount> findByConditions(String conditions,int pageNo,int pageSize)
            throws Exception{
    	conditions = DataUtils.emptyToNull(conditions)==null?"1=1":conditions;
    	QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
    	Page page = super.find(queryRule, pageNo, pageSize);
    	List<PrpDpaymentAccount> resultList = page.getResult();
    	return resultList;
    }
    
	/**
	 * 按条件查询多条数据
	 * mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage每页的行数
	 * @return Page
	 * @throws Exception
	 */
	@Override
	public Page findByConditionsForPage(String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule, pageNo, rowsPerPage);
	}

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection
     * @throws Exception
     */
    public List<PrpDpaymentAccount> findByConditions(String conditions)
            throws Exception{
        return findByConditions(conditions,0,0);
    }

    /**
     * 按条件删除数据
     * @param conditions 查询条件
     * @return 删除的行数
     * @throws Exception
     */
    public int deleteByConditions(String conditions)
            throws Exception{
        StringBuffer buffer = new StringBuffer(100);
        buffer.append("DELETE FROM PrpdPaymentAccount WHERE ");
        buffer.append(conditions);
        Session session = super.getSession();
		Query q = session.createQuery(buffer.toString());
		int count = q.executeUpdate();
		return count;
    }

    /**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(String conditions) 
        throws Exception{
        int count = -1;
        String sql = "SELECT count(*) FROM PrpdPaymentAccount WHERE " + conditions;
        List<?> result = HibernateUtils.findbySql(super.getSession(), sql);
        count = Integer.parseInt(result.get(0).toString());
        return count;
    }

	public PrpDpaymentAccount saveAccount(HashMap<?,?> hashMap) throws Exception {
		String accountCode = (String) hashMap.get("AccountCode");
		String registNo = (String) hashMap.get("RegistNo");
		String serialNo = (String) hashMap.get("serialNo");
		PrpDpaymentAccount prpdPaymentAccount = null;
		String conditions = "registNo = '" + registNo + "' and accountCode = '"+ accountCode + "'";
			prpdPaymentAccount = this.findByPrimaryKey(accountCode);
			if (prpdPaymentAccount == null) {
				prpdPaymentAccount = new PrpDpaymentAccount();
				String accountCurrency = DataUtils.dbNullToEmpty((String) hashMap.get("AccountCurrency"));
				String accountType = DataUtils.dbNullToEmpty((String) hashMap.get("AccountType"));
				String accountName = DataUtils.dbNullToEmpty((String) hashMap.get("AccountName"));
				String customerCode = DataUtils.dbNullToEmpty((String) hashMap.get("CustomerCode"));
				String userCode = DataUtils.dbNullToEmpty((String) hashMap.get("UserCode"));
				String ownerType = DataUtils.dbNullToEmpty((String) hashMap.get("OwnerType"));
				String ownerName = DataUtils.dbNullToEmpty((String) hashMap.get("OwnerName"));
				String certificateType = DataUtils.dbNullToEmpty((String) hashMap.get("CertificateType"));
				String certificateCode = DataUtils.dbNullToEmpty((String) hashMap.get("CertificateCode"));
				String ownerPhoneNo = DataUtils.dbNullToEmpty((String) hashMap.get("OwnerPhoneNo"));
				String operatorCode = DataUtils.dbNullToEmpty((String) hashMap.get("OperatorCode"));
				String operatorComCode = DataUtils.dbNullToEmpty((String) hashMap.get("OperatorComcode"));
				String operatorName = DataUtils.dbNullToEmpty((String) hashMap.get("OperatorName"));
				String operateDate = DataUtils.dbNullToEmpty((String) hashMap.get("OperateDate"));
				String updateDate = DataUtils.dbNullToEmpty((String) hashMap.get("UpdateDate"));
				String validStatus = DataUtils.dbNullToEmpty((String) hashMap.get("ValidStatus"));
				String remark = DataUtils.dbNullToEmpty((String) hashMap.get("Remark"));
				String bankCode = DataUtils.dbNullToEmpty((String) hashMap.get("BankCode"));
				String bankName = DataUtils.dbNullToEmpty((String) hashMap.get("BankName"));
				
				String areaCode = DataUtils.dbNullToEmpty((String) hashMap.get("AreaCode"));
				String compensateOwnerName = DataUtils.dbNullToEmpty((String) hashMap.get("CompensateOwnerName"));
				String courierAddress = DataUtils.dbNullToEmpty((String) hashMap.get("CourierAddress"));
				String customBankName = DataUtils.dbNullToEmpty((String) hashMap.get("CustomBankName"));
				String customBankCode = DataUtils.dbNullToEmpty((String) hashMap.get("CustomBankCode"));
				String uniformNo = DataUtils.dbNullToEmpty((String) hashMap.get("UniformNo"));
				
				
				prpdPaymentAccount.setAccountCode(accountCode);
				prpdPaymentAccount.setAccountCurrency(accountCurrency);
				prpdPaymentAccount.setAccountType(accountType);
				prpdPaymentAccount.setAccountName(accountName);
				prpdPaymentAccount.setCustomerCode(customerCode);
				prpdPaymentAccount.setUserCode(userCode);
				prpdPaymentAccount.setOwnerType(ownerType);
				prpdPaymentAccount.setOwnerName(ownerName);
				prpdPaymentAccount.setCertificateType(certificateType);
				prpdPaymentAccount.setCertificateCode(certificateCode);
				prpdPaymentAccount.setOwnerPhoneNo(ownerPhoneNo);
				prpdPaymentAccount.setOperatorCode(operatorCode);
				prpdPaymentAccount.setOperatorComCode(operatorComCode);
				prpdPaymentAccount.setOperatorName(operatorName);
				prpdPaymentAccount.setOperateDate(new DateTime(operateDate));
				prpdPaymentAccount.setUpdateDate(new DateTime(updateDate));
				prpdPaymentAccount.setValidStatus(validStatus);
				prpdPaymentAccount.setRemark(remark);
				prpdPaymentAccount.setBankCode(bankCode);
				prpdPaymentAccount.setBankName(bankName);
				prpdPaymentAccount.setAreaCode(areaCode);
				prpdPaymentAccount.setCompensateOwnerName(compensateOwnerName);
				prpdPaymentAccount.setCourierAddress(courierAddress);
				prpdPaymentAccount.setCustomBankName(customBankName);
				prpdPaymentAccount.setCustomBankCode(customBankCode);
				prpdPaymentAccount.setUniformNo(uniformNo);
				
				prpdPaymentAccount.setOperateSys("LOL");// 代表信息来自老理赔
				prpdPaymentAccount.setUsedOrNot("0");// 新增的帳户默认未用於实收实付
				save(prpdPaymentAccount);
			} else {
				prpdPaymentAccount = new PrpDpaymentAccount();
				String accountCurrency = DataUtils.dbNullToEmpty((String) hashMap.get("AccountCurrency"));
				String accountType = DataUtils.dbNullToEmpty((String) hashMap.get("AccountType"));
				String accountName = DataUtils.dbNullToEmpty((String) hashMap.get("AccountName"));
				String customerCode = DataUtils.dbNullToEmpty((String) hashMap.get("CustomerCode"));
				String userCode = DataUtils.dbNullToEmpty((String) hashMap.get("UserCode"));
				String ownerType = DataUtils.dbNullToEmpty((String) hashMap.get("OwnerType"));
				String ownerName = DataUtils.dbNullToEmpty((String) hashMap.get("OwnerName"));
				String certificateType = DataUtils.dbNullToEmpty((String) hashMap.get("CertificateType"));
				String certificateCode = DataUtils.dbNullToEmpty((String) hashMap.get("CertificateCode"));
				String ownerPhoneNo = DataUtils.dbNullToEmpty((String) hashMap.get("OwnerPhoneNo"));
				String operatorCode = DataUtils.dbNullToEmpty((String) hashMap.get("OperatorCode"));
				String operatorComCode = DataUtils.dbNullToEmpty((String) hashMap.get("OperatorComcode"));
				String operatorName = DataUtils.dbNullToEmpty((String) hashMap.get("OperatorName"));
				String operateDate = DataUtils.dbNullToEmpty((String) hashMap.get("OperateDate"));
				String updateDate = DataUtils.dbNullToEmpty((String) hashMap.get("UpdateDate"));
				String validStatus = DataUtils.dbNullToEmpty((String) hashMap.get("ValidStatus"));
				String remark = DataUtils.dbNullToEmpty((String) hashMap.get("Remark"));
				String bankCode = DataUtils.dbNullToEmpty((String) hashMap.get("BankCode"));
				String bankName = DataUtils.dbNullToEmpty((String) hashMap.get("BankName"));
				String areaCode = DataUtils.dbNullToEmpty((String) hashMap.get("AreaCode"));
				String compensateOwnerName = DataUtils.dbNullToEmpty((String) hashMap.get("CompensateOwnerName"));
				String courierAddress = DataUtils.dbNullToEmpty((String) hashMap.get("CourierAddress"));
				String customBankName = DataUtils.dbNullToEmpty((String) hashMap.get("CustomBankName"));
				String customBankCode = DataUtils.dbNullToEmpty((String) hashMap.get("CustomBankCode"));
				String uniformNo = DataUtils.dbNullToEmpty((String) hashMap.get("UniformNo"));
				prpdPaymentAccount.setAccountCode(accountCode);
				prpdPaymentAccount.setAccountCurrency(accountCurrency);
				prpdPaymentAccount.setAccountType(accountType);
				prpdPaymentAccount.setAccountName(accountName);
				prpdPaymentAccount.setCustomerCode(customerCode);
				prpdPaymentAccount.setUserCode(userCode);
				prpdPaymentAccount.setOwnerType(ownerType);
				prpdPaymentAccount.setOwnerName(ownerName);
				prpdPaymentAccount.setCertificateType(certificateType);
				prpdPaymentAccount.setCertificateCode(certificateCode);
				prpdPaymentAccount.setOwnerPhoneNo(ownerPhoneNo);
				prpdPaymentAccount.setUpdateDate(new DateTime(updateDate));
				prpdPaymentAccount.setOperatorCode(operatorCode);
				prpdPaymentAccount.setOperatorComCode(operatorComCode);
				prpdPaymentAccount.setOperatorName(operatorName);
				prpdPaymentAccount.setOperateDate(new DateTime(operateDate));
				prpdPaymentAccount.setUpdateDate(new DateTime(new Date()));
				prpdPaymentAccount.setValidStatus(validStatus);
				prpdPaymentAccount.setRemark(remark);
				prpdPaymentAccount.setBankCode(bankCode);
				prpdPaymentAccount.setBankName(bankName);
				prpdPaymentAccount.setAreaCode(areaCode);
				prpdPaymentAccount.setCompensateOwnerName(compensateOwnerName);
				prpdPaymentAccount.setCourierAddress(courierAddress);
				prpdPaymentAccount.setCustomBankName(customBankName);
				prpdPaymentAccount.setCustomBankCode(customBankCode);
				prpdPaymentAccount.setUniformNo(uniformNo);
				prpdPaymentAccount.setOperateSys("LOL");// 代表信息来自老理赔
				prpdPaymentAccount.setUsedOrNot("1");
				update(prpdPaymentAccount);
			}
			List<PrpLaccount> col =this.prpLaccountService.findByConditions(conditions);
			if (col.size() > 0) {

			} else {
				int count = this.prpLaccountService.getCount("registNo='" + registNo + "'");
				PrpLaccount prpLaccount = new PrpLaccount();
				prpLaccount.setAccountCode(accountCode);
				prpLaccount.getId().setRegistNo(registNo);
				prpLaccount.getId().setSerialNo(count + 1);
				this.prpLaccountService.save(prpLaccount);
			}
			prpdPaymentAccount.setSerialNo(serialNo);
	
		return prpdPaymentAccount;
	}

	public PrpLaccountService getPrpLaccountService() {
		return prpLaccountService;
	}

	public void setPrpLaccountService(PrpLaccountService prpLaccountService) {
		this.prpLaccountService = prpLaccountService;
	}

}
