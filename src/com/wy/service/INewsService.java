package com.wy.service;

import java.util.List;
import java.util.Map;
import com.wy.model.News;
import com.wy.model.NewsFile;

public interface INewsService {
	public News selectByPrimaryKey(Integer newsId);
	public List<News> selectAll(Map map);
	public void insert(News news,List<NewsFile> newsFileList);
	public void update(News news,List<NewsFile> newsFileList);
	public void delete(News news);
	public void updatePublish(News news);
	public void updateTop(News news);
	public void updateDelete(News news);
	public  List<News> selectAllByType(Integer typeId);
	public int selectTotal(Map map);
}
