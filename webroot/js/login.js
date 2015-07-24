 
var strKey = "eHuoguoOpenId"; 
alert(2);
var strStoreDate=getCookie(strKey);
alert(strStoreDate);
var inviteId=GetQueryString("inviteId");
if(strStoreDate==""||strStoreDate==null||strStoreDate=="undefined"||strStoreDate=="null")
{
	if(inviteId!=""&&inviteId!=null&&!inviteId!="null")
	{
		window.location.href="weixin/oauth2Check.action?inviteId="+inviteId;
	}
	else
	{
		
		window.location.href="weixin/oauth2Check.action";
	}
}
else
{
	if(inviteId!=""&&inviteId!=null)
	{
		
		window.location.href="signUp.action?inviteId="+inviteId;
	}
	else
	{
		window.location.href="index.action";
	}
	//获取用户信息并且写入localstorage
	
}

function GetQueryString(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	   var r = window.location.search.substr(1).match(reg);
	   if (r!=null) return (r[2]); return null;
	}

//设置cookie
function setCookie(c_name,value,expiredays)
{
var exdate=new Date()
exdate.setDate(exdate.getDate()+expiredays)
document.cookie=c_name+ "=" +escape(value)+
((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

//取回cookie
function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
	{ 
	c_start=c_start + c_name.length+1 
	c_end=document.cookie.indexOf(";",c_start)
	if (c_end==-1) c_end=document.cookie.length
	return unescape(document.cookie.substring(c_start,c_end))
	} 
  }
return ""
}