package com.eeze.videostreaming.exception;

/*
 * @author : Vinit Udawant
 */


public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}