package com.github.battle.market.job.queue;

import com.github.battle.market.entity.ban.ShopBanEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.ban.ShopBanSerializer;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public final class ShopBanQueue extends BukkitRunnable {

    private final Queue<ShopBanEntity> shopBanEntityQueue;
    private final ShopBanSerializer shopBanSerializer;
    private final MysqlBootstrap bootstrap;

    public ShopBanQueue(@NonNull Plugin plugin, @NonNull MysqlBootstrap bootstrap) {
        this.shopBanEntityQueue = new ConcurrentLinkedQueue<>();
        this.shopBanSerializer = new ShopBanSerializer(bootstrap);
        this.bootstrap = bootstrap;

        runTaskTimerAsynchronously(plugin, 0, MysqlBootstrap.QUEUE_TIME_MILLIS);
    }

    @Override
    public void run() {
        ShopBanEntity shopBanEntity;
        while ((shopBanEntity = shopBanEntityQueue.poll()) != null) {
            shopBanSerializer.serializeModel(shopBanEntity);
        }
    }

    public void addBanEntityToQueue(@NonNull ShopBanEntity shopBanEntity) {
        shopBanEntityQueue.add(shopBanEntity);
    }
}
