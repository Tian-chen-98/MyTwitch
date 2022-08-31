package com.tianchen.twitch.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tianchen.twitch.entity.db.Item;

public class FavoriteRequestBody {
    @JsonProperty("favorite")
    private Item favoriteItem;

    public Item getFavoriteItem() {
    return favoriteItem;
    } 
}
