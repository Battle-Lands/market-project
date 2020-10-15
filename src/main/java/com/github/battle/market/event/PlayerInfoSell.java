package com.github.battle.market.event;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class PlayerInfoSell implements Listener {

    @EventHandler
    private void onTransaction(TransactionEvent event) {
        System.out.println("transaction event is called");
    }
}
