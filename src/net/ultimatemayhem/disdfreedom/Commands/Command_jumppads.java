package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_jumppads extends SCCommand
{
  public Command_jumppads() {}
  
  public boolean run(CommandSender sender, org.bukkit.entity.Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length > 0)
    {
      if (args[0].equalsIgnoreCase("advanced"))
      {
        DisdFreedom.advancedJumpPads = !DisdFreedom.advancedJumpPads;
        
        if (DisdFreedom.advancedJumpPads)
        {
          if (sender_p != null)
          {
            DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender_p.getName() + " - Set Jump Pad mode to: Advanced");
          }
          else
          {
            DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Set Jump Pad mode to: Advanced");
          }
          

        }
        else if (sender_p != null)
        {
          DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender_p.getName() + " - Set Jump Pad mode to: Normal");
        }
        else
        {
          DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Set Jump Pad mode to: Normal");
        }
        
        return true;
      }
    }
    
    if (args.length > 0)
    {
      if (args[0].equalsIgnoreCase("info"))
      {
        sender.sendMessage(ChatColor.GREEN + "--- Jump Pad Info ---");
        sender.sendMessage(ChatColor.GREEN + "Enabled: " + DisdFreedom.jumpPadsEnabled);
        sender.sendMessage(ChatColor.GREEN + "Height: " + DisdFreedom.jumpPadHeight);
        if (DisdFreedom.advancedJumpPads)
        {
          sender.sendMessage(ChatColor.GREEN + "Mode: Advanced");
        }
        else
        {
          sender.sendMessage(ChatColor.GREEN + "Mode: Normal");
        }
        return true;
      }
    }
    
    if (args.length > 1)
    {
      try
      {
        String reqHeight = args[1];
        int newHeight = Integer.parseInt(reqHeight);
        if (newHeight < 11)
        {
          DisdFreedom.jumpPadHeight = newHeight;
          
          if (sender_p != null)
          {
            DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender_p.getName() + " - Setting Jump Pad height to: " + newHeight);
          }
          else
          {
            DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Setting Jump Pad height to: " + newHeight);
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "The new height cannot be greater than 10.");
        }
      }
      catch (Exception ex)
      {
        sender.sendMessage(ChatColor.RED + "There was a problem setting the new Jump Pad height: " + ex.getMessage());
      }
      return true;
    }
    if (args.length == 1)
    {
      sender.sendMessage(ChatColor.RED + "Please specify the new height.");
      return true;
    }
    
    DisdFreedom.jumpPadsEnabled = !DisdFreedom.jumpPadsEnabled;
    
    if (DisdFreedom.jumpPadsEnabled)
    {
      if (sender_p != null)
      {
        DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender_p.getName() + " - Enabling Jump Pads");
      }
      else
      {
        DisdFreedom.server.broadcastMessage(ChatColor.GREEN + sender.getName() + " - Enabling Jump Pads");
      }
      

    }
    else if (sender_p != null)
    {
      DisdFreedom.server.broadcastMessage(ChatColor.RED + sender_p.getName() + " - Disabling Jump Pads");
    }
    else
    {
      DisdFreedom.server.broadcastMessage(ChatColor.RED + sender.getName() + " - Disabling Jump Pads");
    }
    

    return true;
  }
}
