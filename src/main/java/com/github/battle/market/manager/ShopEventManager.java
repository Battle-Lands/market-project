package com.github.battle.market.manager;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopState;
import com.github.battle.market.event.ShopEvent;
import com.github.battle.market.event.ShopUpdateEvent;
import com.github.battle.market.event.update.*;
import com.github.battle.market.job.ShopUpdateQueue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("all")
public final class ShopEventManager {

    private final ShopUpdateQueue shopUpdateQueue;

    public ShopEvent setLocation(@NonNull ShopEntity shopEntity, @NonNull Player player, Location location) {
        final ShopUpdateEvent event = location != null
          ? new ShopLocationUpdateEvent(shopEntity, player)
          : new ShopLocationRemovedEvent(shopEntity, player);

        event.setOldValue(shopEntity.getLocation());
        event.setNewValue(location);

        shopEntity.setLocation(location);
        return addToQueue(event.callEvent());
    }

    public ShopEvent setDescription(@NonNull ShopEntity shopEntity, @NonNull Player player, String description) {
        final ShopUpdateEvent event = description != null
          ? new ShopDescriptionUpdateEvent(shopEntity, player)
          : new ShopDescriptionRemovedEvent(shopEntity, player);

        event.setOldValue(shopEntity.getDescription());
        event.setNewValue(description);

        shopEntity.setDescription(description);
        return addToQueue(event.callEvent());
    }

    public ShopEvent proceduralCheckShop(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        final ShopUpdateEvent shopEvent = new ShopCreatedEvent(shopEntity, player);

        changeStatus(shopEntity, shopEvent, ShopState.ACCESSIBLE);
        return addToQueue(shopEvent.callEvent());
    }

    public ShopEvent invalidateShop(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        final ShopUpdateEvent shopEvent = new ShopRemovedEvent(shopEntity, player);

        shopEntity.setDescription(null);
        shopEntity.setLocation(null);

        changeStatus(shopEntity, shopEvent, ShopState.REMOVED);
        return addToQueue(shopEvent.callEvent());
    }

    public ShopEvent banShop(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        final ShopUpdateEvent shopEvent = new ShopBannedEvent(shopEntity, player);

        changeStatus(shopEntity, shopEvent, ShopState.BANNED);
        return addToQueue(shopEvent.callEvent());
    }

    private void changeStatus(@NonNull ShopEntity shopEntity, @NonNull ShopUpdateEvent shopEvent, @NonNull ShopState state) {
        shopEvent.setOldValue(shopEntity.getState());
        shopEvent.setNewValue(state);
        shopEntity.setState(state);
    }

    public ShopEvent addToQueue(@NonNull ShopEvent shopEvent) {
        shopUpdateQueue.addUpdateToQueue((ShopUpdateEvent) shopEvent);
        return shopEvent;
    }
}
