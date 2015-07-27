package com.wy.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.weixin.message.model.SNSUserInfo;
import com.wy.model.InviteInfo;
import com.wy.model.JoinInfo;
import com.wy.model.WeixinUser;
import com.wy.service.IInviteInfoService;
import com.wy.service.IJoinInfoService;
import com.wy.service.IUserService;
import com.wy.service.IWeixinUserService;

@Controller
@RequestMapping("/")
public class BaseController {
//*********************************************首页***********************************************
	@Resource
	private IInviteInfoService inviteInfoService;
	@Resource
	private IWeixinUserService weixinUserService;
	@Resource
	private IJoinInfoService joinInfoService;
	
	@RequestMapping("/index")
	public String toIndex(String page,String inviteId){
		return "index.html?page="+page+"inviteId="+inviteId+"&rd="+Math.random();
	}
	
	
	@RequestMapping("/signUp")
	public String toSignUp(String inviteId){
		return "signUp.html?inviteId="+inviteId+"&rd="+Math.random();
	}
	
	
	@RequestMapping("/getInviteInfo")
	@ResponseBody
	public InviteInfo getInviteInfo(Integer inviteId){
		InviteInfo inviteInfo=inviteInfoService.getInviteInfoById(inviteId);
		return inviteInfo;
	}
	
	@RequestMapping("/saveInviteInfo")
	@ResponseBody
	public InviteInfo saveInviteInfo(String id,String inviteOpenid,String inviteDay,String inviteBegin,String inviteEnd,String inviteAddress) throws ParseException{
		//inviteInfoService.insert(inviteInfo);
		InviteInfo inviteInfo=new InviteInfo();
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm");
		Date format_inviteDay=sdfDay.parse(inviteDay);
		Date format_inviteBegin=sdfTime.parse(inviteBegin);
		Date format_inviteEnd=sdfTime.parse(inviteEnd);
		inviteInfo.setInviteOpenid(inviteOpenid);
		inviteInfo.setInviteDay(format_inviteDay);
		inviteInfo.setInviteBegin(format_inviteBegin);
		inviteInfo.setInviteEnd(format_inviteEnd);
		inviteInfo.setInviteAddress(inviteAddress);
		inviteInfo.setCreateTime(new Date());
		if(id!=""&&id!=null)
		{
			//修改
			inviteInfo.setId(Integer.parseInt(id));
			inviteInfoService.update(inviteInfo);
		}
		else
		{
			//新增
			inviteInfoService.insert(inviteInfo);
		}
		return inviteInfo;
	}
	
	
	@RequestMapping("/saveJoinInfo")
	@ResponseBody
	public String saveJoinInfo(int inviteId,HttpSession session) throws ParseException{
		//inviteInfoService.insert(inviteInfo);
		JoinInfo joinInfo=new JoinInfo();
		joinInfo.setInviteId(inviteId);
		SNSUserInfo snsUserInfo=(SNSUserInfo) session.getAttribute("snsUserInfo");
		joinInfo.setOpenId(snsUserInfo.getOpenId());
		joinInfo.setCreateTime(new Date());
		if(joinInfoService.getMySignInfo(joinInfo)==null)
		{
		joinInfoService.insert(joinInfo);
		}
		return "ok1";
	}
	
	@RequestMapping("/deleteJoinInfo")
	@ResponseBody
	public String deleteJoinInfo(int inviteId,HttpSession session) throws ParseException{
		JoinInfo joinInfo=new JoinInfo();
		joinInfo.setInviteId(inviteId);
		SNSUserInfo snsUserInfo=(SNSUserInfo) session.getAttribute("snsUserInfo");
		if(snsUserInfo!=null)
		{
		joinInfo.setOpenId(snsUserInfo.getOpenId());
		joinInfoService.deleteByInviteId(joinInfo);
		return "ok1";
		}
		else
		{
			return "error";
		}
	}
	
	@RequestMapping("/getJoinInfo")
	@ResponseBody
	public JoinInfo getJoinInfo(Integer inviteId){
		JoinInfo joinInfo=joinInfoService.getJoinInfoByInviteId(inviteId);
		return joinInfo;
	}
	
}
