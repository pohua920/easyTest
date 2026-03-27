//为险别是否可以临分的标志位赋值add by WangJun 20131211
function ChangeValue(fm,index)
{
	if (isNaN(fm.facultativeFlag.length))
	{
		if(fm.facultativeFlag.checked)
		{
			fm.isFacultative.value="1";
		}
		else
		{
			fm.isFacultative.value="0"
		}
	}
	else
	{
		if(fm.facultativeFlag[index].checked)
		{
			fm.isFacultative[index].value="1";
		}
		else
		{
			fm.isFacultative[index].value="0"
		}
	}
}