package org.magnum.mobilecloud.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.*;

import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VideoServiceImp implements VideoService{

	@Autowired
    private VideoRepository videoRepository;
	
	@Override
	public Collection<Video> getVideoList() {
		List<Video> videoList = new ArrayList<>();
		videoRepository.findAll().forEach(videoList::add);
		return videoList;
	}

	@Override
	public Video addVideo(Video video) {
		assert (video != null);
		return videoRepository.save(video);
	}

	@Override
	public Video getVideoById(long id) throws IOException {
		return videoRepository.findOne(id);
	}

	@Override
	public Collection<Video> getVideoByTitle(String title) {
		return videoRepository.findByNameLike(title);
	}

	@Override
	public Collection<Video> getVideoByDurationLessThan(long duration) {
		return videoRepository.findByDurationLessThan(duration);
	}

	private Video addLike (Video v, String name) {
		v.setLikes(v.getLikes()+1);
		v.getLikedBy().add(name);
		return v;
	}
	
	private Video removeLike(Video v, String name) {
		v.setLikes(v.getLikes()-1);
		v.getLikedBy().remove(name);
		return v;
	}
	
	@Override
	public void likeVideo(long id, Principal p) throws FileNotFoundException {
		Video v = videoRepository.findOne(id);
		if (Objects.isNull(v)) throw new FileNotFoundException("Video metadata not found");
		if(!v.getLikedBy().contains(p.getName()))
			//add like
			videoRepository.save(addLike(v, p.getName()));
	}
	

	@Override
	public void unLikeVideo(long id, Principal p) throws FileNotFoundException {
		Video v = videoRepository.findOne(id);
		if (Objects.isNull(v)) throw new FileNotFoundException("Video metadata not found");
		if(v.getLikedBy().contains(p.getName()))
			//remove like
			videoRepository.save(removeLike(v, p.getName()));
	}

}
