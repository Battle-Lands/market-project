package com.github.battle.market.entity;

import com.github.battle.market.entity.transaction.TransactionType;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public interface ShopTransaction {

    int getId();

    int getShopId();

    String getClient();

    long getPrice();

    long getAmount();

    TransactionType getType();

    String getItemData();

    Timestamp getCreatedAt();

    ItemStack getItemStack();

    OfflinePlayer getClientPlayer();
}
