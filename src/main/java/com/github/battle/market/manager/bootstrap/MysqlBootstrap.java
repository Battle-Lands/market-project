package com.github.battle.market.manager.bootstrap;

import com.github.battle.core.database.reader.SQLReader;
import com.github.battle.core.database.requester.MySQLRequester;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public final class MysqlBootstrap {

    private ForkJoinPool forkJoinPool;

    private final MySQLRequester requester;
    private final SQLReader reader;
    public MysqlBootstrap(@NonNull Plugin plugin, @NonNull MySQLRequester requester, @NonNull ForkJoinPool forkJoinPool) {
        this.requester = requester;
        this.reader = new SQLReader(plugin);
        this.forkJoinPool = forkJoinPool;
    }

    public MysqlBootstrap createInitialTables(@NonNull String... queries) {
        for (String query : queries) {
            requester.execute(getQuery(query));
        }
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
