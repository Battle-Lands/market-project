package com.github.battle.market.event;

import com.github.battle.market.entity.ShopEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public abstract class ShopEvent extends Event {

    private final ShopEntity shopEntity;
    private final Player player;

    private static final HandlerList handlers = new HandlerList();

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
