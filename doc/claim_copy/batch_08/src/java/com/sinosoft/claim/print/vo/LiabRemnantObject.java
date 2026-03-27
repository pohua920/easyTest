/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;


/**
 * 责任险  残余物理算书  数据对象 ,继承工程险理算，不在添加重复的属性
 * @author 中科軟
 */
public class LiabRemnantObject  extends CompensateObject{

	public LiabRemnantObject() {
		super();
	}
	public LiabRemnantObject(CompensateObject compensateObject) {
		super(compensateObject);
	}
}
