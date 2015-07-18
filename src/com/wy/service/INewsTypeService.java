package com.wy.service;

import java.util.List;

import com.wy.model.NewsType;

public interface INewsTypeService {
	public NewsType getNewsTypeById(NewsType newsType);
	public List<NewsType> getNewsTypeList();
	public List<NewsType> selectAllEnable();
	public void insert(NewsType newsType);
	public void update(NewsType newsType);
	public void delete(NewsType newsType);
}
