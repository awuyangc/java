package com.wy.service;

import java.util.List;

import com.wy.model.InviteInfo;

public interface IInviteInfoService {
	public InviteInfo getInviteInfoById(Integer id);
	public List<InviteInfo> getInviteInfoList();
	public void insert(InviteInfo inviteInfo);
	public void update(InviteInfo inviteInfo);
	public void delete(Integer id);
}
