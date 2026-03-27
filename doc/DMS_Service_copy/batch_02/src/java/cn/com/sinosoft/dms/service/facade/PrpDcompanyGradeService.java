package cn.com.sinosoft.dms.service.facade;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompany;


public interface PrpDcompanyGradeService {
	void insertGrade(PrpDcompany prpDcompany, PrpDcompany newCompany);

	void clearPrpDcompanyGrade();//删除库中所有数据
    
	/**
	 * @param prpDcompany 增加机构的信息
	 * @param subComCode   下级机构代码（如果为空则要增加的机构为叶子节点）
	 */
	public void insertPrpDcompanyGrade(PrpDcompany prpDcompany,String subComCode);
	
	/**
	 * @param prpDcompany 删除机构的信息
	 * @param subComCode  下级机构代码（如果为空则要删除的机构为叶子节点）
	 */
	public void deletePrpDcompanyGrade(PrpDcompany prpDcompany);
}
