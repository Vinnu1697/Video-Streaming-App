package com.eeze.videostreaming;

import com.eeze.videostreaming.model.Video;
import com.eeze.videostreaming.repository.EngagementRepository;
import com.eeze.videostreaming.repository.VideoRepository;
import com.eeze.videostreaming.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private EngagementRepository engagementRepository;


    @InjectMocks
    private VideoService videoService;

    private Video activeVideo;
    private Video deletedVideo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activeVideo = new Video(1L, "Active Video", "Synopsis", "Director",
                "Cast", 2024, "Action", 120, false);
        deletedVideo = new Video(2L, "Deleted Video", "Synopsis", "Director",
                "Cast", 2024, "Action", 120, true);

        when(videoRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(activeVideo));
        when(videoRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.empty());
    }



    @Test
    void testPublishVideo() {
        when(videoRepository.save(any(Video.class))).thenReturn(activeVideo);

        Video savedVideo = videoService.publishVideo(activeVideo);

        assertNotNull(savedVideo);
        assertEquals("Active Video", savedVideo.getTitle());
        verify(videoRepository, times(1)).save(activeVideo);
    }

    @Test
    void testGetVideo_Active() {
        when(videoRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(activeVideo));

        Optional<Video> retrievedVideo = videoService.getVideo(1L);

        assertTrue(retrievedVideo.isPresent());
        assertEquals("Active Video", retrievedVideo.get().getTitle());

        verify(videoRepository, times(1)).findByIdAndIsDeletedFalse(1L);
    }


    @Test
    void testGetVideo_Deleted() {
        when(videoRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.empty());

        Optional<Video> retrievedVideo = videoService.getVideo(2L);

        assertTrue(retrievedVideo.isEmpty());
        verify(videoRepository, times(1)).findByIdAndIsDeletedFalse(2L);
    }



    @Test
    void testListVideos() {
        when(videoRepository.findByIsDeletedFalse()).thenReturn(List.of(activeVideo));

        List<Video> videos = videoService.listVideos();

        assertFalse(videos.isEmpty());
        assertEquals(1, videos.size());
        assertEquals("Active Video", videos.get(0).getTitle());
        verify(videoRepository, times(1)).findByIsDeletedFalse();
    }

    @Test
    void testPlayVideo_Active() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(activeVideo));

        Optional<String> playResult = videoService.playVideo(1L);

        assertTrue(playResult.isPresent());
        assertEquals("Playing video: Active Video", playResult.get());
        verify(videoRepository, times(1)).findById(1L);
    }

    @Test
    void testPlayVideo_Deleted() {
        when(videoRepository.findById(2L)).thenReturn(Optional.of(deletedVideo));

        Optional<String> playResult = videoService.playVideo(2L);

        assertFalse(playResult.isPresent());
        verify(videoRepository, times(1)).findById(2L);
    }

    @Test
    void testDeleteVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(activeVideo));

        videoService.deleteVideo(1L);

        assertTrue(activeVideo.isDeleted());
        verify(videoRepository, times(1)).findById(1L);
        verify(videoRepository, times(1)).save(activeVideo);
    }
}
