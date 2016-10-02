package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_lockdown extends SCCommand
{
  public Command_lockdown() {}
  
  public boolean run(CommandSender sender, org.bukkit.entity.Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length < 1)
    {
      return false;
    }
    
    if (args[0].equalsIgnoreCase("on"))
    {
      DisdFreedom.lockdownMode = true;
      DisdFreedom.server.broadcastMessage(ChatColor.RED + sender.getName() + " - Locking down the server");
      DisdFreedom.server.broadcastMessage(ChatColor.RED + "While in lockdown mode, new players cannot join.");
      return true;
    }
    if (args[0].equalsIgnoreCase("off"))
    {
      DisdFreedom.lockdownMode = false;
      DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Unlocking the server");
      DisdFreedom.server.broadcastMessage(ChatColor.GREEN + "New players are now free to join.");
      return true;
    }
    return true;
  }
}
