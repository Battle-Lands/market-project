package com.github.battle.market.listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.ShopEventManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class ChestShopActionListener implements Listener {

    private final PlayerShopManager playerShopManager;
    private final ShopEventManager shopEventManager;

    @EventHandler(ignoreCancelled = true)
    private void onTransaction(@NonNull TransactionEvent event) {
        final ItemStack[] stock = event.getStock();
        if (stock == null || stock.length < 1) return;

        final ShopEntity shopEntity = playerShopManager.getPlayerShop(event.getOwner());
        if (shopEntity == null) return;

        final long price = (long) event.getPrice();
        final Player client = event.getClient();
        final ItemStack itemStack = stock[0];

        switch (event.getTransactionType()) {
            case BUY: {
                shopEventManager.buyShopAction(
                  shopEntity,
                  client,
                  price,
                  itemStack
                );
                return;
            }
            case SELL:
                shopEventManager.sellShopAction(
                  shopEntity,
                  client,
                  price,
                  itemStack
                );
        }
    }
}
