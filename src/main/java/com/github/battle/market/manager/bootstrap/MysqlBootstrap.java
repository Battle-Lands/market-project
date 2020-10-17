package com.github.battle.market.manager.bootstrap;

import com.github.battle.core.database.requester.MySQLRequester;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

@RequiredArgsConstructor
public final class MysqlBootstrap {

    private final MySQLRequester requester;
    private final ConfigurationSection bootstrapQueries;

    public MysqlBootstrap(@NonNull FileConfiguration configuration, @NonNull MySQLRequester requester) {
        this.requester = requester;
        this.bootstrapQueries = configuration.getConfigurationSection("bootstrap.queries");
    }

    public MysqlBootstrap createInitialTables() {
        requester.execute(
          getQuery("shop_information.create_table")
        );
        return this;
    }

    public String getQuery(@NonNull String sectionPath) {
        return String.join(
          "",
          bootstrapQueries.getStringList(sectionPath)
        );
    }
}
