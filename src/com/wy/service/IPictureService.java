package com.wy.service;

import java.util.List;

import com.wy.model.Picture;

public interface IPictureService {
	public Picture getPictureById(Integer picId);
	public List<Picture> getPictureList();
	public void insert(Picture picture);
	public void update(Picture picture);
	public void delete(Integer picId);
	
}
