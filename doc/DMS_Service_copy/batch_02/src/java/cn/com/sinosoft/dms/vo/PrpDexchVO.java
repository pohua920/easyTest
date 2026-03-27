package cn.com.sinosoft.dms.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PrpDexchVO {
	private static final long serialVersionUID = 1L;
	private Date exchDate;
	private String baseCurrency;
	private String exchCurrency;
	private Integer base;
	private BigDecimal exchRate;
	private BigDecimal buyPrice;
	private BigDecimal salePrice;
	private BigDecimal cashPrice;
	private String flag;

	public PrpDexchVO() {
	}

	public Date getExchDate() {
		return exchDate;
	}

	public void setExchDate(Date exchDate) {
		this.exchDate = exchDate;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getExchCurrency() {
		return exchCurrency;
	}

	public void setExchCurrency(String exchCurrency) {
		this.exchCurrency = exchCurrency;
	}

	public Integer getBase() {
		return base;
	}

	public void setBase(Integer base) {
		this.base = base;
	}

	public BigDecimal getExchRate() {
		return exchRate;
	}

	public void setExchRate(BigDecimal exchRate) {
		this.exchRate = exchRate;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getCashPrice() {
		return cashPrice;
	}

	public void setCashPrice(BigDecimal cashPrice) {
		this.cashPrice = cashPrice;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
