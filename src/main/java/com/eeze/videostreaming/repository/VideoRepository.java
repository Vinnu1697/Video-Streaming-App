package com.eeze.videostreaming.repository;

/*
 * @author : Vinit Udawant
 */


import com.eeze.videostreaming.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByDirectorContainingIgnoreCaseAndIsDeletedFalse(String director);

    List<Video> findByGenreContainingIgnoreCaseAndIsDeletedFalse(String genre);

    List<Video> findByIsDeletedFalse();

    Optional<Video> findByIdAndIsDeletedFalse(Long id);

}
