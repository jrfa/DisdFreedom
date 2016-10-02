package net.ultimatemayhem.disdfreedom.Commands;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;








@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=true)
public class Command_disdfreedom
  extends SCCommand
{
  String[] messages = {
    "Version: 2.4", 
    "DisdFreedom was created by disaster839 and", 
    "is an extension plugin for Total Freedom." };
  
  ChatColor messageColor = ChatColor.GREEN;
  String lineTxt = "";
  String pattern = ChatColor.BLUE + "-" + ChatColor.AQUA + "=";
  int lineLength = 20;
  
  Random random = new Random();
  

  public Command_disdfreedom() {}
  

  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    double length = 0.0D;
    for (int i = 0; i < lineLength; i++)
    {
      length += 0.5D;
    }
    lineLength = ((int)length);
    for (int i = 0; i < lineLength; i++)
    {
      lineTxt += pattern;
    }
    sender.sendMessage(ChatColor.GREEN + lineTxt + ChatColor.GOLD + " DisdFreedom " + lineTxt);
    for (int i = 0; i < messages.length; i++)
    {
      sender.sendMessage(messageColor + messages[i]);
    }
    
    return true;
  }
}
