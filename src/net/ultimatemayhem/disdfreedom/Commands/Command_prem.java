package net.ultimatemayhem.disdfreedom.Commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;











@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_prem
  extends SCCommand
{
  String playerName;
  
  public Command_prem() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length != 1)
    {
      return false;
    }
    

    try
    {
      Player p = getPlayer(args[0]);
      playerName = p.getName();
    }
    catch (CantFindPlayerException ex)
    {
      playerName = args[0];
    }
    
    if (isPlayerPremium(sender, playerName)) {
      sender.sendMessage(ChatColor.GOLD + playerName + " is premium.");
    } else {
      sender.sendMessage(ChatColor.RED + playerName + " is not premium.");
    }
    return true;
  }
  
  public boolean isPlayerPremium(CommandSender sender, String s) {
    boolean isPremium = false;
    try {
      URL url = new URL("http://minecraft.net/haspaid.jsp?user=" + s);
      
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      
      String input;
      while ((input = in.readLine()) != null) { String input;
        if (input.equalsIgnoreCase("true")) {
          isPremium = true;
        }
      }
      in.close();
    } catch (Exception ex) { sender.sendMessage(ChatColor.RED + "Error checking player: " + ex.getMessage()); }
    return isPremium;
  }
}
