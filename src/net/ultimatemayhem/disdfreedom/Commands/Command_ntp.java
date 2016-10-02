package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_ntp extends SCCommand
{
  public ArrayList<Player> matchingPlayers = new ArrayList();
  boolean teleported = false;
  
  public Command_ntp() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
    if (senderIsConsole)
    {
      sender.sendMessage(ChatColor.RED + "Sorry, this command cannot be used from the console.");
      return true;
    }
    
    try
    {
      for (Player player : net.ultimatemayhem.disdfreedom.DisdFreedom.server.getOnlinePlayers())
      {
        String displayName = player.getDisplayName().toLowerCase();
        
        displayName = ChatColor.stripColor(displayName);
        
        String reqName = args[0].toLowerCase();
        if (displayName.contains(reqName))
        {
          matchingPlayers.add(player);
          if (matchingPlayers.size() < 2)
          {
            if (displayName.contains(reqName))
            {
              sender_p.teleport(player);
              sender.sendMessage(ChatColor.GREEN + "Teleported to: " + player.getName());
              teleported = true;
            }
            else
            {
              sender.sendMessage(ChatColor.RED + "Couldn't find the player with that nick.");
            }
            

          }
          else if (!teleported)
          {
            Random r = new Random();
            int randomInt = r.nextInt(matchingPlayers.size());
            Player p = (Player)sender;
            p.teleport((Entity)matchingPlayers.get(randomInt));
            sender.sendMessage(ChatColor.GREEN + "More than one player with that nick.  Teleported to random.");
            teleported = true;
          }
        }
      }
    }
    catch (Exception localException) {}
    



    if (!teleported)
    {
      sender.sendMessage(ChatColor.RED + "Couldn't find the player with that nick.");
    }
    return true;
  }
}
