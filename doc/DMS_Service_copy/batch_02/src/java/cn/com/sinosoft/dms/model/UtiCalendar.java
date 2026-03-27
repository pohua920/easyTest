package cn.com.sinosoft.dms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "UTICALENDAR")
public class UtiCalendar implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** 日期 */
  Date dateDay;

  /** 日期类型 "1":工作日,"2":休息日,"3":节假日 */
  String dayType;

  /** 星期 */
  int weekDay;

  /** 标志位 */
  String flag;

  public UtiCalendar() {
  }

  @Id
  @Temporal(TemporalType.DATE)
  @Column(name = "DATEDAY")
  public Date getDateDay() {
    return dateDay;
  }

  public void setDateDay(Date dateDay) {
    this.dateDay = dateDay;
  }

  @Column(name = "DAYTYPE")
  public String getDayType() {
    return dayType;
  }

  public void setDayType(String dayType) {
    this.dayType = dayType;
  }

  @Column(name = "WEEKDAY")
  public int getWeekDay() {
    return weekDay;
  }

  public void setWeekDay(int weekDay) {
    this.weekDay = weekDay;
  }

  @Column(name = "FLAG")
  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }
}
