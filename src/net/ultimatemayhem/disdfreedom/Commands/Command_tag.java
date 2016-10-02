package net.ultimatemayhem.disdfreedom.Commands;

import java.util.HashMap;
import java.util.logging.Logger;
import net.ultimatemayhem.disdfreedom.DF_Util;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;






@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=true)
public class Command_tag
  extends SCCommand
{
  public Command_tag() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (sender_p == null) {
      return true;
    }
    
    if (args.length < 1) {
      if ((sender_p != null) && (sender_p.getDisplayName() != sender_p.getName())) {
        String tag = sender_p.getDisplayName().replaceAll(sender_p.getName(), "");
        if ((tag != "") && (DisdFreedom.playerTags.containsKey(sender.getName()))) {
          sender.sendMessage(ChatColor.BLUE + "Tag: " + tag);
          DisdFreedom.logger.info("LENGTH: " + tag.length() + " TAG: " + tag);
        } else {
          sender.sendMessage(ChatColor.RED + "You have no tag.");
        }
        return true;
      }
      return false;
    }
    
    Player p = sender_p;
    if (!args[0].contains("&")) {
      try
      {
        p = getPlayer(args[0]);
      }
      catch (CantFindPlayerException localCantFindPlayerException) {}
    }
    
    if (p != null) {
      String reqNick = args[0];
      if (args.length > 1) {
        reqNick = args[1];
      }
      
      if (reqNick.equalsIgnoreCase("off")) {
        if (DisdFreedom.playerTags.containsKey(p.getName())) {
          DisdFreedom.playerTags.remove(p.getName());
        }
        p.setDisplayName(p.getName());
        
        sender.sendMessage(ChatColor.BLUE + "Tag removed.");
        return true; }
      if (reqNick.equalsIgnoreCase("clearall")) {
        for (Player player : DisdFreedom.server.getOnlinePlayers()) {
          if (DisdFreedom.playerTags.containsKey(player.getName())) {
            try {
              DisdFreedom.playerTags.remove(player.getName());
            } catch (Exception localException) {}
          }
          player.setDisplayName(player.getName());
          player.sendMessage(ChatColor.BLUE + "Tag removed.");
        }
        sender.sendMessage(ChatColor.BLUE + "All tags have been removed.");
        return true;
      }
      
      if (reqNick.contains("&")) {
        reqNick = ChatColor.translateAlternateColorCodes('&', reqNick);
        reqNick = reqNick.replaceAll("&", "");
      } else {
        reqNick = ChatColor.AQUA + reqNick;
      }
      
      if (reqNick.isEmpty()) {
        return false;
      }
      
      if (reqNick.length() >= 30) {
        sender.sendMessage(ChatColor.RED + "Length too long!");
        return true;
      }
      
      if ((reqNick.toLowerCase().contains("admin")) && (!DF_Util.isSuperAdmin(sender_p))) {
        sender.sendMessage(ChatColor.RED + "Invalid tag.");
        return true;
      }
      
      if (reqNick.equalsIgnoreCase(p.getName())) {
        return false;
      }
      
      reqNick = reqNick.replaceAll(p.getName(), "");
      
      String tag = reqNick + ChatColor.RED + " " + p.getName() + ChatColor.WHITE;
      
      p.setDisplayName(tag);
      
      if (DisdFreedom.playerTags.containsKey(p.getName())) {
        DisdFreedom.playerTags.remove(p.getName());
      }
      
      DisdFreedom.playerTags.put(p.getName(), tag);
      
      if (p.getName().equals(sender_p.getName())) {
        p.sendMessage(ChatColor.BLUE + "Tag set to: " + reqNick);
      } else {
        sender.sendMessage(ChatColor.BLUE + "Set " + ChatColor.GOLD + p.getName() + ChatColor.BLUE + "'s tag to: " + tag.replaceAll(p.getName(), ""));
        p.sendMessage(ChatColor.GOLD + sender_p.getName() + ChatColor.BLUE + " Set your tag to: " + reqNick.replaceAll(p.getName(), ""));
      }
    }
    return true;
  }
}
