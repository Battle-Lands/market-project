package com.github.battle.market.serializator.ban;

import com.github.battle.core.plugin.PluginCore;
import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.market.entity.ShopBanEntity;
import com.github.battle.market.event.update.UpdateType;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ShopBanAdapter implements ModelAdapter<ShopBanEntity, ResultSet> {

    @Override
    public ShopBanEntity adaptModel(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;

        final String rawStaffName = resultSet.getString("staff");
        final String rawType = resultSet.getString("type");

        return ShopBanEntity.builder()
          .punishmentId(resultSet.getInt("id"))
          .shopId(resultSet.getInt("shop_id"))
          .staffName(rawStaffName)
          .reason(resultSet.getString("reason"))
          .type(UpdateType.getUpdateTypeByName(rawType))
          .build();
    }
}
