//API R1.0.0.51 (Mix eid and pki) ~20201103
//Version websock V1.3.4.103321(Win)、1.3.4.10(Mac)、1.3.4.3(Linux)
//Use MOICA func and eid func merge，
//goodDay		ChooseCard:ListInfo.exe
//EIDgoodDay	ChooseCard:ListEID
//21
"use strict";
function INObject()
{
    this.PstTrg=null;
    this.TbsPckg=null;
    this.RspDt=null;
    this.WbSckVrsn="1.3.4.103319";
    //this.PppFrmStyle="width=100,height=140,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=yes";
    this.PppFrmStyle="height=200, width=200, left=100, top=20";
    this.ISIE=false;
    this.GoOrBack="back";
    this.HTmOt=null;
}//function INObject()
//21
function ARevFun()
{
	this.FUN=null;
	this.Para=null;
	this.PopTout=120000;
	this.UrFUN=null;	
}//function ARevFun()
var InToken = null;
var Gbjct=null;
var GARevFun=null;
//22
function SendData(Target,OtTarget,Title,Style,RevFUN,UrRevFUN){
	var l_vRetData;
	var l_iRetCode;
	
	try{
		l_vRetData="";
		l_iRetCode=0;
		//alert("22 Gbjct.GoOrBack--"+Gbjct.GoOrBack);
		if (Gbjct.GoOrBack!="go") {	
			if (Gbjct.HTmOt!=null) {
				//alert("Gbjct.HTmOt");
				window.clearTimeout(Gbjct.HTmOt);
			}	
			if (Gbjct.PstTrg!=null && Gbjct.PstTrg.closed==false) {
				Gbjct.PstTrg.close();
				Gbjct.PstTrg =null;
			}//if (Gbjct.PstTrg!=null && Gbjct.PstTrg.closed==false)			
		}//if (Gbjct.GoOrBack!="go") 
		var MyAct=popInfo();
		if (CkBType()=="IE")
		{
			Gbjct.ISIE=true;
			Gbjct.PstTrg=window.open('','ChtPopupForm',Gbjct.PppFrmStyle);
			if (Gbjct.PstTrg!=null) {
				Gbjct.PstTrg.resizeTo(90,180);
				
				Gbjct.PstTrg.document.writeln("<div align='center'>");
				Gbjct.PstTrg.document.writeln(MyAct.outerHTML);
				Gbjct.PstTrg.document.writeln("</div>");
				Gbjct.TbsPckg=getInfo();
				//ccchan
				//console.log("Gbjct.TbsPckg(IE):"+Gbjct.TbsPckg);
				var l_vSpan=document.getElementById("SendObject");
				if (l_vSpan==null) 
				{
					l_vSpan = document.createElement("span");
					document.body.appendChild(l_vSpan);
					l_vSpan.id="SendObject";					
				}//if (l_vSpan==null)		
				l_vSpan.innerHTML='<OBJECT id="ChtICTokenHttp" width=1 height=1 style="LEFT: 1px; TOP: 1px" type="application/x-httpcomponent" VIEWASTEXT></OBJECT>';
				var l_vHttp=document.getElementById("ChtICTokenHttp");
				if (l_vHttp.sendRequest!=null)
				{
					l_vHttp.url=Target;
					l_vHttp.actionMethod="POST";			
					var code=l_vHttp.sendRequest(postData(InToken.FUN));
					if(code!=0) 
					{
						if(code==12152)
							l_iRetCode=2206;						
						else
							l_iRetCode=2200;
							
						l_vRetData=rMsg(l_iRetCode,"(" + l_iRetCode +")");
						l_iRetCode=code;
						l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
						InToken.RetObj=JSON.parse(l_vRetData);
						
					} else {
						Gbjct.RspDt = l_vHttp.responseText;
						l_vRetData=RevFUN(UrRevFUN);
						InToken.RetObj=JSON.parse(l_vRetData);
					}//if(code!=0)
				}  else {
					l_iRetCode=2202;
					l_vRetData=rMsg(l_iRetCode,"(" + l_iRetCode +")");
					l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
					InToken.RetObj=JSON.parse(l_vRetData);				
				}//if (l_vHttp.sendRequest==true)			
			} else {
				l_iRetCode=2201;
				l_vRetData=rMsg(l_iRetCode,"(1)");
				l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData + '"}';
				InToken.RetObj=JSON.parse(l_vRetData);			
			}//if (Gbjct.PstTrg!=null && Gbjct.PstTrg.closed==false)
			
			if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
			{		
				if (Gbjct.HTmOt!=null) {
					//alert("Gbjct.HTmOt");
					window.clearTimeout(Gbjct.HTmOt);
				}			
				Gbjct.PstTrg.close();
				Gbjct.PstTrg =null;
			}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
			if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
		}
		else
		{
			Gbjct.PppFrmStyle="width=200,height=200,left=100,top=30";
			Gbjct.TbsPckg=getInfo();
			//ccchan
			//console.log("Gbjct.TbsPckg:"+Gbjct.TbsPckg);
			Gbjct.PstTrg=window.open(OtTarget,"ChtPopupForm",Style);
			if (Gbjct.PstTrg!=null)
			{
				if (CkBType()!="Edge") Gbjct.PstTrg.resizeTo(230,250);
				//*
				var l_vTimeO;
				if (InToken.FUN=="CheckEnvir") {
					l_vTimeO=20000;
				} else {
					l_vTimeO=GARevFun.PopTout+50000;						
				}//if (InToken.FUN=="CheckEnvir")
					
				Gbjct.HTmOt=window.setTimeout(function(){
					var l_iRetCode;
					var l_vRetData;
					if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed) {
						try 
						{
							if (Gbjct.HTmOt!=null) {
								window.clearTimeout(Gbjct.HTmOt);
							}					
							Gbjct.PstTrg.close();
							Gbjct.PstTrg =null;
							if (Gbjct.RspDt==null) {
								l_iRetCode=2202;
								l_vRetData=rMsg(l_iRetCode,"");
								l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
								InToken.RetObj=JSON.parse(l_vRetData);
								if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();					
							}//if (Gbjct.RspDt==null)									
						}  catch(e) {
							l_iRetCode=2204;
							l_vRetData='{"RCode":"' + "l_iRetCode" +'","RMsg":"'+ e.message +'"}';
							if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
							{		
								if (Gbjct.HTmOt!=null) {
									//alert("Gbjct.HTmOt");
									window.clearTimeout(Gbjct.HTmOt);
								}			
								Gbjct.PstTrg.close();
								Gbjct.PstTrg =null;
							}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)								
						}//try				
					}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)				
					},l_vTimeO);//*/
			} else {
				l_iRetCode=2201;
				l_vRetData=rMsg(l_iRetCode,"(2)");
				l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData + '"}';
				InToken.RetObj=JSON.parse(l_vRetData);
				if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
				{		
					if (Gbjct.HTmOt!=null) {
						//alert("Gbjct.HTmOt");
						window.clearTimeout(Gbjct.HTmOt);
					}			
					Gbjct.PstTrg.close();
					Gbjct.PstTrg =null;
				}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)					
				if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();			
			}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed))
		}//if (CkBType()=="IE")		
	} catch(e){
		l_iRetCode=2201;
		l_vRetData=rMsg(l_iRetCode,"(3)");
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ e.message + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
		{		
			if (Gbjct.HTmOt!=null) {
				//alert("Gbjct.HTmOt");
				window.clearTimeout(Gbjct.HTmOt);
			}			
			Gbjct.PstTrg.close();
			Gbjct.PstTrg =null;
		}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)			
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();	
	}//try
}//function SendData(Target,OtTarget,Title,Style,Tken)
//23
function receiveMessage(event)
{
	var l_iRetCode=0;
	var l_vRetData;
	
	Gbjct.RspDt=null;
	l_vRetData="";
	Gbjct.GoOrBack="back";
	try {
		if (Gbjct.PstTrg!=null)
		{
			if (event.origin!="http://localhost:61161") {
				l_iRetCode=2300;
			} else {	
				var ret = JSON.parse(event.data);
				if (ret.func != null) {
					switch(ret.func) {
						//case "getDynamicCardFileAction":
						case "getTbs":
						case "CMCAtn":
							Gbjct.GoOrBack="go";
							Gbjct.PstTrg.postMessage(Gbjct.TbsPckg,"*");					
							break;					
						case "sign":
						case "umakeSig":
							Gbjct.RspDt=event.data;
							l_vRetData=StSgn(GARevFun.UrFUN);
							break;
						case "changeUserPINCode":
							Gbjct.RspDt=event.data;
							l_vRetData=UsrChgPn(GARevFun.UrFUN);
							break;					
						case "pkcs11info":
							Gbjct.RspDt=event.data;						
							switch(GARevFun.FUN)
							{
								case "gtCrt":
									l_vRetData=gtCrt(GARevFun.UrFUN);
									break;
								case "PrsCrt":
									l_vRetData=PrsCrt(GARevFun.UrFUN);
									break;								
								default:
									l_vRetData=stDa(GARevFun.UrFUN);
									break;
							}//switch(GARevFun.FUN)
							break;
						case "bulidResetUserPINRequest":
							Gbjct.RspDt=event.data;
							l_vRetData=UrGtRstInf(GARevFun.UrFUN);
							break;
						case "resetUserPIN":
							Gbjct.RspDt=event.data;
							l_vRetData=UrRstInfo(GARevFun.UrFUN);
							break;
						case "bulidUnblockCardRequest":
							Gbjct.RspDt=event.data;
							l_vRetData=UrGtUBlkInfo(GARevFun.UrFUN);						
							break;
						case "unblockCard":
							Gbjct.RspDt=event.data;
							l_vRetData=UrRstUBlkInfo(GARevFun.UrFUN);						
							break;
						case "buildOpenCardValidateUserRequest":
						case "buildOpenCardGetUserPINRequest":
							Gbjct.RspDt=event.data;
							l_vRetData=UrGtPnCrdInfo(GARevFun.UrFUN);						
							break;					
						case "openCard":
							Gbjct.RspDt=event.data;
							l_vRetData=UrPnCrdInfo(GARevFun.UrFUN);						
							break;	
						case "writeCert":
							Gbjct.RspDt=event.data;
							l_vRetData=WrtCrt(GARevFun.UrFUN);
							break;			
						case "csr": //makecsr exe's return
							Gbjct.RspDt=event.data;
							l_vRetData=MkCsr(GARevFun.UrFUN);
							break;							
						case "printImageb64toDefaultPrinter":
							Gbjct.RspDt=event.data;
							l_vRetData=PrtImgb64toDfltPrtr(GARevFun.UrFUN);
							break;							
						case "printImageb64toSelectedPrinter":
							Gbjct.RspDt=event.data;
							l_vRetData=PrtImgb64toSelctPrtr(GARevFun.UrFUN);
							break;							
						case "printImageb64toCardPrinter":
							Gbjct.RspDt=event.data;
							l_vRetData=PrtImgb64toCrdPrtr(GARevFun.UrFUN);
							break;							
						case "ContactEncoderPosition":
							Gbjct.RspDt=event.data;
							l_vRetData=CntctEncdrPstn(GARevFun.UrFUN);
							break;								
						case "ContactlessEncoderPosition":
							Gbjct.RspDt=event.data;
							l_vRetData=CntctlessEncdrPstn(GARevFun.UrFUN);
							break;								
						case "RejectPosition":
							Gbjct.RspDt=event.data;
							l_vRetData=RjtPstn(GARevFun.UrFUN);
							break;								
						case "PrintPosition":
							Gbjct.RspDt=event.data;
							l_vRetData=PrtPstn(GARevFun.UrFUN);
							break;								
						case "PrinterINFO":
							Gbjct.RspDt=event.data;
							l_vRetData=PrtrINFO(GARevFun.UrFUN);
							break;	
						case "exDynamicCreateFile":
							Gbjct.RspDt=event.data;
							l_vRetData=exDynmCrtFileInCrd(GARevFun.UrFUN);
							break;	
						case "exDynamicReleaseFile":
							Gbjct.RspDt=event.data;
							l_vRetData=exDynmRelsFileInCrd(GARevFun.UrFUN);
							break;
						//eid part1 begin
						case "getChallenge"://yun test challenge
							Gbjct.RspDt=event.data;
							l_vRetData=GtChllng(GARevFun.UrFUN);
							break;
						case "ListEID":
							Gbjct.RspDt = event.data;
							l_vRetData = EIDstDa(GARevFun.UrFUN);
							break;
						case "GetEIDPublicData":
							Gbjct.RspDt = event.data;
							l_vRetData = GetEIDPublicDataRev(GARevFun.UrFUN);
							break;
						case "GetEIDHouseHoldData":
							Gbjct.RspDt = event.data;
							l_vRetData = GetEIDHouseHoldDataRev(GARevFun.UrFUN);
							break;
						case "GetEIDPrivateDataStep1":
							Gbjct.RspDt = event.data;
							l_vRetData = GetEIDPrivateDataStep1Rev(GARevFun.UrFUN);
							break;
						case "GetEIDPrivateDataStep2":
							Gbjct.RspDt = event.data;
							l_vRetData = GetEIDPrivateDataStep2Rev(GARevFun.UrFUN);
							break;
						case "changePINEID":
							Gbjct.RspDt = event.data;
							l_vRetData = ChangePinEIDRev(GARevFun.UrFUN);
							break;
						case "bulidUnblockCardRequestEID":
							Gbjct.RspDt = event.data;
							l_vRetData = BulidUnblockCardRequestEIDRev(GARevFun.UrFUN);
							break;
						case "unblockCardEID":
							Gbjct.RspDt = event.data;
							l_vRetData = UnblockCardEIDRev(GARevFun.UrFUN);
							break;
						//eid part1 end
						default:
							if (ret.ret_code==1979711502)
							{
								l_iRetCode=2304;
								l_vRetData=rMsg(l_iRetCode,"("+ret.func+":"+ret.ret_code+"--"+l_iRetCode+")");
								l_iRetCode=ret.ret_code;
								l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
							} else {
								l_iRetCode=2301;
								l_vRetData=rMsg(l_iRetCode,"("+ret.func+":"+ret.ret_code+"--"+l_iRetCode+")");
								if (ret.ret_code!=null) {
									l_iRetCode=ret.ret_code;
								}//if (ret.ret_code!=null)
								l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
							}//if (ret.ret_code==1979711502)
							break;
					}//switch(ret.func)
				}else{
					var l_vTemp;
					l_iRetCode=2302;
					if (ret.ret_code!=null) {
						l_vTemp="(Error:"+ret.ret_code+" Msg:"+ret.message+"--"+l_iRetCode+")";
					} else {
						l_vTemp="";
					}//if (ret.ret_code!=null)					
					l_vRetData=rMsg(l_iRetCode,l_vTemp);
					l_iRetCode=ret.ret_code;
					l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
				}//if (ret.func != null)	
			}//if (event.origin!="http://localhost:61161")		
		} else {
			l_iRetCode=2303;
			l_vRetData=rMsg(l_iRetCode,"Popup Connection closed!!Retry!!");
			l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
		}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
		//alert("Gbjct.GoOrBack"+Gbjct.GoOrBack);
		if (Gbjct.GoOrBack=="back") 
		{
			try
			{
				if (Gbjct.HTmOt!=null) {
					window.clearTimeout(Gbjct.HTmOt);
				}			
				if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
				{
					//alert("back not closed");
					Gbjct.PstTrg.close();
					Gbjct.PstTrg =null;				
				}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)				
			} catch(e) {
				l_iRetCode=2303;
				l_vRetData=rMsg(l_iRetCode,e.message);
				l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
				if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
				{		
					if (Gbjct.HTmOt!=null) {
						//alert("Gbjct.HTmOt");
						window.clearTimeout(Gbjct.HTmOt);
					}			
					Gbjct.PstTrg.close();
					Gbjct.PstTrg =null;
				}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)					
			}//try				
		}//if (l_vGoOrBack=="back")
	}catch(e){
		l_iRetCode=2303;
		l_vRetData=rMsg(l_iRetCode,e.message);
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRetData +'"}';
		if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)
		{		
			if (Gbjct.HTmOt!=null) {
				//alert("Gbjct.HTmOt");
				window.clearTimeout(Gbjct.HTmOt);
			}			
			Gbjct.PstTrg.close();
			Gbjct.PstTrg =null;
		}//if (Gbjct.PstTrg!=null && !Gbjct.PstTrg.closed)			
	}//try		
	
	if (l_vRetData!="") 
	{
		InToken.RetObj=JSON.parse(l_vRetData);		
		if (GARevFun.UrFUN && typeof(GARevFun.UrFUN) === "function") GARevFun.UrFUN();
	}//if (l_iRetCode!=0)
}//function receiveMessage(event)
//24
if (window.addEventListener) window.addEventListener("message", receiveMessage, false);
//25
function postData(FUN)
{
	var RetData;
	RetData="";
	if (FUN != null) {
		switch(FUN) {
			case "umakeSig":
			case "unblockCard":
			case "MakeSignature":
			case "changeUserPINCode":
			case "resetUserPIN":
			case "openCard":
			case "bulidUnblockCardRequest":
			case "buildOpenCardValidateUserRequest":
			case "buildOpenCardGetUserPINRequest":
			case "bulidResetUserPINRequest":			
			case "writecert":
			case "makeCsr":
			case "printImageb64toDefaultPrinter":
			case "printImageb64toSelectedPrinter":
			case "printImageb64toCardPrinter":
			case "ContactEncoderPosition":
			case "ContactlessEncoderPosition":
			case "RejectPosition":
			case "PrintPosition":
			case "PrinterINFO":			
			case "exDynamicCreateFile":
			case "exDynamicReleaseFile":
			//eid part2 begin
			case "getChallenge":
			case "GetEIDPublicData":
			case "GetEIDHouseHoldData":
			case "GetEIDPrivateDataStep1":
			case "GetEIDPrivateDataStep2":
			case "BulidUnblockCardRequestEID":
			case "UnblockCardEID":
			case "ChangePinEID":
			//eid part2 end
				RetData="tbsPackage="+Gbjct.TbsPckg;			
				break;					
			default:
				break;
		}//switch(FUN)
	}//if (ret.func != null)
	return RetData;
}//function postData(FUN)
//26
function checkEnv(UrRevFUN, IsPrintCard) {     
	if (typeof (IsPrintCard) === "undefined") IsPrintCard=false;
	InToken.FUN="CheckEnvir";
	GARevFun.FUN="stDa";
	GARevFun.Para="";	
	GARevFun.IsPrintCard=IsPrintCard;
	GARevFun.UrFUN=UrRevFUN;
	
 	SendData("http://localhost:61161/pkcs11info","http://localhost:61161/ChtPopupForm","初始中",Gbjct.PppFrmStyle,stDa,UrRevFUN);	
}//function checkEnv()
//27 retrun
//object.RCode
//object.RMsg
function checkCondition()
{
	var l_vRMsg;
	var l_vRetCode;
	var l_vRetData;
	
	l_vRMsg="";
	l_vRetCode=0;
	if (InToken.ActvSltID[0]==-1) {
		l_vRetCode=2700;
		l_vRMsg=rMsg(l_vRetCode,"");
	}//if (this.ActvSltID[0]==-1)
	
	if (InToken.ActvSltID.length==0) {
		l_vRetCode=2701;
		l_vRMsg=rMsg(l_vRetCode,"");
	}//if (this.ActvSltID[0]==-1)	
	
	if (l_vRetCode==0) {
		if ((InToken.ActvSltID[0]<InToken.SlotID.length)&&(InToken.ActvSltID[0]>=0)) {
		} else {
			l_vRetCode=2702;
		}//if (this.ActvSltID<=this.SlotID.length)
	}//if (l_vRetCode.RCode==0)
		
	l_vRetData='{"RCode":"' + l_vRetCode +'","RMsg":"'+ l_vRMsg +'"}';
	
    return JSON.parse(l_vRetData);
}//function checkCondition()
//28
function rMsg(RCode,ExMsg)
{
	var l_vRetMsg;
	
	l_vRetMsg="";
	switch(RCode)
	{
		case 1000:
			l_vRetMsg="尚未初始化Token!" + ExMsg;
			break;		
		case 2200:
			l_vRetMsg="元件回傳錯誤!" + ExMsg;
			break;		
		case 2201:
			l_vRetMsg="開啟等待視窗時發生錯誤!如果您已封鎖彈出式視窗，請將彈出式視窗項目設定為『允許』!" + ExMsg;
			break;
		case 2202:
			l_vRetMsg="元件尚未安裝或啟動，請安裝或啟動相關元件!" + ExMsg;
			break;				
		case 2204:
			l_vRetMsg="等待視窗已關閉發生錯誤!" + ExMsg;
			break;		
		case 2205:
			l_vRetMsg="連線逾時發生錯誤!" + ExMsg;
			break;			
		case 2206:
			l_vRetMsg="處理元件回傳訊息發現錯誤，請檢查作業系統和瀏覽器的網路相關設定是否正確；若無法確定問題，請使用瀏覽器初始設定。" + ExMsg;
			break;		
		case 2300:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 2301:
			l_vRetMsg="元件回傳錯誤訊息!"+ExMsg;
			break;
		case 2302:
			l_vRetMsg="元件回傳錯誤無法識別!"+ExMsg;
			break;
		case 2303:
			l_vRetMsg="執行發生錯誤回傳錯誤訊息!"+ExMsg;
			break;
		case 2304:
			l_vRetMsg="回傳資料錯誤找不到讀卡機或未啟動Token!"+ExMsg;
			break;			
		case 2700:
			l_vRetMsg="找不到讀卡機!" + ExMsg;
			break;
		case 2701:
			l_vRetMsg="找不任何卡片或讀卡機!"+ExMsg;
			break;
		case 2702:
			l_vRetMsg="找不到指定的卡片!" + ExMsg;
			break;
	    case 2703:
	        l_vRetMsg = ExMsg + "的卡片沒有插好， 請重新插卡後再試一次!";
	        break;
		case 3100:
			l_vRetMsg="元件未回傳任何資料!" + ExMsg;
			break;
		case 3101:
			l_vRetMsg="非合法授權連接的位址!" + ExMsg;
			break;
		case 3102:
			l_vRetMsg="請安裝新版本元件，版本至少需為：V_" + ExMsg;
			break;
		case 3103:
			l_vRetMsg="找不到指定的卡片或未安裝讀卡機!" + ExMsg;
			break			
		case 3104:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 3200:
			l_vRetMsg="輸入參數錯誤!"+ExMsg;
			break;
		case 3201:
			l_vRetMsg="元件未回傳任何資料!"+ExMsg;
			break;
		case 3202:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;		
		case 3203:
			l_vRetMsg="找不到指定的讀卡機"+ExMsg;
			break;	
		case 3204:
			l_vRetMsg="取得憑證失敗，讀不到憑證!"+ExMsg;
			break;				
		case 3205:
			l_vRetMsg="元件發生錯誤!"+ExMsg;
			break;
		case 3206://20161007
			l_vRetMsg="找不到讀卡機!"+ExMsg;
			break;			
		case 3300:
			l_vRetMsg="輸入參數錯誤!"+ExMsg;
			break;
		case 3301:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 3302:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;
		case 3400:
			l_vRetMsg="輸入參數錯誤!"+ExMsg;
			break;
		case 3401:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 3402:
		case 3403:
		case 3404:
		case 3405:		
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 3500:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 3501:
			l_vRetMsg="簽章發生錯誤，卡片不正確!"+ExMsg;
			break;
		case 3502:
			l_vRetMsg="元件發生錯誤!"+ExMsg;
			break;
		case 3503:
			l_vRetMsg="IC卡密碼錯誤!"+ExMsg;
			break;				
		case 3600:
			l_vRetMsg="輸入參數錯誤!"+ExMsg;
			break;
		case 3601:
			l_vRetMsg="輸入參數長度錯誤!"+ExMsg;
			break;
		case 3602:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 3603:		
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 3700:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 3701:
			l_vRetMsg="修改PIN碼發生錯誤，卡片不正確!"+ExMsg;
			break;
		case 3702:
			l_vRetMsg="元件發生錯誤!"+ExMsg;
			break;
		case 3800:
			l_vRetMsg="輸入參數長度錯誤!"+ExMsg;
			break;
		case 3801:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 3802:		
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;
		case 3900:
			l_vRetMsg="輸入參數長度錯誤!"+ExMsg;
			break;
		case 3901:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 3902:
			l_vRetMsg="解析憑證發生錯誤，卡片不正確!"+ExMsg;
			break;
		case 3903:
			l_vRetMsg="元件發生錯誤!"+ExMsg;
			break;
		case 3904://20161007
			l_vRetMsg="找不到讀卡機!"+ExMsg;
			break;			
		case 4000:
		case 4001:
		case 4002:
		case 4004:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 4003:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 4100:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 4101:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 4300:
		case 4301:
		case 4302:
		case 4304:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 4303:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 4400:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 4401:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 4500:
		case 4501:
		case 4502:
		case 4504:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 4503:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 4600:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 4601:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 4700:
		case 4701:
		case 4702:
		case 4703:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 4704:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;	
		case 4800:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 4801:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 4900:
		case 4901:
		case 4902:
		case 4903:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 4904:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;			
		case 5000:
			l_vRetMsg="未啟用初始化IC卡元件!"+ExMsg;
			break;
		case 5200:
			l_vRetMsg="清空已被IC卡元件!"+ExMsg;
			break;
		case 5500:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 5800:
			l_vRetMsg="設定的ID不存在!"+ExMsg;
			break;
		case 8001://eid reader
		case 8002:
		case 8003:
		case 8004:
		case 8005:
		case 8006:
		case 8007:
		case 8008:
		case 8009:
		case 8010:
			l_vRetMsg="元件回傳錯誤! "+ EidErrorReason(ExMsg);
			break;
		case 9000:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 9001:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;		
		case 9100:
		case 9101:
		case 9102:
		case 9103:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;			
		case 9104:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 9200:
			l_vRetMsg="非合法授權連接的位址!"+ExMsg;
			break;
		case 9201:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		case 9300:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9301:
			l_vRetMsg="輸入參數為空或不足!"+ExMsg;
			break;							
		case 9302://writecert err
			l_vRetMsg="元件回傳錯誤! "+ EidErrorReason(ExMsg);
			break;
		case 9303:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;
		case 9304:
			l_vRetMsg="輸入參數為空或不足!"+ExMsg;
			break;	
		case 9400:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9401:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9402:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 9403:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;	
		case 9410:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9411:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9412:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 9413:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;	
		case 9420:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9421:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9422:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 9423:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;	
		case 9501:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9502:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		case 9511:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9512:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		case 9521:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9522:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		case 9531:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9532:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		case 9541:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9542:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		case 9600:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9601:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9602:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;
		case 9603:
			l_vRetMsg="尚未啟用元件!"+ExMsg;
			break;		
		case 9700:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9701:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9702:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;	
		case 9710:
			l_vRetMsg="輸入參數錯誤為空值!"+ExMsg;
			break;
		case 9711:
			l_vRetMsg="未輸入參數!"+ExMsg;
			break;							
		case 9712:
			l_vRetMsg="元件回傳錯誤!"+ExMsg;
			break;			
		default:
			l_vRetMsg=ExMsg;
			break;			
	}//swirch(RCode)
	return l_vRetMsg;
}//function returnMsg(RCode,ExMsg)
//28-1 retrun
function MinorErrorReason(rcode){
	switch(rcode){
		case 0x06:
			return "函式失敗";
		case 0xA0:
			return "PIN碼錯誤";
		case 0xA2:
			return "PIN碼長度錯誤";
		case 0xA4:
			return "已鎖卡";
		case 0x150:
			return "記憶體緩衝不足";
		case 0xFFFFFFFF80000001: 	
		case -2147483647:
			return "PIN碼錯誤，剩餘一次機會";
		case 0xFFFFFFFF80000002:
		case -2147483646:
			return "PIN碼錯誤，剩餘兩次機會";
		default:
			return rcode.toString(16);
	}
}
//29
function getInfo(){
	var PreData = {};
	PreData["tbs"]=InToken.TBS;
	PreData["tbsEncoding"]=InToken.TbsEncoding;
	PreData["hashAlgorithm"]=InToken.HashAlgorithm;
	PreData["pin"]=InToken.PIN;
	PreData["userpin"]=InToken.PIN;
	PreData["func"]=InToken.FUN;
	PreData["signatureType"]=InToken.SType;
	PreData["readername"]=InToken.getSlotName(InToken.ActvSltID[0]);
	PreData["slotDescription"]=InToken.getSlotName(InToken.ActvSltID[0]);
	PreData["currentPIN"]=InToken.OldPIN;
	PreData["newpin"]=InToken.NewPIN;
	PreData["sid"]=InToken.UID;
	PreData["caname"]=InToken.CrdSys;
	PreData["msresponse"]=InToken.KpMsg;
	PreData["cmsresponse"]=InToken.KpMsg;
	PreData["cardid"]=InToken.SmrtCrdID[InToken.ActvSltID[0]];
	PreData["checkValidity"]=InToken.checkValidity;//"false";
	PreData["keyidb64"]=InToken.keyidb64;
	PreData["nonce"]=InToken.nonce;
	PreData["withSigningTime"]=InToken.withSigningTime;
	PreData["withCardSN"]=InToken.withCardSN;
	//makcsr
	PreData["keyid"] = InToken.keyid; 	
	PreData["challenge"] = InToken.challenge; 	
	//writeCert
	PreData["type"] = InToken.WrtCrtType; 
	PreData["value"] = InToken.WrtCrtValue;
	//PrintCard
	PreData["printerName"] = InToken.printerName;
	PreData["cardPrinterName"] = InToken.cardPrinterName;	
	PreData["paperSizes"] = InToken.paperSizes;	
	PreData["imageb64string"] = InToken.imageb64string;	
	PreData["uuid"] = InToken.uuid;	
	PreData["filename"] = InToken.filename;		
	//exDynamicCreateFile,exDynamicReleaseFile
	PreData["exDynamicCreateFileInput"]=InToken.exDynamicCreateFileInput;
	PreData["exDynamicReleaseFileInput"]=InToken.exDynamicReleaseFileInput;
	//eid part3 begin
	PreData["ifdName"] = InToken.getSlotName(InToken.ActvSltID[0]);
	PreData["can"] = InToken.can;
	PreData["mrz"] = InToken.mrz;
	PreData["queryDG"] = InToken.queryDG;
	PreData["dvCert"] = InToken.dvCert;
	PreData["atCert"] = InToken.atCert;
	PreData["pin1"] = InToken.pin1;
	PreData["signedData"] = InToken.signedData;
	PreData["handle"] = InToken.handle;
	PreData["changePINType"] = InToken.changePINType;
	PreData["unblockType"] = InToken.unblockType;
	PreData["cardNumber"] = InToken.cardNumber;
	PreData["oldPIN"] = InToken.oldPIN;
	PreData["newPIN"] = InToken.newPIN;
	PreData["unblockCardResponse"] = InToken.unblockCardResponse;
	//yun
	PreData["useChallenge"] = InToken.WrtCrtUseChallenge;
	PreData["isTest"] = InToken.isTest;
	//eid part3 end
	var ReData = JSON.stringify(PreData ).replace(/\+/g,"%2B");
	
	return ReData;
}//function getInfo()
//30
function popInfo() {
	var objInfo = new Image();
	objInfo.src = 'data:image/gif;base64,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';
	return objInfo;
}//function popInfo()
//31
function stDa(UrRevFUN) {
	var l_iRetCode=0;
	var l_iSlotCounter=0;
	var l_vRetData;
	var l_vRMsg;
	
	l_vRetData="";
	l_vRMsg="";	
	if (GARevFun.IsPrintCard==true){ //if it works for printing card (CMC component), no need to check IC Card Reader as below
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg +'"}';
		return l_vRetData;
	}
	
	if (Gbjct.RspDt==null) {
		l_iRetCode=3100;
		l_vRMsg=rMsg(l_iRetCode,"");
	} else {
		//alert("Gbjct.RspDt："+Gbjct.RspDt);
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=3101;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0)
			{
				var l_vTemp;
				l_vTemp=setVNum();
				//alert("ret.serverVersion："+ret.serverVersion)
				if (l_vTemp.localeCompare(ret.serverVersion)>0) {
					l_iRetCode=3102;
					l_vRMsg=rMsg(l_iRetCode,l_vTemp);				
				} else {
					var slots;
					//if (typeof ret.slots !== 'undefined')
					if (typeof ret.slots[0] !== 'undefined' && ret.slots[0] !== null) 
					{
						slots = ret.slots;
						for(var index in slots)
						{ 
							InToken.SlotID[index]=slots[index].slotDescription;
							InToken.SlotName[slots[index].slotDescription]=index;
							if (slots[index].token!=null) 
							{
								InToken.SmrtCrdID[index]=slots[index].token.serialNumber;
								InToken.ActvSltID[0]=index;
								l_iSlotCounter++;
							} else  {
								InToken.SmrtCrdID[index]="";
							}//if (slots[index].token.serialNumber!="") 
						}//for(var index in slots)
							
						if (l_iSlotCounter==0) {
							l_iRetCode=3103;
							l_vRMsg=rMsg(l_iRetCode,"");
						} else {
							if (l_iSlotCounter>1) {
								InToken.ActvSltID[0]=-1;
							}//if (l_iSlotCounter>1)					
						}//if (l_iSlotCounter>1)
					} else {
						l_iRetCode=3103;
						l_vRMsg=rMsg(l_iRetCode,"(Location 2)");					
					}//if (slots.length!=0)						
				}//if (l_vTemp.localeCompare(ret.serverVersion)>0)
			} else {
				l_iRetCode=3104;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)	
		}//if(ret.ret_code==0x76000031)		
	}//if (Gbjct.RspDt==null)
		
	l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);	
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	
	//return JSON.parse(l_vRetData);
	return l_vRetData;
}//function stDa(Gbjct,InToken
//32
function gtCrt(UrRevFUN) {
	var l_iRetCode;
	var l_vRMsg;
	var l_vExp;
	var l_vRetData;
	var l_B64Cert;	
	
	l_vRMsg="";
	l_B64Cert="";
	l_iRetCode=0;
	
	switch(GARevFun.Para)
	{
		case 0:
			l_vExp=reg(GARevFun.keyidb64);
			break;		
		case 1:
			l_vExp=/digitalSignature/i;
			break;
		case 2:
			l_vExp=/keyEncipherment|dataEncipherment/i;
			break;		
		default:
			l_iRetCode=3200;
			l_vRMsg=rMsg(l_iRetCode,"");
			break;
	}//switch(index)
	
	if (l_iRetCode==0) {
		if (Gbjct.RspDt==null) {
			l_iRetCode=3201;
			l_vRMsg=rMsg(l_iRetCode,"");
		} else {
			var ret=JSON.parse(Gbjct.RspDt);
			if(ret.ret_code==0x76000031)
			{
				l_iRetCode=3202;
				l_vRMsg=rMsg(l_iRetCode,l_iRetCode);
				l_iRetCode=ret.ret_code;
			} else {
				if (ret.ret_code==0) {
					var slots;
					//if (typeof ret.slots !== 'undefined')
					if (typeof ret.slots[0] !== 'undefined' && ret.slots[0] !== null) 
					{
						slots = ret.slots;
						for (var index in slots) { 
							if(slots[index].slotDescription == InToken.SlotID[InToken.ActvSltID[0]])
							{													
								if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==slots[index].token.serialNumber) {
									var certs=slots[index].token.certs;	
									for(var indexCert in certs){								
										if (
											(l_vExp.test(certs[indexCert].usage)==true && (GARevFun.Para==1 || GARevFun.Para==2) ) || 
											(l_vExp.test(certs[indexCert].id)==true && GARevFun.Para==0)									
										) {
											l_B64Cert=certs[indexCert].certb64;
										}//if (l_vExp.test(certs[indexCert].usage)==true)
									}//for(var indexCert in certs)
								} else {
									l_iRetCode=3203;
									l_vRMsg=rMsg(l_iRetCode,"");					
								}//if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==certs.serialNumber)										
							}//if(slots[index].slotDescription == InToken.SlotID[InToken.getSlotName(InToken.ActvSltID)])
						}//for (var index in slots)
						if (l_iRetCode==0) {
							if (l_B64Cert=="") {
								l_iRetCode=3204;
								l_vRMsg=rMsg(l_iRetCode,"");
							}//if (l_B64Cert="")
						}//if (l_iRetCode==0)						
					} else {
						l_iRetCode=3206;
						l_vRMsg=rMsg(l_iRetCode,"");						
					}//if (slots.length!=0)				
				} else {
					l_iRetCode=3205;
					l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
					l_iRetCode=ret.ret_code;
				}//if (ret.ret_code==0)
			}//if(ret.ret_code==0x76000031)		
		}//if (Gbjct.RspDt==null)	
	}//if (l_iRetCode==0)
	l_vRetData='{"RCode":"' + l_iRetCode + '", "Certificate":"' + l_B64Cert +'","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
		
	//return JSON.parse(l_vRetData);
	return l_vRetData;
}//function gtCrt() 
//33
function getCert(index,UrRevFUN,keyidb64) {
	if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";
	
	var l_iRetCode;
	var l_vRMsg;
	var l_vExp;
	var l_vRetData;
	var l_B64Cert;	
	
	l_vRMsg="";
	l_B64Cert="";
	l_iRetCode=0;	
	InToken.FUN="GetUserCert";
	GARevFun.FUN="gtCrt";
	GARevFun.Para=index;
	GARevFun.keyidb64=keyidb64;
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;	
	
	switch(GARevFun.Para)
	{
		case 0:
			l_vExp=reg(GARevFun.keyidb64);
			break;			
		case 1:
			l_vExp=/digitalSignature/i;
			break;
		case 2:
			l_vExp=/keyEncipherment|dataEncipherment/i;
			break;		
		default:
			l_iRetCode=3300;
			l_vRMsg=rMsg(l_iRetCode,"");
			break;
	}//switch(index)
	
	if (UrRevFUN==null) 
	{
		l_iRetCode=3302;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 
			
	if (checkGoodDay()==false) {
		l_iRetCode=3301;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		l_vRetData=checkCondition(InToken);
		if (l_vRetData.RCode!=0) {
			l_iRetCode=l_vRetData.RCode;
			l_vRMsg=rMsg(l_iRetCode,"");		
		}//if (l_vRetData.RCode!=0)
	}//if (l_iRetCode==0)
	
	if (l_iRetCode==0) {	
		SendData("http://localhost:61161/pkcs11info?withcert=true","http://localhost:61161/ChtPopupForm","IC卡讀取中",Gbjct.PppFrmStyle,gtCrt,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '", "Certificate":"' + l_B64Cert +'","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}//function getCert()
//34
function UserSign(B64ToBeSign,UserPIN,HashAlg,UrRevFUN,PKCSSignType,keyidb64,nonce,withSigningTime,withCardSN,checkValidity,SignType) {
	var l_iRetCode=0;
	var l_B64Signature="";
	var l_vRMsg;
	var l_vRetData;
	
	if (typeof (PKCSSignType) === "undefined") PKCSSignType="PKCS1";
    if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";	
    if (typeof (nonce) === "undefined") nonce="__NotExistedTag__";	
    if (typeof (withSigningTime) === "undefined") withSigningTime="__NotExistedTag__";	
    if (typeof (withCardSN) === "undefined") withCardSN="__NotExistedTag__";	
    if (typeof (checkValidity) === "undefined") checkValidity="__NotExistedTag__";	
	
	if (PKCSSignType=="PKCS7") {
		InToken.SType="PKCS7";
	} else { //Default: "PKCS1"
	}
	
	if (SignType.localeCompare("Cryp")==0) {
		InToken.FUN="umakeSig";
		GARevFun.FUN="StSgn";		
	} else {
		InToken.FUN="MakeSignature";
		GARevFun.FUN="StSgn";
	}//if (SignType.localeCompare("Cryp")==0)	
		
	InToken.PIN=UserPIN;
	InToken.TBS=B64ToBeSign;
	InToken.keyidb64=keyidb64;
	InToken.nonce=nonce;
	InToken.withSigningTime=withSigningTime;
	InToken.withCardSN=withCardSN;
	InToken.checkValidity=checkValidity;		
	
	GARevFun.Para=HashAlg.toUpperCase();
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;
	
	switch(GARevFun.Para)
	{
		case "SHA256":
			InToken.HashAlgorithm="SHA256";
			break;
		case "SHA1":
			InToken.HashAlgorithm="SHA1";
			break;		
		default:
			l_iRetCode=3400;
			l_vRMsg=rMsg(l_iRetCode,"");
			break;
	}//switch(index)
	if (B64ToBeSign==null) 
	{
		l_iRetCode=3402;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (B64ToBeSign==null) 
		
	if (UserPIN==null) 
	{
		l_iRetCode=3403;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UserPIN==null) 
		
	if (HashAlg==null) 
	{
		l_iRetCode=3404;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (HashAlg==null) 
		
	if (UrRevFUN==null) 
	{
		l_iRetCode=3405;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 		
		
	if (checkGoodDay()==false) {
		l_iRetCode=3401;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		if (SignType.localeCompare("Cryp")==0) {
			SendData("http://localhost:61161/umakeSig","http://localhost:61161/ChtPopupForm","IC卡讀取中",Gbjct.PppFrmStyle,StSgn,UrRevFUN);			
		} else {
			SendData("http://localhost:61161/sign","http://localhost:61161/ChtPopupForm","IC卡讀取中",Gbjct.PppFrmStyle,StSgn,UrRevFUN);				
		}//if (SignType.localeCompare("Cryp")==0)
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '", "B64Signature":"' + l_B64Signature +'","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}//TokenSign(B64ToBeSign,UserPIN,HashAlg,RevFun)
//35
function StSgn(UrRevFUN) {
	var l_iRetCode=0;
	var l_B64Signature="";
	var l_vRMsg;
	var l_vRetData;
	var ErrMsg="";
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76100006){		
		//if(ret.last_error==0xA4 || ret.last_error==0xFFFFFFFF80000002 || ret.last_error==0xFFFFFFFF80000001) //如果登入錯誤是PIN碼問題造成，則把minor Code(last error)傳回去
		if(ret.last_error!="" && ret.last_error!=0){	
			ret.ret_code=ret.last_error; //若有last_error且不為0，則交換到主要的ret_code
			ErrMsg=MinorErrorReason(ret.ret_code);
		}else{
			ErrMsg=ret.message; //IC卡登入失敗(可能原因是鎖卡、...) //比較少見：ret_code==0x76100006, 但last_error==0
		}						
		l_iRetCode=3503;		
		l_vRMsg=rMsg(l_iRetCode,ErrMsg+"("+ret.ret_code+"--"+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else if(ret.ret_code==0x76000031){
		l_iRetCode=3500;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			if (ret.func.localeCompare("umakeSig")==0) {
				if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==ret.data.cardSN) {
					l_B64Signature=ret.data.signature;
				} else {
					l_iRetCode=3501;
					l_vRMsg=rMsg(l_iRetCode,"");     
				}//if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==ret.cardSN)    
			} else {
				if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==ret.cardSN) {
					l_B64Signature=ret.signature;
				} else {
					l_iRetCode=3501;
					l_vRMsg=rMsg(l_iRetCode,"");
				}//if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==ret.cardSN)
			}//if (ret.func.localeCompare("umakeSig")==0)
		} else {
			l_iRetCode=3502;
			l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
	InToken.OldPIN="";
	InToken.NewPIN="";
	InToken.keyidb64="__NotExistedTag__";
	InToken.nonce="__NotExistedTag__";
	InToken.withSigningTime="__NotExistedTag__";
	InToken.withCardSN="__NotExistedTag__";
	InToken.checkValidity="__NotExistedTag__";	
	l_vRetData='{"RCode":"' + l_iRetCode + '", "B64Signature":"' + l_B64Signature +'","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	//return JSON.parse(l_vRetData)
	return l_vRetData;
}//ICToken.prototype.sign = function (B64ToBeSign,UserPIN,HashAlg)
//36
function UChngPin(NewPIN,OldPIN,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_B64Signature="";
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="changeUserPINCode";
	GARevFun.FUN="UsrChgPn";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if ((NewPIN=="")||(OldPIN=="")||(NewPIN==null)||(OldPIN==null))
	{
		l_iRetCode=3600;
		l_vRMsg=rMsg(l_iRetCode,"");
	}//if ((NewPIN=="")||(OldPIN=="")||NewPIN==null)||(OldPIN==null))
	if (UrRevFUN==null) 
	{
		l_iRetCode=3603;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
		
	if (checkGoodDay()==false) {
		l_iRetCode=3602;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0)
	{
		if ((decodeURIComponent(NewPIN).length > 8) || (decodeURIComponent(OldPIN).length > 8))
		{
			l_iRetCode=3601;
			l_vRMsg=rMsg(l_iRetCode,"");		
		}//if ((decodeURIComponent(NewPIN).length > 8) || (decodeURIComponent(OldPIN).length > 8))
	}//if (l_iRetCode==0)
	
	if (l_iRetCode==0) {
		InToken.OldPIN=OldPIN;
		InToken.NewPIN=NewPIN;
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/ChtPopupForm",
					"處理中", Gbjct.PppFrmStyle,UsrChgPn,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//37
function UsrChgPn(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
		} else {
			l_iRetCode=3702;
			l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.OldPIN="";
	InToken.NewPIN="";
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function UsrChgPn(UrRevFUN)
//38
function UsrPrsCrt(index,UrRevFUN,keyidb64)
{
	if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";
	
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	InToken.FUN="GetUserCert";	
	GARevFun.FUN="PrsCrt";
	GARevFun.Para=index;
	GARevFun.keyidb64=keyidb64;
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;	
	
	switch(index)
	{
		case 0:
			l_vExp=reg(GARevFun.keyidb64);
			break;		
		case 1:
			l_vExp=/digitalSignature/i;
			break;
		case 2:
			l_vExp=/keyEncipherment|dataEncipherment/i;
			break;		
		default:
			l_iRetCode=3800;
			l_vRMsg=rMsg(l_iRetCode,"");
			break;
	}//switch(index)
	if (UrRevFUN==null) 
	{
		l_iRetCode=3802;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=3801;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/pkcs11info?withcert=true","http://localhost:61161/ChtPopupForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,PrsCrt,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}//function UsrPrsCrt(index,UrRevFUN)
function regexEscape(str) {
    return str.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')
}
function reg(input) {
    var flags;
    flags = 'i'; //could be any combination of 'g', 'i', and 'm'
    input = regexEscape(input);
    return new RegExp(input, flags);
}
//39
//yun add reading email: certs[indexCert].email
function PrsCrt(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;
	var l_vRetData;
	
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	switch(GARevFun.Para)
	{
		case 0:
			l_vExp=reg(GARevFun.keyidb64);
			break;
		case 1:
			l_vExp=/digitalSignature/i;
			break;
		case 2:
			l_vExp=/keyEncipherment|dataEncipherment/i;
			break;		
		default:
			l_iRetCode=3900;
			l_vRMsg=rMsg(l_iRetCode,"");
			break;
	}//switch(GARevFun.Para)	
	
	if (l_iRetCode==0) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=3901;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				var slots;
				//if (typeof ret.slots !== 'undefined')
				if (typeof ret.slots[0] !== 'undefined' && ret.slots[0] !== null) 
				{
					slots = ret.slots;
					for (var index in slots) { 
						if(slots[index].slotDescription == InToken.SlotID[InToken.ActvSltID[0]])
						{													
							if (InToken.SmrtCrdID[InToken.ActvSltID[0]]==slots[index].token.serialNumber) {
								var certs=slots[index].token.certs;	
								for(var indexCert in certs){								
									if (
									(l_vExp.test(certs[indexCert].usage)==true && (GARevFun.Para==1 || GARevFun.Para==2) ) || 
									(l_vExp.test(certs[indexCert].id)==true && GARevFun.Para==0 )
									){										
										var notAfter=new Date(certs[indexCert].notAfterT*1000);
										var notBefore=new Date(certs[indexCert].notBeforeT*1000);
										l_CertInfo= '"NotAfterYear":"' + notAfter.getFullYear() +'","NotAfterMonth":"'+(notAfter.getMonth()+1)+'","NotAfterDate":"'+notAfter.getDate()
													+'","NotBeforeYear":"' + notBefore.getFullYear() +'","NotBeforeMonth":"'+(notBefore.getMonth()+1)+'","NotBeforeDate":"'+notBefore.getDate()
													+'","Subject":"' +certs[indexCert].subjectDN +'","CertSN":"'+certs[indexCert].sn
													+'","IssuerDN":"'+certs[indexCert].issuerDN+'","KeyUsage":"'+certs[indexCert].usage
													+'","SubjectID":"'+certs[indexCert].subjectID+'","CardRank":"'+certs[indexCert].holderRank
													+'","Email":"'+certs[indexCert].email
													+'"';
									}//if (l_vExp.test(certs[indexCert].usage)==true)
								}//for(var indexCert in certs)
									
								var keys=slots[index].token.keys;	
								for(var indexKey in keys){								
									if ( 
									(l_vExp.test(keys[indexKey].id)==true && GARevFun.Para==0 )
									){
										if (l_CertInfo!=null)
											l_CertInfo=l_CertInfo+',"KeyB64":"' + keys[indexKey].keyb64+'"';
										else
											l_CertInfo='"KeyB64":"' + keys[indexKey].keyb64+'"';
									}//if (l_vExp.test(keys[indexKey].id)==true && GARevFun.Para==0 )
								}//for(var indexKey in keys){								
								
							} else {
								l_iRetCode=3902;
								l_vRMsg=rMsg(l_iRetCode,"");					
							}//if (this.SmrtCrdID[this.ActvSltID[0]]==certs.serialNumber)										
						}//if(slots[index].slotDescription == this.SlotID[this.getSlotName(this.ActvSltID)])
					}//for (var index in slots)					
				} else {
					l_iRetCode=3904;
					l_vRMsg=rMsg(l_iRetCode,"")					
				}//if (ret.slots.length!=0)
				
			} else {
				l_iRetCode=3903;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	}//if (l_iRetCode==0) 
	
	if (l_CertInfo!=null) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'",'+l_CertInfo+'}';			
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_CertInfo!=null)
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
		
	return l_vRetData;
}//function PrsCrt(UrRevFUN)
//42
function checkGoodDay()
{
	var l_vCardID;
	var l_vGoodDayOK;
	var l_vRetData;
	
	l_vGoodDayOK=false;
	
	l_vRetData=checkCondition();
	if (l_vRetData.RCode==0) {
		if ((InToken.ActvSltID[0]<InToken.SlotID.length)&&(InToken.ActvSltID[0]>=0)) {
			l_vGoodDayOK=true;
		}//if (InToken.ActvSltID<=InToken.SlotID.length)
	}//if (l_vRetData.RCode==0)	
		
	return l_vGoodDayOK;
}//function checkGoodDay()
//-------------------------------------------------------------------------------------------------------------------
// ChtICTokenGXCA、ChtICToken Function
//-------------------------------------------------------------------------------------------------------------------
//40 Reset user PIN
function UsrGtRstInfo(CardSystem,UID,UrRevFUN) 
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	InToken.FUN="bulidResetUserPINRequest";
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	GARevFun.FUN="UrGtRstInf";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;	
	
	if (CardSystem==null) {
		l_iRetCode=4001;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
	if (UID==null) {
		l_iRetCode=4002;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
	if (UrRevFUN==null) {
		l_iRetCode=4004;
		l_vRMsg=rMsg(l_iRetCode,"");	
		alert("輸入參數錯誤或不足");
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=4003;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrGtRstInf,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}//function UsrGtRstInfo(UID,RevFun)  
//41
function UrGtRstInf(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt!=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=4100;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				l_vRetData='{"RCode":"' + l_iRetCode + '", "data":[' + JSON.stringify(ret.data) +'],"RMsg":"'+ l_vRMsg +'"}';
			} else {
				l_iRetCode=4101;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
		l_iRetCode=4100;
		l_vRMsg=rMsg(l_iRetCode,"(Response null)");
	}//if (Gbjct.RspDt!=null) 
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	
	return l_vRetData;
}//function UrGtRstInf(UrRevFUN)
//43
function UsrRstInfo(CardSystem,UID,ServerRetData,UrRevFUN)
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	InToken.FUN="resetUserPIN";
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	InToken.KpMsg=ServerRetData;
	GARevFun.FUN="UrRstInfo";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;	
	
	if (CardSystem==null) {
		l_iRetCode=4300;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (CardSystem==null)
		
	if (UID==null) {
		l_iRetCode=4301;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
		
	if (ServerRetData==null) {
		l_iRetCode=4302;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (ServerRetData==null)
		
	if (UrRevFUN==null) {
		l_iRetCode=4304;
		l_vRMsg=rMsg(l_iRetCode,"");
		alert("輸入參數錯誤或不足");
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=4303;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrRstInfo,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//function UsrRstInfo(CardSystem,UID,ServerRetData,UrRevFUN)
//44
function UrRstInfo(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt!=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=4400;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';	
			} else {
				l_iRetCode=4401;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
		l_iRetCode=4400;
		l_vRMsg=rMsg(l_iRetCode,"(Response null)");		
	}//if (Gbjct.RspDt!=null) 
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();	
	
	return l_vRetData;
}//function UrRstInfo(UrRevFUN)
//100
function WriteCert(WrtCrtType,WrtCrtValue,WrtCrtUseChallenge,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="writecert";
	GARevFun.FUN="WrtCrt";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (isNull(WrtCrtValue)||isNull(WrtCrtValue)||isNull(WrtCrtUseChallenge))
	{
		l_iRetCode=9300;
		l_vRMsg=rMsg(l_iRetCode,"");
	}
	if (UrRevFUN==null) 
	{
		l_iRetCode=9301;
		l_vRMsg=rMsg(l_iRetCode,"");					
	}//if (UrRevFUN==null) 	
		
	if (checkGoodDay()==false) {
		l_iRetCode=9303;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
	
	if (l_iRetCode==0) {
		InToken.WrtCrtType=WrtCrtType;
		InToken.WrtCrtValue=WrtCrtValue;
		InToken.WrtCrtUseChallenge=WrtCrtUseChallenge;
		SendData("http://localhost:61161/writecert","http://localhost:61161/ChtPopupForm",
					"寫憑證中", Gbjct.PppFrmStyle,WrtCrt,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)

//101
function WrtCrt(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;

	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
		} else {
			l_iRetCode=9302;
			l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)

	InToken.WrtCrtType="";
	InToken.WrtCrtValue="";

	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function WrtCrt(UrRevFUN)
//yun嘗試加入取challenge
function GetChallenge(UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="getChallenge";
	GARevFun.FUN="GtChllng";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;
	if (UrRevFUN==null) 
	{
		l_iRetCode=9301;
		l_vRMsg=rMsg(l_iRetCode,"");					
	}//if (UrRevFUN==null) 	
		
	if (checkGoodDay()==false) {
		l_iRetCode=9303;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
	
	if (l_iRetCode==0) {
		//InToken.pkcs11=5;
		//InToken.isTest=false;
		SendData("http://localhost:61161/getChallenge","http://localhost:61161/ChtPopupForm",
					"取challenge中", Gbjct.PppFrmStyle,GtChllng,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}

function GtChllng(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_Challenge="";
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_Challenge = ret.challenge;
		} else {
			l_iRetCode=9302;
			l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	
	l_vRetData='{"RCode":"' + l_iRetCode + '", "Challenge":"' + l_Challenge +'","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}

ICToken.prototype.getChallenge = function (RevFun) {	
	GetChallenge(RevFun);	
}
//102
function MakeCsr(keyid,HashAlg,pin,challenge,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	var l_CSR="";
	
	l_vRMsg="";	
	InToken.FUN="makeCsr";
	GARevFun.FUN="MkCsr";
	GARevFun.Para=HashAlg.toUpperCase();
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;				
	if ((keyid==null)||(keyid=="")||(HashAlg==null)||(HashAlg=="")||(pin==null)||(pin==""))
	{
		l_iRetCode=9600;
		l_vRMsg=rMsg(l_iRetCode,"");
	}
	if (UrRevFUN==null) 
	{
		l_iRetCode=9601;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		InToken.keyid=keyid;
		InToken.HashAlgorithm=HashAlg;		
		InToken.PIN=pin;		
		InToken.challenge=challenge;
		SendData("http://localhost:61161/makeCsr","http://localhost:61161/ChtPopupForm",
					"作業中", Gbjct.PppFrmStyle,MkCsr,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//103
function MkCsr(UrRevFUN)
{	
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_CSR="";
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_CSR=ret.csr;
		} else {
			l_iRetCode=9602;
			l_vRMsg=rMsg(l_iRetCode,ret.messagedata+"("+ret.ret_code+"--"+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.keyid="";
	InToken.HashAlgorithm="";	
	InToken.PIN="";	
	InToken.challenge="";
	
	
	l_vRetData='{"RCode":"' + l_iRetCode + '", "CSR":"' + l_CSR +'","RMsg":"'+ l_vRMsg +'"}';
	return l_vRetData;
}//function MkCsr(UrRevFUN)
//End 
//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------
// PrintCard Function 
//-------------------------------------------------------------------------------------------------------------------
//200
function PrintImageb64toDefaultPrinter(cardPrinterName,paperSizes,imageb64string,uuid,filename,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="printImageb64toDefaultPrinter";
	GARevFun.FUN="PrtImgb64toDfltPrtr";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if ((cardPrinterName==null)||(cardPrinterName=="")||(paperSizes==null)||(paperSizes=="")||(imageb64string==null)||(imageb64string=="")||(uuid==null)||(uuid=="")|| (filename==null)||(filename==""))
	{
		l_iRetCode=9400;
		l_vRMsg=rMsg(l_iRetCode,"");
	}//if ((cardPrinterName=="")||(paperSizes=="")||(cardPrinterName==null)||(paperSizes==null))
	if (UrRevFUN==null) 
	{
		l_iRetCode=9401;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		InToken.cardPrinterName=cardPrinterName;
		InToken.paperSizes=paperSizes;		
		InToken.imageb64string=imageb64string;
		InToken.uuid=uuid;		
		InToken.filename=filename;
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintImageb64toPrinter","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,PrtImgb64toDfltPrtr,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//201
function PrtImgb64toDfltPrtr(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9402;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.cardPrinterName="";
	InToken.paperSizes="";	
	InToken.imageb64string="";
	InToken.uuid="";	
	InToken.filename="";
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function WrtCrt(UrRevFUN)
//202
function PrintImageb64toSelectedPrinter(printerName,cardPrinterName,paperSizes,imageb64string,uuid,filename,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="printImageb64toSelectedPrinter";
	GARevFun.FUN="PrtImgb64toSelctPrtr";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if ((printerName==null)||(printerName=="")||(cardPrinterName==null)||(cardPrinterName=="")||(paperSizes==null)||(paperSizes=="")||(imageb64string==null)||(imageb64string=="")||(uuid==null)||(uuid=="")|| (filename==null)||(filename==""))
	{
		l_iRetCode=9410;
		l_vRMsg=rMsg(l_iRetCode,"");
	}//if ((cardPrinterName=="")||(paperSizes=="")||(cardPrinterName==null)||(paperSizes==null))
	if (UrRevFUN==null) 
	{
		l_iRetCode=9411;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		InToken.printerName=printerName;
		InToken.cardPrinterName=cardPrinterName;
		InToken.paperSizes=paperSizes;		
		InToken.imageb64string=imageb64string;
		InToken.uuid=uuid;		
		InToken.filename=filename;
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintImageb64toPrinter","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,PrtImgb64toSelctPrtr,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//203
function PrtImgb64toSelctPrtr(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9412;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.printerName="";
	InToken.cardPrinterName="";
	InToken.paperSizes="";	
	InToken.imageb64string="";
	InToken.uuid="";	
	InToken.filename="";
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function WrtCrt(UrRevFUN)
//204
function PrintImageb64toCardPrinter(cardPrinterName,paperSizes,imageb64string,uuid,filename,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="printImageb64toCardPrinter";
	GARevFun.FUN="PrtImgb64toCrdPrtr";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if ((cardPrinterName==null)||(cardPrinterName=="")||(paperSizes==null)||(paperSizes=="")||(imageb64string==null)||(imageb64string=="")||(uuid==null)||(uuid=="")|| (filename==null)||(filename==""))
	{
		l_iRetCode=9420;
		l_vRMsg=rMsg(l_iRetCode,"");
	}//if ((cardPrinterName=="")||(paperSizes=="")||(cardPrinterName==null)||(paperSizes==null))
	if (UrRevFUN==null) 
	{
		l_iRetCode=9421;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		InToken.cardPrinterName=cardPrinterName;
		InToken.paperSizes=paperSizes;		
		InToken.imageb64string=imageb64string;
		InToken.uuid=uuid;		
		InToken.filename=filename;
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintImageb64toCardPrinter","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,PrtImgb64toCrdPrtr,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//205
function PrtImgb64toCrdPrtr(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9422;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.cardPrinterName="";
	InToken.paperSizes="";	
	InToken.imageb64string="";
	InToken.uuid="";	
	InToken.filename="";
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function WrtCrt(UrRevFUN)
//206
function ContactEncoderPosition(UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="ContactEncoderPosition";
	GARevFun.FUN="CntctEncdrPstn";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (UrRevFUN==null) 
	{
		l_iRetCode=9501;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintCardCtrl","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,CntctEncdrPstn,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//207
function CntctEncdrPstn(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9502;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function WrtCrt(UrRevFUN)
//208
function ContactlessEncoderPosition(UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="ContactlessEncoderPosition";
	GARevFun.FUN="CntctlessEncdrPstn";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (UrRevFUN==null) 
	{
		l_iRetCode=9511;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintCardCtrl","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,CntctlessEncdrPstn,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//209
function CntctlessEncdrPstn(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9512;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function WrtCrt(UrRevFUN)
//210
function RejectPosition(UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="RejectPosition";
	GARevFun.FUN="RjtPstn";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (UrRevFUN==null) 
	{
		l_iRetCode=9521;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintCardCtrl","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,RjtPstn,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//211
function RjtPstn(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9522;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}//function RjtPstn(UrRevFUN)
//212
function PrintPosition(UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="PrintPosition";
	GARevFun.FUN="PrtPstn";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (UrRevFUN==null) 
	{
		l_iRetCode=9531;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintCardCtrl","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,PrtPstn,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//213
function PrtPstn(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9532;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	return l_vRetData;
}
//214
function PrinterINFO(UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="PrinterINFO";
	GARevFun.FUN="PrtrINFO";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (UrRevFUN==null) 
	{
		l_iRetCode=9541;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/PrintCard/sendCommand_PrintCardCtrl","http://localhost:61161/PrintCard/ChtPopupFormPrintCard",
					"作業中", Gbjct.PppFrmStyle,PrtrINFO,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//215
function PrtrINFO(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
		} else {
			l_iRetCode=9542;
			l_vRMsg=rMsg(l_iRetCode,Gbjct.RspDt.replace(/"/g, "\\\""));
			l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}
//216
function ExDynamicCreateFile(exDynamicCreateFileInput,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="exDynamicCreateFile";
	GARevFun.FUN="exDynmCrtFileInCrd";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (exDynamicCreateFileInput==null)
	{
		l_iRetCode=9700;
		l_vRMsg=rMsg(l_iRetCode,"");
	}
	if (UrRevFUN==null) 
	{
		l_iRetCode=9701;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		InToken.exDynamicCreateFileInput=exDynamicCreateFileInput;
		SendData("http://localhost:61161/MOEACA/sendCommand_exDynamicCreateFile","http://localhost:61161/MOEACA/ChtPopupFormMOEACA",
					"作業中", Gbjct.PppFrmStyle,exDynmCrtFileInCrd,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//217
function exDynmCrtFileInCrd(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,ret.msg);
		} else {
			l_iRetCode=9702;
			l_vRMsg=rMsg(l_iRetCode,ret.msg);
			//l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.exDynamicCreateFileInput="";
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}
//218
function ExDynamicReleaseFile(exDynamicReleaseFileInput,UrRevFUN)	
{	
	var l_iRetCode=0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	
	l_vRMsg="";	
	InToken.FUN="exDynamicReleaseFile";
	GARevFun.FUN="exDynmRelsFileInCrd";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;		
	if (exDynamicReleaseFileInput==null)
	{
		l_iRetCode=9710;
		l_vRMsg=rMsg(l_iRetCode,"");
	}
	if (UrRevFUN==null) 
	{
		l_iRetCode=9711;
		l_vRMsg=rMsg(l_iRetCode,"");			
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null) 	
			
	if (l_iRetCode==0) {
		InToken.exDynamicReleaseFileInput=exDynamicReleaseFileInput;
		SendData("http://localhost:61161/MOEACA/sendCommand_exDynamicReleaseFile","http://localhost:61161/MOEACA/ChtPopupFormMOEACA",
					"作業中", Gbjct.PppFrmStyle,exDynmRelsFileInCrd,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN)
//219
function exDynmRelsFileInCrd(UrRevFUN)
{
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	var ret=JSON.parse(Gbjct.RspDt);
	if(ret.ret_code==0x76000031)
	{
		l_iRetCode=3700;
		l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
		l_iRetCode=ret.ret_code;
	} else {
		if (ret.ret_code==0) {
			l_iRetCode=0;
			l_vRMsg=rMsg(l_iRetCode,ret.msg);
		} else {
			l_iRetCode=9712;
			l_vRMsg=rMsg(l_iRetCode,ret.msg);
			//l_iRetCode=ret.ret_code;
		}//if (ret.ret_code==0)
	}//if(ret.ret_code==0x76000031)
		
	InToken.exDynamicReleaseFileInput="";
	
	l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}
//End 
//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------
// ChtICTokenGXCA、ChtICToken Function 
//-------------------------------------------------------------------------------------------------------------------
//45 unblock PIN
function UsrGtUBlkInfo(CardSystem,UID,NewPIN,UrRevFUN)
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	InToken.FUN="bulidUnblockCardRequest";
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	InToken.NewPIN=NewPIN;
	GARevFun.FUN="UrGtUBlkInfo";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;	
	
	if (CardSystem==null) {
		l_iRetCode=4500;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (CardSystem==null)
		
	if (UID==null) {
		l_iRetCode=4501;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
		
	if (NewPIN==null) {
		l_iRetCode=4502;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (NewPIN==null)	
	
	if (UrRevFUN==null) {
		l_iRetCode=4504;
		l_vRMsg=rMsg(l_iRetCode,"");
		alert("輸入參數錯誤或不足");
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=4503;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrGtUBlkInfo,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}//function UsrGtUBlkInfo(CardSystem,UID,NewPIN,RevFun)
//46
function UrGtUBlkInfo(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt!=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=4600;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				l_vRetData='{"RCode":"' + l_iRetCode + '", "data":[' + JSON.stringify(ret.data) +'],"RMsg":"'+ l_vRMsg +'"}';
			} else {
				l_iRetCode=4601;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
		l_iRetCode=4600;
		l_vRMsg=rMsg(l_iRetCode,"(Response null)");		
	}//if (Gbjct.RspDt!=null)
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	InToken.NewPIN="";	
	
	return l_vRetData;
}
//47
function UsrRstUBlkInfo(CardSystem,UID,ServerRetData,UrRevFUN)
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	InToken.FUN="unblockCard";
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	InToken.KpMsg=ServerRetData;
	GARevFun.FUN="UrRstUBlkInfo";
	GARevFun.Para="";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN=UrRevFUN;	
	if (CardSystem==null) {
		l_iRetCode=4700;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (CardSystem==null)
		
	if (UID==null) {
		l_iRetCode=4701;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
	if (ServerRetData==null) {
		l_iRetCode=4702;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (ServerRetData==null)
	if (UrRevFUN==null) {
		l_iRetCode=4703;
		l_vRMsg=rMsg(l_iRetCode,"");
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=4704;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrRstUBlkInfo,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}//UsrRstUBlkInfo(CardSystem,UID,ServerRetData,UrRevFUN)
//48
function UrRstUBlkInfo(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt !=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=4800;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';	
			} else {
				l_iRetCode=4801;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
		l_iRetCode=4800;
		l_vRMsg=rMsg(l_iRetCode,"(Response null)");		
	}//if (Gbjct.RspDt !=null) 
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();	
	
	return l_vRetData;
}//function UsrRstUBlkInfo(CardSystem,UID,ServerRetData,UrRevFUN)
//End 
//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------
//ChtICTokenGXCA Function
//-------------------------------------------------------------------------------------------------------------------
//49 OpenCard
function UsrGtPnCrdInfo(CardSystem,UID,NewPIN,UrRevFUN)
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	InToken.FUN="buildOpenCardGetUserPINRequest";
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	InToken.NewPIN=NewPIN;
	GARevFun.FUN="UrGtPnCrdInfo";
	GARevFun.Para="";
	//GARevFun.PopTout=1800000;
	GARevFun.UrFUN=UrRevFUN;	
	
	if (CardSystem==null) {
		l_iRetCode=4900;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (CardSystem==null)
		
	if (UID==null) {
		l_iRetCode=4901;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
		
	if (NewPIN==null) {
		l_iRetCode=4902;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (NewPIN==null)	
	
	if (UrRevFUN==null) {
		l_iRetCode=4903;
		l_vRMsg=rMsg(l_iRetCode,"");
		alert("輸入參數錯誤或不足");
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=4904;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrGtPnCrdInfo,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)	
}//function UsrGtPnCrdInfo(CardSystem,UID,NewPIN,RevFun)
//90
function UrGtPnCrdInfo(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt !=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=9000;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				l_vRetData='{"RCode":"' + l_iRetCode + '", "data":[' + JSON.stringify(ret.data) +'],"RMsg":"'+ l_vRMsg +'"}';
			} else {
				l_iRetCode=9001;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
			l_iRetCode=9000;
			l_vRMsg=rMsg(l_iRetCode,"(Response null)");		
	}//if (Gbjct.RspDt !=null) 
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	InToken.OldPIN="";	
	
	return l_vRetData;
}//function UrGtPnCrdInfo(CardSystem,UID,ServerRetData,UrRevFUN)
//91
function UsrPnCrdInfo(CardSystem,UID,ServerRetData,UrRevFUN,Step)
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	if (Step==1) {
		InToken.FUN="buildOpenCardValidateUserRequest";		
	} else {
		InToken.FUN="openCard";
	}//if (Step==1)
	
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	InToken.KpMsg=ServerRetData;
	GARevFun.FUN="UrPnCrdInfo";
	GARevFun.Para="";
	//GARevFun.PopTout=1800000;
	GARevFun.UrFUN=UrRevFUN;	
	if (CardSystem==null) {
		l_iRetCode=9100;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (CardSystem==null)
		
	if (UID==null) {
		l_iRetCode=9101;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
	if (ServerRetData==null) {
		l_iRetCode=9102;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (ServerRetData==null)
	if (UrRevFUN==null) {
		l_iRetCode=9103;
		l_vRMsg=rMsg(l_iRetCode,"");
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=9104;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrPnCrdInfo,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();		
	}//if (l_iRetCode==0)			
}
//92
function UrPnCrdInfo(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt !=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=9200;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				if (ret.data==null) {
					l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';						
				} else {
					l_vRetData='{"RCode":"' + l_iRetCode + '", "data":[' + JSON.stringify(ret.data) +'],"RMsg":"'+ l_vRMsg +'"}';
				}				
			} else {
				l_iRetCode=9201;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
		l_iRetCode=9200;
		l_vRMsg=rMsg(l_iRetCode,"(Response null)");		
	}//if (Gbjct.RspDt !=null)
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();	
	
	return l_vRetData;
}//function UrPnCrdInfo(CardSystem,UID,ServerRetData,UrRevFUN)
//93
function UsrPnCrdInfo(CardSystem,UID,ServerRetData,UrRevFUN,Step)
{
	var l_iRetCode=0;
	var l_CertInfo="";
	var l_vExp;
	var l_vRetData;
	var l_vTemp;
	var l_vRMsg;
	
	l_vRMsg="";
	if (Step==1) {
		InToken.FUN="buildOpenCardValidateUserRequest";		
	} else {
		InToken.FUN="openCard";
	}//if (Step==1)
	
	InToken.UID=UID;
	InToken.CrdSys=CardSystem.toUpperCase();
	InToken.KpMsg=ServerRetData;
	GARevFun.FUN="UrPnCrdInfo";
	GARevFun.Para="";
	//GARevFun.PopTout=1800000;
	GARevFun.UrFUN=UrRevFUN;	
	if (CardSystem==null) {
		l_iRetCode=9100;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (CardSystem==null)
		
	if (UID==null) {
		l_iRetCode=9101;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (UID==null)
	if (ServerRetData==null) {
		l_iRetCode=9102;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (ServerRetData==null)
	if (UrRevFUN==null) {
		l_iRetCode=9103;
		l_vRMsg=rMsg(l_iRetCode,"");
		alert("輸入參數錯誤或不足");		
	}//if (UrRevFUN==null)
		
	if (checkGoodDay()==false) {
		l_iRetCode=9104;
		l_vRMsg=rMsg(l_iRetCode,"");		
	}//if (checkGoodDay()==false)
		
	if (l_iRetCode==0) {
		SendData("http://localhost:61161/CardManagement/sendCardCommand","http://localhost:61161/CardManagement/CMCPopForm"
				,"IC卡讀取中",Gbjct.PppFrmStyle,UrPnCrdInfo,UrRevFUN);
	} else {
		l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg + '"}';
		InToken.RetObj=JSON.parse(l_vRetData);
		if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();		
	}//if (l_iRetCode==0)			
}
//94
function UrPnCrdInfo(UrRevFUN)
{
	var l_vExp;
	var l_iRetCode;
	var l_CertInfo;
	var l_vRMsg;	
	var l_vRetData;
	
	l_iRetCode=0;	
	l_CertInfo=null;
	l_vRMsg="";
	
	if (Gbjct.RspDt !=null) {
		var ret=JSON.parse(Gbjct.RspDt);
		if(ret.ret_code==0x76000031)
		{
			l_iRetCode=9200;
			l_vRMsg=rMsg(l_iRetCode,"("+l_iRetCode+")");
			l_iRetCode=ret.ret_code;
		} else {
			if (ret.ret_code==0) {
				if (ret.data==null) {
					l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';						
				} else {
					l_vRetData='{"RCode":"' + l_iRetCode + '", "data":[' + JSON.stringify(ret.data) +'],"RMsg":"'+ l_vRMsg +'"}';
				}				
			} else {
				l_iRetCode=9201;
				l_vRMsg=rMsg(l_iRetCode,ret.message+"("+ret.ret_code+"--"+l_iRetCode+")");
				l_iRetCode=ret.ret_code;
			}//if (ret.ret_code==0)
		}//if(ret.ret_code==0x76000031)			
	} else {
		l_iRetCode=9200;
		l_vRMsg=rMsg(l_iRetCode,"(Response null)");		
	}//if (Gbjct.RspDt !=null)
		
	if (l_iRetCode!=0) {
		l_vRetData='{"RCode":"' + l_iRetCode + '","RMsg":"'+ l_vRMsg +'"}';		
	}//if (l_iRetCode!=0)
		
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();	
	
	return l_vRetData;
}//function UrPnCrdInfo(CardSystem,UID,ServerRetData,UrRevFUN)
//End 
//-------------------------------------------------------------------------------------------------------------------
//80
function CkBType(){
    var retCode = "Chrome"; // Return IE or Chrome
    if (navigator.appName == 'Microsoft Internet Explorer'){
       var l_RegExp;
	   l_RegExp= new RegExp("MSIE ([0-9]{1,}[\\.0-9]{0,})");
       if (l_RegExp.exec(navigator.userAgent) !== null) retCode = "IE";    }
    else if(navigator.appName == "Netscape"){
       if(navigator.appVersion.indexOf('Edge') >= 0)
	   {
		   retCode = "Edge";
	   } else if (navigator.appVersion.indexOf('rv:11') >= 0)
	   {
		   retCode = "IE";
	   }//if(navigator.appVersion.indexOf('Edge') >= 0)
    }//if (navigator.appName == 'Microsoft Internet Explorer')
    return retCode;          
}//function CkBType()
//81
function setVNum(){	
    if (navigator.appVersion.indexOf("Win") >=0){
        Gbjct.WbSckVrsn = "1.3.4.103305";
    } else if(navigator.appVersion.indexOf("Mac")>=0){
		Gbjct.WbSckVrsn="1.3.4.10";
	} else if (navigator.appVersion.indexOf("Linux") >= 0) {
		Gbjct.WbSckVrsn="1.3.4.3";
    }//if (navigator.appVersion.indexOf("Win") >=0)
	
	return Gbjct.WbSckVrsn;
}//function setVNum()
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//ICToken
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//50
function ICToken() {
	this.SmrtCrdID=[];
    this.SlotID =[];
	this.SlotName =[];
	
	this.ActvSltID=[];
	
	this.OldPIN="";	
	this.NewPIN="";
	this.UID="";
	this.CrdSys="";	
	
	this.KpMsg="";
	
	this.exDynamicCreateFileInput="";
	this.exDynamicReleaseFileInput="";	
	
	this.TBS="TBS";
	this.TbsEncoding="base64";
	this.HashAlgorithm="SAH256";
	this.PIN="";
	this.FUN="MakeSignature";
	this.SType="PKCS1";
	this.type="pki";
	this.RetObj=JSON.parse('{"RCode":"' + 5000 +'","RMsg":"'+ rMsg(5000,"") +'"}');
}//function ICToken()
//51
function getICToken(){
	if (InToken==null) InToken = new ICToken();
	return InToken;
}//function ICToken()
//52
ICToken.prototype.clean = function(){
	InToken.SmrtCrdID=[];
    InToken.SlotID =[];
	InToken.SlotName =[];
	InToken.ActvSltID=[];
	InToken.OldPIN="";
	InToken.NewPIN="";
	InToken.UID="";
	InToken.CrdSys="";
	InToken.KpMsg="";
	InToken.exDynamicCreateFileInput="";
	InToken.exDynamicReleaseFileInput="";
	InToken.TBS="TBS";
	InToken.TbsEncoding="base64";
	InToken.HashAlgorithm="SAH256";
	InToken.PIN="";
	InToken.FUN="MakeSignature";
	InToken.SType="PKCS1";
	//eid part4 begin 
	InToken.can = "";
	InToken.mrz = "";
	InToken.pin1 = "";
	InToken.dvCert = "";
	InToken.atCert = "";
	InToken.ifdName=[];
	InToken.queryDG= "";
	InToken.signedData= "";
	InToken.handle= "";
	InToken.changePINType= "";
	InToken.unblockType= "";
	InToken.cardNumber= "";
	InToken.oldPIN= "";
	InToken.newPIN= "";
	InToken.unblockCardResponse= "";
	//eid part4 end
	InToken.RetObj=JSON.parse('{"RCode":"' + 5200 +'","RMsg":"'+ rMsg(5200,"") +'"}');	
}//ICToken.prototype.clean
//53 return
//object.RCode
//object.RMsg
ICToken.prototype.goodDay = function(RevFun, IsPrintCard) { //if it works for printing card (CMC component), no need to check IC Card Reader in "checkEnv" function
	if (typeof (IsPrintCard) === "undefined") IsPrintCard=false;
	if (InToken==null) InToken = new ICToken();
	if (Gbjct!=null) Gbjct=null;
	Gbjct=new INObject();
	if (GARevFun!=null) GARevFun=null;
	GARevFun=new ARevFun();	
	InToken.clean();
	checkEnv(RevFun, IsPrintCard);
}//ICToken.prototype.goodDay
//54 staleness
ICToken.prototype.close = function () {
	InToken=null;
	Gbjct=null;
	GARevFun=null;
    return true;
}//ICToken.prototype.close
//55 retrun
//object.RCode
//object.CardID
//object.RMsg
ICToken.prototype.getSmartCardID = function (RevFun) {
	var l_vCardID;
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	
	l_iRetCode=0;
	if (checkGoodDay()==false) {
		l_iRetCode=5500;
		l_vRMsg=rMsg(l_iRetCode,"");		
	} else {
		l_vRetData=checkCondition();
		if (l_vRetData.RCode==0) {
			if ((InToken.ActvSltID[0]<InToken.SlotID.length)&&(InToken.ActvSltID[0]>=0)) {
				l_vCardID=InToken.SmrtCrdID[InToken.ActvSltID[0]];
			}//if (InToken.ActvSltID<=InToken.SlotID.length)
		} else {
			l_iRetCode=l_vRetData.RCode;
			l_vRMsg=l_vRetData.RMsg;
		}//if (l_vRetData.RCode==0)			
	}//if (checkGoodDay()==false)
		
	l_vRetData='{"RCode":"' + l_iRetCode + '", "CardID":"' + l_vCardID +'","RMsg":"'+ l_vRMsg +'"}';	
	InToken.RetObj=JSON.parse(l_vRetData);
	if (RevFun && typeof(RevFun) === "function") RevFun();
}//ICToken.prototype.getSmartCardID
//56
ICToken.prototype.countSlotID = function () {
	return InToken.SlotID.length
}//ICToken.prototype.countSlotID
//57
ICToken.prototype.getSlotName = function (SlotIDNum) {
	var RetData;
	
	RetData="";
	if ((SlotIDNum<InToken.SlotID.length)&&(SlotIDNum>=0)) {
		RetData=InToken.SlotID[SlotIDNum];
	}//if (SlotIDNum<=InToken.SlotID.length)
	
	return RetData;
}//ICToken.prototype.getSlotNumber
//58 if there are more then one smart card, you must set this function first.
//return 
//object.RCode
//object.RMsg
ICToken.prototype.setActiveSlotID = function (SlotIDNum) {
	var l_iRetCode;
	var l_vRMsg;
	var l_vRetData;
	
	l_iRetCode=0;
	l_vRMsg="";
	if ((SlotIDNum<InToken.SlotID.length)&&(SlotIDNum>=0)) {
		InToken.ActvSltID[0]=SlotIDNum;
	} else {
		l_iRetCode=5800;
		l_vRMsg=rMsg(l_iRetCode,"");
	}//if (SlotIDNum<=InToken.SlotID.length)
	
	l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg +'"}';
	
    return JSON.parse(l_vRetData);
}//ICToken.prototype.setActiveSlotID
//59 retrun
//object.RCode
//object.Certificate
//object.RMsg
ICToken.prototype.getB64Certificate = function (index,RevFun,keyidb64) {
	if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";
	getCert(index,RevFun,keyidb64);
}//ICToken.prototype.getB64Certificate = function (index)
//60
ICToken.prototype.getActiveSlotID = function () {
	return this.ActvSltID[0];
}//ICToken.prototype.readSmartCardID
//61 retrun
//object.RCode
//object.B64Signature
//object.RMsg
ICToken.prototype.sign = function (B64ToBeSign,UserPIN,HashAlg,RevFun,PKCSSignType,keyidb64,nonce,withSigningTime,withCardSN,checkValidity) {
    if (typeof (PKCSSignType) === "undefined") PKCSSignType="PKCS1";	
    if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";	
    if (typeof (nonce) === "undefined") nonce="__NotExistedTag__";	
    if (typeof (withSigningTime) === "undefined") withSigningTime="__NotExistedTag__";	
    if (typeof (withCardSN) === "undefined") withCardSN="__NotExistedTag__";	
    if (typeof (checkValidity) === "undefined") checkValidity="__NotExistedTag__";	
	UserSign(B64ToBeSign,encodeURIComponent(UserPIN),HashAlg,RevFun,PKCSSignType,keyidb64,encodeURIComponent(nonce),encodeURIComponent(withSigningTime),encodeURIComponent(withCardSN),encodeURIComponent(checkValidity),"none"); //B64ToBeSign也應encodeURIComponent才對?	
}
//62 retrun
//object.RCode
//object.RMsg
ICToken.prototype.changePIN = function (NewPIN,OldPIN,RevFun)
{
	UChngPin(encodeURIComponent(NewPIN),encodeURIComponent(OldPIN),RevFun);
}//ICToken.prototype.changePIN = function (NewPIN,OldPIN,RevFun)
//63 retrun
//object.RCode
//object.CertInfo
//object.RMsg
ICToken.prototype.parseCertificate = function (index,RevFun,keyidb64)
{
	if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";	
	UsrPrsCrt(index,RevFun,keyidb64);	
}//ICToken.prototype.parseCertificate = function (index,RevFun)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Reset User PIn
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//70 retrun
//object.RCode
//object.data[0]
//object.RMsg
ICToken.prototype.getRSInfo = function (CardSystem,UID,RevFun)
{
	UsrGtRstInfo(CardSystem,encodeURIComponent(UID),RevFun);	
}//ICToken.prototype.getRSInfo = function (index,RevFun)
//"http://10.144.133.146/GCMS/MOEACA/ResetUserPIN/ResetUserPIN.exe"
//71
ICToken.prototype.resetInfo= function (CardSystem,UID,ServerRetData,RevFun)
{
	UsrRstInfo(CardSystem,encodeURIComponent(UID),ServerRetData,RevFun);	
}//ICToken.prototype.resetInfo = function (index,RevFun)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//unbolck User PIn
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//72 retrun
//object.RCode
//object.data[0]
//object.RMsg
ICToken.prototype.getUBlkInfo = function (CardSystem,UID,NewPIN,RevFun)
{
	UsrGtUBlkInfo(CardSystem,encodeURIComponent(UID),encodeURIComponent(NewPIN),RevFun);	
}//ICToken.prototype.getUBlkInfo = function (CardSystem,UID,NewPIN,RevFun)
//73
ICToken.prototype.resetUBlkInfo= function (CardSystem,UID,ServerRetData,RevFun)
{
	UsrRstUBlkInfo(CardSystem,encodeURIComponent(UID),ServerRetData,RevFun);	
}//ICToken.prototype.resetUBlkInfo = function (index,RevFun)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//GCA\XCA Open Card
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//74 retrun
//object.RCode
//object.data[0]
//object.RMsg
ICToken.prototype.getPnCrdInfo = function (CardSystem,UID,PIN,RevFun)
{
	UsrGtPnCrdInfo(CardSystem,encodeURIComponent(UID),encodeURIComponent(PIN),RevFun);	
}//ICToken.prototype.getPnCrdInfo = function (CardSystem,UID,PIN,RevFun)
//75
ICToken.prototype.openCrdInfo= function (CardSystem,UID,ServerRetData,RevFun,Step)
{
	UsrPnCrdInfo(CardSystem,encodeURIComponent(UID),ServerRetData,RevFun,Step);	
}//ICToken.prototype.openCrdInfo= function (CardSystem,UID,ServerRetData,RevFun)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//CrypSign
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//76 retrun
//object.RCode
//object.B64Signature
//object.RMsg
ICToken.prototype.CrypSign = function (B64ToBeSign,CrypUserPIN,HashAlg,RevFun,PKCSSignType,keyidb64,nonce,withSigningTime,withCardSN,checkValidity) {   
    if (typeof (PKCSSignType) === "undefined") PKCSSignType="PKCS1";	
    if (typeof (keyidb64) === "undefined") keyidb64="__NotExistedTag__";	
    if (typeof (nonce) === "undefined") nonce="__NotExistedTag__";	
    if (typeof (withSigningTime) === "undefined") withSigningTime="__NotExistedTag__";	
    if (typeof (withCardSN) === "undefined") withCardSN="__NotExistedTag__";	
    if (typeof (checkValidity) === "undefined") checkValidity="__NotExistedTag__";	
	UserSign(B64ToBeSign,encodeURIComponent(CrypUserPIN),HashAlg,RevFun,PKCSSignType,keyidb64,encodeURIComponent(nonce),encodeURIComponent(withSigningTime),encodeURIComponent(withCardSN),encodeURIComponent(checkValidity),"Cryp"); //B64ToBeSign也應encodeURIComponent才對?
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//writeCert
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//77 retrun
//object.RCode
//object.RMsg
ICToken.prototype.writeCert = function (WrtCrtType,WrtCrtValue,WrtCrtUseChallenge,RevFun) {
	WriteCert(WrtCrtType,encodeURIComponent(WrtCrtValue),WrtCrtUseChallenge,RevFun);
}//ICToken.prototype.writeCert = function (WrtCrtType,WrtCrtValue,RevFun)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//makeCsr
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//78 retrun
//object.RCode
//object.RMsg
ICToken.prototype.makeCsr = function (keyid,HashAlg,pin,challenge,RevFun) {	
	MakeCsr(encodeURIComponent(keyid),encodeURIComponent(HashAlg),encodeURIComponent(pin),encodeURIComponent(challenge),RevFun);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//PrintCard
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//79 retrun
//object.RCode
//object.RMsg
ICToken.prototype.printImageb64toDefaultPrinter = function (cardPrinterName,paperSizes,imageb64string,uuid,filename,RevFun) {	
	PrintImageb64toDefaultPrinter(encodeURIComponent(cardPrinterName),encodeURIComponent(paperSizes),encodeURIComponent(imageb64string),encodeURIComponent(uuid),encodeURIComponent(filename),RevFun);
}
ICToken.prototype.printImageb64toSelectedPrinter = function (printerName,cardPrinterName,paperSizes,imageb64string,uuid,filename,RevFun) {	
	PrintImageb64toSelectedPrinter(encodeURIComponent(printerName),encodeURIComponent(cardPrinterName),encodeURIComponent(paperSizes),encodeURIComponent(imageb64string),encodeURIComponent(uuid),encodeURIComponent(filename),RevFun);
}
ICToken.prototype.printImageb64toCardPrinter = function (cardPrinterName,paperSizes,imageb64string,uuid,filename,RevFun) {	
	PrintImageb64toCardPrinter(encodeURIComponent(cardPrinterName),encodeURIComponent(paperSizes),encodeURIComponent(imageb64string),encodeURIComponent(uuid),encodeURIComponent(filename),RevFun);
}
ICToken.prototype.contactEncoderPosition = function (RevFun) {	
	ContactEncoderPosition(RevFun);
}
ICToken.prototype.contactlessEncoderPosition = function (RevFun) {	
	ContactlessEncoderPosition(RevFun);
}
ICToken.prototype.rejectPosition = function (RevFun) {	
	RejectPosition(RevFun);
}
ICToken.prototype.printPosition = function (RevFun) {	
	PrintPosition(RevFun);
}
ICToken.prototype.printerINFO = function (RevFun) {	
	PrinterINFO(RevFun);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//exDynamicCreateFile, exDynamicReleaseFile
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//80 retrun
//object.RCode
//object.RMsg
ICToken.prototype.exDynamicCreateFile = function (exDynamicCreateFileInput,RevFun) {	
	ExDynamicCreateFile(encodeURIComponent(exDynamicCreateFileInput),RevFun);
}
ICToken.prototype.exDynamicReleaseFile = function (exDynamicReleaseFileInput,RevFun) {	
	ExDynamicReleaseFile(encodeURIComponent(exDynamicReleaseFileInput),RevFun);
}
//eid part5 begin//
function GetEIDPublicData(can, mrz,cardNumber, dgData, UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "GetEIDPublicData";
	GARevFun.FUN = "GetEIDPublicDataRev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (!isNull(can))
	{//use can to read
		InToken.can=can;
	}else if (!isNull(cardNumber) )
	{//use mrz to read
		InToken.cardNumber=cardNumber;
	}else if (!isNull(mrz) )
	{//use cardNumber to read
		InToken.mrz=mrz;
	}else{
		l_iRetCode=9301;
		l_vRMsg=rMsg(l_iRetCode,"");
	}
	if (UrRevFUN == null || dgData == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)

	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)

	if (l_iRetCode == 0) {
		InToken.queryDG = dgData;
		SendData("http://localhost:61161/eIDPM/GetEIDPublicData", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"讀公開區中", Gbjct.PppFrmStyle, GetEIDPublicDataRev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}

function GetEIDPublicDataRev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_DG = "";
	var l_rawdata = "";
	var ret = JSON.parse(Gbjct.RspDt);
	console.log("ret", ret);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
			l_DG = { "dg11": ret.dg11, "dg12": ret.dg12, "dg2": ret.dg2 };
			l_rawdata = {
				"sod": ret.sod, "dg11Rawdata": ret.publicRawData.dg11Rawdata
				, "dg12Rawdata": ret.publicRawData.dg12Rawdata, "dg2Rawdata": ret.publicRawData.dg2Rawdata
			}
		} else {
			l_iRetCode = 8003;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
			//l_iRetCode = ret.result;
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	InToken.can = "";
	InToken.mrz = "";
	InToken.queryDG = "";
	l_vRetData = '{"RCode":"' + l_iRetCode + '", "DG":' + JSON.stringify(l_DG) + ',"eidRawdata":' + JSON.stringify(l_rawdata) + ',"RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}
function GetEIDHouseHoldData(UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "GetEIDHouseHoldData";
	GARevFun.FUN = "GetEIDHouseHoldDataRev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (UrRevFUN == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)
	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)
	if (l_iRetCode == 0) {
		SendData("http://localhost:61161/eIDPM/GetEIDHouseHoldData", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"讀戶籍區中", Gbjct.PppFrmStyle, GetEIDHouseHoldDataRev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}//if (l_iRetCode==0)
}

function GetEIDHouseHoldDataRev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_DG = "";
	var l_rawdata = "";
	var ret = JSON.parse(Gbjct.RspDt);
	console.log("ret", ret);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
			var l_DG = { "dg13": ret.dg13 };
			var l_rawdata = { "sod": ret.sod, "dg13Rawdata": ret.houseHoldRawData.dg13Rawdata }
		} else {
			l_iRetCode = 8002;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	l_vRetData = '{"RCode":"' + l_iRetCode + '", "DG":' + JSON.stringify(l_DG) + ',"eidRawdata":' + JSON.stringify(l_rawdata) + ',"RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}
function GetEIDPrivateDataStep1(pin1, dvCert, atCert, UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "GetEIDPrivateDataStep1";
	GARevFun.FUN = "GetEIDPrivateDataStep1Rev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (isNull(pin1)|| isNull(dvCert) || isNull(atCert)) {
		l_iRetCode = 9304;
		l_vRMsg = rMsg(l_iRetCode, "");
	}
	if (UrRevFUN == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)
	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)
	if (l_iRetCode == 0) {
		console.log(dvCert);
		console.log(atCert);
		InToken.pin1 = pin1;
		InToken.dvCert = dvCert;
		InToken.atCert = atCert;
		SendData("http://localhost:61161/eIDPM/GetEIDPrivateDataStep1", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"讀加密區步驟一", Gbjct.PppFrmStyle, GetEIDPrivateDataStep1Rev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}
}
function GetEIDPrivateDataStep1Rev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_handle = "";
	var l_challenge = "";
	var ret = JSON.parse(Gbjct.RspDt);
	console.log("ret", ret);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
			l_challenge = ret.challenge;
			l_handle = ret.handle;
		} else {
			l_iRetCode = 8004;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	InToken.pin1 = "";
	InToken.dvCert = "";
	InToken.atCert = "";
	l_vRetData = '{"RCode":"' + l_iRetCode + '", "Challenge":"' + l_challenge + '","Handle":"' + l_handle + '","RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	console.log("l_vRetData:" + l_vRetData);
	return l_vRetData;
}
function GetEIDPrivateDataStep2(queryDG, signedData, handle, UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "GetEIDPrivateDataStep2";
	GARevFun.FUN = "GetEIDPrivateDataStep2Rev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (isNull(queryDG)|| isNull(handle)||isNull(signedData)) {
		l_iRetCode = 9304;
		l_vRMsg = rMsg(l_iRetCode, "");
	}
	if (UrRevFUN == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)
	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)

	if (l_iRetCode == 0) {
		InToken.queryDG = queryDG;
		if (signedData == null)
			signedData = "";
		InToken.signedData = signedData;
		InToken.handle = handle;
		SendData("http://localhost:61161/eIDPM/GetEIDPrivateDataStep2", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"讀加密區步驟一", Gbjct.PppFrmStyle, GetEIDPrivateDataStep2Rev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}
}

function GetEIDPrivateDataStep2Rev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_DG = "";
	var l_rawdata = "";
	var ret = JSON.parse(Gbjct.RspDt);
	//console.log("ret", ret);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
			l_DG = { "dg2":ret.dg2,"dg3": ret.dg3, "dg4": ret.dg4, "dg6": ret.dg6, "dg7": ret.dg7, "dg8": ret.dg8, "dg9": ret.dg9, "dg5": ret.dg5 ,"dg11":ret.dg11,"dg12":ret.dg12,"dg13":ret.dg13};
			l_rawdata = {
				"sod": ret.sod, "dg3Rawdata": ret.privateRawData.dg3Rawdata, "dg4Rawdata": ret.privateRawData.dg4Rawdata, "dg5Rawdata": ret.privateRawData.dg5Rawdata, "dg6Rawdata": ret.privateRawData.dg6Rawdata
				, "dg7Rawdata": ret.privateRawData.dg7Rawdata, "dg8Rawdata": ret.privateRawData.dg8Rawdata, "dg9Rawdata": ret.privateRawData.dg9Rawdata 
			}
			if(typeof(ret.publicRawData)!=="undefined"){
				l_rawdata.dg2Rawdata = ret.publicRawData.dg2Rawdata;
				l_rawdata.dg11Rawdata = ret.publicRawData.dg11Rawdata;
				l_rawdata.dg12Rawdata = ret.publicRawData.dg12Rawdata;
			}
			if(typeof (ret.houseHoldRawData) !=="undefined"){
				l_rawdata.dg13Rawdata = ret.publicRawData.dg13Rawdata;
			}
		} else {
			l_iRetCode = 8005;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	InToken.pin1 = "";
	InToken.dvCert = "";
	InToken.atCert = "";
	l_vRetData = '{"RCode":"' + l_iRetCode + '", "DG":' + JSON.stringify(l_DG) + ',"eidRawdata":' + JSON.stringify(l_rawdata) + ',"RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	console.log("l_vRetData:" + l_vRetData);
	return l_vRetData;
}
function ChangePinEID(changePINType, cardNumber, oldPIN, newPIN, UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "ChangePinEID";
	GARevFun.FUN = "ChangePinEIDRev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (isNull(changePINType) || isNull(changePINType)|| isNull(oldPIN) || isNull(newPIN)) {
		l_iRetCode = 9304;
		l_vRMsg = rMsg(l_iRetCode, "");
	}
	if (UrRevFUN == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)
	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)
	if (l_iRetCode == 0) {
		InToken.changePINType = changePINType;
		InToken.cardNumber = cardNumber;
		InToken.oldPIN = oldPIN;
		InToken.newPIN = newPIN;

		SendData("http://localhost:61161/eIDPM/ChangePinEID", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"修改Pin碼", Gbjct.PppFrmStyle, ChangePinEIDRev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}
}
function ChangePinEIDRev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var ret = JSON.parse(Gbjct.RspDt);
	console.log("ret", ret);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
		} else {
			l_iRetCode = 8006;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	InToken.changePINType = "";
	InToken.cardNumber = "";
	InToken.oldPIN = "";
	InToken.newPIN = "";
	l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	console.log("l_vRetData:" + l_vRetData);
	return l_vRetData;
}
function BulidUnblockCardRequestEID(unblockType, cardNumber, newPIN, UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "BulidUnblockCardRequestEID";
	GARevFun.FUN = "BulidUnblockCardRequestEIDRev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (isNull(unblockType) || isNull(cardNumber) || isNull(newPIN)) {
		l_iRetCode = 9304;
		l_vRMsg = rMsg(l_iRetCode, "");
	}
	if (UrRevFUN == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)
	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)
	if (l_iRetCode == 0) {
		InToken.unblockType = unblockType;
		InToken.cardNumber = cardNumber;
		InToken.newPIN = newPIN;
		SendData("http://localhost:61161/eIDPM/BulidUnblockCardRequestEID", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"鎖卡解碼請求檔產製", Gbjct.PppFrmStyle, BulidUnblockCardRequestEIDRev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}
}
function BulidUnblockCardRequestEIDRev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var l_iUnblockCardRequest = "";
	var ret = JSON.parse(Gbjct.RspDt);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
			l_iUnblockCardRequest = ret.unblockCardRequest;
		} else {
			l_iRetCode = 8007;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	InToken.unblockType = "";
	InToken.cardNumber = "";
	InToken.newPIN = "";
	l_vRetData = '{"RCode":"' + l_iRetCode + '","unblockCardRequest":"' + l_iUnblockCardRequest + '","RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	console.log("l_vRetData:" + l_vRetData);
	return l_vRetData;
}
function UnblockCardEID(unblockType, cardNumber, newPIN, unblockCardResponse, UrRevFUN) {
	var l_iRetCode = 0;
	var l_vTemp;
	var l_vRMsg;
	var l_vRetData;
	l_vRMsg = "";
	InToken.FUN = "UnblockCardEID";
	GARevFun.FUN = "UnblockCardEIDRev";
	GARevFun.Para = "";
	//GARevFun.PopTout=900000;
	GARevFun.UrFUN = UrRevFUN;
	if (isNull(unblockType) || isNull(cardNumber) || isNull(newPIN) || isNull(unblockCardResponse)) {
		l_iRetCode = 9304;
		l_vRMsg = rMsg(l_iRetCode, "");
	}
	if (UrRevFUN == null) {
		l_iRetCode = 9301;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (UrRevFUN==null)
	if (checkGoodDay() == false) {
		l_iRetCode = 9303;
		l_vRMsg = rMsg(l_iRetCode, "");
	}//if (checkGoodDay()==false)
	if (l_iRetCode == 0) {
		InToken.unblockType = unblockType;
		InToken.cardNumber = cardNumber;
		InToken.newPIN = newPIN;
		InToken.unblockCardResponse = unblockCardResponse;
		SendData("http://localhost:61161/eIDPM/UnblockCardEID", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM",
			"鎖卡解碼中", Gbjct.PppFrmStyle, UnblockCardEIDRev, UrRevFUN);
	} else {
		l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
		InToken.RetObj = JSON.parse(l_vRetData);
		if (UrRevFUN && typeof (UrRevFUN) === "function") UrRevFUN();
	}
}

function UnblockCardEIDRev(UrRevFUN) {
	var l_vRetData;
	var l_iRetCode;
	var l_vRMsg;
	var ret = JSON.parse(Gbjct.RspDt);
	console.log("ret", ret);
	if (ret.result == 0x76000031) {
		l_iRetCode = 3700;
		l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
		l_iRetCode = ret.result;
	} else {
		if (ret.result == "API_OK") {
			l_iRetCode = 0;
		} else {
			l_iRetCode = 8008;
			l_vRMsg = rMsg(l_iRetCode, ret.result);
		}//if (ret.result==0)
	}//if(ret.result==0x76000031)
	InToken.unblockType = "";
	InToken.cardNumber = "";
	InToken.newPIN = "";
	InToken.unblockCardResponse = "";
	l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}

function isNull(input){
	if(input==null||input=="")
		return true;
	return false;
}
ICToken.prototype.getEIDPublicData = function (can,mrz,cardNumber,dgData,RevFun) {
	GetEIDPublicData(encodeURIComponent(can),encodeURIComponent(mrz),cardNumber,dgData,RevFun);
}
ICToken.prototype.getEIDHouseHoldData = function (RevFun) {
	GetEIDHouseHoldData(RevFun);
}
ICToken.prototype.getEIDPrivateDataStep1 = function (pin1, dvCert, atCert, RevFun) {
	GetEIDPrivateDataStep1(encodeURIComponent(pin1), encodeURIComponent(dvCert), encodeURIComponent(atCert), RevFun);
}
ICToken.prototype.getEIDPrivateDataStep2 = function (queryDG, signedData, handle, RevFun) {
	GetEIDPrivateDataStep2(queryDG, encodeURIComponent(signedData), encodeURIComponent(handle), RevFun);
}
ICToken.prototype.changePinEID = function (changePINType, cardNumber, oldPIN, newPIN, RevFun) {
	ChangePinEID(changePINType, cardNumber, oldPIN, newPIN, RevFun);
}
ICToken.prototype.bulidUnblockCardRequestEID = function (unblockType, cardNumber, newPIN, RevFun) {
	BulidUnblockCardRequestEID(unblockType, cardNumber, newPIN, RevFun);
}
ICToken.prototype.unblockCardEID = function (unblockType, cardNumber, newPIN, unblockCardResponse, RevFun) {
	UnblockCardEID(unblockType, cardNumber, newPIN, unblockCardResponse, RevFun);
}
//test eidGoodday
ICToken.prototype.EIDgoodDay = function (RevFun) { 
	if (InToken == null) InToken = new ICToken();
	if (Gbjct != null) Gbjct = null;
	Gbjct = new INObject();
	if (GARevFun != null) GARevFun = null;
	GARevFun = new ARevFun();
	InToken.clean();
	EIDcheckEnv(RevFun);
}//EIDToken.prototype.goodDay
function EIDcheckEnv(UrRevFUN) {

	InToken.FUN = "EIDCheckEnvir";
	GARevFun.FUN = "EIDstDa";
	GARevFun.Para = "";
	GARevFun.UrFUN = UrRevFUN;
	SendData("http://localhost:61161/eIDPM/ListEID", "http://localhost:61161/eIDPM/ChtPopupFormEIDPM", "初始中", Gbjct.PppFrmStyle, EIDstDa, UrRevFUN);
}
function EIDstDa(UrRevFUN) {
	var l_iRetCode = 0;
	var l_iSlotCounter = 0;
	var l_vRetData;
	var l_vRMsg;
	l_vRetData = "";
	l_vRMsg = "";

	if (Gbjct.RspDt == null) {
		l_iRetCode = 3100;
		l_vRMsg = rMsg(l_iRetCode, "");
	} else {
		//alert("Gbjct.RspDt："+Gbjct.RspDt);
		var ret = JSON.parse(Gbjct.RspDt);
		if (ret.result == 0x76000031) {
			l_iRetCode = 3101;
			l_vRMsg = rMsg(l_iRetCode, "(" + l_iRetCode + ")");
			l_iRetCode = ret.result;
		} else {
			if (ret.result == "API_OK") {
				var l_vTemp;

				var slots;
				//if (typeof ret.slots !== 'undefined')
				if (typeof ret.ifdName[0] !== 'undefined' && ret.ifdName[0] !== null) {
					slots = ret.ifdName;
					for (var index in slots) {
						InToken.SlotID[index] = slots[index];
						InToken.SlotName[slots[index].slotDescription] = index;
						//InToken.SmrtCrdID[index]=slots[index].token.serialNumber;
						InToken.ActvSltID[0] = index;
						l_iSlotCounter++;
					}//for(var index in slots)
					if (l_iSlotCounter == 0) {
						l_iRetCode = 3103;
						l_vRMsg = rMsg(l_iRetCode, "");
					} else {
						if (l_iSlotCounter > 1) {
							InToken.ActvSltID[0] = -1;
						}//if (l_iSlotCounter>1)
					}//if (l_iSlotCounter>1)
				} else {
					l_iRetCode = 3103;
					l_vRMsg = rMsg(l_iRetCode, "(Location 2)");
				}//if (slots.length!=0)
			} else {
				l_iRetCode = 8001;
				l_vRMsg = rMsg(l_iRetCode,ret.result);
			}//if (ret.result==0)
		}//if(ret.result==0x76000031)
	}//if (Gbjct.RspDt==null)
	l_vRetData = '{"RCode":"' + l_iRetCode + '","RMsg":"' + l_vRMsg + '"}';
	//InToken.RetObj=JSON.parse(l_vRetData);
	//if (UrRevFUN && typeof(UrRevFUN) === "function") UrRevFUN();
	return l_vRetData;
}
ICToken.prototype.setActiveSlotIDbyName = function (SlotIDName) {
	var l_iRetCode;
	var l_vRMsg;
	var l_vRetData;
	var l_find;
	
	l_iRetCode=0;
	l_vRMsg="";
	l_find=0;
	
	for(var index=0 ; index<InToken.SlotID.length ; index++){
		if(InToken.SlotID[index]==SlotIDName){
			InToken.ActvSltID[0]=index;
			l_find=1;
			break;
		}
	}
	if (!l_find) {
		l_iRetCode=5800;
		l_vRMsg=rMsg(l_iRetCode,"");
	}
	
	l_vRetData='{"RCode":"' + l_iRetCode +'","RMsg":"'+ l_vRMsg +'"}';
	
    return JSON.parse(l_vRetData);
}//ICToken.prototype.setActiveSlotIDbyName
function EidErrorReason(rcode){
	console.log("rcode:" +rcode);
	switch(rcode){
		//Notice use to Reenter
		case "API_INCORRECT_CAN":
			return "錯誤的CAN導致驗證失敗";
		case "API_INCORRECT_MRZ":
			return "錯誤的MRZ導致驗證失敗";
		case "API_INCORRECT_CARDNUM":
			return "錯誤的證件號碼導致驗證失敗";
		case "API_NO_CARD":
			return "讀卡機中沒有卡片";
		case "API_NO_SUPPORTED_CARD":
			return "讀卡機沒有接New eID卡片";		
		case "API_INCORRECT_PIN1_RC2":
			return "錯誤的PIN1導致驗證失敗(剩餘次數：2)";
		case "API_INCORRECT_PIN1_RC1":
			return "錯誤的PIN1導致驗證失敗(剩餘次數：1)";	
		case "API_PIN1_IS_BLOCKED":
			return "PIN1被鎖定";		
		case "API_INCORRECT_PUK_RC2":
			return "錯誤的PUK導致驗證失敗(剩餘次數：2)";
		case "API_INCORRECT_PUK_RC1":
			return "錯誤的PUK導致驗證失敗(剩餘次數：1)";	
		case "API_PUK_IS_BLOCKED":
			return "PUK被鎖定";		
		case "API_INCORRECT_ADM1_RC2":
			return "錯誤的ADM 1導致驗證失敗(剩餘次數：2)";
		case "API_INCORRECT_ADM1_RC1":
			return "錯誤的ADM 1導致驗證失敗(剩餘次數：1)";	
		case "API_ ADM 1_IS_BLOCKED":
			return "ADM 1被鎖定";	
		case "IFD_OK":
			return "API呼叫成功";
		case "IFD_NO_IFD_AVAILABLE":
			return "沒有讀卡機";
		case "IFD_UNKNOWN_IFD":
			return "錯誤的讀卡機名稱";
		case "IFD_TIMEOUT_ERROR":
			return "讀卡機請求超時";
		case "IFD_NO_CARD":
			return "讀卡機中沒有卡片";
		case "":
			return;
		//tun
		case "DOCUMENT_NUMBER_COMPARE_ERROR":
			return "卡號比對錯誤";			
		case "PIN1_INCORRECT_2":
			return "PIN1碼輸入錯誤，剩2次機會";
		case "PIN1_INCORRECT_1":
			return "PIN1碼輸入錯誤，剩1次機會";			
		case "PIN1_LOCKED":
			return "PIN1已鎖定";
		case "PIN2_INCORRECT_2":
			return "PIN2碼輸入錯誤，剩2次機會";			
		case "PIN2_INCORRECT_1":
			return "PIN2碼輸入錯誤，剩1次機會";			
		case "PIN2_LOCKED":
			return "PIN2已鎖定";	
		case "SOKEY_INCORRECT":
			return "SOPIN輸入錯誤";	
		case "SOKEY_LOCKED":
			return "SOPIN已鎖定";
		case "CKR_NO_SLOT_ERROR":
			return "P11找不到Slot";
		case "CKR_SLOT_HAS_NO_TOKEN":
			return "P11 Slot找不到卡號";
		case "CKR_READER_NOT_FOUND":
			return "P11找不到讀卡機名稱";
			
		//Update endUser's Software
		case "0x86000001":
			return "Addon載入DLL失敗";
		case "0x86000002":
			return "Addon取Function list失敗";
		case "CRYPTO_AES_ENCRYPT_ERROR":
			return "AES加密錯誤";
		case "CRYPTO_AES_DECRYPT_ERROR":
			return "AES解密錯誤";
		case "CRYPTO_AES_DECRYPT_VERIFY_ERROR":
			return "AES驗MAC錯誤";
		case "CRYPTO_BASIC_BUFFER_TOO_SMALL":
			return "配置空間過小";
		case "CRYPTO_RSA_ENCRYPT_ERROR":
			return "RSA加密錯誤";
		case "CRYPTO_RSA_DECRYPT_ERROR":
			return "RSA解密錯誤";
		case "CRYPTO_RSA_SIGN_ERROR":
			return "RSA簽章錯誤";
		case "CRYPTO_RSA_VERIFY_ERROR":
			return "RSA驗簽章錯誤";
		case "CRYPTO_HASH_ERROR":
			return "HASH錯誤";
		case "RESPONSE_JSON_PARSE_ERROR":
			return "回應封包JSON解析錯誤";
		case "RESPONSE_FORMAT_ERROR":
			return "回應封包格式錯誤";
		case "RESPONSE_TIMEOUT_ERROR":
			return "回應封包逾時錯誤";
		case "RESPONSE_APIVERSION_ERROR":
			return "回應封包版本錯誤";
		case "RESPONSE_SIGNATURE_VERIFY_ERROR":
			return "回應封包驗簽章錯誤";
		case "RESPONSE_DATA_HASH_COMPARE_ERROR":
			return "回應封包雜湊資料比對錯誤";
		case "RESPONSE_UNSUPPORTED_TYPE_ERROR":
			return "回應封包不支援的型態錯誤";
		case "API24727_GENERAL_ERROR":
			return "24727 API錯誤";
		case "UNSUPPORTED_TYPE_ERROR":
			return "不支援的型態";
		case "UNSUPPORTED_FEATURE_ERROR":
			return "不支援的功能";
		case "CKR_GET_FUNC_LIST_FAIL":
			return "P11取Function list失敗";
		case "CKR_EXEC_FUNC_LIST_FAIL":
			return "P11取Function list失敗";
		case "CKR_OBJECT_NOT_FOUND":
			return "P11找不到物件";
		case "API_PARSE_CARD_PROFILE_FAILURE":
			return "讀取或解析Card Profile檔案時發生錯誤";
		case "API_ACCESS_CARD_INFO_FAILURE":
			return "讀取或解析Card Info資料時發生錯誤";
		//To developer
		case "API_DLL_NOT_INITIALIZED":
			return "Middleware.dll或ASN1.dll載入失敗";
		case "API_NOT_INITIALIZED":
			return "New eID Middleware沒有執行初始化";
		case "API_MARSHAL_ERROR":
			return "解析ASN.1資料格式錯誤";
		case "API_INCORRECT_TLV":
			return "卡片資料格式錯誤";
		case "API_INCORRECT_DG_TAG":
			return "錯誤的DG TAG";
		case "API_INCORRECT_DE_TAG":
			return "錯誤的DE TAG";
		case "API_INCORRECT_PARAMETER":
			return "錯誤的參數";
		case "API_INCORRECT_CERT_FORMAT":
			return "DV或AT憑證格式錯誤";
		case "API_INCORRECT_DV_CERT":
			return "DV憑證驗證失敗";
		case "API_INCORRECT_AT_CERT":
			return "AT憑證驗證失敗";
		case "API_INVALID_DV_CERT_DATA":
			return "DV憑證資料不符(例如：有效時間)導致驗證失敗";
		case "API_INVALID_AT_CERT_DATA":
			return "AT憑證資料不符(例如：有效時間)導致驗證失敗";			
		case "API_INITIAL_ASN1_FAILURE":
			return "初始化ASN1 lib失敗";
		case "API_GET_CARD_DATA_FAILURE"://卡片有問題，換卡測
			return "讀取或解析EF.CardAccess或EF.CardSecurity時發生錯誤";
		case "API_SECURITY_CONDITION_NOT_SATISFIED":
			return "安全條件不符合";
		case "API_NAMED_ENTITY_NOT_FOUND":
			return "找不到對應物件的名稱";
		case "API_PROTOCOL_NOT_RECOGNIZED":
			return "未定義的認證協定";
		case "API_INAPPROPRIATE_PROTOCOL_FOR_ACTION":
			return "認證協定錯誤";
		case "API_INSUFFICIENT_RESOURCES":
			return "輸入的資料不足";
		case "API_PREREQUISITE_NOT_SATISFIED":
			return "前提條件未滿足";
		case "API_NO_ACTIVE_SESSION":
			return "沒有活著的session可以結束";
		case "IFD_UNKNOWN_ERROR":
			return "讀卡機層發生不明錯誤";
		case "IFD_DATA_OVER_BUFFER":
			return "回傳的APDU資料超過呼叫時宣告的大小";
		case "IFD_MEMORY_SIZE_ZERO":
			return "APDU資料長度為零";
		case "IFD_MEMORY_IS_NULL":
			return "APDU沒有記憶體位置";
		case "IFD_NO_TRANSACTION_STARTED":
			return "傳輸連線未開始";
		case "IFD_UNKNOWN_ACTION":
			return "未預期的結束連線動作";
		case "IFD_SHARING_VIOLATION":
			return "目前已有其他應用服務在使用讀卡機，無法設為獨佔狀態；或目前已有其他獨佔狀態的連線，此連線請求失敗。";
		case "IFD_UNKNOWN_SLOT":
			return "讀卡機插槽代碼錯誤";
		case "IFD_CANCEL_NOT_POSSIBLE":
			return "目前進行的動作不能取消";
		case "IFD_INVALID_SLOT_HANDLE":
			return "讀卡機插槽控制資訊錯誤";
		case "IFD_INVALID_CONTEXT_HANDLE":
			return "讀卡機層控制資訊錯誤";
		default:
		// case 0x000000XX	P11 ERROR	請用戶更新維護軟體 詳細錯誤碼請參閱pkcs11t.h標準定義說明
		console.log(typeof(rcode));
		console.log(rcode);
		return rcode;
	}
}