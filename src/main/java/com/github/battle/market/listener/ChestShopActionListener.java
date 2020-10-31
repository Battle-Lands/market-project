package com.github.battle.market.listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class ChestShopActionListener implements Listener {

    private final PlayerShopManager playerShopManager;

    @EventHandler(ignoreCancelled = true)
    private void onTransaction(TransactionEvent event) {
        final ItemStack[] stock = event.getStock();
        if(stock == null) return;

        final ShopEntity shopEntity = playerShopManager.getPlayerShop(event.getOwner());
        if(shopEntity == null) return;

        final int total = getTotalAmount(stock);
        switch (event.getTransactionType()) {
            case BUY: {
                shopEntity.addBuyAmount(total);
                return;
            }
            case SELL: shopEntity.addSellAmount(total);
        }
    }

    private int getTotalAmount(@NonNull ItemStack[] stock) {
        int amount = 0;
        for (ItemStack itemStack : stock) {
            amount += itemStack.getAmount();
        }

        return amount;
    }
}
