package com.wy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wy.dao.NewsFileMapper;
import com.wy.dao.NewsMapper;
import com.wy.model.News;
import com.wy.model.NewsFile;
import com.wy.model.NewsFileCollection;
import com.wy.service.INewsService;

@Service("newsService")
public class NewsServiceImpl implements INewsService {

	@Resource
	private NewsMapper newsMapper;
	@Resource
	private NewsFileMapper newsFileMapper;
	
	@Override
	@Transactional
	public void insert(News news,List<NewsFile> newsFileList) {
		// TODO Auto-generated method stub
		//首先插入新闻
		newsMapper.insert(news);
		//然后插入附件
		for(NewsFile newsFile:newsFileList)
		{
			newsFile.setNewsId(news.getNewsId());
			newsFile.setTypeId(news.getTypeId());
			newsFileMapper.insert(newsFile);
		}
	}

	@Override
	@Transactional
	public void update(News news,List<NewsFile> newsFileList) {
		// TODO Auto-generated method stub
		//先更新新闻
		newsMapper.updateByPrimaryKey(news);
		//然后更新附件，采用先删除，在保存的方式
		newsFileMapper.deleteByNewsId(news.getNewsId());
		for(NewsFile newsFile:newsFileList)
		{
			newsFile.setNewsId(news.getNewsId());
			newsFile.setTypeId(news.getTypeId());
			newsFileMapper.insert(newsFile);
		}
	}

	@Override
	public void delete(News news) {
		// TODO Auto-generated method stub
		newsMapper.deleteByPrimaryKey(news.getNewsId());
	}

	@Override
	public void updatePublish(News news) {
		// TODO Auto-generated method stub
		newsMapper.updatePublish(news);
	}

	@Override
	public void updateTop(News news) {
		// TODO Auto-generated method stub
		newsMapper.updateTop(news);
	}

	@Override
	public void updateDelete(News news) {
		// TODO Auto-generated method stub
		newsMapper.updateDelete(news);
	}

	@Override
	public List<News> selectAllByType(Integer typeId) {
		// TODO Auto-generated method stub
		return newsMapper.selectAllByType(typeId);
	}



	@Override
	public List<News> selectAll(Map map) {
		// TODO Auto-generated method stub
		return newsMapper.selectAll(map);
	}



	@Override
	public int selectTotal(Map map) {
		// TODO Auto-generated method stub
		return newsMapper.selectTotal(map);
	}

	@Override
	public News selectByPrimaryKey(Integer newsId) {
		// TODO Auto-generated method stub
		return newsMapper.selectByPrimaryKey(newsId);
	}
	
}
