package org.magnum.mobilecloud.video.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long>{

    Collection<Video> findByDurationLessThan(long duration);

    Collection<Video> findByNameLike(String title);

}
