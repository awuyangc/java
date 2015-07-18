package com.wy.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.CheckModel;
import com.qq.weixin.HttpUtil;
import com.qq.weixin.TokenService;
import com.qq.weixin.message.model.SNSUserInfo;
import com.qq.weixin.message.model.WeixinOauth2Token;
import com.qq.weixin.utils.AdvancedUtil;
import com.qq.weixin.utils.SignUtil;
import com.qq.weixin.utils.WeixinUtil;
import com.wy.model.AccessToken;
import com.wy.model.JsapiTicket;
import com.wy.model.WeixinUser;
import com.wy.service.IAccessTokenServie;
import com.wy.service.IJsapiTicketServie;
import com.wy.service.IWeixinUserService;

@Controller
@RequestMapping("/weixin")
public class TokenController {
	@Resource
	private TokenService tokenService;
	@Resource
	private IAccessTokenServie accessTokenService;
	@Resource
	private IWeixinUserService weixinUserService;
	@Resource
	private IJsapiTicketServie jsapiTicketService;
	
	private static WeixinOauth2Token weixinOauth2Token;
	// 第三方用户唯一凭证
	private static String appId = "wx6d373275087fc071";
	// 第三方用户唯一凭证密钥
	private static String appSecret = "db99176f00ebd14f90e55197e368c92d";
	
	@RequestMapping(value = "/check" ,method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String weixinCheckGet(HttpServletResponse response,CheckModel checkModel) throws IOException{
		String wxToken="youareworthit";
		return tokenService.validate(wxToken,checkModel); 

	}
	
	@RequestMapping(value = "/check" ,method = RequestMethod.POST)
	public void weixinCheckPost(HttpServletRequest request,
		HttpServletResponse response) throws IOException{
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
		//String respXml = CoreService.processRequest(request);
		//response.getWriter().write(respXml);

	}
	
	@RequestMapping(value = "/oauth2Check" ,method = RequestMethod.GET, produces = "text/plain")
	public String oauth2Check(String code,String inviteId) throws IOException{
		
		//查询数据库的access_token看是否过期
		AccessToken accessToken=accessTokenService.getAccessTokenById(1);
		//如果过期
		if(checkAccessToken())
		{
		//重新请求获取唯一access_token。此token有效期7200秒，每天200次调用
		accessToken=WeixinUtil.getAccessToken(appId, appSecret);
		System.out.println("过期重新请求accessToken："+accessToken.getAccessToken());
		updateToken(accessToken.getAccessToken());
		}
		
		//查看数据库的jsapiTicket是否过期
		JsapiTicket jsapiTicket=jsapiTicketService.getJsapiTicketById(1);
		if(checkTicket())
		{
		jsapiTicket=WeixinUtil.getJsapiTicket(accessToken.getAccessToken());
		System.out.println("过期重新请求ticket："+jsapiTicket.getTicket());
		updateTicket(jsapiTicket.getTicket());
		}
		
		
		String appid="wx6d373275087fc071";
		String redirect_uri="http://awuyangc.xicp.net/origin/weixin/getToken.action?inviteId="+inviteId+"&random="+Math.random();
		return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&"+ 
					"redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
	}
	
	@RequestMapping(value = "/getToken" ,method = RequestMethod.GET, produces = "text/plain")
	public String getToken(String code,SNSUserInfo snsUserInfo,WeixinUser weixinUser,HttpSession session,String inviteId) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		// 用户同意授权
		if (!"authdeny".equals(code)) {
			// 网页授权接口访问凭证
			// 获取网页授权access_token,应用的appid和appsecret
			weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(appId, appSecret, code);
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			snsUserInfo = AdvancedUtil.getSNSUserInfo(weixinOauth2Token.getAccessToken(),openId);
			session.setAttribute("snsUserInfo", snsUserInfo);
			//查找本地用户，如果不存在，则记录用户
			weixinUser=weixinUserService.getWeixinUserByOpenId(openId);
			if(weixinUser==null)
			{
				weixinUser=new WeixinUser();
				BeanUtils.copyProperties(snsUserInfo, weixinUser);
				//BeanUtils.copyProperties(snsUserInfo, weixinUser);
				weixinUserService.insert(weixinUser);
			}
		}
		if(inviteId!=""&&inviteId!=null)
		{
			return "redirect:/signUp.action?inviteId="+inviteId;
		}
		else
		{
			return "redirect:/index.action";
		}
		
	}
	
	@RequestMapping("/getSession")
	@ResponseBody
	public SNSUserInfo getSessioin(HttpSession session,SNSUserInfo snsUserInfo){
		snsUserInfo=(SNSUserInfo) session.getAttribute("snsUserInfo");
			return snsUserInfo;
	}
	
	public JSONObject getHttpJson(String url) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient = HttpUtil.createSSLClientDefault();
		HttpUriRequest httpRequest=new HttpGet(url);
		CloseableHttpResponse httpResponse = httpClient.execute(httpRequest);
		JSONObject fromObject=null;
		try {  
			int statusCode = httpResponse.getStatusLine().getStatusCode();  
	           if (statusCode == HttpStatus.SC_OK) {  
	               HttpEntity entity = httpResponse.getEntity();
	               String entityString = EntityUtils.toString(entity);
	               fromObject = JSONObject.parseObject(entityString);
            }  
		}
	     catch (ClientProtocolException e) {  
	        e.printStackTrace();  
	    } 
		catch (IOException e) {  
	        e.printStackTrace();  
		} 
		finally {  
			//通过token获取用户信息
			if(httpResponse != null){  
				 httpResponse.close();  
				}  
				if(httpClient!= null){  
					httpClient.close();  
				}
		}  
		return fromObject;
	}
	
	
	
	//保存accessToken
	 public void updateToken(String strAccessToken){
		AccessToken accessToken=new AccessToken();
		accessToken.setExpiresIn(7200);
		long endTime = System.currentTimeMillis() + accessToken.getExpiresIn() * 1000-600*1000;
    	Date date = new Date(endTime);
		accessToken.setId(1);
		accessToken.setAccessExpires(endTime);
		accessToken.setAccessExpiresch(date);
		accessToken.setAccessToken(strAccessToken);
		 accessTokenService.update(accessToken);
	 }
	 
	 //判断accessToken是否过期
	 public boolean checkAccessToken() throws IOException{
		 	AccessToken accessToken=accessTokenService.getAccessTokenById(1);
	    	if(System.currentTimeMillis()<accessToken.getAccessExpires())
	    	{
	    		//没过期
	    		return false;
	    	}
	    	else
	    	{
	    		//过期
	    		return true;
	    	}
	 }
	 
	//保存jsapiTicket
	 public void updateTicket(String strJsapiTicket){
		 JsapiTicket jsapiTicket=new JsapiTicket();
		 jsapiTicket.setExpiresIn(7200);
		long endTime = System.currentTimeMillis() + jsapiTicket.getExpiresIn() * 1000-600*1000;
    	Date date = new Date(endTime);
    	jsapiTicket.setId(1);
    	jsapiTicket.setTicketExpires(endTime);
    	jsapiTicket.setTicketExpiresch(date);
    	jsapiTicket.setTicket(strJsapiTicket);
    	jsapiTicketService.update(jsapiTicket);
	 }
	 
	 //判断jsapiTicket是否过期
	 public boolean checkTicket() throws IOException{
		 	JsapiTicket jsapiTicket=jsapiTicketService.getJsapiTicketById(1);
	    	if(System.currentTimeMillis()<jsapiTicket.getTicketExpires())
	    	{
	    		//没过期
	    		return false;
	    	}
	    	else
	    	{
	    		//过期
	    		return true;
	    	}
	 }
	 
	 //进行签名
	 @RequestMapping("/sign")
	 @ResponseBody
	 public Map<String, String> sign(HttpServletRequest request,String url){
		JsapiTicket jsapiTicket=jsapiTicketService.getJsapiTicketById(1);
		Map<String, String> ret = new HashMap<String, String>();
		ret=SignUtil.sign(jsapiTicket.getTicket(), url);
		return ret;
	 }
	 
}
