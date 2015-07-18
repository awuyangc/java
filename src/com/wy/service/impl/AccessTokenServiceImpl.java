package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.AccessTokenMapper;
import com.wy.model.AccessToken;
import com.wy.service.IAccessTokenServie;

@Service("accessTokenService")
public class AccessTokenServiceImpl implements IAccessTokenServie {
	
	@Resource
	private AccessTokenMapper accessTokenMapper;
	
	@Override
	public AccessToken getAccessTokenById(Integer accessTokenId) {
		// TODO Auto-generated method stub
		return accessTokenMapper.selectByPrimaryKey(accessTokenId);
	}

	@Override
	public List<AccessToken> getAccessTokenList() {
		// TODO Auto-generated method stub
		return accessTokenMapper.selectAll();
	}

	@Override
	public void insert(AccessToken accessToken) {
		// TODO Auto-generated method stub
		accessTokenMapper.insert(accessToken);
	}

	@Override
	public void update(AccessToken accessToken) {
		// TODO Auto-generated method stub
		accessTokenMapper.updateByPrimaryKey(accessToken);
	}

	@Override
	public void delete(AccessToken accessToken) {
		// TODO Auto-generated method stub
		accessTokenMapper.deleteByPrimaryKey(accessToken.getId());
	}

	@Override
	public void deleteByAccessTokenId(Integer accessTokenId) {
		// TODO Auto-generated method stub
		accessTokenMapper.deleteByPrimaryKey(accessTokenId);
	}

}
