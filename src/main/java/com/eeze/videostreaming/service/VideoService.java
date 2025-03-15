package com.eeze.videostreaming.service;

/*
 * @author : Vinit Udawant
 */


import com.eeze.videostreaming.model.Video;
import com.eeze.videostreaming.model.Engagement;
import com.eeze.videostreaming.repository.VideoRepository;
import com.eeze.videostreaming.repository.EngagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final EngagementRepository engagementRepository;

    public VideoService(VideoRepository videoRepository, EngagementRepository engagementRepository) {
        this.videoRepository = videoRepository;
        this.engagementRepository = engagementRepository;
    }

    public Video publishVideo(Video video) {
        log.info("Publishing new video: {}", video.getTitle());
        return videoRepository.save(video);
    }

    public Optional<Video> getVideo(Long id) {
        log.info("Fetching video with ID: {}", id);
        return videoRepository.findByIdAndIsDeletedFalse(id);
    }

    public Optional<Video> updateVideo(Long id, Video video) {
        log.info("Updating video with ID: {}", id);
        return videoRepository.findById(id).map(existing -> {
            existing.setTitle(video.getTitle());
            existing.setSynopsis(video.getSynopsis());
            existing.setDirector(video.getDirector());
            existing.setCast(video.getCast());
            existing.setYearOfRelease(video.getYearOfRelease());
            existing.setGenre(video.getGenre());
            existing.setRunningTime(video.getRunningTime());
            return videoRepository.save(existing);
        });
    }

    public void deleteVideo(Long id) {
        log.info("Soft deleting video with ID: {}", id);
        videoRepository.findById(id).ifPresent(video -> {
            video.setDeleted(true);
            videoRepository.save(video);
        });
    }

    public List<Video> listVideos() {
        log.info("Listing all available videos");
        return videoRepository.findByIsDeletedFalse();
    }

    public List<Video> searchByDirector(String director) {
        log.info("Searching videos by director: {}", director);
        return videoRepository.findByDirectorContainingIgnoreCaseAndIsDeletedFalse(director);
    }

    public List<Video> searchByGenre(String genre) {
        log.info("Searching videos by genre: {}", genre);
        return videoRepository.findByGenreContainingIgnoreCaseAndIsDeletedFalse(genre);
    }

    public Optional<String> playVideo(Long id) {
        log.info("Playing video with ID: {}", id);
        return videoRepository.findById(id)
                .filter(video -> !video.isDeleted())
                .map(video -> {
                    incrementViews(video);
                    return "Playing video: " + video.getTitle();
                });
    }

    public Optional<String> getEngagementStats(Long id) {
        log.info("Fetching engagement stats for video ID: {}", id);
        return videoRepository.findById(id)
                .filter(video -> !video.isDeleted())
                .flatMap(video ->
                        engagementRepository.findByVideo(video).map(e ->
                                "Impressions: " + e.getImpressions() + ", Views: " + e.getViews()));
    }

    public Optional<Video> loadVideo(Long id) {
        log.info("Loading video ID: {}", id);
        return videoRepository.findByIdAndIsDeletedFalse(id).map(video -> {
            incrementImpressions(video);
            return video;
        });
    }

    private void incrementImpressions(Video video) {
        log.info("Incrementing impressions for video ID: {}", video.getId());
        Engagement engagement = engagementRepository.findByVideo(video)
                .orElse(new Engagement(null, video, 0, 0));
        engagement.setImpressions(engagement.getImpressions() + 1);
        engagementRepository.save(engagement);
    }

    private void incrementViews(Video video) {
        log.info("Incrementing views for video ID: {}", video.getId());
        Engagement engagement = engagementRepository.findByVideo(video)
                .orElse(new Engagement(null, video, 0, 0));
        engagement.setViews(engagement.getViews() + 1);
        engagementRepository.save(engagement);
    }
}
