package com.github.battle.market.manager.bootstrap;

import com.github.battle.core.database.requester.MySQLRequester;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public final class MysqlBootstrap {

    private final ConfigurationSection bootstrapQueries;
    private final MySQLRequester requester;
    private ForkJoinPool forkJoinPool;

    public MysqlBootstrap(@NonNull FileConfiguration configuration, @NonNull MySQLRequester requester, @NonNull ForkJoinPool forkJoinPool) {
        this.bootstrapQueries = configuration.getConfigurationSection("bootstrap.queries");
        this.requester = requester;
        this.forkJoinPool = forkJoinPool;
    }

    public MysqlBootstrap createInitialTables(@NonNull String... queries) {
        for (String query : queries) {
            requester.execute(getQuery(query));
        }
        return this;
    }

    public String getQuery(@NonNull String sectionPath) {
        return String.join(
          " ",
          bootstrapQueries.getStringList(sectionPath)
        );
    }

    public void executeAsync(@NonNull Runnable runnable) {
        forkJoinPool.execute(runnable);
    }

    public void closeForkJoinPool() {
        forkJoinPool.shutdownNow();
    }
}
