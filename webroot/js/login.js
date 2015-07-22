var strKey = "eHuoguoOpenId"; 
var strStoreDate=localStorage.getItem(strKey);
var inviteId=GetQueryString("inviteId");
if(strStoreDate==""||strStoreDate==null)
{
	if(inviteId!=""&&inviteId!=null&&!inviteId.equals("null"))
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
