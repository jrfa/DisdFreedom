package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import net.ultimatemayhem.disdfreedom.AbilityManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_strength extends SCCommand
{
  public Command_strength() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length < 1)
    {
      return false;
    }
    
    if (args[0].equalsIgnoreCase("on"))
    {
      if (!AbilityManager.strengthEnabledFor.contains(sender_p.getName()))
      {
        AbilityManager.strengthEnabledFor.add(sender_p.getName());
      }
      sender.sendMessage(org.bukkit.ChatColor.GRAY + "Strength enabled.");
      return true;
    }
    if (args[0].equalsIgnoreCase("off"))
    {
      for (int i = 0; i < AbilityManager.strengthEnabledFor.size(); i++)
      {
        if (((String)AbilityManager.strengthEnabledFor.get(i)).equals(sender_p.getName()))
        {
          AbilityManager.strengthEnabledFor.remove(i);
        }
      }
      sender.sendMessage(org.bukkit.ChatColor.GRAY + "Strength disabled.");
      return true;
    }
    return true;
  }
}
