<STYLE>
    @media all
    {
	    mpc\:container {
	                    behavior:url(/platform/common/behaviors/mpc.htc);
	                    }
   
	    mpc\:page {
	                    behavior:url(/platform/common/behaviors/mpc.htc);
	                    }
    }
     H1              {
                    font: bold 18pt verdana;
                    color: navy
                    }
                    
    P               {
                    font: 10pt verdana;
                    }
	A:link { color:#003399; text-decoration:none; }
	A:visited { color:#6699CC; text-decoration:none; }
	A:hover { text-decoration:underline; }
</STYLE>

<SCRIPT LANGUAGE="JavaScript">
function TabOrientation()
{
	if(oMPC.style.tdTabOrientation == "top")oMPC.style.tdTabOrientation = "bottom" ;
	else oMPC.style.tdTabOrientation = "top"
}
function Height()
{
	alert(parseInt(heightBox.value));
	oMPC.style.height = parseInt(heightBox.value);
}
function Width()
{
	oMPC.style.width = parseInt(widthBox.value);
}
function BackColor()
{
	oMPC.style.backgroundColor = BackColorBox.value;
}
function Color()
{
	oMPC.style.color = ColorBox.value;
}

</SCRIPT>