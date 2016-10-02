package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_limit extends SCCommand
{
  public Command_limit() {}
  
  public boolean run(CommandSender sender, org.bukkit.entity.Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    DisdFreedom.server.broadcastMessage(org.bukkit.ChatColor.RED + sender.getName() + " - Setting all players WorldEdit limit to 500");
    DisdFreedom.server.dispatchCommand(sender, "wildcard gcmd ? /limit 500");
    return true;
  }
}
