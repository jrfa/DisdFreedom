package net.ultimatemayhem.disdfreedom.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_noob extends SCCommand
{
  public Command_noob() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
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
    for (int i = 0; i < 2; i++)
    {
      p.getWorld().strikeLightning(p.getLocation());
    }
    
    if (args.length > 0)
    {
      net.ultimatemayhem.disdfreedom.DisdFreedom.server.dispatchCommand(sender, "tempban " + p.getName() + " 300 read www.totalfreedom.me");
    }
    else
    {
      sender.sendMessage(ChatColor.RED + "Please specify a name!");
    }
    
    return true;
  }
}
