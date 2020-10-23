package com.github.battle.market.serializator.item;

import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.LinkedList;
import java.util.List;

public final class PlayerShopItemTemplate {

    private final ConfigurationSection section;

    public PlayerShopItemTemplate(@NonNull FileConfiguration configuration) {
        this.section = configuration.getConfigurationSection("view.shop");
    }

    public ConfigurationSection getItemBaseSection() {
        return section.getConfigurationSection("item_base");
    }

    public String getItemBaseName(OfflinePlayer player) {
        final String name = getItemBaseSection().getString("name");
        return PlaceholderAPI.setPlaceholders(player, name);
    }

    public List<String> getItemBaseLore(OfflinePlayer player) {
        final List<String> relation = getItemBaseSection()
          .getStringList("lore");

        final List<String> strings = new LinkedList<>();

        for (String text : relation) {
            final String element = PlaceholderAPI.setPlaceholders(player, text);

            final boolean containsPlaceholders = PlaceholderAPI.containsPlaceholders(element);
            if (containsPlaceholders) continue;

            strings.add(element);
        }

        return strings;
    }
}
