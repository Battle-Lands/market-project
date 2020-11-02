package com.github.battle.market.serializator.transaction;

import com.github.battle.core.serialization.ModelSerializer;
import com.github.battle.market.entity.ShopTransaction;
import com.github.battle.market.entity.transaction.TransactionType;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ShopTransactionSerializer implements ModelSerializer<ShopTransaction> {

    private final MysqlBootstrap bootstrap;

    @Override
    public void serializeModel(ShopTransaction transaction) {
        if (transaction == null) return;

        final String transactionName = TransactionType.getTransactionName(transaction.getType());
        bootstrap.executeUpdate(
          "shop_transaction.insert",
          transaction.getShopId(),
          transaction.getClient(),
          transaction.getPrice(),
          transaction.getAmount(),
          transactionName,
          transaction.getItemData(),
          transaction.getCreatedAt()
        );
    }
}
