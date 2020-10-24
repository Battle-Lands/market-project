package com.github.battle.market.listener;

import com.github.battle.market.manager.ShopBanManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public final class PlayerShopEntityListener implements Listener {

    private final ShopBanManager shopBanManager;

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        shopBanManager.removePlayerShopEntryBan(event.getPlayer());
    }
}
