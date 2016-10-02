package net.ultimatemayhem.disdfreedom.Listener;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;























































































































































































































































































































































































class PlayerTorch
{
  Player targetPlayer;
  boolean enabled = false;
  
  Block prevBlock;
  ArrayList<Location> torchBlocks = new ArrayList();
  
  public PlayerTorch(Player player) {
    targetPlayer = player;
  }
  
  public String getPlayerName() {
    return targetPlayer.getName();
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public void onPlayerMove() {
    if (enabled) {
      Location location = targetPlayer.getLocation();
      location.setY(location.getY() + 3.0D);
      location.getBlock().setType(Material.GLOWSTONE);
      if (prevBlock != null)
      {
        torchBlocks.add(prevBlock.getLocation());
        if (torchBlocks.size() == 4) {
          for (Location loc : torchBlocks) {
            loc.getBlock().setType(Material.AIR);
          }
          torchBlocks.clear();
        }
      }
      prevBlock = location.getBlock();
    }
  }
  
  public void setEnabled(boolean flag) {
    enabled = flag;
  }
}
