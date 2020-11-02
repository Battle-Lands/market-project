package com.github.battle.market.manager;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.shop.ShopState;
import com.github.battle.market.event.ShopBanEvent;
import com.github.battle.market.event.ShopEvent;
import com.github.battle.market.event.ShopTransactionEvent;
import com.github.battle.market.event.ShopUpdateEvent;
import com.github.battle.market.event.ban.ShopBannedEvent;
import com.github.battle.market.event.ban.ShopUnbannedEvent;
import com.github.battle.market.event.state.ShopCreatedEvent;
import com.github.battle.market.event.state.ShopRemovedEvent;
import com.github.battle.market.event.transaction.ShopTransactionBuyEvent;
import com.github.battle.market.event.transaction.ShopTransactionSellEvent;
import com.github.battle.market.event.update.ShopDescriptionRemovedEvent;
import com.github.battle.market.event.update.ShopDescriptionUpdateEvent;
import com.github.battle.market.event.update.ShopLocationRemovedEvent;
import com.github.battle.market.event.update.ShopLocationUpdateEvent;
import com.github.battle.market.job.queue.ShopTransactionQueue;
import com.github.battle.market.job.queue.ShopUpdateQueue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
@SuppressWarnings("all")
public final class ShopEventManager {

    private final ShopTransactionQueue transactionQueue;
    private final ShopUpdateQueue shopUpdateQueue;
    private final ShopBanManager shopBanManager;

    // UPDATE SECTION

    public ShopEvent setLocation(@NonNull ShopEntity shopEntity, @NonNull Player player, Location location) {
        final ShopUpdateEvent event = location != null
          ? new ShopLocationUpdateEvent(shopEntity, player)
          : new ShopLocationRemovedEvent(shopEntity, player);

        event.setOldValue(shopEntity.getLocation());
        event.setNewValue(location);

        shopEntity.setLocation(location);
        return addToEventQueue(event.callEvent());
    }

    public ShopEvent setDescription(@NonNull ShopEntity shopEntity, @NonNull Player player, String description) {
        final ShopUpdateEvent event = description != null
          ? new ShopDescriptionUpdateEvent(shopEntity, player)
          : new ShopDescriptionRemovedEvent(shopEntity, player);

        event.setOldValue(shopEntity.getDescription());
        event.setNewValue(description);

        shopEntity.setDescription(description);
        return addToEventQueue(event.callEvent());
    }

    public ShopEvent proceduralCheckShop(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        final ShopUpdateEvent shopEvent = new ShopCreatedEvent(shopEntity, player);

        changeStatus(shopEntity, shopEvent, ShopState.ACCESSIBLE);
        return addToEventQueue(shopEvent.callEvent());
    }

    public ShopEvent invalidateShop(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        final ShopUpdateEvent shopEvent = new ShopRemovedEvent(shopEntity, player);
        clearAllShopInfo(shopEntity);

        changeStatus(shopEntity, shopEvent, ShopState.REMOVED);
        return addToEventQueue(shopEvent.callEvent());
    }

    public void clearAllShopInfo(@NonNull ShopEntity shopEntity) {
        shopEntity.setDescription(null);
        shopEntity.setLocation(null);
    }

    private void changeStatus(@NonNull ShopEntity shopEntity, @NonNull ShopUpdateEvent shopEvent, @NonNull ShopState state) {
        shopEvent.setOldValue(shopEntity.getRawState());
        shopEvent.setNewValue(state);
        shopEntity.setState(state);
    }

    // BAN SECTION

    public ShopBanEvent banShop(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull String reason) {
        final ShopBanEvent shopEvent = new ShopBannedEvent(shopEntity, player);
        clearAllShopInfo(shopEntity);

        shopEvent.setReason(reason);
        changeStatus(shopEntity, shopEvent, ShopState.BANNED);

        final ShopBanEvent shopBanEvent = (ShopBanEvent) addToEventQueue(shopEvent.callEvent());
        return addToBanQueue(shopBanEvent);
    }

    public ShopBanEvent unbanShop(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull String reason) {
        final ShopBanEvent shopEvent = new ShopUnbannedEvent(shopEntity, player);

        shopEvent.setReason(reason);
        changeStatus(shopEntity, shopEvent, ShopState.ACCESSIBLE);

        final ShopBanEvent shopBanEvent = (ShopBanEvent) addToEventQueue(shopEvent.callEvent());
        return addToBanQueue(shopBanEvent);
    }

    // TRANSACTION SECTION

    public ShopTransactionEvent buyShopAction(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull long price, @NonNull ItemStack itemStack) {
        final ShopTransactionEvent shopEvent = new ShopTransactionBuyEvent(
          shopEntity,
          player,
          price,
          itemStack
        );

        shopEntity.addBuyAmount(itemStack.getAmount());
        shopEvent.callEvent();

        return addToTransactionQueue(shopEvent);
    }

    public ShopTransactionEvent sellShopAction(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull long price, @NonNull ItemStack itemStack) {
        final ShopTransactionSellEvent shopEvent = new ShopTransactionSellEvent(
          shopEntity,
          player,
          price,
          itemStack
        );

        shopEntity.addSellAmount(itemStack.getAmount());
        shopEvent.callEvent();

        return addToTransactionQueue(shopEvent);
    }

    // QUEUE SECTION

    public ShopEvent addToEventQueue(@NonNull ShopEvent event) {
        shopUpdateQueue.addUpdateToQueue((ShopUpdateEvent) event);
        return event;
    }

    public ShopBanEvent addToBanQueue(@NonNull ShopBanEvent event) {
        shopBanManager.addShopBan(event.getShopBanEntity());
        return event;
    }

    public ShopTransactionEvent addToTransactionQueue(@NonNull ShopTransactionEvent event) {
        transactionQueue.addTransactionToQueue(event.getTransaction());
        return event;
    }
}
