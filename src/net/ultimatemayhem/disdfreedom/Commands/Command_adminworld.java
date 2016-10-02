package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import java.util.List;
import net.ultimatemayhem.disdfreedom.DF_Util;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_adminworld extends SCCommand
{
  public Command_adminworld() {}
  
  public boolean run(CommandSender sender, Player sender_p, org.bukkit.command.Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (sender_p != null)
    {
      if (args.length > 0)
      {
        if (args[0].equalsIgnoreCase("setspawn"))
        {
          if (sender_p.getLocation().getWorld().getName().equalsIgnoreCase("adminworld"))
          {
            DisdFreedom.getAdminWorld().setSpawnLocation((int)sender_p.getLocation().getX(), (int)sender_p.getLocation().getY(), (int)sender_p.getLocation().getZ());
            sender.sendMessage(ChatColor.GRAY + "Spawn set.");
          }
          else
          {
            sender.sendMessage(ChatColor.GRAY + "You must be in the adminworld to set the spawn.");
          }
          return true; }
        if (args[0].equalsIgnoreCase("unload")) {
          if (DF_Util.isSeniorAdmin(sender)) {
            if (DisdFreedom.getAdminWorld() != null) {
              for (Player p : DisdFreedom.getAdminWorld().getPlayers())
                try {
                  ArrayList<World> worlds = DF_Util.getAvailableWorlds();
                  World availableWorld = (World)worlds.get(0);
                  p.teleport(availableWorld.getSpawnLocation());
                  p.sendMessage(ChatColor.GRAY + "The adminworld has been unloaded.");
                } catch (Exception ex) { p.kickPlayer("Admin World is being unloaded");
                }
              if (!sender_p.getWorld().getName().equalsIgnoreCase("adminworld")) {
                sender_p.sendMessage("The adminworld has been unloaded.");
              }
              DisdFreedom.server.unloadWorld("adminworld", true);
              DisdFreedom.setAdminWorld(null);
            } else {
              sender.sendMessage(ChatColor.GRAY + "The adminworld is not loaded.");
            }
          } else {
            sender.sendMessage(DisdFreedom.MSG_NO_PERMS);
          }
          return true; }
        if (args[0].equalsIgnoreCase("list")) {
          if (DisdFreedom.getAdminWorld() != null) {
            sender.sendMessage(ChatColor.GRAY + "There are currently " + DisdFreedom.getAdminWorld().getPlayers().size() + " player(s) in the Admin World:");
          }
          sender.sendMessage(ChatColor.GRAY + getPlayers());
          return true;
        }
      }
      if (DisdFreedom.getAdminWorld() == null)
      {
        DisdFreedom.generateAdminWorld();
        if (DisdFreedom.getAdminWorld().getSpawnLocation() == null)
        {
          DisdFreedom.getAdminWorld().setSpawnLocation(1, 52, 1);
        }
      }
      teleportToAdminWorld(sender_p);
      if (DisdFreedom.adminworldEnabled) {
        sender.sendMessage(ChatColor.GRAY + "Welcome to the Admin World!");
        sender.sendMessage(ChatColor.GRAY + "There are currently " + DisdFreedom.getAdminWorld().getPlayers().size() + " player(s) in the Admin World:");
        sender.sendMessage(ChatColor.GRAY + getPlayers());
      }
    }
    return true;
  }
  
  public void teleportToAdminWorld(Player p)
  {
    Location adminWorldSpawn = DisdFreedom.getAdminWorld().getSpawnLocation();
    p.teleport(adminWorldSpawn);
  }
  
  public String getPlayers() {
    if (DisdFreedom.getAdminWorld() != null) {
      String players = "";
      for (int i = 0; i < DisdFreedom.getAdminWorld().getPlayers().size(); i++) {
        if (players != "") {
          players = players + ", " + ((Player)DisdFreedom.getAdminWorld().getPlayers().get(i)).getName();
        } else {
          players = ((Player)DisdFreedom.getAdminWorld().getPlayers().get(i)).getName();
        }
      }
      return players;
    }
    return "The adminworld is not loaded.";
  }
}
