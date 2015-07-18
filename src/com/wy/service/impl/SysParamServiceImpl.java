package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.SysParamMapper;
import com.wy.model.SysParam;
import com.wy.service.ISysParamService;

@Service("sysParamService")
public class SysParamServiceImpl implements ISysParamService {

	@Resource
	private SysParamMapper sysParamMapper;

	@Override
	public SysParam getSysParamByName(String sysParamName) {
		// TODO Auto-generated method stub
		return sysParamMapper.selectByPrimaryKey(sysParamName);
	}
	
	@Override
	public List<SysParam> getSysParamList() {
		// TODO Auto-generated method stub
		return sysParamMapper.selectAll();
	}

	@Override
	public void insert(SysParam sysParam) {
		// TODO Auto-generated method stub
		sysParamMapper.insert(sysParam);
	}

	@Override
	public void update(SysParam sysParam) {
		// TODO Auto-generated method stub
		sysParamMapper.updateByPrimaryKey(sysParam);
	}

	@Override
	public void delete(SysParam sysParam) {
		// TODO Auto-generated method stub
		sysParamMapper.deleteByPrimaryKey(sysParam.getSysParamId());
	}




}
