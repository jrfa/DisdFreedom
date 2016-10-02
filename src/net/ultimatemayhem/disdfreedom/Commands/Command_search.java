package net.ultimatemayhem.disdfreedom.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;









@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=true)
public class Command_search
  extends SCCommand
{
  public Command_search() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length != 1) {
      return false;
    }
    try
    {
      int reqID = Integer.parseInt(args[0]);
      String name = Material.getMaterial(reqID).name();
      name = name.replaceAll("_", " ");
      sender.sendMessage(ChatColor.AQUA + "Found block " + name + " with ID: " + reqID);
    } catch (Exception ex) { sender.sendMessage(ChatColor.RED + "Error finding ID."); }
    return true;
  }
}
