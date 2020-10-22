package com.github.battle.market.serializator;

import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import lombok.*;
import lombok.experimental.Accessors;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItemAdapter implements ModelAdapter<List<ShopEntity>, Void> {

    @NonNull
    private final PlayerShopManager playerShopManager;

    @Override
    public List<ShopEntity> adaptModel(Void instance) {
        final List<ShopEntity> shopEntities = new ArrayList<>();
        for (Optional<ShopEntity> optional : playerShopManager.getAllShopEntities()) {
            if (!optional.isPresent()) continue;

            // The bellow lines cause a unnecessary cache route, i need to fix this
            // But i have no idea how can i do this
            final ShopEntity shopEntity = optional.get();

            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(shopEntity.getOwner());
            if (offlinePlayer.isBanned()) continue;

            shopEntities.add(shopEntity);
        }

        return shopEntities;
    }
}
