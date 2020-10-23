package com.github.battle.market.event;

import com.github.battle.market.entity.ShopBanEntity;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopState;
import com.github.battle.market.event.update.UpdateType;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
public abstract class ShopBanEvent extends ShopUpdateEvent<ShopState> {

    private final ShopBanEntity shopBanEntity;
    public ShopBanEvent(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull UpdateType type) {
        super(shopEntity, player, type);

        this.shopBanEntity = ShopBanEntity.builder()
          .shopId(shopEntity.getId())
          .staff(player)
          .type(type)
          .build();
    }

    public void setReason(@NonNull String reason) {
        shopBanEntity.setReason(reason);
    }

    public String getReason() {
        return shopBanEntity.getReason();
    }
}
