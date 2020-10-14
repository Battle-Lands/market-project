package com.github.battle.market.bootstrap;

import com.github.battle.core.database.requester.MySQLRequester;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

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
          getQuery("create_shop_information_table")
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
