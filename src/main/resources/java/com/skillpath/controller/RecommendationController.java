package com.skillpath.controller;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.skillpath.service.GraphService;

@RestController
public class RecommendationController {

    private final GraphService graphService;

    public RecommendationController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/api/recommendations/{studentId}")
    public List<Map<String, Object>> getRecommendations(
            @PathVariable String studentId) {

        return graphService.getRecommendations(studentId);
    }
}