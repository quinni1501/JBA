package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.IVideoDao;
import vn.iotstar.dao.impl.VideoDao;
import vn.iotstar.entity.Video;
import vn.iotstar.services.IVideoService;

public class VideoService implements IVideoService{

	IVideoDao vidDao = new VideoDao();
	@Override
	public int count() {
		return vidDao.count();
	}

	@Override
	public List<Video> findAll(int page, int pagesize) {
		return vidDao.findAll(page, pagesize);
	}

	@Override
	public List<Video> findByVideotitle(String vidTitle) {
		return vidDao.findByVideotitle(vidTitle);
	}

	@Override
	public List<Video> findAll() {
		return vidDao.findAll();
	}

	@Override
	public Video findById(String vidId) {
		return vidDao.findById(vidId);
	}

	@Override
	public void delete(String vidId) throws Exception {
		vidDao.delete(vidId);
	}

	@Override
	public void update(Video video) {
		vidDao.update(video);
		
	}

	@Override
	public void insert(Video video) {
		vidDao.insert(video);
		
	}
	
	

	
	

}
