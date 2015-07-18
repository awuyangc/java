package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.wy.dao.PictureMapper;
import com.wy.model.Picture;
import com.wy.service.IPictureService;
@Service("pictureService")
public class PictureServiceImpl implements IPictureService {
	@Resource
	private PictureMapper pictureMapper;
	
	@Override
	public Picture getPictureById(Integer picId) {
		// TODO Auto-generated method stub
		return pictureMapper.selectByPrimaryKey(picId);
	}

	@Override
	public List<Picture> getPictureList() {
		// TODO Auto-generated method stub
		return pictureMapper.selectAll();
	}

	@Override
	public void insert(Picture picture) {
		// TODO Auto-generated method stub
		pictureMapper.insert(picture);
	}

	@Override
	public void update(Picture picture) {
		// TODO Auto-generated method stub
		pictureMapper.updateByPrimaryKey(picture);
	}

	@Override
	public void delete(Integer picId) {
		pictureMapper.deleteByPrimaryKey(picId);
	}

}
