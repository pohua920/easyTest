/**
 * @author user
 */
		YAHOO.namespace("quote.data");

		//�������е�У�����
		YAHOO.quote.data.zhsPar=/[\u4e00-\u9fa5]{2,}/;
		YAHOO.quote.data.idnoPar=/^\d{15}(\d{2}[A-Za-z0-9])?$|^\d{9}([A-Za-z0-9])?$|^\d{11}([A-Za-z0-9])?$/;
		YAHOO.quote.data.forbidPar=/\*|\@/;
		YAHOO.quote.data.numPar= /^(([+])?(0|([1-9][0-9]*))([.][0-9]+)?)?$/;
		YAHOO.quote.data.plusnumPar= /^[0-9]*[1-9][0-9]*$/;			//正整数
		YAHOO.quote.data.moneyPar=/\d{1,10}/;
		YAHOO.quote.data.plusMoneyPar = /^((0|[1-9][0-9]*)([.][0-9]{1,2})?)?$/;
		YAHOO.quote.data.moneyPointPar=/^(\-?(0|[1-9][0-9]{1,11})([.][0-9]{1,2})?)?$/;//2009.10.26 by hualimin 整数位不能大于12位 
		YAHOO.quote.data.ratePar=/\d{1,4}\.\d{0,4}$|\d{1,4}$/;
		YAHOO.quote.data.money1Par = /^\-?[1-9]\d{0,2},(\d{3},)*\d{3}(\.\d+)?$|^\-?[1-9]\d{0,2}(\.\d+)?$|^\-0?(\.\d+)$|^\-0(\.\d+)?$|^0?(\.\d+)?/;
		YAHOO.quote.data.numSpacePar=/\d{1,}\ |\d{1,}$/g;
		YAHOO.quote.data.vinPar=/[A-Za-z0-9]{17}/;
		YAHOO.quote.data.agePar=/[0-9]{1,3}/;
		YAHOO.quote.data.newDicPar=/^@/;
		YAHOO.quote.data.errTipPar = /\[错误\:/;
		
		//YAHOO.quote.data.datePar =/[\d]{4}[-][\d]{1,2}[-][\d]{1,2}/;
		//YAHOO.quote.data.datetimePar =/[\d]{4}[-][\d]{1,2}[-][\d]{1,2}/;
		YAHOO.quote.data.longMoneyPar=/^(\d{1,6}|\d{1,6}\.\d{1,4})$/; //add by hualimin 最多 6位整数，最多4位小数
		YAHOO.quote.data.midRatePar=/^(\d{1,4}|\d{1,4}\.\d{1,4})$/; //add by wulei 最多 4位整数，最多4位小数
		YAHOO.quote.data.retentionValuePar=/^(\d{1,12}|\d{1,12}\.\d{1,2})$/;//add by wanghaibo 最多12位整数，最多2位小数,自留额
		YAHOO.quote.data.retentionRatePar=/^(\d{1,3}|\d{1,3}\.\d{1,6})$/;//add by wanghaibo 最多3位整数，最多6位小数，自留比例
		YAHOO.quote.data.nzhsPar=/\w+$/;//add by hualimin 2009-8-8 数字，字母，或者下划线
		YAHOO.quote.data.postPar=/^[0-9]{6}$/;
		YAHOO.quote.data.mobilePar=/^(?:13\d|15[123456789])-?\d{5}(\d{3}|\*{3})$/;
		YAHOO.quote.data.emailPar=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
		YAHOO.quote.data.integersPar=/^[0-9]*[1-9][0-9]*$/;
		YAHOO.quote.data.dtintegersPar=/dt-integers/;
		YAHOO.quote.data.id15Par=/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/; 
		YAHOO.quote.data.id18Par=/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/; 
		YAHOO.quote.data.currencyPar=/^[A-Z]+$/;//add by wanghaibo 正确的币别
		YAHOO.quote.data.zzsPar= /^[0-9]*$/;	//正整数1-9 by wanghaibo
		//�������е�У�����ͣ���Ӧinput���class��

		YAHOO.quote.data.dtidPar=/dt-id/;
		YAHOO.quote.data.dtemailPar=/dt-email/;//电子邮箱
		YAHOO.quote.data.dtmobilePar=/dt-mobile/;//手机号
		YAHOO.quote.data.dtpostPar=/dt-post/; //邮政编码
		YAHOO.quote.data.dtlongMoneyPar=/dt-lmoney/;//add by hualimin  最多 6位整数，最多4位小数
		YAHOO.quote.data.dtmidRatePar=/dt-mrate/;//add by hualimin  最多 4位整数，最多4位小数
		YAHOO.quote.data.dtnzhsPar=/dt-nzhs/;//add by hualimin 2009-8-8
		YAHOO.quote.data.dcchkPar=/dc-chk/;
		YAHOO.quote.data.dttextPar=/dt-text/;
		YAHOO.quote.data.dtnumPar=/dt-num/;
		YAHOO.quote.data.dtplusnumPar=/dt-plusnum/;
		YAHOO.quote.data.dtmoneyPar=/dt-money/;
		YAHOO.quote.data.dtplusMoneyPar=/dt-plusMoney/;
		YAHOO.quote.data.dtratePar=/dt-rate/;
		YAHOO.quote.data.dtdatePar=/dt-date/;
		YAHOO.quote.data.dtzhsPar=/dt-zhs/;
		YAHOO.quote.data.dtvinPar=/dt-vin/;
		YAHOO.quote.data.dtagePar=/dt-age/;
		YAHOO.quote.data.dtcurrencyPar=/dt-currentcy/;
		YAHOO.quote.data.dtretentionValuePar=/dt-retentionValue/;
		YAHOO.quote.data.dtretentionRatePar=/dt-retentionRate/;
		YAHOO.quote.data.dtzzsPar=/dt-zzs/;
		YAHOO.quote.data.lenPar=/[^\x00-\xff]/g;

		YAHOO.quote.data.strLen=function(){return this.replace(YAHOO.quote.data.lenPar,"aa").length;}
		YAHOO.quote.data.isDate=function(str){
			var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
			if(r==null)return false;
			var d = new Date(r[1], r[3]-1, r[4]);
			return(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
		}
		YAHOO.quote.data.isTime=function(str){
			var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
			if(r==null)return false;
			var d = new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
			return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
		}

		//У�����
		YAHOO.quote.data.datacheck=function(root){
			YAHOO.quote.data.datacheck.clearTips();
			var vFlag = true;
			var arrEl = YAHOO.util.Dom.getElementsBy(YAHOO.quote.data.datacheck.isElement,"input",root);
			for(var i=0;i<arrEl.length;i++){
				var el = arrEl[i];
				//�Ƿ񳬳�

				//�Ƿ�Ϊ��:���Ҫ��Ϊ�ն�Ϊ�գ���ֱ���״?
				if(YAHOO.quote.data.dcchkPar.test(el.className)){
					if(trim(el.value)==""){
						YAHOO.quote.data.datacheck.adderror(el,"这里是必输项，必须填写信息！");
						vFlag = false;
					}
				}
				//��������
				if(YAHOO.quote.data.dtnumPar.test(el.className)){
					if(trim(el.value)!=""&&!YAHOO.quote.data.numPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里必须填写正确的数字！请认真确认！");
						vFlag = false;
					}
				}
				//正整数
				if(YAHOO.quote.data.dtplusnumPar.test(el.className)){
					if(trim(el.value)!=""&&!YAHOO.quote.data.plusnumPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里必须填写正整数！请认真确认！");
						vFlag = false;
					}
				}
				//��������
				if(YAHOO.quote.data.dtdatePar.test(el.className)){
					if(trim(el.value)!=""&&!YAHOO.quote.data.isDate(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里必须填写正确的日期(YYYY-MM-DD)！请认真确认！");
						vFlag = false;
					}
				}
				//��������
				if(YAHOO.quote.data.dtmoneyPar.test(el.className)){
					if(trim(el.value)!="" && !YAHOO.quote.data.moneyPointPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里必须填写正确的数量！请仔细确认");
						vFlag = false;
					}
				}
				//���������
				if(YAHOO.quote.data.dtplusMoneyPar.test(el.className)){
					if(trim(el.value)!="" && !YAHOO.quote.data.plusMoneyPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"该处金额必须填写正金额且保留小数点后两位！请仔细确认！");
						vFlag = false;
					}
				}
				//��������
				if(YAHOO.quote.data.dtratePar.test(el.className)){
					if(trim(el.value)!=""&&!YAHOO.quote.data.ratePar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里必须填写正确的数值！例如0.1234。请仔细确认！");
						vFlag = false;
					}
				}
				//����������
				if(YAHOO.quote.data.dtretentionValuePar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.retentionValuePar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里最多12位整数，2位小数！请仔细确认！");
						vFlag = false;
					}
				}
				if(YAHOO.quote.data.dtretentionRatePar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.retentionRatePar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里最多3位整数，6位小数！请仔细确认！");
						vFlag = false;
					}
				}
				//add by hualimin 2009-8-8 check not zhs
				if(YAHOO.quote.data.dtnzhsPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.nzhsPar.test(trim(el.value))){
						YAHOO.quote.data.datacheck.adderror(el,"这里只能是数字字母或下划线！请仔细确认！");
						vFlag = false;
					}
				}
				//add by hualimin 2009-10-22 check lmoney
				if(YAHOO.quote.data.dtlongMoneyPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.longMoneyPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里最多6位整数，4位小数！请仔细确认！");
						vFlag = false;
					}
				}
				if(YAHOO.quote.data.dtmidRatePar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.midRatePar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里最多4位整数，4位小数！请仔细确认！");
						vFlag = false;
					}
				}
				//У��VIN��
				if(YAHOO.quote.data.dtvinPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.vinPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"这里必须填写17位VIN码！请仔细确认VIN码是否为17位！");
						vFlag = false;
					}
				}

				//У������
				if(YAHOO.quote.data.dtagePar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.agePar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"请填写正确的年龄！");
						vFlag = false;
					}
				}
				if(YAHOO.quote.data.dtintegersPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.integersPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"请填写正确的整形数字！");
						vFlag = false;
					}
				}
				if(YAHOO.quote.data.dtpostPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.postPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"请填写正确的邮政编码！");
						vFlag = false;
					}
				}
				
				if(YAHOO.quote.data.dtmobilePar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.mobilePar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"请填写正确的手机号码！");
						vFlag = false;
					}
				}
				if(YAHOO.quote.data.dtemailPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.emailPar.test(el.value)){
						YAHOO.quote.data.datacheck.adderror(el,"请填写正确的电子邮件地址！");
						vFlag = false;
					}
				}
				if(YAHOO.quote.data.dtidPar.test(el.className)){
					if(trim(el.value)!=""&&! YAHOO.quote.data.id18Par.test(el.value)){
						if(trim(el.value)!=""&&! YAHOO.quote.data.id15Par.test(el.value)){							
							YAHOO.quote.data.datacheck.adderror(el,"请填写正确的身份证号！");
							vFlag = false;
						}
					}
				}
				if(YAHOO.quote.data.dtcurrencyPar.test(el.className)){				
						if(trim(el.value)!=""&&! YAHOO.quote.data.currencyPar.test(el.value)){							
							YAHOO.quote.data.datacheck.adderror(el,"请填写正确的币别,例如CNY！");
							vFlag = false;
						}
				}
				if(YAHOO.quote.data.dtzzsPar.test(el.className)){				
						if(trim(el.value)!=""&&! YAHOO.quote.data.zzsPar.test(el.value)){							
							YAHOO.quote.data.datacheck.adderror(el,"这里只能填写正整数！");
							vFlag = false;
						}
				}
			}
			return vFlag;
		}
		YAHOO.quote.data.datacheck.errTips=[];
//		YAHOO.quote.data.datacheck.errObjs=[];
		YAHOO.quote.data.datacheck.clearTips=function(){
			for(var i=0;i<YAHOO.quote.data.datacheck.errTips.length;i++){
				var errtip = YAHOO.quote.data.datacheck.errTips[i];
				var el = errtip._context;
				if(false){
					el.title="";
					YAHOO.util.Dom.removeClass(el,"dc-err");
				}else{
					el[0].title="";
					YAHOO.util.Dom.removeClass(el[0],"dc-err");
				}
				errtip.destroy();;
			}
			YAHOO.quote.data.datacheck.errTips=[];
		}
		//���el��id����ȡ��Ӧ��tip��id��
		YAHOO.quote.data.datacheck.getTipNameByEl=function(el,tname){
			if(el.id!=""){
				tname = "errtip_"+el.id;
			}else{
				tname = "errtip_"+el.name;
			}
//			alert("name is "+tname);
			return tname;
		}

		/**
		 * ���Ӵ�����Ϣ
		 * @param {String|HTMLElement} el
		 * @param {String} msg
		 */
		YAHOO.quote.data.datacheck.adderror=function(el,msg){
			el.title = el.title + "[错误: "+msg+" ]";
			var tipname = new YAHOO.quote.data.datacheck.getTipNameByEl(el,tipname);
			tipname = ""+tipname;
			var errtip = new YAHOO.widget.Tooltip( tipname.toString(),{context:el,zIndex:300});
			YAHOO.quote.data.datacheck.errTips[ YAHOO.quote.data.datacheck.errTips.length ] = errtip;
			//YAHOO.quote.data.datacheck.errObjs[ YAHOO.quote.data.datacheck.errObjs.length ] = el;
			YAHOO.util.Dom.addClass(el,"dc-err");
		}
		//�ж��Ƿ�Ϊָ�������У�����
		YAHOO.quote.data.datacheck.isElement=function(el){
			return true;
		}
