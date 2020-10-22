package com.github.battle.market.job;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.event.ShopUpdateEvent;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.ShopUpdateSerializer;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.github.battle.market.event.ShopUpdateEvent.UpdateType.REMOVED;

public final class ShopUpdateQueue extends BukkitRunnable {

    private final Queue<ShopUpdateEvent<?>> shopUpdateEvents;
    private final ShopUpdateSerializer shopUpdateSerializer;

    public ShopUpdateQueue(@NonNull Plugin plugin, @NonNull MysqlBootstrap bootstrap, @NonNull MySQLRequester requester) {
        this.shopUpdateEvents = new ConcurrentLinkedQueue<>();
        this.shopUpdateSerializer = new ShopUpdateSerializer(bootstrap, requester);

        runTaskTimerAsynchronously(plugin, 0, 20 * 60 * 30);
    }

    @Override
    public void run() {
        ShopUpdateEvent<?> shopUpdateEvent;
        while ((shopUpdateEvent = shopUpdateEvents.poll()) != null) {
            if (shopUpdateEvent.getType() == REMOVED) continue;
            shopUpdateSerializer.serializeModel(shopUpdateEvent);
        }
    }

    public void addUpdateToQueue(@NonNull ShopUpdateEvent<?> shopUpdateEvent) {
        shopUpdateEvents.add(shopUpdateEvent);
    }
}
