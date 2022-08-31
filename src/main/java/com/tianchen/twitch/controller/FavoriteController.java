package com.tianchen.twitch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianchen.twitch.entity.db.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianchen.twitch.entity.request.FavoriteRequestBody;
import com.tianchen.twitch.service.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class FavoriteController {
    @Autowired
   private FavoriteService favoriteService;

   @RequestMapping(value = "/favorite", method = RequestMethod.POST)
   public void setFavoriteItem(@RequestBody FavoriteRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) {
       // session for user authentication purpose. If user not logged in, return 403 Forbidden
       HttpSession session = request.getSession(false);
       if (session == null) {
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           return ;
       }
       // retrieve user id
       String userId = (String) session.getAttribute("user_id");
       favoriteService.setFavoriteItem(userId, requestBody.getFavoriteItem());
   }

   @RequestMapping(value = "/favorite", method = RequestMethod.DELETE)
   public void unsetFavoriteItem(@RequestBody FavoriteRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) {
       HttpSession session = request.getSession(false);
       if (session == null) {
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           return;
       }
       String userId = (String) session.getAttribute("user_id");
       // item id that needs to be removed is from requestBody
       favoriteService.unsetFavoriteItem(userId, requestBody.getFavoriteItem().getId());
   }

   @RequestMapping(value = "/favorite", method = RequestMethod.GET)
   @ResponseBody
   public Map<String, List<Item>> getFavoriteItem(HttpServletRequest request, HttpServletResponse response) {
       HttpSession session = request.getSession(false);
       if (session == null) {
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           return new HashMap<>();
       }
       String userId = (String) session.getAttribute("user_id");
       return favoriteService.getFavoriteItems(userId);
   }

}
