package com.github.battle.market.event;

import com.github.battle.market.entity.ShopEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
public abstract class ShopEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    private final ShopEntity shopEntity;
    private final Player player;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public ShopEvent callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }
}
