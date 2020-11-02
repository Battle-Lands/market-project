package com.github.battle.market.entity.transaction;

import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.entity.ShopTransaction;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
public final class ShopTransactionEntity implements ShopTransaction {

    private final int id;
    private final int shopId;
    private final String client;
    private final long price;
    private final long amount;
    private final TransactionType type;
    private final String itemData;
    private final Timestamp createdAt;

    @Override
    public ItemStack getItemStack() {
        return null;
    }

    @Override
    public OfflinePlayer getClientPlayer() {
        return PluginCore.getOfflinePlayer(client);
    }
}
