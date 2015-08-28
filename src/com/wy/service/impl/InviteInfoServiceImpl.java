package com.wy.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wy.dao.InviteInfoMapper;
import com.wy.model.InviteInfo;
import com.wy.service.IInviteInfoService;

@Service("inviteInfoService")
public class InviteInfoServiceImpl implements IInviteInfoService {

	@Resource
	private InviteInfoMapper inviteInfoMapper;	
	
	@Override
	public InviteInfo getInviteInfoById(Integer id) {
		// TODO Auto-generated method stub
		return inviteInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<InviteInfo> getInviteInfoList() {
		// TODO Auto-generated method stub
		return inviteInfoMapper.selectAll();
	}

	@Override
	public void insert(InviteInfo inviteInfo) {
		// TODO Auto-generated method stub
		inviteInfoMapper.insert(inviteInfo);
	}

	@Override
	public void update(InviteInfo inviteInfo) {
		// TODO Auto-generated method stub
		inviteInfoMapper.updateByPrimaryKey(inviteInfo);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		inviteInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<InviteInfo> getMyInviteInfoList(String openId) {
		// TODO Auto-generated method stub
		return inviteInfoMapper.getMyInviteInfo(openId);
	}

}
