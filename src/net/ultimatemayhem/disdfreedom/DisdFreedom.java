package net.ultimatemayhem.disdfreedom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import net.ultimatemayhem.disdfreedom.Commands.SCCommand;
import net.ultimatemayhem.disdfreedom.Listener.DFEntityListener;
import net.ultimatemayhem.disdfreedom.Listener.DFPlayerListener;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;







public class DisdFreedom
  extends JavaPlugin
{
  public static final Logger logger = Logger.getLogger("Minecraft");
  public static DisdFreedom plugin = null;
  public static Server server = Bukkit.getServer();
  
  public static final String COMMAND_PATH = "net.ultimatemayhem.disdfreedom.Commands";
  public static final String COMMAND_PREFIX = "Command_";
  public static final String SUPERADMIN_FILE = "plugins/TotalFreedomMod/superadmin.yml";
  public static final String MSG_NO_PERMS = ChatColor.YELLOW + "You do not have permission to use this command.";
  public static String flatlandsGenerationParams = "16,stone,32,dirt,1,grass";
  public static final double version = 2.4D;
  public static ArrayList<Player> pendingPlayerExplosions = new ArrayList();
  public static ArrayList<Player> blockingPlayerActionsFor = new ArrayList();
  public static ArrayList<String> superpowersEnabledFor = new ArrayList();
  public static ArrayList<String> burriedPlayers = new ArrayList();
  public static boolean lockdownMode = false;
  
  public static HashMap<String, String> playerTags = new HashMap();
  
  public static boolean adminworldEnabled = true;
  public static boolean griefDetectEnabled = true;
  public static int worldEditCommandLimitSize = 1000;
  static World adminWorld;
  
  public DisdFreedom() {}
  
  public void onEnable() { logger.info("DisdFreedom by disaster839 activated!");
    logger.info("Make sure TotalFreedomMod is also installed!");
    
    plugin = this;
    
    PluginManager pm = server.getPluginManager();
    pm.registerEvents(new DFPlayerListener(), plugin);
    pm.registerEvents(new DFEntityListener(), plugin);
    
    File configFile = new File(plugin.getDataFolder(), "config.yml");
    if (!configFile.exists())
    {
      DF_Util.setConfig(getConfig());
      getConfig().options().copyDefaults(true);
      try
      {
        getConfig().save(configFile);
      }
      catch (IOException localIOException) {}
    }
    

    loadConfig();
    
    if (server.getWorld("adminworld") != null) {
      adminWorld = server.getWorld("adminworld");
    }
  }
  


  public void loadConfig()
  {
    adminworldEnabled = getConfig().getBoolean("admin_world_enabled");
    griefDetectEnabled = getConfig().getBoolean("grief_detect");
    worldEditCommandLimitSize = getConfig().getInt("max_worldedit_size");
  }
  


  public void onDisable()
  {
    logger.info("DisdFreedom is now deactivated!");
  }
  


  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    try
    {
      Player sender_p = null;
      boolean senderIsConsole = false;
      if ((sender instanceof Player))
      {
        sender_p = (Player)sender;
      }
      else
      {
        senderIsConsole = true;
      }
      

      try
      {
        ClassLoader classLoader = DisdFreedom.class.getClassLoader();
        SCCommand dispatcher = (SCCommand)classLoader.loadClass(String.format("%s.%s%s", new Object[] { "net.ultimatemayhem.disdfreedom.Commands", "Command_", cmd.getName().toLowerCase() })).newInstance();
        dispatcher.setPlugin(this);
        dispatcher.setCommandsender(sender);
      }
      catch (Throwable ex)
      {
        logger.severe("Command not loaded: " + cmd.getName() + "\n" + ExceptionUtils.getStackTrace(ex));
        sender.sendMessage(ChatColor.RED + "Command Error: Command not loaded: " + cmd.getName());
        return true;
      }
      
      try
      {
        if (dispatcher.senderHasPermission(dispatcher.getClass(), sender))
        {
          return dispatcher.run(sender, sender_p, cmd, commandLabel, args, senderIsConsole);
        }
        

        sender.sendMessage(MSG_NO_PERMS);

      }
      catch (Throwable ex)
      {
        sender.sendMessage(ChatColor.RED + "Command Error: " + ex.getMessage());
      }
      
      SCCommand dispatcher = null;
    }
    catch (Throwable ex)
    {
      logger.severe("Command Error: " + commandLabel + "\n" + ExceptionUtils.getStackTrace(ex));
      sender.sendMessage(ChatColor.RED + "Unknown Command Error.");
    }
    
    return true;
  }
  


  public static void generateAdminWorld()
  {
    try
    {
      adminWorld = server.getWorld("adminworld");
      if (adminWorld == null)
      {
        WorldCreator creator = new WorldCreator("adminworld");
        creator.environment(World.Environment.NORMAL);
        creator.generator(new CleanroomChunkGenerator(flatlandsGenerationParams));
        creator.generateStructures(false);
        adminWorld = creator.createWorld();
      }
    }
    catch (Exception localException) {}
  }
  


  public static World getAdminWorld()
  {
    return adminWorld;
  }
  
  public static void setAdminWorld(Object obj) {
    if (obj == null) {
      adminWorld = null;
    } else if ((obj instanceof World)) {
      adminWorld = (World)obj;
    }
  }
  
  public static boolean areSuperpowersEnabledFor(String s)
  {
    if (superpowersEnabledFor.contains(s))
    {
      return true;
    }
    return false;
  }
  
  public static void spawnParticleSquare(Entity entity, Effect effect, int radius)
  {
    for (int x = 0; x < radius; x++)
    {
      for (int y = 0; y < radius; y++)
      {
        for (int z = 0; z < radius; z++)
        {
          Location location = entity.getLocation();
          location.setX(location.getX() + x);
          location.setY(location.getY() + y);
          location.setZ(location.getZ() + z);
          entity.getWorld().playEffect(location, effect, 0);
        }
      }
    }
  }
  
  public static boolean jumpPadsEnabled = false;
  public static boolean advancedJumpPads = false;
  public static float jumpPadHeight = 2.0F;
  public static int jumpPadBlockID = 35;
  static CombinedVelocity cv;
  
  public static void updateJumppadsForPlayer(Player player)
  {
    if (jumpPadsEnabled)
    {

































































      JumppadHandler handler = new JumppadHandler(player);
      handler.handle();
    }
  }
}
