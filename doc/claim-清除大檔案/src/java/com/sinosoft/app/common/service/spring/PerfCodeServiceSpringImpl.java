/************************************************************************
 * Description: 代码获取类
 * Author     : 
 * CreateDate : 2011-08-10
 * UpdateLog  : Name           Date         Reason/Content
 *          ------------------------------------------------------------
 *
 ************************************************************************/
package com.sinosoft.app.common.service.spring;

import com.sinosoft.app.common.model.PerfCode;
import com.sinosoft.app.common.model.PerfCodeId;
import com.sinosoft.app.common.service.facade.PerfCodeService;
import com.sinosoft.sys.platform.common.Contacts;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;

import java.util.List;

@SuppressWarnings("unchecked")
public class PerfCodeServiceSpringImpl extends GenericDaoHibernate<PerfCode, String> implements PerfCodeService {

	/**
	 * 初始缓存实例
	 */
	private static CacheService cacheManager = CacheManager.getInstance("perfCode");

	/**
	 * PerfCode表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @return 代码列表
	 */
	public List<PerfCode> findPerfCodeList(String codeType) throws Exception {
		String key = cacheManager.generateCacheKey("findCacheKeyPerfCodeList", codeType);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<PerfCode>) result;
		}
		String hql = "select a from PerfCode a where a.id.codeType=? and a.validStatus='1' order by displayNo";
		List<PerfCode> perfCodeList = this.findByHql(hql, codeType);
		cacheManager.putCache(key, perfCodeList);
		return perfCodeList;
	}

	/**
	 * PerfCode表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PerfCode findPerfCodeById(String codeType, String codeCode) throws Exception {
		String key = cacheManager.generateCacheKey("findPerfCodeById", codeType, codeCode);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (PerfCode) result;
		}
		PerfCodeId id = new PerfCodeId();
		id.setCodeType(codeType);
		id.setCodeCode(codeCode);
		PerfCode perfCode = this.get(PerfCode.class, id);
		cacheManager.putCache(key, perfCode);
		return perfCode;
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param isConnect 是否需要代码和名称连接显示
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @return String 列表值
	 */
	public String getSelectValue(boolean isConnect, String codeType, boolean isBlankLine) throws Exception {
		List<PerfCode> codes = findPerfCodeList(codeType);
		return listToString(isConnect, codes, isBlankLine);
	}

	/**
	 * List转换String
	 * @param isConnect 是否需要代码和名称连接显示
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String listToString(boolean isConnect, List<PerfCode> codes, boolean isBlankLine) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (codes.size() == 0) {
			return buffer.toString();
		} else {
			buffer.append("{#");
			// 是否添加全部
			if (isBlankLine) {
				buffer.append("'':'請選擇',");
			}
			for (int i = 0; i < codes.size(); i++) {
				PerfCode code = (PerfCode) codes.get(i);
				buffer.append("'");
				buffer.append(code.getId().getCodeCode());
				buffer.append("':'");
				if (isConnect == true) {
					buffer.append(code.getId().getCodeCode() + "-" + code.getCodeCName());
				} else {
					buffer.append(code.getCodeCName());
				}
				buffer.append("'");
				if (i != codes.size() - 1) {
					buffer.append(",");
				}
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	/**
	 * 查询代码类型
	 * @param PerfType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	@SuppressWarnings("unchecked")
	public Page queryPerfCode(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		Page page = super.find(queryRule, pageNo, pageSize);
		// 代码翻译
		List<PerfCode> perfCodes = page.getResult();
		return new Page(0, page.getTotalCount(), pageSize, perfCodes);
	}

	/**
	 * 删除代码类型
	 * @param String codeType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void deletePerfCode(String codeType, String codeCode) throws Exception {
		PerfCode perfCode = findPerfCodeById(codeType, codeCode);
		// 物理删除
		// perfCode.setValidStatus("0");
		super.delete(perfCode);
		// super.getHibernateTemplate().merge(perfCode);
		cacheManager.clearAllCacheManager();
	}

	/**
	 * 保存代码类型
	 * @param PerfType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void savePerfCode(PerfCode perfCode, String operateType) throws Exception {

		PerfCode type = findPerfCodeById(perfCode.getId().getCodeType(), perfCode.getId().getCodeCode());
		System.out.println("type=" + type);
		if (Contacts.OperateADD.equals(operateType)) {
			if (null==type||"".equals(type)) {// 新增
				perfCode.setValidStatus("1");
				super.save(perfCode);
			} else {
				throw new BusinessException(perfCode.getId().getCodeCode() + "已存在！", false);
			}
		} else if (Contacts.OperateUPDATE.equals(operateType)) {
			perfCode.getId().setCodeType(type.getId().getCodeType());
			super.getHibernateTemplate().merge(perfCode);
		}
		cacheManager.clearAllCacheManager();
	}

	@Override
	public List<PerfCode> findPerfCodeByRule(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void updatePerfCode(PerfCode perfCode) throws Exception {
		perfCode.setValidStatus("0");
		super.getHibernateTemplate().merge(perfCode);
		cacheManager.clearAllCacheManager();
	}

	@Override
	public PerfCode findPerfCodeByComcode(String comCode) throws Exception {
		String hql = "From PerfCode where id.codeType in ('East','South','West','North') and id.codeCode=?";
		List<PerfCode> list = super.findByHql(hql, comCode);
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
