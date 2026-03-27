



function showPage(img,spanID)

{

  if(spanID.style.display=="")

  {

   //¹Ø±Õ

    spanID.style.display="none";

    img.src="/visa/images/butCollapse.gif";

  }

  else

  {

   //Õ¹¿ª

    spanID.style.display="";

    img.src="/visa/images/butExpand.gif";

  }

}



