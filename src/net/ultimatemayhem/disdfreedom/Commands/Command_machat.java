package net.ultimatemayhem.disdfreedom.Commands;

import java.util.logging.Logger;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_machat extends SCCommand
{
  public Command_machat() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length <= 1)
    {
      return false;
    }
    
    if (args.length == 1)
    {
      sender.sendMessage(ChatColor.RED + "You must enter what to make them say!");
      return true;
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
    if (!args[1].contains("/"))
    {
      if (sender_p != null)
      {
        DisdFreedom.logger.info(sender_p.getName() + " manipulated " + p.getName() + "'s chat and made them say: " + args[1]);
      }
      else
      {
        DisdFreedom.logger.info(sender.getName() + " manipulated " + p.getName() + "'s chat and made them say: " + args[1]);
      }
      



      args[0] = "";
      
      String newMessage = org.apache.commons.lang.StringUtils.join(args, " ");
      
      p.chat(newMessage);
    }
    else
    {
      sender.sendMessage(ChatColor.RED + "You may not make them perform a command.");
    }
    
    return true;
  }
}
