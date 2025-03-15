package com.eeze.videostreaming.model;

/*
 * @author : Vinit Udawant
 */


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    private int impressions;
    private int views;
}
