package com.github.battle.market.entity;

import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.event.update.UpdateType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

@Getter
@Builder
public final class ShopBanEntity {

    private final int shopId;
    private final int punishmentId;
    private final String staffName;
    private final UpdateType type;

    @Setter
    private String reason;

    public OfflinePlayer getStaff() {
        return PluginCore.getOfflinePlayer(staffName);
    }
}
