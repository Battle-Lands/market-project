package com.github.battle.market.job.queue;

import com.github.battle.market.event.ShopUpdateEvent;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.ShopUpdateSerializer;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ShopUpdateQueue extends BukkitRunnable {

    private final Queue<ShopUpdateEvent<?>> shopUpdateEventQueue;
    private final ShopUpdateSerializer shopUpdateSerializer;

    public ShopUpdateQueue(@NonNull Plugin plugin, @NonNull MysqlBootstrap bootstrap) {
        this.shopUpdateEventQueue = new ConcurrentLinkedQueue<>();
        this.shopUpdateSerializer = new ShopUpdateSerializer(bootstrap);

        runTaskTimerAsynchronously(plugin, 0, MysqlBootstrap.QUEUE_TIME_MILLIS);
    }

    @Override
    public void run() {
        ShopUpdateEvent<?> shopUpdateEvent;
        while ((shopUpdateEvent = shopUpdateEventQueue.poll()) != null) {
            shopUpdateSerializer.serializeModel(shopUpdateEvent);
        }
    }

    public void addUpdateToQueue(@NonNull ShopUpdateEvent<?> shopUpdateEvent) {
        shopUpdateEventQueue.add(shopUpdateEvent);
    }
}
