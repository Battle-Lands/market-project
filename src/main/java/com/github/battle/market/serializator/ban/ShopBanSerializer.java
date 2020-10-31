package com.github.battle.market.serializator.ban;

import com.github.battle.core.serialization.ModelSerializer;
import com.github.battle.market.entity.ShopBanEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ShopBanSerializer implements ModelSerializer<ShopBanEntity> {

    private final MysqlBootstrap bootstrap;

    @Override
    public void serializeModel(ShopBanEntity entity) {
        if (entity == null) return;

        bootstrap.executeUpdate(
          "shop_ban.insert",
          entity.getShopId(),
          entity.getStaffName(),
          entity.getReason(),
          entity.getType().name()
        );
    }
}
