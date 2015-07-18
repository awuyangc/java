package com.wy.service;

import java.util.List;

import com.wy.model.WeixinUser;

public interface IWeixinUserService {
	public WeixinUser getWeixinUserByOpenId(String openId);
	public List<WeixinUser> getWeixinUserList();
	public void insert(WeixinUser weixinUser);
	public void update(WeixinUser weixinUser);
	public void delete(WeixinUser weixinUser);
}
