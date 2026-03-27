package cn.com.sinosoft.test;

import ins.framework.common.QueryRule;

import java.util.ArrayList;
import java.util.List;

public class AAA {
	public static void main(String [] args) throws NoSuchFieldException{
		QueryRule q =QueryRule.getInstance();
		List list = new ArrayList();
		ins.framework.utils.BeanUtils.forceSetProperty(q, "propertyName", "aa");	
		ins.framework.utils.BeanUtils.forceSetProperty(q, "queryRuleList", list);	
		Object o = q;
	}
}
