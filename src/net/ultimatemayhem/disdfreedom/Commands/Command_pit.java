package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_pit extends SCCommand
{
  public Command_pit() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length != 1)
    {
      return false;
    }
    

    try
    {
      p = getPlayer(args[0]);
    }
    catch (CantFindPlayerException ex) {
      Player p;
      playerMsg(ex.getMessage(), ChatColor.RED);
      return true;
    }
    
    Player p;
    
    if (sender_p != null)
    {
      DisdFreedom.server.broadcastMessage(ChatColor.RED + sender_p.getName() + " has dug a pit for " + p.getName());
    }
    else
    {
      DisdFreedom.server.broadcastMessage(ChatColor.RED + sender.getName() + " has dug a pit for " + p.getName());
    }
    
    p.setOp(false);
    p.setGameMode(org.bukkit.GameMode.SURVIVAL);
    
    Location playerLocation = p.getLocation();
    playerLocation.setY(playerLocation.getY() - 1.0D);
    
    Block onBlock = playerLocation.getBlock();
    
    Location underBlockLocation = onBlock.getLocation();
    underBlockLocation.setY(underBlockLocation.getY() - 3.0D);
    p.teleport(underBlockLocation);
    
    try
    {
      for (int i = playerLocation.getBlockY(); i > -2; i--)
      {
        Location targetBlockLocation = onBlock.getLocation();
        targetBlockLocation.setY(targetBlockLocation.getY() - i);
        Block targetBlock = p.getWorld().getBlockAt(targetBlockLocation);
        if (targetBlock.getTypeId() != 0)
        {
          targetBlock.breakNaturally();
        }
      }
    }
    catch (Exception localException) {}
    


    return true;
  }
}
