package com.eeze.videostreaming.controller;

/*
 * @author : Vinit Udawant
 */


import com.eeze.videostreaming.exception.VideoNotFoundException;
import com.eeze.videostreaming.model.Video;
import com.eeze.videostreaming.service.VideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public Video publishVideo(@RequestBody Video video) {
        return videoService.publishVideo(video);
    }

    @GetMapping("/{id}/load")
    public ResponseEntity<Video> loadVideo(@PathVariable Long id) {
        return ResponseEntity.of(videoService.loadVideo(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video video) {
        return ResponseEntity.of(videoService.updateVideo(id, video));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        return videoService.getVideo(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new VideoNotFoundException("Video with ID: " + id + " not found"));
    }

    @GetMapping("/{id}/play")
    public ResponseEntity<String> playVideo(@PathVariable Long id) {
        return ResponseEntity.of(videoService.playVideo(id));
    }

    @GetMapping
    public List<Video> listVideos() {
        return videoService.listVideos();
    }

    @GetMapping("/search")
    public List<Video> searchVideos(@RequestParam(required = false) String director,
                                    @RequestParam(required = false) String genre) {
        if (director != null) return videoService.searchByDirector(director);
        if (genre != null) return videoService.searchByGenre(genre);
        return videoService.listVideos();
    }

    @GetMapping("/{id}/engagement")
    public ResponseEntity<String> getEngagementStats(@PathVariable Long id) {
        return ResponseEntity.of(videoService.getEngagementStats(id));
    }
}
