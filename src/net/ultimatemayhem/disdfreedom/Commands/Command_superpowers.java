package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_superpowers extends SCCommand
{
  public Command_superpowers() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (sender_p != null)
    {
      if (args.length > 0)
      {
        if (args[0].equalsIgnoreCase("on"))
        {
          if (!DisdFreedom.superpowersEnabledFor.contains(sender.getName()))
          {
            DisdFreedom.superpowersEnabledFor.add(sender.getName());
            sender.sendMessage(ChatColor.GRAY + "Super powers enabled.");
          }
          else
          {
            sender.sendMessage(ChatColor.GRAY + "Super powers are already enabled.");
          }
        }
        else if (args[0].equalsIgnoreCase("off"))
        {
          if (DisdFreedom.superpowersEnabledFor.contains(sender.getName()))
          {
            for (int i = 0; i < DisdFreedom.superpowersEnabledFor.size(); i++)
            {
              if (((String)DisdFreedom.superpowersEnabledFor.get(i)).equals(sender.getName()))
              {
                DisdFreedom.superpowersEnabledFor.remove(i);
                sender.sendMessage(ChatColor.GRAY + "Super powers disabled.");
              }
              
            }
            
          } else {
            sender.sendMessage(ChatColor.GRAY + "Super powers are not enabled.");
          }
        }
        else if (args[0].equalsIgnoreCase("ride"))
        {
          if (args.length > 1)
          {
            if (args[1].equalsIgnoreCase("off"))
            {
              if ((sender_p.getVehicle() != null) && ((sender_p.getVehicle() instanceof Player)))
              {
                Player ridingPlayer = (Player)sender_p.getVehicle();
                ridingPlayer.setPassenger(sender_p);
                sender.sendMessage(ChatColor.GRAY + "Stopped riding: " + ridingPlayer.getName());
              }
              else
              {
                sender.sendMessage(ChatColor.GRAY + "You aren't riding anyone!");
              }
              return true;
            }
            for (Player p : DisdFreedom.server.getOnlinePlayers())
            {
              if (p.getName().toLowerCase().contains(args[1]))
              {

                if (p != sender_p)
                {
                  p.setPassenger(sender_p);
                  if (p.getPassenger() == sender_p)
                  {
                    sender.sendMessage(ChatColor.GRAY + "Now riding: " + p.getName());
                  }
                  else
                  {
                    sender.sendMessage(ChatColor.GRAY + "Stopped riding: " + p.getName());
                  }
                }
                else
                {
                  sender.sendMessage(ChatColor.GRAY + "You cannot ride yourself!");
                }
              }
            }
          }
          else
          {
            sender.sendMessage(ChatColor.GRAY + "Please specify who you would like to ride.");
          }
          
        }
      }
      else {
        return false;
      }
      
    }
    else {
      sender.sendMessage(ChatColor.RED + "You can only use super powers in game.");
    }
    return true;
  }
}
