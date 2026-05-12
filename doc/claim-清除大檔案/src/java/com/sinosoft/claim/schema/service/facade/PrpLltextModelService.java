package com.sinosoft.claim.schema.service.facade;

import java.util.List;
import com.sinosoft.claim.schema.model.PrpLltextModel;

/***
 * 理算说明模板读取
 * @author 中科软
 */
public interface PrpLltextModelService {

	public List<PrpLltextModel> findByConditions(String conditions);

}
