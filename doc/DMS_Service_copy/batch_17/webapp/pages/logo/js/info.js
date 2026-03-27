 function showMenu(obj){

        a=top.document.getElementById("bottomFrame");
        if(a.cols=="179,*"){
            a.cols="0,*";
            obj.src="nimg/switch_menu_show.jpg";
            obj.title="ÏÔÊ¾²Ëµ¥"
        }
            else {
                    a.cols="179,*";
                    obj.src="nimg/switch_menu_hide.jpg";
                    obj.title="Òþ²Ø²Ëµ¥"
                 }
        }   