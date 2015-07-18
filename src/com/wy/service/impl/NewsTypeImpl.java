package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.NewsTypeMapper;
import com.wy.model.NewsType;
import com.wy.service.INewsTypeService;

@Service("newsTypeService")
public class NewsTypeImpl implements INewsTypeService {
	@Resource
	private NewsTypeMapper newsTypeMapper;
	
	@Override
	public NewsType getNewsTypeById(NewsType newsType) {
		// TODO Auto-generated method stub
		return newsTypeMapper.selectByPrimaryKey(newsType.getTypeId());
	}

	@Override
	public List<NewsType> getNewsTypeList() {
		// TODO Auto-generated method stub
		return newsTypeMapper.selectAll();
	}

	@Override
	public void insert(NewsType newsType) {
		newsTypeMapper.insert(newsType);
	}

	@Override
	public void update(NewsType newsType) {
		// TODO Auto-generated method stub
		newsTypeMapper.updateByPrimaryKey(newsType);
	}

	@Override
	public void delete(NewsType newsType) {
		// TODO Auto-generated method stub
		newsTypeMapper.deleteByPrimaryKey(newsType.getTypeId());
	}

	@Override
	public List<NewsType> selectAllEnable() {
		// TODO Auto-generated method stub
		return newsTypeMapper.selectAllEnable();
	}
	
}
