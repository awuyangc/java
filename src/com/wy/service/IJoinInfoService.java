package com.wy.service;

import java.util.List;
import com.wy.model.JoinInfo;

public interface IJoinInfoService {
	public JoinInfo getJoinInfoById(Integer id);
	public List<JoinInfo> getJoinInfoList();
	public void insert(JoinInfo joinInfo);
	public void update(JoinInfo joinInfo);
	public void delete(Integer id);
}
