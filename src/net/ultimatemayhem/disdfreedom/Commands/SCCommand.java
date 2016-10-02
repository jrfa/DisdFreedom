package net.ultimatemayhem.disdfreedom.Commands;

import java.util.List;
import java.util.logging.Logger;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import net.ultimatemayhem.disdfreedom.SuperAdmins;
import net.ultimatemayhem.disdfreedom.UserInfo;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SCCommand
{
  protected DisdFreedom plugin;
  protected Server server;
  private CommandSender commandsender;
  public boolean adminOnly = false;
  

  public SCCommand() {}
  

  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    DisdFreedom.logger.severe("Command Error: Command not implemented: " + cmd.getName());
    sender.sendMessage(ChatColor.RED + "Command Error: Command not implemented: " + cmd.getName());
    return false;
  }
  
  public void setPlugin(DisdFreedom plugin)
  {
    this.plugin = plugin;
    server = plugin.getServer();
  }
  
  public void setCommandsender(CommandSender commandsender)
  {
    this.commandsender = commandsender;
  }
  
  public void playerMsg(CommandSender sender, String message, ChatColor color)
  {
    sender.sendMessage(color + message);
  }
  
  public void playerMsg(String message, ChatColor color)
  {
    playerMsg(commandsender, message, color);
  }
  
  public void playerMsg(CommandSender sender, String message)
  {
    playerMsg(sender, message, ChatColor.GRAY);
  }
  
  public void playerMsg(String message)
  {
    playerMsg(commandsender, message);
  }
  
  public void broadcastMessage(String s, ChatColor c)
  {
    DisdFreedom.server.broadcastMessage(c + s);
  }
  
  public boolean senderHasPermission(Class<?> cmd_class, CommandSender sender)
  {
    CommandPermissions permissions = (CommandPermissions)cmd_class.getAnnotation(CommandPermissions.class);
    if (permissions != null)
    {
      if (permissions.ignore_permissions())
      {
        return true;
      }
      

      boolean is_super = SuperAdmins.isUserSuperadmin(sender);
      boolean is_senior = false;
      if (is_super)
      {
        is_senior = SuperAdmins.isSeniorAdmin(sender);
      }
      


      ADMIN_LEVEL level = permissions.level();
      SOURCE_TYPE_ALLOWED source = permissions.source();
      boolean block_host_console = permissions.block_host_console();
      
      Player sender_p = null;
      if ((sender instanceof Player))
      {
        sender_p = (Player)sender;
      }
      
      if (sender_p == null)
      {
        if (source == SOURCE_TYPE_ALLOWED.ONLY_IN_GAME)
        {
          return false;
        }
        if ((level == ADMIN_LEVEL.SENIOR) && (!is_senior))
        {
          return false;
        }
        if ((block_host_console) && (sender.getName() == "CONSOLE"))
        {
          return false;
        }
      }
      else
      {
        if (source == SOURCE_TYPE_ALLOWED.ONLY_CONSOLE)
        {
          return false;
        }
        if (level == ADMIN_LEVEL.SENIOR)
        {
          if (is_senior)
          {
            UserInfo playerdata = UserInfo.getPlayerData(sender_p);
            Boolean superadminIdVerified = playerdata.isSuperadminIdVerified();
            
            if (superadminIdVerified != null)
            {
              if (!superadminIdVerified.booleanValue())
              {
                return false;
              }
            }
          }
          else
          {
            return false;
          }
        } else {
          if ((level == ADMIN_LEVEL.SUPER) && (!is_super))
          {
            return false;
          }
          if ((level == ADMIN_LEVEL.OP) && (!sender_p.isOp()))
          {
            return false; }
        }
      }
      return true;
    }
    
    return true;
  }
  
  public Player getPlayer(String partialname) throws CantFindPlayerException
  {
    List<Player> matches = server.matchPlayer(partialname);
    if (matches.isEmpty())
    {
      for (Player p : server.getOnlinePlayers())
      {
        if (p.getDisplayName().toLowerCase().contains(partialname.toLowerCase()))
        {
          return p;
        }
      }
      throw new CantFindPlayerException(partialname);
    }
    

    return (Player)matches.get(0);
  }
}
