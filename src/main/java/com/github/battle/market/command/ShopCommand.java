package com.github.battle.market.command;

import com.github.battle.core.serialization.location.text.LocationText;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.ShopEventManager;
import com.github.battle.market.view.ShopView;
import com.google.common.collect.ImmutableMap;
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

    private static final String SHOP_PERMISSION = "battlelands.shop.create";

    private final PlayerShopManager playerShopManager;
    private final ShopView shopPaginatedView;

    private final ShopEventManager shopEventManager;

    @Command(
      name = "shop",
      aliases = "loja",
      target = CommandTarget.PLAYER
    )
    public void shopViewCommand(Context<Player> playerContext, @Optional OfflinePlayer seller) {
        if (seller == null) {
            if (playerShopManager.hasNonShopSet()) {
                playerContext.sendMessage("§cNo shop has been set on server.");
                return;
            }

            shopPaginatedView.open(playerContext.getSender());
            return;
        }

        final ShopEntity shopEntity = playerShopManager.getPlayerShop(seller);
        if (shopEntity == null) {
            playerContext.sendMessage("§cThat player don't have any shop set");
            return;
        }

        playerContext.sendMessage("shop information %s", shopEntity);
    }

    @Command(
      name = "shop.remove",
      aliases = {"remover", "delete", "deletar", "rm"},
      permission = SHOP_PERMISSION
    )
    public void removeShopCommand(Context<Player> playerContext) {
        final Player sender = playerContext.getSender();
        final ShopEntity shopEntity = playerShopManager.getPlayerShop(sender);
        if (shopEntity == null) {
            playerContext.sendMessage("§cYou don't have any shop set.");
            return;
        }

        shopEventManager.invalidateShop(shopEntity, sender);
        playerShopManager.invalidPlayerShop(sender);

        playerContext.sendMessage("§cYou've been deleted your shop.");
    }

    @Command(
      name = "shop.set",
      aliases = {"define", "definir", "setar"},
      permission = SHOP_PERMISSION
    )
    public void setShopCommand(Context<Player> playerContext, @Optional String[] args) {
        final Player sender = playerContext.getSender();
        final Location location = sender.getLocation();

        ShopEntity shopEntity = playerShopManager.getLazyPlayerShop(sender);
        if (shopEntity == null) {
            playerContext.sendMessage("§cIs not possible to get your shop.");
            return;
        }

        if (shopEntity.isCreated()) {
            shopEntity = playerShopManager.refleshPlayerShop(sender);
            shopEventManager.proceduralCheckShop(shopEntity, sender);
        }

        if (args == null) {
            shopEventManager.setLocation(shopEntity, sender, location);
            //shopEntity.setLocation(location);
            playerContext.sendMessage(
              "§aYour shop's location has been set to §9'%s'§a.",
              LocationText.serializeLocation(shopEntity.getLocation())
            );
        } else {
            if (args[0].equalsIgnoreCase("null")) {
                shopEventManager.setDescription(shopEntity, sender, null);
                //shopEntity.setDescription(null);
                playerContext.sendMessage(
                  "§cYour shop's description has been removed."
                );
            } else {
                shopEventManager.setDescription(shopEntity, sender, String.join(" ", args));
                //shopEntity.setDescription(String.join(" ", args));
                playerContext.sendMessage(
                  "§aYour shop's descripition has been set to §9'%s'§a.",
                  shopEntity.getDescription()
                );
            }
        }

        playerShopManager.refleshPlayerShop(sender);
    }
}

