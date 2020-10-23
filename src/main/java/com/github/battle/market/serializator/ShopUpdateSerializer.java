package com.github.battle.market.serializator;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.serialization.ModelSerializer;
import com.github.battle.core.serialization.location.text.LocationText;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopUpdateEvent;
import com.github.battle.market.event.update.UpdateType;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

@RequiredArgsConstructor
public final class ShopUpdateSerializer implements ModelSerializer<ShopUpdateEvent<?>> {

    private final MysqlBootstrap bootstrap;
    private final MySQLRequester requester;

    @Override
    public void serializeModel(@NonNull ShopUpdateEvent<?> shopUpdateEvent) {
        if (shopUpdateEvent == null) return;
        final ShopEntity shopEntity = shopUpdateEvent.getShopEntity();

        final UpdateType type = shopUpdateEvent.getType();
        requester.executeUpdate(
          bootstrap.getQuery("shop_update.insert"),
          shopEntity.getId(),
          type.name(),
          match(type, shopUpdateEvent.getOldValue()),
          match(type, shopUpdateEvent.getNewValue()),
          shopUpdateEvent.getTimestamp()
        );
    }

    public Object match(@NonNull UpdateType updateType, Object value) {
        switch (updateType) {
            case REMOVED_LOCATION:
            case UPDATED_LOCATION:
                return value != null
                  ? LocationText.serializeLocation((Location) value)
                  : null;
            case CREATED:
            case REMOVED:
            case BANNED:
                return value != null
                  ? ((Enum<?>) value).name()
                  : null;
            default:
                return value;
        }
    }
}
