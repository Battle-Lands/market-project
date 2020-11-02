package com.github.battle.market.event;

import com.github.battle.core.serialization.itemstack.text.ItemStackText;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopTransaction;
import com.github.battle.market.entity.transaction.ShopTransactionEntity;
import com.github.battle.market.entity.transaction.TransactionType;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class ShopTransactionEvent extends ShopEvent {

    private final ShopTransaction transaction;

    public ShopTransactionEvent(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull Long price, @NonNull TransactionType type, @NonNull ItemStack itemStack) {
        super(shopEntity, player);

        this.transaction = ShopTransactionEntity.builder()
          .shopId(shopEntity.getId())
          .client(player.getName())
          .price(price)
          .amount(itemStack.getAmount())
          .type(type)
          .itemData(ItemStackText.serialize(itemStack))
          .createdAt(getTimestamp())
          .build();
    }
}
