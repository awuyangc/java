package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.BaiduPoiMapper;
import com.wy.model.BaiduPoi;
import com.wy.service.IBaiduPoiService;

@Service("baiduPoiService")
public class BaiduPoiServiceImpl implements IBaiduPoiService {

	@Resource
	private BaiduPoiMapper baiduPoiMapper;
	
	@Override
	public BaiduPoi getBaiduPoiById(String uid) {
		// TODO Auto-generated method stub
		return baiduPoiMapper.selectByPrimaryKey(uid);
	}

	@Override
	public List<BaiduPoi> getBaiduPoiList() {
		// TODO Auto-generated method stub
		return baiduPoiMapper.selectAll();
	}

	@Override
	public void insert(BaiduPoi baiduPoi) {
		// TODO Auto-generated method stub
		baiduPoiMapper.insert(baiduPoi);
	}

	@Override
	public void update(BaiduPoi baiduPoi) {
		// TODO Auto-generated method stub
		baiduPoiMapper.updateByPrimaryKey(baiduPoi);
	}

	@Override
	public void delete(BaiduPoi baiduPoi) {
		// TODO Auto-generated method stub
		baiduPoiMapper.deleteByPrimaryKey(baiduPoi.getUid());
	}

}
