package com.github.battle.market.entity;

import com.github.battle.market.event.update.UpdateType;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

public interface ShopBan {

    int getShopId();

    int getPunishmentId();

    String getStaffName();

    UpdateType getType();

    String getReason();

    void setReason(@NonNull String reason);

    OfflinePlayer getStaff();
}
