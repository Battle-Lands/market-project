package com.github.battle.market.manager.bootstrap;

import com.github.battle.core.database.reader.SQLReader;
import com.github.battle.core.database.requester.MySQLRequester;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public final class MysqlBootstrap {

    private final ForkJoinPool forkJoinPool;
    private final MySQLRequester requester;
    private final SQLReader reader;

    public MysqlBootstrap(@NonNull Plugin plugin, @NonNull MySQLRequester requester, @NonNull ForkJoinPool forkJoinPool) {
        this.forkJoinPool = forkJoinPool;
        this.requester = requester;
        this.reader = new SQLReader(plugin);
    }

    public MysqlBootstrap createInitialTables(@NonNull String... rawQueries) {
        for (int index = 0; index < rawQueries.length; index++) {
            rawQueries[index] = getQuery(rawQueries[index]);
        }
        requester.executeQueries(rawQueries);
        return this;
    }

    public String getQuery(@NonNull String sectionPath) {
        return reader.getQuery(sectionPath);
    }

    public void executeAsync(@NonNull Runnable runnable) {
        forkJoinPool.execute(runnable);
    }

    public void closeForkJoinPool() {
        forkJoinPool.shutdownNow();
    }
}
