package com.wy.service;

import java.util.List;

import com.wy.model.BaiduPoi;

public interface IBaiduPoiService {
	public BaiduPoi getBaiduPoiById(String baiduPoiId);
	public List<BaiduPoi> getBaiduPoiList();
	public void insert(BaiduPoi baiduPoi);
	public void update(BaiduPoi baiduPoi);
	public void delete(BaiduPoi baiduPoi);
}
