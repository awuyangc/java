package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.NewsFileMapper;
import com.wy.dao.NewsMapper;
import com.wy.model.News;
import com.wy.model.NewsFile;
import com.wy.service.INewsFileService;

@Service("newsFileService")
public class NewsFileServiceImpl implements INewsFileService {

	@Resource
	private NewsFileMapper newsFileMapper;
	
	@Override
	public List<NewsFile> getNewsFileList() {
		// TODO Auto-generated method stub
		return newsFileMapper.selectAll();
	}

	@Override
	public void insert(NewsFile newsFile) {
		// TODO Auto-generated method stub
		newsFileMapper.insert(newsFile);
	}

	@Override
	public void update(NewsFile newsFile) {
		// TODO Auto-generated method stub
		newsFileMapper.updateByPrimaryKey(newsFile);
	}

	@Override
	public void delete(NewsFile newsFile) {
		// TODO Auto-generated method stub
		newsFileMapper.deleteByPrimaryKey(newsFile.getFileId());
	}

	@Override
	public void deleteByNewsId(Integer newsId) {
		// TODO Auto-generated method stub
		newsFileMapper.deleteByNewsId(newsId);
	}

	@Override
	public List<NewsFile> selectByNewsId(Integer newsId) {
		// TODO Auto-generated method stub
		return newsFileMapper.selectByNewsId(newsId);
	}

	@Override
	public NewsFile getNewsFileById(Integer fileId) {
		// TODO Auto-generated method stub
		return newsFileMapper.selectByPrimaryKey(fileId);
	}

}
