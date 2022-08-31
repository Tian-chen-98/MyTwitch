package com.tianchen.twitch.service;

public class RecommendationException extends RuntimeException{
    public RecommendationException(String errorMessage) {
        super(errorMessage);
    }
}
