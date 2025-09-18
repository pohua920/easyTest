package com.tlg.msSqlSh.entity;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tlg.iBatis.IBatisBaseEntity;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public class CtbcStockholderTemp extends IBatisBaseEntity<BigDecimal> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CtbcStockholderTemp.class);
    private String cname;
    private String ename;
    private String id1;
    private String id2;
    private String id3;
    private String id4;
    private String id5;
    private String relationcode;
    private String relationcodename;
    private String relationname;
    private String relationid;
    private Integer startdate;
    private Integer enddate;
    private Integer createdate;
    private String note;
    private Long syncdate;
    private String formtype;
    
    public CtbcStockholderTemp() {
    	
    }

	public String getCname() {
        return cname;
    }

	public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    public String getId4() {
        return id4;
    }

    public void setId4(String id4) {
        this.id4 = id4;
    }

    public String getId5() {
        return id5;
    }

    public void setId5(String id5) {
        this.id5 = id5;
    }

    public String getRelationcode() {
        return relationcode;
    }

    public void setRelationcode(String relationcode) {
        this.relationcode = relationcode;
    }

    public String getRelationcodename() {
        return relationcodename;
    }

    public void setRelationcodename(String relationcodename) {
        this.relationcodename = relationcodename;
    }

    public String getRelationname() {
        return relationname;
    }

    public void setRelationname(String relationname) {
        this.relationname = relationname;
    }

    public String getRelationid() {
        return relationid;
    }

    public void setRelationid(String relationid) {
        this.relationid = relationid;
    }

    public Integer getStartdate() {
        return startdate;
    }

    public void setStartdate(Integer startdate) {
        this.startdate = startdate;
    }

    public Integer getEnddate() {
        return enddate;
    }

    public void setEnddate(Integer enddate) {
        this.enddate = enddate;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getSyncdate() {
        return syncdate;
    }

    public void setSyncdate(Long syncdate) {
        this.syncdate = syncdate;
    }

    public String getFormtype() {
        return formtype;
    }

    public void setFormtype(String formtype) {
        this.formtype = formtype;
    }
    
    private static final String CUTNOS = "|99|199|219|239|259|279|299|311|411|511|531|539|547|555|";
    private final SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat syncDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public CtbcStockholderTemp(String data,String filename) {
    	List<String> stockholderData = getRawDataTexts(data);
    	setCname(stockholderData.get(0).equals("")?null:stockholderData.get(0));
		setEname(stockholderData.get(1).equals("")?null:stockholderData.get(1));
		setId1(stockholderData.get(2));
		setId2(stockholderData.get(3).equals("")?null:stockholderData.get(3));
		setId3(stockholderData.get(4).equals("")?null:stockholderData.get(4));
		setId4(stockholderData.get(5).equals("")?null:stockholderData.get(5));
		setId5(stockholderData.get(6).equals("")?null:stockholderData.get(6));
		setRelationcode(stockholderData.get(7));
		setRelationcodename(stockholderData.get(8));
		setRelationname(stockholderData.get(9));
		setRelationid(stockholderData.get(10));
		setStartdate(Integer.valueOf(stockholderData.get(11)));
		setEnddate(stockholderData.get(12).equals("") ? Integer.valueOf(endDateFormat.format(new Date()))+2000000:Integer.valueOf(stockholderData.get(12)));
		setCreatedate(Integer.valueOf(stockholderData.get(13)));
		setNote(stockholderData.get(14).equals("")?null:stockholderData.get(14));
		setSyncdate(Long.valueOf(syncDateFormat.format(new Date())));
		setFormtype(filename);
	}
    
    //切割每筆資料並存入list回傳
  	private List<String> getRawDataTexts(String data){
  		List<String> texts = new LinkedList<>();
  		
  		int byteIndex = 0;
  		char[] charArr = data.toCharArray();
  		StringBuilder textSb = new StringBuilder();
  		for(int i=0; i<charArr.length; i++) {
  			
  			String unitText = String.valueOf(charArr[i]);
  			textSb.append(unitText);
  			if(CUTNOS.indexOf("|"+byteIndex+"|")!=-1) {
  				texts.add(textSb.toString().trim());
  				if(("|"+byteIndex+"|").equals("|555|")) {
  					texts.add(data.substring(i+1,data.length()));
  					break;
  				}
  				textSb = new StringBuilder();
  			}
  			byteIndex = unitText.getBytes(StandardCharsets.UTF_8).length > 1 ? byteIndex+2 : byteIndex+1 ;
  		}
  		logger.info("splitData:"+texts);
  		return texts;
  	}
}