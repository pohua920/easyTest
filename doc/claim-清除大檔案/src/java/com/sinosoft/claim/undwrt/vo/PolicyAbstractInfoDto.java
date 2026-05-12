package com.sinosoft.claim.undwrt.vo;

/**
 * @author 中科软
 */
public class PolicyAbstractInfoDto {
	/**保品损失*/
	private double sumLoss = 0d; 
	/**其他费用*/
	private double other = 0d; 
	/**结案合计*/
	private double sumPaid = 0d; 

	public double getOther() {
		return other;
	}

	public void setOther(double other) {
		this.other = other;
	}

	public double getSumLoss() {
		return sumLoss;
	}

	public void setSumLoss(double sumLoss) {
		this.sumLoss = sumLoss;
	}

	public double getSumPaid() {
		return sumPaid;
	}

	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
	}
}