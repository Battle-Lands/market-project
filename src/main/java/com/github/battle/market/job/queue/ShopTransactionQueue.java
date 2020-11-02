package com.github.battle.market.job.queue;

import com.github.battle.market.entity.ShopTransaction;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.transaction.ShopTransactionSerializer;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ShopTransactionQueue extends BukkitRunnable {

    private final ShopTransactionSerializer transactionSerializer;
    private final Queue<ShopTransaction> transactionQueue;

    public ShopTransactionQueue(@NonNull Plugin plugin, @NonNull MysqlBootstrap bootstrap) {
        this.transactionSerializer = new ShopTransactionSerializer(bootstrap);
        this.transactionQueue = new ConcurrentLinkedQueue<>();

        runTaskTimerAsynchronously(plugin, 0, MysqlBootstrap.QUEUE_TIME_MILLIS);
    }

    @Override
    public void run() {
        ShopTransaction transaction;
        while ((transaction = transactionQueue.poll()) != null) {
            transactionSerializer.serializeModel(transaction);
        }
    }

    public void addTransactionToQueue(@NonNull ShopTransaction transaction) {
        transactionQueue.add(transaction);
    }
}
