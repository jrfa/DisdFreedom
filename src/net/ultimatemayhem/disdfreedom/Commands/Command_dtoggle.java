package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_dtoggle extends SCCommand
{
  public Command_dtoggle() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    boolean toggled = false;
    boolean enabled = true;
    for (Plugin p : DisdFreedom.server.getPluginManager().getPlugins())
    {
      if (p.getName().equalsIgnoreCase("disguisecraft"))
      {
        if (p.isEnabled())
        {
          p.getPluginLoader().disablePlugin(p);
          enabled = false;
        }
        else
        {
          p.getPluginLoader().enablePlugin(p);
          enabled = true;
        }
        toggled = true;
      }
    }
    if (toggled)
    {
      if (!enabled)
      {
        DisdFreedom.server.broadcastMessage(org.bukkit.ChatColor.RED + sender.getName() + " - Disabling DisguiseCraft");
      }
      else
      {
        DisdFreedom.server.broadcastMessage(org.bukkit.ChatColor.GREEN + sender.getName() + " - Enabling DisguiseCraft");
      }
    }
    return true;
  }
}
