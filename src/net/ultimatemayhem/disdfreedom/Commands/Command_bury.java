package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.AbilityManager;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_bury extends SCCommand
{
  public Command_bury()
  {
    adminOnly = true;
  }
  


  public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length == 0)
    {
      return false;
    }
    

    try
    {
      p = getPlayer(args[0].toString());
    }
    catch (CantFindPlayerException ex) {
      Player p;
      playerMsg(ex.getMessage(), ChatColor.RED);
      return true;
    }
    Player p;
    if (args.length >= 2)
    {
      DisdFreedom.server.broadcastMessage(ChatColor.GREEN + p.getName() + " was returned to ground level.");
      


      AbilityManager.removeBurriedPlayer(p.getName());
      


      p.teleport(p.getWorld().getSpawnLocation());
      
      return true;
    }
    




    DisdFreedom.server.broadcastMessage(ChatColor.RED + p.getName() + " was sent deep down into the null void.");
    


    Location playerLocation = p.getLocation();
    playerLocation.setY(-4.0D);
    p.teleport(playerLocation);
    


    AbilityManager.addBurriedPlayer(p.getName());
    


    p.setOp(false);
    p.setGameMode(org.bukkit.GameMode.SURVIVAL);
    

    return true;
  }
}
