/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	fm.buttonSaveType.value = saveType;
	if (!validateForm(fm, 'Prop_Data')) {
		return false;
	}
	//險別代碼和險別名稱不能為空
	if (fm.prpLpropKindCode.length > 1) {
		for (var i = 1; i < fm.prpLpropKindCode.length; i++) {
			if ($.trim($(fm.prpLpropKindCode[i]).val()) == "" || $.trim($(fm.prpLpropKindName[i]).val()) == "") {
				alert("險別代碼和險別名稱不能為空！");
				return false;
			}
		}
	}

	var addNewRepairCompentRow = -1; //默认没有增加一条记录数据
	var compensatebackFlag = fm.prpLverifyLossCompensateFlag.value; //理算退回的标记
	for (i = 1; i < fm.prpLpropSerialNo.length; i++) {
		if (fm.prpLpropCompensateBackFlag[i].value != "1") addNewRepairCompentRow = 1;

	}

	//如果是理算回退的，並且没有增加新行的话，那么可以直接提交到理算 
	if (compensatebackFlag == "1" && addNewRepairCompentRow < 0) fm.NextComeBackCompensate.value = "1";
	sumPrpLverifyLossWarpDefLoss();
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
}


function getSumPropDefLoss(field) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var propSumLoss;
	var propSumReject;
	var propSumDefLoss;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	propSumLoss = parseFloat(fm.all("prpLpropSumLoss")[findex].value);
	propSumReject = parseFloat(fm.all("prpLpropSumReject")[findex].value);

	if (isNaN(propSumLoss) || propSumLoss.length < 1) {
		propSumLoss = 0;
	}
	if (isNaN(propSumReject) || propSumReject.length < 1) {
		propSumReject = 0;
	}
	propSumDefLoss = propSumLoss - propSumReject;
	fm.all("prpLpropSumDefLoss")[findex].value = point(round(propSumDefLoss, 0), 0);
	return true;

}
/**删除行时，重新计算金额合计
 * @param field
 * @return
 */
function getNewSum() {
    var sumloss = "prpLpropSumLoss"; //受损金额
    var sumreject = "prpLpropSumReject"; //残值
    var sumdefloss = "prpLpropSumDefLoss"; //定损金额
    var sumvalue;
    var sumallvalue;
    if (fm.all(sumloss) != null) {
        for (i = 1; i < fm.all(sumloss).length; i++) {
            sumvalue = fm.all(sumloss)[i].value;
            if (isNaN(sumvalue) || sumvalue.length < 1) {
                sumvalue = 0;
            }
            if (isNaN(sumallvalue) || sumallvalue.length < 1) {
                sumallvalue = 0;
            }
            sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
        }
        fm.prpLpropSumSumLoss.value = point(round(sumallvalue, 0), 0);
    }
    sumallvalue = 0;
    if (fm.all(sumreject) != null) {
        for (i = 1; i < fm.all(sumreject).length; i++) {
            sumvalue = fm.all(sumreject)[i].value;

            if (isNaN(sumvalue) || sumvalue.length < 1)
                sumvalue = 0;
            if (isNaN(sumallvalue) || sumallvalue.length < 1)
                sumallvalue = 0;
            sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
        }
        fm.prpLpropSumSumReject.value = point(round(sumallvalue, 0), 0);
    }
    sumallvalue = 0;
    if (fm.all(sumdefloss) != null) {
    	for (i = 1; i < fm.all(sumdefloss).length; i++) {
            sumvalue = fm.all(sumdefloss)[i].value;
            if (isNaN(sumvalue) || sumvalue.length < 1) {
                sumvalue = 0;
            }
            if (isNaN(sumallvalue) || sumallvalue.length < 1) {
                sumallvalue = 0;
            }
            sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
        }
        fm.prpLpropSumSumDefLoss.value = point(round(sumallvalue, 0), 0);
    }
}
//设置偏差金额
function sumPrpLverifyLossWarpDefLoss(){
	var prpLpropSumSumDefLoss = $("input[name='prpLpropSumSumDefLoss']").val();//定损金额
	$("input[name='prpLverifyLossWarpDefLoss']").val(prpLpropSumSumDefLoss);//偏差金额
	$("input[name='prpLverifyLossSumPreDefLoss']").val(prpLpropSumSumDefLoss);//定损金额
	$("input[name='prpLverifyLossSumDefLoss']").val(prpLpropSumSumDefLoss);//核损金额
}