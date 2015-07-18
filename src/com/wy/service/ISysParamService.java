package com.wy.service;

import java.util.List;

import com.wy.model.SysParam;

public interface ISysParamService {
	public SysParam getSysParamByName(String sysParamName);
	public List<SysParam> getSysParamList();
	public void insert(SysParam sysParam);
	public void update(SysParam sysParam);
	public void delete(SysParam sysParam);
}
