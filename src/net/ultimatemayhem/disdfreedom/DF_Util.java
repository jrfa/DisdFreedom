package net.ultimatemayhem.disdfreedom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DF_Util
{
  public DF_Util() {}
  
  public static java.util.ArrayList getAvailableWorlds()
  {
    java.util.ArrayList<World> availableWorlds = new java.util.ArrayList();
    for (World world : DisdFreedom.server.getWorlds())
    {
      if (((!world.getName().equals("adminworld")) && (!world.getName().contains("nether"))) || (!world.getName().contains("end")))
      {
        availableWorlds.add(world);
      }
    }
    return availableWorlds;
  }
  
  public static boolean isSuperAdmin(Player player)
  {
    return SuperAdmins.isUserSuperadmin(player);
  }
  
  public static boolean isSeniorAdmin(org.bukkit.command.CommandSender sender) {
    return SuperAdmins.isSeniorAdmin(sender);
  }
  


















  public static String getPlayerIP(Player player)
  {
    return player.getAddress().getAddress().getHostAddress();
  }
  
  public static enum blockPlaceType
  {
    FLOOR,  WALL,  CUBE;
  }
  
  public static void placeBlocks(Location loc, Material material, blockPlaceType placeType, int width, int height, boolean set)
  {
    switch (placeType)
    {
    case CUBE: 
      for (int x = 0; x < width; x++)
      {
        for (int z = 0; z < width; z++)
        {
          Location location = loc.getBlock().getLocation();
          location.setX(location.getX() + x);
          location.setZ(location.getZ() + z);
          if (!set)
          {
            if (location.getWorld().getBlockAt(location).getType() == Material.AIR)
            {
              location.getWorld().getBlockAt(location).setType(material);
            }
            
          }
          else {
            location.getWorld().getBlockAt(location).setType(material);
          }
        }
      }
      break;
    case FLOOR: 
      for (int x = 0; x < width; x++)
      {
        for (int y = 0; y < height; y++)
        {
          Location location = loc.getBlock().getLocation();
          location.setX(location.getX() + x);
          location.setY(location.getY() + y);
          if (!set)
          {
            if (location.getWorld().getBlockAt(location).getType() == Material.AIR)
            {
              location.getWorld().getBlockAt(location).setType(material);
            }
            
          }
          else {
            location.getWorld().getBlockAt(location).setType(material);
          }
        }
      }
      break;
    case WALL: 
      for (int x = 0; x < width; x++)
      {
        for (int y = 0; y < height; y++)
        {
          for (int z = 0; z < width; z++)
          {
            Location location = loc.getBlock().getLocation();
            location.setX(location.getX() + x);
            location.setY(location.getY() + y);
            location.setZ(location.getZ() + z);
            if (!set)
            {
              if (location.getWorld().getBlockAt(location).getType() == Material.AIR)
              {
                location.getWorld().getBlockAt(location).setType(material);
              }
              
            }
            else {
              location.getWorld().getBlockAt(location).setType(material);
            }
          }
        }
      }
    }
    
  }
  
  public static boolean isNewPlayer(Player player)
  {
    org.bukkit.OfflinePlayer[] offlinePlayers = DisdFreedom.server.getOfflinePlayers();
    boolean newPlayer = true;
    for (org.bukkit.OfflinePlayer offlinePlayer : offlinePlayers)
    {
      if (offlinePlayer.getName().equals(player.getName()))
      {
        newPlayer = false;
      }
    }
    return newPlayer;
  }
  
  public static String getStringWithoutNumbers(String s)
  {
    String finalStr = s;
    for (int i = 0; i < 10; i++)
    {
      finalStr = finalStr.replaceAll(i, "");
    }
    return finalStr;
  }
  
  public static void setConfig(FileConfiguration config)
  {
    config.addDefault("admin_world_enabled", Boolean.valueOf(true));
    config.addDefault("grief_detect", Boolean.valueOf(true));
    config.addDefault("max_worldedit_size", Integer.valueOf(1000));
  }
}
