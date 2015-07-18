package com.wy.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wy.dao.WeixinUserMapper;
import com.wy.model.WeixinUser;
import com.wy.service.IWeixinUserService;

@Service("weixinUserService")
public class WeixinUserServiceImpl implements IWeixinUserService {

	@Resource
	private WeixinUserMapper weixinUserMapper;
	
	@Override
	public WeixinUser getWeixinUserByOpenId(String openId) {
		// TODO Auto-generated method stub
		return weixinUserMapper.selectByPrimaryKey(openId);
	}

	@Override
	public List<WeixinUser> getWeixinUserList() {
		// TODO Auto-generated method stub
		return weixinUserMapper.selectAll();
	}

	@Override
	public void insert(WeixinUser weixinUser) {
		// TODO Auto-generated method stub
		weixinUserMapper.insert(weixinUser);
	}

	@Override
	public void update(WeixinUser weixinUser) {
		// TODO Auto-generated method stub
		weixinUserMapper.updateByPrimaryKey(weixinUser);
	}

	@Override
	public void delete(WeixinUser weixinUser) {
		// TODO Auto-generated method stub
		weixinUserMapper.deleteByPrimaryKey(weixinUser.getOpenId());
	}

	

}
