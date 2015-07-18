package com.wy.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wy.model.InviteInfo;
import com.wy.service.IInviteInfoService;

@Controller
@RequestMapping("/")
public class BaseController {
//*********************************************首页***********************************************
	@Resource
	private IInviteInfoService inviteInfoService;
	
	@RequestMapping("/index")
	public String toIndex(){
		return "index.html?rd="+Math.random();
	}
	
	
	@RequestMapping("/signUp")
	public String toSignUp(String inviteId){
		return "signUp.html?inviteId="+inviteId+"&rd="+Math.random();
	}
	
	
	@RequestMapping("/getInviteInfo")
	@ResponseBody
	public InviteInfo getInviteInfo(Integer inviteId){
		return inviteInfoService.getInviteInfoById(inviteId);
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
	
}
