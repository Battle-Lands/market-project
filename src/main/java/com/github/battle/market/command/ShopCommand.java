package com.github.battle.market.command;

import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.view.ShopView;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class ShopCommand {

    private final PlayerShopManager playerShopManager;
    private final ShopView shopPaginatedView;

    @Command(
      name = "shop",
      target = CommandTarget.PLAYER,
      aliases = "loja"
    )
    public void shopViewCommand(Context<Player> playerContext, @Optional Player seller) {
        if(seller == null) {
            shopPaginatedView.showInventory(playerContext.getSender());
            return;
        }
     }

    @Command(
      name = "shop.set",
      aliases = {"define", "definir", "setar"}
    )
    public void setShopCommand(Context<Player> playerContext, @Optional String[] args) {
        final Player sender = playerContext.getSender();
        final PlayerShopEntity shopEntity = playerShopManager.getPlayerShop(sender);
        shopEntity.setLocation(sender.getLocation());

        if (args != null) {
            final String description = String.join(" ", args);
            shopEntity.setDescription(description);
        }

        playerContext.sendMessage("Sua loja foi setada na localização babababa");
    }
}

