package com.github.battle.market.command;

import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.entity.PlayerShopManager;
import com.github.battle.market.view.ShopPaginatedView;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class ShopCommand {

    private final PlayerShopManager playerShopManager;
    private final ShopPaginatedView shopPaginatedView;

    @Command(
      name = "shop",
      target = CommandTarget.PLAYER,
      aliases = "loja"
    )
    public void shopViewCommand(Context<Player> playerContext, @Optional Player seller) {
        shopPaginatedView.showInventory(playerContext.getSender());
    }

    @Command(
      name = "shop.set",
      aliases = {"define", "definir", "setar"}
    )
    public void setShopCommand(Context<Player> playerContext, @Optional String[] args) {
        final Player sender = playerContext.getSender();
        final PlayerShopEntity shopEntity = playerShopManager.getOrCreatePlayerShop(sender);
        shopEntity.setLocation(sender.getLocation());

        if (args != null) {
            final String description = String.join(" ", args);
            shopEntity.setDescription(description);
        }

        sender.sendMessage("Sua loja foi setada na localização babababa");
    }
}

