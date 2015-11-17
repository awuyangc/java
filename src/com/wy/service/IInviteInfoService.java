package com.wy.service;

import java.util.List;
import java.util.Map;

import com.wy.model.InviteInfo;

public interface IInviteInfoService {
	public InviteInfo getInviteInfoById(Integer id);
	public List<InviteInfo> getInviteInfoList();
	public List<InviteInfo> getMyInviteInfoList(Map map);
	public void insert(InviteInfo inviteInfo);
	public void update(InviteInfo inviteInfo);
	public void delete(Integer id);
}
