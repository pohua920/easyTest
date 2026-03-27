<%
  String UserCode = (String)session.getValue("myUserCode");
  String UserName = (String)session.getValue("myUserName");
%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
  <title>update password</title>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard1.css">
  <script language=Javascript>
    function mySubmit()
    {
/*        var check = password.checkPassword(fm.oldPassword.value,fm.newPassword.value,fm.retypeNewPassword.value);
        if(check==false){
            return false;
        }   */
      if(fm.newPassword.value == "" || fm.retypeNewPassword.value == "" ||
          fm.oldPassword.value == "") {
      alert("請將三組密碼填寫完整後再提交");
      return;    
      }
    //add by dongyapeng 20160524 reason:密碼長度校驗 begin 
	  var lengthNew = fm.newPassword.value.length;
	  if(lengthNew<6){
	      alert("新密碼的長度不能小於6！");
		  fm.newPassword.focus();
		  return false;
	  }
	 	//add by dongyapeng 20160524 reason:密碼長度校驗 end 
      if(fm.newPassword.value == fm.retypeNewPassword.value)
      {
        fm.submit(); //提交
      }else
      {
        alert("新密碼校驗錯誤，請重新輸入新密碼");
        fm.newPassword.value       = "";
        fm.retypeNewPassword.value = "";
      }
    }
    function myReset()
    {
        fm.reset();
    }
  </script>
</head>
<body class="interface">
<form action="/undwrt/common/userModifyPwd.do" method=post name=fm>
  <table class=three align="center" style = "width:400">
  	<tr>
      	<td class=formtitle colspan="2">
      		用戶密碼修改
      	</td>
    </tr>
    <tr>
        <td class="title" style = "width:50%">用戶名：</td>
        <td class="input" style = "width:50%">
          <input name='userName' class=readonly readonly value='<%=UserName%>'>
          <input type=hidden name='userCode' value='<%=UserCode%>'>
        </td>
    </tr>
    <tr>
        <td class="title">原密碼:</td>
        <td class="input"><input class=common type="password" name='oldPassword' maxlength="10"></td>
    </tr>
    <tr>
        <td class="title">新密碼:</td>
        <td class="input"><input class=common type="password" name='newPassword' maxlength="10"></td>
    </tr>
    <tr>
        <td class="title">重複新密碼:</td>
        <td class="input"><input class=common type="password" name='retypeNewPassword' maxlength="10"></td>
    </tr>
    <tr align=center>
      <td>
        <input class="button" type="button" alt="確定 " value="確定" onclick="mySubmit()">
        
      </td>
      <td>
        <input class="button" type="button" alt="重 寫" value="重 寫" onclick="myReset()">
        
      </td>
    </tr>
  </table>
</form>
</body>
</html>
