package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.Server;
import org.bukkit.command.Command;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_clean extends SCCommand
{
  public Command_clean() {}
  
  public boolean run(org.bukkit.command.CommandSender sender, org.bukkit.entity.Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    DisdFreedom.server.dispatchCommand(sender, "clearall");
    DisdFreedom.server.dispatchCommand(sender, "denick");
    return true;
  }
}
