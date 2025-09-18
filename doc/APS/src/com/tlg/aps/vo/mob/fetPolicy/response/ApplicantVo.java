package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicantVo {
	
	@SerializedName("NAME")
	private String name;

	@SerializedName("OCCUPATION")
    private String occupation;

	@SerializedName("RESPONSIBLE_PERSON")
    private String responsiblePerson;

	@SerializedName("CUSTOMER_ID")
    private String customerId;

	@SerializedName("BIRTHDAY")
    private String birthday;

	@SerializedName("TEL_NO")
    private String telNo;

	@SerializedName("ADDRESS")
    private String address;

	@SerializedName("EMAIL_ADDRESS")
    private String emailAddress;

	@SerializedName("COUNTRY")
    private String country;

	@SerializedName("ZIP_CODE")
    private String zipCode;

	@SerializedName("RELEATION")
    private String releation;

	@SerializedName("RELEATION_DESC")
    private String releationDesc;

	@SerializedName("ID_TYPE")
    private String idType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getReleation() {
		return releation;
	}

	public void setReleation(String releation) {
		this.releation = releation;
	}

	public String getReleationDesc() {
		return releationDesc;
	}

	public void setReleationDesc(String releationDesc) {
		this.releationDesc = releationDesc;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
}
