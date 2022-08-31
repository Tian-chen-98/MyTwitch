package com.tianchen.twitch.controller;

import com.tianchen.twitch.entity.db.Item;
import com.tianchen.twitch.service.RecommendationException;
import com.tianchen.twitch.service.RecommendationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;
    
    @RequestMapping(value = "/recommendation", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Item>> recommendation(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession(false);
        Map<String, List<Item>> itemMap;
        try {
            // if not logged it, recommend top trending on the platform
            if (session == null) {
                itemMap = recommendationService.recommendItemsByDefault();
            } else {
                // if logged in, recommend by user's favorites
                String userId = (String) session.getAttribute("user_id");
                itemMap = recommendationService.recommendItemsByUser(userId);
            }
        } catch (RecommendationException e) {
            throw new ServletException(e);
        }
        return itemMap;
    }
}



