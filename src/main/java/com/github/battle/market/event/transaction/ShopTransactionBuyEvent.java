package com.github.battle.market.event.transaction;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.transaction.TransactionType;
import com.github.battle.market.event.ShopTransactionEvent;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ShopTransactionBuyEvent extends ShopTransactionEvent {

    public ShopTransactionBuyEvent(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull Long price, @NonNull ItemStack itemStack) {
        super(shopEntity, player, price, TransactionType.BUY, itemStack);
    }
}
