package org.magnum.mobilecloud.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

import org.magnum.mobilecloud.video.repository.Video;

public interface VideoService {
	Collection<Video> getVideoList();

    Video addVideo(Video video);

    Video getVideoById(long id) throws IOException;

    Collection<Video> getVideoByTitle(String title);

    Collection<Video> getVideoByDurationLessThan(long duration);

    void likeVideo(long id, Principal p) throws FileNotFoundException;

    void unLikeVideo(long id, Principal p) throws FileNotFoundException;
}
