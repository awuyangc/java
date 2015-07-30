package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.JoinInfoMapper;
import com.wy.model.JoinInfo;
import com.wy.service.IJoinInfoService;

@Service("joinInfoService")
public class JoinInfoServiceImpl implements IJoinInfoService {

	@Resource
	private JoinInfoMapper joinInfoMapper;	
	
	@Override
	public JoinInfo getJoinInfoById(Integer id) {
		// TODO Auto-generated method stub
		return joinInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<JoinInfo> getJoinInfoList() {
		// TODO Auto-generated method stub
		return joinInfoMapper.selectAll();
	}

	@Override
	public void insert(JoinInfo joinInfo) {
		// TODO Auto-generated method stub
		joinInfoMapper.insert(joinInfo);
	}

	@Override
	public void update(JoinInfo joinInfo) {
		// TODO Auto-generated method stub
		joinInfoMapper.updateByPrimaryKey(joinInfo);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		joinInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<JoinInfo> getJoinInfoByInviteId(Integer inviteId) {
		// TODO Auto-generated method stub
		return joinInfoMapper.selectByInviteId(inviteId);
	}

	@Override
	public void deleteByInviteId(JoinInfo joinInfo) {
		// TODO Auto-generated method stub
		joinInfoMapper.deleteByInviteId(joinInfo);
	}

	@Override
	public JoinInfo checkMySignInfo(JoinInfo joinInfo) {
		// TODO Auto-generated method stub
		return joinInfoMapper.checkMySignInfo(joinInfo);
	}

	@Override
	public List<JoinInfo> selectMySignInfo(String openId) {
		// TODO Auto-generated method stub
		return joinInfoMapper.selectMySignInfo(openId);
	}

}
