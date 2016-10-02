package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_slam extends SCCommand
{
  public Command_slam() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length != 1) {
      return false;
    }
    
    try
    {
      p = getPlayer(args[0]);
    } catch (CantFindPlayerException ex) { Player p;
      playerMsg(ex.getMessage(), ChatColor.RED);
      return true;
    }
    Player p;
    if (sender_p != null) {
      p.teleport(sender_p);
    }
    


    if (sender_p != null) {
      net.ultimatemayhem.disdfreedom.DisdFreedom.server.broadcastMessage(ChatColor.RED + sender_p.getName() + " slammed " + p.getName() + " into the ground.");
    } else {
      net.ultimatemayhem.disdfreedom.DisdFreedom.server.broadcastMessage(ChatColor.RED + sender.getName() + " slammed " + p.getName() + " into the ground.");
    }
    
    p.setOp(false);
    p.setGameMode(org.bukkit.GameMode.SURVIVAL);
    p.getInventory().clear();
    
    Location playerLocation = p.getLocation();
    playerLocation.setY(100.0D);
    p.teleport(playerLocation);
    
    playerLocation.setY(p.getLocation().getY() - 1.0D);
    
    p.setHealth(0);
    
    p.setVelocity(new org.bukkit.util.Vector(0, -10, 0));
    
    return true;
  }
  
  public void addToBlocksNear(Block b) {
    if (!blocksNear.contains(b)) {
      blocksNear.add(b);
    }
  }
  
  public void breakAllNearBlocks() {
    try {
      for (int i = 0; i < blocksNear.size(); i++) {
        Block targetBlock = (Block)blocksNear.get(i);
        targetBlock.breakNaturally();
      }
    }
    catch (Exception localException) {}
  }
  
  ArrayList<Block> blocksNear = new ArrayList();
}
