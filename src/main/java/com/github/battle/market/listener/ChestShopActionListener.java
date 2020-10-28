package com.github.battle.market.listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChestShopActionListener implements Listener {

    @EventHandler
    private void onTransaction(TransactionEvent event) {
        final Player client = event.getClient();
        switch (event.getTransactionType()) {
            case BUY: {
                client.sendMessage("comprou iasudiuasiduasd");
                return;
            }
            case SELL: {
                client.sendMessage("vendeu asjdkajsdasd");
            }
        }
    }
}
