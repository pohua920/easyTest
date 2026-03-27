<script language="javascript">
        function openTipWindow(index) {
                var factorCode = "";
                if(fm.simpleFactorCode.length == null) {
                    factorCode = fm.simpleFactorCode.value;
                } else {
                    factorCode = fm.simpleFactorCode[index].value;
                }
                var url = '/claim/processUwFactor.do?actionType=openTip&factorCode=' + factorCode;
                var diaWidth = 500, diaHeight = 240;
                var diaLeft = 200;
                var diaTop = 20;
                var args = "dialogWidth:" + diaWidth + "px;dialogHeight:" + diaHeight +
                             "px;dialogLeft:" + diaLeft + "px;dialogTop:" + diaTop + "px;status:yes;resizable:yes;"
                window.showModelessDialog(url, document, args);
        }
</script>