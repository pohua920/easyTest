<%
//以下为意健险种的任務显示页面，需要去掉调度等等相关的判断 。
%>

<logic:notEmpty  name="swfLogDto"  property="swfLogList">
     <logic:iterate id="swfLogList1"  name="swfLogDto"  property="swfLogList">
<%
     if(index %2== 0)
          out.print("<tr class=listodd>");
     else
          out.print("<tr class=listeven>");
%>
         <td align="center">
             <%=index+1%>
         </td>
<%
      strindex = "'"+String.valueOf(index)+"'";
      //取得该行的DTO的数据 判断节点类型和状态，根据不同的状态，实现不同的按扭内容
      swfLogDto = (SwfLogDto) ((ArrayList)swfLogDto1.getSwfLogList()).get(index);
      typeFlag = swfLogDto.getTypeFlag();
      dealHref="";      //清空联接内容
      backHref="";      //清空处理回退的联接
      dispHref="";      //清空显示的联接      
      EditLastType=editType; //复制
      
  	 String strRiskType = uiCodeAction.translateRiskCodetoRiskType(swfLogDto.getRiskCode());
      carFlag="";//

      if (nodeStatus.equals("0")||nodeStatus.equals("-1")||nodeStatus.equals("99")){ //判断当前的任務是哪种类型的，根据任務的节点的状态
%>
        <td align="center">
<%
      nowNodeStatus=swfLogDto.getNodeStatus();

      if (nowNodeStatus.equals("3")){

         if (!nodeStatus.equals("-1")){EditLastType="EDIT" ;}
%>
      <s:text name="schedule.returnDeal"/><%--回退处理 --%>
<%
      }
      if (nowNodeStatus.equals("4")){ %>
      <s:text name="common.status.submited"/><%--已提交 --%>
      <%}
      if (nowNodeStatus.equals("5")){ %>
      <s:text name="workflow.notPassReturn"/><%--不通过退回 --%>
      <%}
      if (nowNodeStatus.equals("0")){
%>
      <s:text name="guarantee.newDeal"/><%--新处理 --%>
<%
        if (swfLogDto.getNodeType().equals("cance"))  EditLastType="CANCELEDIT";
      }
             if (nowNodeStatus.equals("2")){ %> <s:text name="check.dealingWith"/><%--正在处理 --%>
<%
             }
%>
          </td>
<%
       }
      // 把工作流号码等等也需要 加载到link中
      //modify by lixiang add 20050304 start
      //reason:加入传进保单号码的过程

      flowStr="&swfLogFlowID="+swfLogDto.getFlowID()+"&swfLogLogNo="+swfLogDto.getLogNo()+"&status="+swfLogDto.getNodeStatus()
              +"&riskCode="+swfLogDto.getRiskCode()+"&editType="+EditLastType
              +"&nodeType="+swfLogDto.getNodeType()
              +"&businessNo="+swfLogDto.getBusinessNo()
              +"&keyIn="+swfLogDto.getKeyIn()
              +"&policyNo="+swfLogDto.getPolicyNo()
              +"&modelNo="+swfLogDto.getModelNo()
              +"&nodeNo="+swfLogDto.getNodeNo();

       //modify by lixiang add 20050304 end
       //以下是根据节点名称的不同，在显示第一列的超链的内容不同。（一般是业务号码）
       // (nodeStatus.equals("0")&&nowNodeStatus.equals("0")是表示待处理任務中的新任務
       // nodeStatus.equals("-1") 表示的是按下注销拒赔申请功能選單後的任務显示。 %>
          <td align="center">
                  <logic:equal name="swfLogList1" property="nodeType" value="prepa">

<%                if ((nodeStatus.equals("0")&&nowNodeStatus.equals("0"))){
                        dealHref="/claim/claimFinishQueryList.do?prpLclaimClaimNo="+swfLogDto.getKeyIn()
                                +flowStr;
                        }else if (nodeStatus.equals("-1")){ //注销拒赔申请

          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("specialApply")){
            dealHref="/claim/specailCaseQuery.do?"+flowStr;
		}else{

                        dealHref="/claim/claimBeforeCancel.do?ClaimNo="+swfLogDto.getKeyIn()
                                +flowStr;
			}
                        }
                        else if (nowNodeStatus.equals("3")) //区分是退回的方式
                        {  dealHref="/claim/prepayFinishQueryList.do?prpLprepayPrepayNo="+swfLogDto.getKeyIn()+flowStr;
                        }else
                        {
                          dealHref="/claim/prepayFinishQueryList.do?prpLprepayPrepayNo="+swfLogDto.getKeyIn()+flowStr;
                         }
                         dispHref=swfLogDto.getKeyIn();
                         %>
                      <input name=keyIN  type="hidden"  value=${swfLogList1" property="businessNo"/>>
                  </logic:equal>  <!--------acci end---序号状态赔案号------------------------>

                  <logic:equal name="swfLogList1" property="nodeType" value="regis">
                          <% if (nodeStatus.equals("0")){
                          dealHref="/claim/registBeforeEdit.do?prpCmainPolicyNo="+swfLogDto.getBusinessNo()+flowStr;

                          }else{
                          dealHref="/claim/registFinishQueryList.do?prpLregistRegistNo="+swfLogDto.getBusinessNo()+flowStr+"&updateExt=true";
                           }
                          dispHref=swfLogDto.getBusinessNo();
                          %>
                  </logic:equal>
                  <logic:equal name="swfLogList1" property="nodeType" value="compe">
                  <%--modify by weishixin modify 20050227 start--%>
                  <%--resson:UNIX大小写一致--%>
                  <% riskCode=swfLogDto.getRiskCode();
                
				           strRiskType = uiCodeAction.translateRiskCodetoRiskType(riskCode);
                     if("D".equals(strRiskType)) //add liuyanmei
                     {
                             // modify by wangli update start 20050417
                        String   flowStr1="&swfLogFlowID="+swfLogDto.getFlowID()+"&swfLogLogNo="+swfLogDto.getLogNo()+"&status="+swfLogDto.getNodeStatus()
                                 +"&riskCode="+swfLogDto.getRiskCode()+"&editType="+EditLastType
                                  +"&nodeType="+swfLogDto.getNodeType()
                                  +"&businessNo="+swfLogDto.getBusinessNo()
                                  +"&policyNo="+swfLogDto.getPolicyNo()
                                  +"&modelNo="+swfLogDto.getModelNo()
                                  +"&nodeNo="+swfLogDto.getNodeNo();
                                 // +"&caseType="+swfLogDto.getTypeFlag();

                         //remark by wangli start
                     //  dealHref="/claim/common/compensate/CompensateBeforeEdit.jsp?ClaimNo="+swfLogDto.getKeyIn()+flowStr;
                     //remark by wangli end
                         dealHref="/claim/compensateBeforeEditList.do?ClaimNo="+swfLogDto.getKeyIn()+flowStr1;
                       // dealHref="/claim/compensateBeforeEditList.do?ClaimNo="+swfLogDto.getKeyIn()+flowStr;
                     //modify by wangli update end 20050417
                     }
                     else{
                    // out.println("<h1>"+" ClaimNo is "+swfLogDto.getBusinessNo());
                    // out.println("<h3>"+" ClaimNo is "+swfLogDto.getKeyIn());
                    //modify by wangli add 20050519 start
                    //reason:增加caseType 这样会判断走那个流程 此处为意健险
                    dealHref="/claim/compensateBeforeEdit.do?ClaimNo="+swfLogDto.getKeyIn()+"&caseType="+swfLogDto.getTypeFlag()+flowStr+ "&compeCount=" + swfLogDto.getCompeCount();
		      intriskcode=7;
                   // out.println(" caseType is "+swfLogDto.getTypeFlag());
                    //modify by wangli add 20050519 end
                      //remark
    
                     }
                    dispHref=swfLogDto.getKeyIn();
                 %>
                  <%--modify by weishixin modify 20050227 end--%>
                  </logic:equal>

                  <logic:equal name="swfLogList1" property="nodeType" value="compp">
                  <% 
                       dealHref="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo="+swfLogDto.getBusinessNo()+flowStr;
                       dispHref=swfLogDto.getBusinessNo();
                  %>
                  </logic:equal>

                   <logic:equal name="swfLogList1" property="nodeType" value="speci">
                   <%
                   if (nowNodeStatus.equals("0")){
                    riskCode=swfLogDto.getRiskCode();
                    //modify by lixiang start at 2005-12-20
                    //reason:原来没写6状态的内容呢
                    if (typeFlag.equals("3")||typeFlag.equals("4") || typeFlag.equals("6")){
                    //modify by lixiang end at 2005-12-20
                    
                       if("D".equals(strRiskType)) //add liuyanmei
                         {
                            String   flowStr2="&swfLogFlowID="+swfLogDto.getFlowID()+"&swfLogLogNo="+swfLogDto.getLogNo()+"&status="+swfLogDto.getNodeStatus()
                                 +"&riskCode="+swfLogDto.getRiskCode()+"&editType="+EditLastType
                                  +"&nodeType="+swfLogDto.getNodeType()
                                  +"&businessNo="+swfLogDto.getBusinessNo()
                                  +"&policyNo="+swfLogDto.getPolicyNo()
                                  +"&modelNo="+swfLogDto.getModelNo()
                                  +"&nodeNo="+swfLogDto.getNodeNo()
                                  +"&caseType="+swfLogDto.getTypeFlag();

                            dealHref="/claim/compensateBeforeEditList.do?ClaimNo="+swfLogDto.getKeyIn()+flowStr2;
                         }
                        else{
                            dealHref="/claim/compensateBeforeEdit.do?ClaimNo="+swfLogDto.getKeyIn()+"&caseType="+swfLogDto.getTypeFlag()+flowStr;
                         }

                       }
                       if (typeFlag.equals("5")){
                       dealHref="/claim/prepayBeforeEdit.do?ClaimNo="+swfLogDto.getKeyIn()
                         +"&caseType="+swfLogDto.getTypeFlag()
                         +flowStr;
                       }
                         dispHref=swfLogDto.getKeyIn();


                   }else{
                      //modify by lixiang start at 2005-12-20
                      //reason:原来没写6状态的内容呢
                 
                         if (typeFlag.equals("3")||typeFlag.equals("4")||typeFlag.equals("6")){
                       //modify by lixiang end at 2005-12-20
                     
                           dealHref="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo="+swfLogDto.getKeyOut()
                                       +"&caseType="+typeFlag+flowStr;
                         }
                         if (typeFlag.equals("5"))
                         {
                           dealHref="/claim/prepayFinishQueryList.do?prpLprepayPrepayNo="+swfLogDto.getKeyOut()
                                   +"&caseType="+typeFlag+flowStr;
                         }

                         dispHref=swfLogDto.getKeyOut();
                   }
                      %>
                  </logic:equal>

                  <logic:equal name="swfLogList1" property="nodeType" value="claim">
                          <% if ((nodeStatus.equals("0")&&nowNodeStatus.equals("0"))){
                            dealHref="/claim/claimBeforeEdit.do?RegistNo="+swfLogDto.getKeyIn()+flowStr;
                            dispHref=swfLogDto.getKeyIn();
                           }else if (nodeStatus.equals("-1")){ //注销拒赔申请
                             if (!nowNodeStatus.equals("0"))
                             {
          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("specialApply")){
            dealHref="/claim/specailCaseQuery.do?"+flowStr;
		}else{
                             dealHref="/claim/claimBeforeCancel.do?ClaimNo="+swfLogDto.getKeyOut()
                                +flowStr;
			}
                             dispHref=swfLogDto.getKeyOut();
                             }else
                             {
          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("specialApply")){
            dealHref="/claim/specailCaseQuery.do?"+flowStr;
		}else{
                              dealHref="/claim/claimBeforeCancel.do?RegistNo="+swfLogDto.getBusinessNo()
                                + "&type=" + request.getAttribute("com.sinosoft.type")
                                +flowStr;}
                             dispHref=swfLogDto.getBusinessNo();
                             }
                            }else{
					if (funcName!=null&&funcName.equals("addLoss")){
				String Lossstr="";
				      Lossstr="&swfLogFlowID="+swfLogDto.getFlowID()+"&swfLogLogNo="+swfLogDto.getLogNo()+"&status="+swfLogDto.getNodeStatus()
				              +"&riskCode="+swfLogDto.getRiskCode()+"&editType="+"LOSS"
				              +"&nodeType="+swfLogDto.getNodeType()
				              +"&businessNo="+swfLogDto.getBusinessNo()
				              +"&keyIn="+swfLogDto.getKeyIn()
				              +"&policyNo="+swfLogDto.getPolicyNo()
				              +"&modelNo="+swfLogDto.getModelNo()
				              +"&nodeNo="+swfLogDto.getNodeNo();
                            dealHref="/claim/claimFinishQueryList.do?prpLclaimClaimNo="+swfLogDto.getKeyOut()+Lossstr;
						}else{
                            dealHref="/claim/claimFinishQueryList.do?prpLclaimClaimNo="+swfLogDto.getKeyOut()+flowStr;
							}
                           dispHref=swfLogDto.getKeyOut();
                           }
                         %>
                  </logic:equal>
                  <logic:equal name="swfLogList1" property="nodeType" value="check">
                      <% if ((nodeStatus.equals("0")&&nowNodeStatus.equals("0"))){
                              dealHref="/claim/checkBeforeEdit.do?RegistNo="+swfLogDto.getKeyIn()
                                  +"&type=" + request.getAttribute("com.sinosoft.type")
                                  +"&lossItemName="+swfLogDto.getLossItemName()
                                  +"&lossItemCode="+swfLogDto.getLossItemCode()
                                  +"&insureCarFlag="+swfLogDto.getInsureCarFlag()
                                  +flowStr;
                            dispHref=swfLogDto.getKeyIn();
                           }else if (nodeStatus.equals("-1")){ //注销拒赔申请
          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("specialApply")){
            dealHref="/claim/specailCaseQuery.do?"+flowStr;
		}else{
                             dealHref="/claim/claimBeforeCancel.do?RegistNo="+swfLogDto.getKeyIn()
                                + "&type=" + request.getAttribute("com.sinosoft.type")
                                +flowStr;}
                             dispHref=swfLogDto.getKeyIn();
                            }else{
                            dealHref="/claim/checkFinishQueryList.do?prpLcheckCheckNo="+swfLogDto.getKeyIn()
								    + "&type=" + request.getAttribute("com.sinosoft.type")
                                    +"&lossItemCode="+swfLogDto.getLossItemCode()
                                    +"&lossItemName="+swfLogDto.getLossItemName()
                                    +"&insureCarFlag="+swfLogDto.getInsureCarFlag()
                                    +flowStr;
                            dispHref=swfLogDto.getKeyIn();
                           }
                          %>
                  </logic:equal>
                  <logic:equal name="swfLogList1" property="nodeType" value="endca">
                  <%
                     // modify by wangli update start 20050417
                     //dealHref="/claim/endcaseBeforeEdit.do?ClaimNo="+swfLogDto.getKeyIn()+flowStr;
                     dealHref="/claim/endcaseBeforeEdit.do?ClaimNo="+swfLogDto.getKeyIn()+"&ClaimNoSign= ="+"&caseType="+swfLogDto.getTypeFlag()+flowStr;
                     //modify by wangli update end 20050417
                     dispHref=swfLogDto.getKeyIn();
                  %>
                  </logic:equal>

                  <logic:equal name="swfLogList1" property="nodeType" value="certi">
                          <% if ((nodeStatus.equals("0")&&nowNodeStatus.equals("0"))){

                             dealHref="/claim/certifyBeforeEdit.do?RegistNo="+swfLogDto.getKeyIn()+flowStr;
                          }else if (nodeStatus.equals("-1")){ //注销拒赔申请

          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("specialApply")){
            dealHref="/claim/specailCaseQuery.do?"+flowStr;
		}else{
                             dealHref="/claim/claimBeforeCancel.do?RegistNo="+swfLogDto.getKeyIn()
                                + "&type=" + request.getAttribute("com.sinosoft.type")
                                +flowStr;}

                            }else{
                             dealHref="/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo="+swfLogDto.getKeyIn()+flowStr;
                          }
                          dispHref=swfLogDto.getKeyIn();
                           %>
                  </logic:equal>
                  <logic:equal name="swfLogList1" property="nodeType" value="cance">
                  <%
                     if (nodeStatus.equals("0"))
                     {
                     // dealHref="/claim/claimBeforeCancel.do?ClaimNo="+swfLogDto.getKeyIn()+flowStr;
                      dealHref="/claim/claimFinishQueryList.do?prpLclaimClaimNo="+swfLogDto.getKeyIn()+flowStr
                     +"&typeFlag="+swfLogDto.getTypeFlag()+"&flowInTime="+swfLogDto.getFlowInTime();
                    
                     }else
                     {
                     dealHref="/claim/claimFinishQueryList.do?prpLclaimClaimNo="+swfLogDto.getKeyIn()+flowStr;

                     }

                     dispHref=swfLogDto.getKeyIn();

                  %>
                  </logic:equal>

             <%--Modify by chenrenda add 20050315 begin--%>
             <%--Reason:增加回访节点  --%>
             <logic:equal name="swfLogList1" property="nodeType" value="backv">
                            <%
                            if ((nodeStatus.equals("0")&&nowNodeStatus.equals("0"))){
                              dealHref="/claim/backVisitBeforeEdit.do?RegistNo="+swfLogDto.getKeyIn()
                                      +"&lossTypeFlag="+swfLogDto.getTypeFlag()
                                      +"&insureCarFlag="+swfLogDto.getInsureCarFlag()
                                      +"&lossItemCode="+swfLogDto.getLossItemCode()
                                      +"&lossItemName="+swfLogDto.getLossItemName()
                                      +flowStr;
                            }else{
                            dealHref="/claim/backVisitBeforeEdit.do?RegistNo="+swfLogDto.getKeyIn()
                                      +"&lossTypeFlag="+swfLogDto.getTypeFlag()
                                      +"&insureCarFlag="+swfLogDto.getInsureCarFlag()
                                      +"&lossItemCode="+swfLogDto.getLossItemCode()
                                      +"&lossItemName="+swfLogDto.getLossItemName()
                                      +"&flag=1"+flowStr;
                            }
                            dispHref=swfLogDto.getKeyIn();
                            %>
                  </logic:equal>
             <%--modify by chenrenda add 20050315 end--%>
<%
	if(intriskcode==5){%>
           <a href="<%=dealHref%>" title="${swfLogList1" property="titleStr"/>"><%=dispHref%></a>
<%           }else{%>
           <a href="<%=dealHref%>"title="${swfLogList1" property="titleStr"/>" onclick="return compeCount('${swfLogList1" property="compeCount"/>');" ><%=dispHref%></a>
<%}%>
        </td>
        <%//保单号码的内容%>
        <td align="center"> 
        <% //modify by lixiang start at 2006-6-5
           //reason :支持多个关联保单%>
        <logic:iterate id="relatePolicyList1"  name="swfLogList1"  property="relatePolicyList">  
        ${relatePolicyList1" property="policyNo"/>
        </logic:iterate>
         <% //modify by lixiang end at 2006-6-5%>
        </td>
        <td align="center">${swfLogList1" property="riskCodeName"/></td>
        <td align="center">${swfLogList1" property="insuredName"/></td> <!-----acci----------->
        <%//如果是定损核损，列出类型中的内容%>
      <%if (swfLogDto1.getNodeType().equals("certa")){%>
           <td align="center">
             <logic:equal name="swfLogList1" property="exigenceGree" value="0">
              紧急
              </logic:equal>
             <logic:equal name="swfLogList1" property="exigenceGree" value="1">
              一般
              </logic:equal>
           </td>
           <td align="center"><bean:write name='swfLogList1' property='lossItemName'/></td>
           <td align="center"><%=carFlag%></td>
        
          <%}%>

         <%//如果是理算类型的，写具体理算记录数据%>
        <%if (swfLogDto1.getNodeType().equals("compe")){%>
           <td align="center"> <a href="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=<bean:write name='swfLogList1' property='keyOut'/>&editType=<%=EditLastType%>&riskCode=<bean:write name='swfLogList1' property='riskCode'/><%=flowStr%>"><bean:write name='swfLogList1' property='keyOut'/></a></td>
        <%}%>

        <%//如果是计算书类型的，写赔案号码%>
         <%if (swfLogDto1.getNodeType().equals("compp")){%>
          <td align="center"><a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<bean:write name='swfLogList1' property='keyIn'/>&editType=SHOW&riskCode=<bean:write name='swfLogList1' property='riskCode'/><%=flowStr%>" title="${swfLogList1" property="titleStr"/>">
                  <bean:write name='swfLogList1' property='keyIn'/></a>
          <%}%>
          </td>

<%//如果是特殊案件类型的，写赔案号码%>
        <logic:equal name="swfLogDto" property="nodeType" value="speci">
         <td align="center">
           <logic:equal name="swfLogList1" property="nodeStatus" value="0">
                 <logic:equal name="swfLogList1" property="typeFlag" value="3">通融</logic:equal>
                <logic:equal name="swfLogList1.typeFlag" value="4">预付</logic:equal>
                <logic:equal name="swfLogList1.typeFlag" value="5">预赔</logic:equal>
                <logic:equal name="swfLogList1.typeFlag" value="6">其它</logic:equal>
           </logic:equal>

           <% if ((!swfLogDto.getNodeStatus().equals("0"))&&(!swfLogDto.getNodeStatus().equals("3"))){%>
             <a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<bean:write name='swfLogList1' property='keyIn'/>&editType=SHOW&riskCode=<bean:write name='swfLogList1' property='riskCode'/><%=flowStr%>" title="${swfLogList1.titleStr"/>">
                   <bean:write name='swfLogList1' property='keyIn'/></a>
           <%}%>

            </td>
        </logic:equal>

<%//如果是回访
if(nodeType.equals("backv")){
String backvType="查勘回访";
  if (swfLogDto.getTypeFlag().equals("2")) backvType="定损回访";
%>
        <td align="center"><%=backvType%></td>
<%
}
%>
         <%//如果是查勘类型的，写具体车牌号码，%>
          <logic:notEqual name="com.sinosoft.type" value="acci">
          <logic:equal name="swfLogDto.nodeType" value="check">
             <td align="center"><bean:write name='swfLogList1' property='lossItemName'/></td>
          </logic:equal>
          </logic:notEqual>

          <%//如果是查勘类型的，写具体车牌号码，保单车辆，调度号，调度人 %>
          <logic:equal name="swfLogDto.nodeType" value="claim">
          <%
               afterDay=0;   //计算时间的
               afterHour=0;
               if (nodeStatus.equals("0"))
               {
               afterDay=DateTime.intervalDay(new DateTime(swfLogDto.getFlowInTime(),DateTime.YEAR_TO_DAY),swfLogDto.getTimeLimit(),new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY) ,DateTime.current().getHour() ) ;
               //-1是因为如果不到一天，是按照一天计算的，因为我要计算小时，所以就应该记为0天的
               afterHour=(afterDay-1)*24 - swfLogDto.getTimeLimit()+DateTime.current().getHour();

               //剩余时间计算
               afterHour=claimLimit-afterHour;
               %>
               <td align="center"><%=afterHour%></td>
               <%
               }

           %>
           </logic:equal>

        <%//写操作人数据%>
        <td align="center">${swfLogList1.handlerName"/></td>
        <%//写操作时间的信息%>
         <td align="center">
         <%
          if (nodeStatus.equals("4")){%>
           <bean:write name='swfLogList1' property='submitTime'/>
          <% } else {
           %>
             <bean:write name='swfLogList1' property='handleTime'/>

          <% }
          //modify by lixiang remark 20050309 end  %>
          <%//以下为每行隐含中的内容的具体信息%>
          <input name=flowID type="hidden"  value=${swfLogList1.flowID"/>>
          <input name=logNo  type="hidden"  value=${swfLogList1.logNo"/>>
          <input name=keyIN  type="hidden"  value=${swfLogList1.keyIn"/>>
       </td>
         <logic:equal name="swfLogDto.nodeType" value="commo">
              <td align="center">${swfLogList1.nodeTypeName"/></td>
           </logic:equal>
<%
       if (nodeStatus.equals("0")){ %>
        <td align="center">
<%           // 已立案，结案不能回退,目前只限制在已经处理的回退任務
             //这些都不能回退的
             if (swfLogDto.getNodeStatus().equals("3")||swfLogDto.getNodeType().equals("claim")
                ||swfLogDto.getNodeType().equals("sched")||swfLogDto.getNodeType().equals("schel")
                ||swfLogDto.getNodeType().equals("backv")||swfLogDto.getNodeType().equals("check")
                ||swfLogDto.getNodeType().equals("certa")||swfLogDto.getNodeType().equals("wound")
                ||swfLogDto.getNodeType().equals("backc")||swfLogDto.getNodeType().equals("speci")){  %>
            <a href="<%=dealHref%>"><img name=buttonDistribute  src="/claim/images/butDeal.gif" border="0"	hspace="5"  alt="选择处理"></a>&nbsp;
<%}          else {        %>
<%
           if (intriskcode==7){}else{
           %>
            <a href="<%=dealHref%>"><img name=buttonDistribute  src="/claim/images/butDeal.gif" border="0"	hspace="5"  alt="选择处理"></a><input type="image" name=buttonDistribute num=<%=strindex%> src="/claim/images/butReturn.gif" border="0"	hspace="0"  alt="回退处理" onclick="return backStepWorkFlow(this);">
           <%
            } } %>
        </td>
        <%}
        //显示注销申请的处理按钮

          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("cancelApply")){
          %>
           <td align="center"><a href="<%=dealHref%>"><img name=buttonDistribute  src="/claim/images/butCancel.gif" border="0"	hspace="5"   alt="註銷/拒赔 "></a>&nbsp;</td>
<%
          }
        //显示特殊赔案申请的处理按钮
          if (nodeStatus.equals("-1")&&funcName!=null&&funcName.equals("specialApply")){
            dealHref="/claim/specailCaseQuery.do?"+flowStr;

          %>
           <td align="center"><a href="<%=dealHref%>" border="0"	hspace="5"   alt="特殊赔案申请 ">申请</a>&nbsp;</td>
<% 
          }
          if (nodeStatus.equals("4") && !nodeType.equals("sched")&& !nodeType.equals("claim")){
%>
<%//modify by liujianbo modify 20051107 start
  //reason:立案应该封掉“收回”功能。
%>
           <td align="center"><a href="/claim/processWorkflow.do?editType=recycle&flowID=${swfLogList1.flowID"/>&logNo=${swfLogList1.logNo">收回</a>&nbsp;</td>
<%
          }          
%> 
<%//modify by liujianbo modify 20051107 end
%>
    </tr>
<%        index++;%>
      </logic:iterate>
      </logic:notEmpty>
     