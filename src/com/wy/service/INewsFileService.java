package com.wy.service;

import java.util.List;
import com.wy.model.NewsFile;

public interface INewsFileService {
	public NewsFile getNewsFileById(Integer fileId);
	public List<NewsFile> getNewsFileList();
	public void insert(NewsFile newsFile);
	public void update(NewsFile newsFile);
	public void delete(NewsFile newsFile);
	public void deleteByNewsId(Integer newsId);
	public List<NewsFile> selectByNewsId(Integer newsId);
}
