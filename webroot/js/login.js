var strKey = "eHuoguoOpenId"; 
var strStoreDate = window.localStorage? localStorage.getItem(strKey): Cookie.read(strKey);
if(strStoreDate==""||strStoreDate==null)
{
	window.location.href="weixin/oauth2Check.action";
}
else
{
	//获取用户信息并且写入localstorage
	
	window.location.href="index.action";
}

