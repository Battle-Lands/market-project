package com.github.battle.market.command;

import com.github.battle.core.serialization.location.LocationText;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.view.ShopView;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
@RequiredArgsConstructor
public final class ShopCommand {

    private final PlayerShopManager playerShopManager;
    private final ShopView shopPaginatedView;

    @Command(
      name = "shop",
      aliases = "loja",
      target = CommandTarget.PLAYER
    )
    public void shopViewCommand(Context<Player> playerContext, @Optional OfflinePlayer seller) {
        if (seller == null) {
            shopPaginatedView.showInventory(playerContext.getSender());
            return;
        }

        final PlayerShopEntity playerShop = playerShopManager.getPlayerShop(seller);
        if (playerShop == null) {
            playerContext.sendMessage("§cThat player don't have any shop set");
            return;
        }

        playerContext.sendMessage("shop information %s", playerShop);
    }

    @Command(
      name = "shop.remove",
      aliases = {"remover", "delete", "deletar", "rm"}
    )
    public void removeShopCommand(Context<Player> playerContext) {
        final Player sender = playerContext.getSender();
        final boolean hasPlayerShop = playerShopManager.hasPlayerShop(sender);
        if (!hasPlayerShop) {
            playerContext.sendMessage("§cYou don't have any shop set.");
            return;
        }

        playerShopManager.invalidPlayerShop(sender);
        playerContext.sendMessage("§aYou've been deleted your shop.");
    }

    @Command(
      name = "shop.set",
      aliases = {"define", "definir", "setar"}
    )
    public void setShopCommand(Context<Player> playerContext, @Optional String[] args) {
        final Player sender = playerContext.getSender();
        final Location location = sender.getLocation();

        final PlayerShopEntity shopEntity = playerShopManager.getLazyPlayerShop(sender);
        shopEntity.setLocation(location);

        if (args != null) {
            final String description = String.join(" ", args);
            shopEntity.setDescription(description);
        }

        playerShopManager.refleshPlayerShop(sender);
        playerContext.sendMessage(
          "§aYour shop has been created at %s. §7§o(#%s)",
          LocationText.serializeLocation(location),
          shopEntity.getId()
        );
    }
}

