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
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String synopsis;
    private String director;
    private String cast;
    private int yearOfRelease;
    private String genre;
    private int runningTime;
    private boolean isDeleted;
}
