package net.ultimatemayhem.disdfreedom;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Spectator
{
  Player player;
  Player targetPlayer;
  boolean spectating = false;
  
  public Spectator(Player spectator, Player target) {
    for (Spectator curSpectator : AbilityManager.spectators) {
      if (curSpectator.getSpectator().equals(spectator.getName())) {
        curSpectator.stop(true);
      }
    }
    if (spectator.getName() == target.getName()) {
      spectator.sendMessage(ChatColor.RED + "You cannot spectate yourself.");
    } else {
      player = spectator;
      targetPlayer = target;
      start();
    }
  }
  
  public void start() {
    if (targetPlayer.isOnline()) {
      for (Player p : DisdFreedom.server.getOnlinePlayers()) {
        if ((p.getName() != player.getName()) && (!DF_Util.isSuperAdmin(p))) {
          p.hidePlayer(player);
        }
      }
      player.teleport(targetPlayer);
      player.sendMessage(ChatColor.BLUE + "You are now spectating: " + targetPlayer.getName());
      player.sendMessage(ChatColor.BLUE + "You are now invisible to other players, and you cannot teleport while spectating.");
      spectating = true;
    } else {
      player.sendMessage(ChatColor.GRAY + "That player is offline.");
    }
  }
  
  public void update() {
    if (spectating) {
      if (!targetPlayer.isOnline()) {
        stop(player.isOnline());
      }
      
      if (!player.isOnline()) {
        stop(false);
      }
      
      Location newLocation = targetPlayer.getLocation();
      newLocation.setY(newLocation.getY() + 1.0D);
      
      player.teleport(newLocation);
    }
  }
  
  public void stop(boolean online) {
    spectating = false;
    if (online) {
      player.teleport(player.getWorld().getSpawnLocation());
      player.sendMessage(ChatColor.GRAY + "Stopped spectating.");
    }
    for (Player p : DisdFreedom.server.getOnlinePlayers()) {
      if (p.getName() != player.getName()) {
        p.showPlayer(player);
      }
    }
  }
  
  public Player getSpectator() {
    return player;
  }
  
  public Player getSpectating() {
    return targetPlayer;
  }
}
