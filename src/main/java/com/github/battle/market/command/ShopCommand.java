package com.github.battle.market.command;

import com.github.battle.core.plugin.PluginCore;
import com.github.battle.core.serialization.location.text.LocationText;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.shop.ShopState;
import com.github.battle.market.exception.ShopTravelException;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.ShopEventManager;
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

    private static final String SHOP_PERMISSION = "battlelands.shop.create";

    private final PlayerShopManager playerShopManager;
    private final ShopEventManager shopEventManager;
    private final ShopView shopPaginatedView;

    @Command(
      name = "shop",
      aliases = "loja",
      target = CommandTarget.PLAYER
    )
    public void shopViewCommand(Context<Player> playerContext, @Optional String rawOfflinePlayer) {
        final Player sender = playerContext.getSender();
        if (rawOfflinePlayer == null) {
            final boolean hasInventoryIndex = shopPaginatedView.showInventory(sender);
            if (!hasInventoryIndex) {
                playerContext.sendMessage("§cNo shop has been set on server.");
                return;
            }
            return;
        }

        final OfflinePlayer offlinePlayer = PluginCore.getOfflinePlayer(rawOfflinePlayer);
        if (offlinePlayer == null) {
            playerContext.sendMessage("§cThis player doesn't exists.");
            return;
        }

        try {
            travelShopCommand(sender, offlinePlayer);
        } catch (ShopTravelException exception) {
            playerContext.sendMessage(
              "§cAn error occurred, please inform the owner. \n §r- §cCaused by §8(%s) \n §r%s ",
              exception.getClass().getSimpleName(),
              exception.getCause()
            );
        }
    }

    public void travelShopCommand(Player player, OfflinePlayer offlinePlayer) throws ShopTravelException {
        final ShopEntity shopEntity = playerShopManager.getCheckedPlayerShop(offlinePlayer);
        if (shopEntity == null) {
            player.sendMessage("§cThat player don't have any shop set");
            return;
        }

        if (!shopEntity.hasLocationSet()) {
            player.sendMessage("§cThis shop dont have any location set. Please inform the owner.");
            return;
        }

        playerShopManager.travelPlayerShop(
          player,
          shopEntity
        );

        final boolean successfulOnTeleport = player.teleport(shopEntity.getLocation());
        if (successfulOnTeleport && shopEntity.hasDescriptionSet()) {
            player.sendMessage(shopEntity.getDescription());
        }
    }

    @Command(
      name = "shop.remove",
      aliases = {"remover", "delete", "deletar", "rm"},
      permission = SHOP_PERMISSION
    )
    public void removeShopCommand(Context<Player> playerContext) {
        final Player sender = playerContext.getSender();
        final ShopEntity shopEntity = playerShopManager.getCheckedPlayerShop(sender);
        if (shopEntity == null) {
            playerContext.sendMessage("§cYou don't have any shop set.");
            return;
        }

        shopEventManager.invalidateShop(shopEntity, sender);
        playerShopManager.refreshPlayerShop(sender);

        playerContext.sendMessage("§cYou've been deleted your shop.");
    }

    @Command(
      name = "shop.set",
      aliases = {"define", "definir", "setar"},
      permission = SHOP_PERMISSION
    )
    public void setShopCommand(Context<Player> playerContext, @Optional String[] args) {
        final Player sender = playerContext.getSender();
        ShopEntity shopEntity = playerShopManager.getLazyPlayerShop(sender);
        if (shopEntity == null) {
            playerContext.sendMessage("§cIs not possible to get your shop.");
            return;
        }

        if (shopEntity.isCreated()) {
            final ShopState state = shopEntity.getRawState();
            shopEntity = playerShopManager.refreshPlayerShop(sender);

            shopEntity.setState(state);
            shopEventManager.proceduralCheckShop(shopEntity, sender);
        }

        final Location location = sender.getLocation();
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

        playerShopManager.refreshPlayerShop(sender);
    }

    @Command(
      name = "shop.ban",
      permission = "battlelands.shop.ban"
    )
    public void banShopCommand(Context<Player> playerContext, String rawOfflinePlayer, String[] args) {
        final OfflinePlayer offlinePlayer = PluginCore.getOfflinePlayer(rawOfflinePlayer);
        if (offlinePlayer == null) {
            playerContext.sendMessage("§cThis player doesn't exists.");
            return;
        }

        final ShopEntity shopEntity = playerShopManager.getPlayerShop(offlinePlayer);
        if (shopEntity == null) {
            playerContext.sendMessage("§cThat player don't have any shop set.");
            return;
        }

        if (shopEntity.isBanned()) {
            playerContext.sendMessage("This shop is already banned.");
            return;
        }

        final String reason = String.join(" ", args);
        shopEventManager.banShop(shopEntity, playerContext.getSender(), reason);
        playerShopManager.refreshPlayerShop(offlinePlayer);

        playerContext.sendMessage(
          "§cYou've banned shop's id %s of %s",
          shopEntity.getId(),
          shopEntity.getOwner()
        );
    }

    @Command(
      name = "shop.unban",
      permission = "battlelands.shop.ban"
    )
    public void unbanShopCommand(Context<Player> playerContext, String rawOfflinePlayer, String[] args) {
        final OfflinePlayer offlinePlayer = PluginCore.getOfflinePlayer(rawOfflinePlayer);
        if (offlinePlayer == null) {
            playerContext.sendMessage("§cThis player doesn't exists.");
            return;
        }

        final ShopEntity shopEntity = playerShopManager.getPlayerShop(offlinePlayer);
        if (shopEntity == null) {
            playerContext.sendMessage("§cThat player don't have any shop set.");
            return;
        }

        if (!shopEntity.isBanned()) {
            playerContext.sendMessage("This shop is not banned.");
            return;
        }

        final String reason = String.join(" ", args);
        shopEventManager.unbanShop(shopEntity, playerContext.getSender(), reason);
        playerShopManager.refreshPlayerShop(offlinePlayer);

        playerContext.sendMessage(
          "§cYou've unbanned shop's id %s of %s",
          shopEntity.getId(),
          shopEntity.getOwner()
        );
    }
}

