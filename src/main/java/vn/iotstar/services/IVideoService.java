package vn.iotstar.services;

import java.util.List;

import vn.iotstar.entity.Video;

public interface IVideoService {
	int count();

	List<Video> findAll(int page, int pagesize);

	List<Video> findByVideotitle(String vidTitle);

	List<Video> findAll();

	Video findById(String vidId);

	void delete(String vidId) throws Exception;

	void update(Video video);

	void insert(Video video);

}
