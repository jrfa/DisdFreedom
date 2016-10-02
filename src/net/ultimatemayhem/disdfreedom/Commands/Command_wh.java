package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_wh extends SCCommand
{
  public ArrayList<String> matchingPlayers = new ArrayList();
  
  public Command_wh() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    String itemName = "";
    
    if (args.length > 0)
    {
      try {
        itemName = Material.getMaterial(Integer.parseInt(args[0])).name();
      } catch (Exception ex) {
        try {
          itemName = Material.getMaterial(args[0].toUpperCase()).name();
        } catch (Exception ex2) { sender.sendMessage(ChatColor.RED + "Error, ie: /wh stone_sword");return true;
        } }
      Player[] arrayOfPlayer;
      Exception localException1 = (arrayOfPlayer = DisdFreedom.server.getOnlinePlayers()).length; for (ex2 = 0; ex2 < localException1; ex2++) { Player p = arrayOfPlayer[ex2];
        if (p.getInventory().contains(Material.getMaterial(itemName))) {
          matchingPlayers.add(p.getName());
        }
      }
    } else {
      sender.sendMessage(ChatColor.GRAY + "Please specify the item name or ID.");
      return true;
    }
    
    if (getMatchingPlayers() != "None")
    {
      sender.sendMessage(ChatColor.GRAY + matchingPlayers.size() + " player(s) have " + itemName + ": " + getMatchingPlayers());
    }
    else
    {
      sender.sendMessage(ChatColor.GRAY + "Could not find any player with the item: " + itemName);
    }
    
    return true;
  }
  
  public String getMatchingPlayers()
  {
    String players = "";
    if (matchingPlayers.size() > 0)
    {
      for (int i = 0; i < matchingPlayers.size(); i++)
      {
        if (players != "")
        {
          players = players + ", " + (String)matchingPlayers.get(i);
        }
        else
        {
          players = (String)matchingPlayers.get(i);
        }
        
      }
      
    } else {
      players = "None";
    }
    return players;
  }
}
