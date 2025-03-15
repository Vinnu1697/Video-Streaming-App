package com.eeze.videostreaming.repository;

/*
 * @author : Vinit Udawant
 */


import com.eeze.videostreaming.model.Engagement;
import com.eeze.videostreaming.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    Optional<Engagement> findByVideo(Video video);
}
