<!--***************************************************************************
* Description: 伤害险新增照会模组页面
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>

<table class="common" cellpadding="5" cellspacing="1" align="center" border="0">
	<tr>
		<td>
			<img name="butDanger" class="button" alt="<s:text name='undwrt.pages.undwrtDeal.riskAssessMessages'/>" 
				src="/undwrt/common/images/butCollapse.gif" onclick="showPage(this,ZHInfo)"> 照會訊息<br>
			<span id="ZHInfo" style="display: none">
				<table width="100%">
					<tr>
						<td>
							<span id="spanZH" style="display: " cellspacing="1" cellpadding="0">
								<table class="common" cellpadding="5" cellspacing="1" align="center" id="ZH">
									<tr class=listtitle>
										<td colspan="11">
											照會訊息
										</td>
									</tr>
									<tr class=common>
										<td width="4%">
											序號
										</td>
										<td width="10%">
											被保險人
										</td>
										<td width="16%" colspan="2">
											照會代碼
										</td>
										<td width="10%">
											照會日期
										</td>
										<td width="10%">
											第一次回覆期限
										</td>
										<td width="10%">
											第二次回覆期限
										</td>
										<td width="10%">
											第三次回覆期限
										</td>
										<td width="10%">
											處理狀態 <br>Y已處理 N未處理
										</td>
										<td width="10%">
											照會回覆日期
										</td>
									</tr>
									<s:iterator value="zhList">
										<tr class=common>
											<td width="4%">
												<%-- <input class="formtitle1" readonly name="zhNo" value="${id.serialNo }"/> --%>
												${zhNo }
											</td>
											<td width="10%">
												<%-- <input class="free" name="insuredName" value="${insuredName }"/> --%>
												${insuredName }
											</td>
											<td width="8%">
												<%-- <input class="free" name="zhCode" value="${noteCode }"/> --%>
												${zhCode }
											</td>
											<td width="8%">
												<input class="button" type="button" name="zhTextButton" value="照會內容" 
													onclick="show_zhText(this,'zhText_sub_span${zhNo }')"/>
												<span id="zhText_sub_span${zhNo }" style="display:none;position:absolute;background-color:C0C0C0;">
								          			<table class="sub">
								                    	<tr>
								                      		<td class="title">
								                        		<textarea name="zhText" readonly rows="10" cols="50" class="common3">${zhText }</textarea>
								                      		</td>
								                    	</tr>
								                    	<tr>
								                      		<td align="center">
								                        		<input type="button" name="zhTextclose" class="button" alt="确定" value="确 定"
								                          			onclick="close_zhText(this,'zhText_sub_span${zhNo }')">
								                   			</td>
								                 		</tr>
								              		</table>
								 				</span>
											</td>
											<td width="10%">
												<%-- <input type="hidden" class="free" name="zhDate" value=""/>
												<input class="free" name="zhDateRC" value="<rc:rcDate name="noteDate" format="yyyy-MM-dd"/>" 
													onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" 
													onchange="getToRcDateValue(fm,this)"/> --%>
												${zhDate }
											</td>
											<td width="10%">
												<%-- <input type="hidden" class="free" name="firstDate" value=""/>
												<input class="free" name="firstDateRC" value="<rc:rcDate name="replyDate" format="yyyy-MM-dd"/>" 
													onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" 
													onchange="getToRcDateValue(fm,this)"/> --%>
												${firstDate }
											</td>
											<td width="10%">
												<%-- <input type="hidden" class="free" name="secondDate" value=""/>
												<input class="free" name="secondDateRC" value="<rc:rcDate name="SecondReplyDate" format="yyyy-MM-dd"/>" 
													onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" 
													onchange="getToRcDateValue(fm,this)"/> --%>
												${secondDate }
											</td>
											<td width="10%">
												<%-- <input type="hidden" class="free" name="thirdDate" value=""/>
												<input class="free" name="thirdDateRC" value="<rc:rcDate name="thirdReplyDate" format="yyyy-MM-dd"/>" 
													onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" 
													onchange="getToRcDateValue(fm,this)"/> --%>
												${thirdDate }
											</td>
											<td width="10%">
												<input type="hidden" name="dealStatus" value="${dealStatus }"/>
												${dealStatus }
											</td>
											<td width="10%">
												<%-- <input type="hidden" class="free" name="replyDate" value=""/>
												<input class="free" name="replyDateRC" value="<rc:rcDate name="actualReplyDate" format="yyyy-MM-dd"/>" 
													onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" 
													onchange="getToRcDateValue(fm,this)"/> --%>
												${replyDate }
											</td>
										</tr>
									</s:iterator>
								</table>
							</span>
						</td>
					</tr>
				</table>
			</span>
		</td>
	</tr>
</table>
