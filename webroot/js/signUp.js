 $.afui.autoLaunch=false;
 $.afui.useOSThemes=false;
 /* This function runs when the content is loaded.*/
 $(document).ready(function(){
	//获取js开发许可
	 	$.ajax({
	        url: "weixin/sign.action",
	        data:{url:location.href.split('#')[0]},
	        success: function (data) {
	        	wx.config({
	        	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	        	    appId: 'wx6d373275087fc071', // 必填，公众号的唯一标识
	        	    timestamp: data.timestamp, // 必填，生成签名的时间戳
	        	    nonceStr: data.nonceStr, // 必填，生成签名的随机串
	        	    signature: data.signature,// 必填，签名，见附录1
	        	    jsApiList: [
							'checkJsApi',
							'onMenuShareTimeline',
							'onMenuShareAppMessage',
							'onMenuShareQQ',
							'onMenuShareWeibo',
							'hideMenuItems',
							'showMenuItems',
							'hideAllNonBaseMenuItem',
							'showAllNonBaseMenuItem',
							'translateVoice',
							'startRecord',
							'stopRecord',
							'onRecordEnd',
							'playVoice',
							'pauseVoice',
							'stopVoice',
							'uploadVoice',
							'downloadVoice',
							'chooseImage',
							'previewImage',
							'uploadImage',
							'downloadImage',
							'getNetworkType',
							'openLocation',
							'getLocation',
							'hideOptionMenu',
							'showOptionMenu',
							'closeWindow',
							'scanQRCode',
							'chooseWXPay',
							'openProductSpecificView',
							'addCard',
							'chooseCard',
							'openCard'
	        	      ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	        	});
	        }
	    });  
		
		 // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.ready(function(){
			//wx.hideOptionMenu();
		});

		wx.error(function(res){
			// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
			});
		
		 //显示加载动画
		 setTimeout(function(){$.afui.launch();},1500);
		 if($.afui.useOSThemes&&!$.os.ios&&$("#afui").get(0).className!=="ios")
	     {
		 	$("#afui").removeClass("ios");
	     }
		$.ajax({
	         url: "weixin/getSession.action",
	         success: function (data) {
	        	 $("#userNickName").text(data.nickname);
	        	 $("#userNickNameSplash").text("您好！"+data.nickname);
	        	 $("#openId").val(data.openId);
	        	 $("#nickname").val(data.nickname);
	         }
	     });  
		
		var inviteId=GetQueryString("inviteId");
		$.ajax({
			async: false,
	        url: "getInviteInfo.action",
	        data:{"inviteId":inviteId},
	        success: function (data) {
	        	$("#inviteInfo").html("您的好友XXX邀请您 "+data.inviteDay+" 日  "+data.inviteBegin+" 到 "+data.inviteEnd+"之间 一伙锅！");
	        	$("#restaurantId").val(data.inviteAddress);
	        	
	        }
	    }); 
		$.ajax({
		   	dataType : "jsonp",
		   		  url: "http://yuntuapi.amap.com/datasearch/id?tableid=55656259e4b0ccb608f13383&_id="+$("#restaurantId")+"&key=a46ffb73729bb688480643eea31387e7",
		      success: function (result) {
		       	 var listRestaurant="";
		       	 $(result.datas).each(function(i,val){
		       		 alert(val._id);
		       		listRestaurant +='<li id="li'+val._id+'">'+
						 			 '<a href="#restaurantDetail" onclick="$(\'#restaurantId\').val('+val._id+');$(\'#restaurantName\').val(\''+val._name+'\')">'+
									 '<img src="'+val._image[0]._url+'">'+
									 '<h2>'+val._name+'</h2>'+
									 '<p>'+val._address+'</p>'+
									 '</a>'+
									 '</li>';
		       	 });
		       	$("#restaurantInfo").html(listRestaurant);
			  }
	 });
		$("#Home").click(function (){
			wx.closeWindow();
		});
});

 function initMain()
 {
 }
 

function dispatchPanelEvent(fnc,myPanel){
     if (typeof fnc === "string" && window[fnc]) {
         return window[fnc](myPanel);
     }
     else if(fnc.indexOf(".")!==-1){
         var scope=window,items=fnc.split("."),len=items.length,i=0;
         for(i;i<len-1;i++){
             scope=scope[items[i]];
             if(scope===undefined) return;
         }
         return scope[items[i]](myPanel);
     }
 };

 $(document).on("panelload",function(e){
    var hasLoad=$(e.target).attr("data-load");
    if(hasLoad!=null&&hasLoad!=undefined)
    {
    return dispatchPanelEvent(hasLoad,e.target);
    }
 })

 $(document).on("panelunload",function(e){
    var hasLoad=$(e.target).attr("data-unload");
    if(hasLoad!=null&&hasLoad!=undefined)
    {
    return dispatchPanelEvent(hasLoad,e.target);
    }
 })
 
 function GetQueryString(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	   var r = window.location.search.substr(1).match(reg);
	   if (r!=null) return (r[2]); return null;
	}