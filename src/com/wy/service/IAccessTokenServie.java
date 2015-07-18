package com.wy.service;

import java.util.List;

import com.wy.model.AccessToken;

public interface IAccessTokenServie {
	public AccessToken getAccessTokenById(Integer accessTokenId);
	public List<AccessToken> getAccessTokenList();
	public void insert(AccessToken accessToken);
	public void update(AccessToken accessToken);
	public void delete(AccessToken accessToken);
	public void deleteByAccessTokenId(Integer accessTokenId);
}
