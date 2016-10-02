package net.ultimatemayhem.disdfreedom.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import net.ultimatemayhem.disdfreedom.AbilityManager;
import net.ultimatemayhem.disdfreedom.DF_Util;
import net.ultimatemayhem.disdfreedom.DeathMessages;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import net.ultimatemayhem.disdfreedom.Spectator;
import net.ultimatemayhem.disdfreedom.WorldEdit_Check;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class DFPlayerListener implements org.bukkit.event.Listener
{
  boolean torchEnabled = false;
  
  Block prevBlock;
  ArrayList<Location> torchBlocks = new ArrayList();
  ArrayList<PlayerTorch> torchEnabledFor = new ArrayList();
  
  public DFPlayerListener() {}
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerMove(PlayerMoveEvent event) { Player player = event.getPlayer();
    
    if (DisdFreedom.jumpPadsEnabled)
    {
      DisdFreedom.updateJumppadsForPlayer(player);
    }
    
    if (AbilityManager.isBurriedPlayer(player.getName()))
    {
      event.setCancelled(true);
      Location playerLocation = event.getTo().clone();
      playerLocation = event.getFrom();
      player.teleport(playerLocation);
    }
    try
    {
      if ((AbilityManager.isPlayerSpectating(player)) || (AbilityManager.isPlayerBeingSpectated(player))) {
        for (Spectator spectator : AbilityManager.spectators) {
          if (spectator.getSpectating().getName() == player.getName()) {
            spectator.update();
          }
        }
      }
    }
    catch (Exception localException) {}
    for (PlayerTorch playerTorch : torchEnabledFor) {
      if (playerTorch.getPlayerName().equals(player.getName())) {
        playerTorch.onPlayerMove();
      }
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerTeleport(PlayerTeleportEvent event)
  {
    Player player = event.getPlayer();
    if (event.getTo().getWorld().getName().equalsIgnoreCase("adminworld"))
    {
      if (!DF_Util.isSuperAdmin(player))
      {
        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You cannot teleport to that location because it is in a world you cannot enter.");
      }
      else if (!DisdFreedom.adminworldEnabled)
      {
        event.setCancelled(true);
        player.sendMessage(ChatColor.GRAY + "The adminworld is currently disabled.");
      }
    }
    
    if (AbilityManager.isBurriedPlayer(player.getName()))
    {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerChat(AsyncPlayerChatEvent event)
  {
    Player player = event.getPlayer();
    if (!event.isCancelled())
    {
      if (player.getWorld().getName().equalsIgnoreCase("adminworld"))
      {
        event.setCancelled(true);
        sendAdminworldChatMessage(player, event.getMessage());
      }
    }
    
    if ((!event.isCancelled()) && (AbilityManager.isBurriedPlayer(player.getName())))
    {
      event.setCancelled(true);
      player.sendMessage(ChatColor.RED + "You have been muted.");
    }
    
    if (DisdFreedom.playerTags.containsKey(player.getName())) {
      try {
        player.setDisplayName((String)DisdFreedom.playerTags.get(player.getName()));
      }
      catch (Exception localException) {}
    }
  }
  























  public void sendAdminworldChatMessage(Player player, String s)
  {
    for (Player p : DisdFreedom.server.getOnlinePlayers())
    {
      if (p.getWorld().getName().equalsIgnoreCase("adminworld"))
      {
        p.sendMessage(ChatColor.BLUE + "[" + ChatColor.GRAY + "AdminWorld" + ChatColor.BLUE + "] " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + ": " + s);
      }
    }
    DisdFreedom.logger.info("[AdminWorld] " + player.getName() + ": " + s);
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    Block block = event.getClickedBlock();
    if (DisdFreedom.areSuperpowersEnabledFor(player.getName()))
    {
      if (event.getAction() == Action.LEFT_CLICK_AIR)
      {
        Block targetBlock = null;
        
        if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
          targetBlock = player.getTargetBlock(null, 120);
        }
        
        if (targetBlock != null)
        {
          player.getWorld().strikeLightning(targetBlock.getLocation());
          targetBlock.getWorld().createExplosion(targetBlock.getLocation(), 10.0F, false);
        }
      }
    }
  }
  
  public boolean isPlacingBlock(Player player, PlayerInteractEvent event)
  {
    if ((!event.isCancelled()) && (event.getAction() == Action.RIGHT_CLICK_BLOCK))
    {
      if (player.getItemInHand().getType() != org.bukkit.Material.AIR)
      {
        return true;
      }
    }
    return false; }
  
  Random random = new Random();
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
  {
    Entity entity = event.getRightClicked();
    Player player = event.getPlayer();
    if (DisdFreedom.areSuperpowersEnabledFor(player.getName()))
    {
      if ((entity instanceof Player))
      {
        int rndInt = random.nextInt(6);
        if (rndInt < 5)
        {
          entity.setVelocity(new Vector(2, 2, 0));
        }
        else
        {
          entity.setVelocity(new Vector(0, 2, 2));
        }
      }
      else if ((entity instanceof org.bukkit.entity.Animals))
      {
        entity.setPassenger(player);
      }
    }
  }
  

  @EventHandler(priority=EventPriority.NORMAL)
  public void onDamageEntityByEntity(EntityDamageByEntityEvent event)
  {
    if ((event.getEntity() instanceof Player))
    {
      Player player = (Player)event.getEntity();
      if (AbilityManager.isStrengthEnabledFor(player.getName()))
      {
        event.setDamage(0);
        player.setHealth(20);
      }
    }
    
    if ((event.getDamager() instanceof Player))
    {
      Player player = (Player)event.getDamager();
      if (AbilityManager.isStrengthEnabledFor(player.getName()))
      {
        int rndInt = random.nextInt(1000);
        event.setDamage(event.getDamage() + rndInt);
        event.getEntity().setFireTicks(1000);
        
        DisdFreedom.spawnParticleSquare(event.getEntity(), Effect.MOBSPAWNER_FLAMES, 2);
        DisdFreedom.spawnParticleSquare(event.getEntity(), Effect.SMOKE, 2);
        event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 2.0F, false);
        event.getEntity().setVelocity(event.getEntity().getLocation().subtract(player.getLocation()).toVector().multiply(6));
      }
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onDamageEntity(EntityDamageEvent event)
  {
    if ((event.getEntity() instanceof Player))
    {
      Player player = (Player)event.getEntity();
      if (AbilityManager.isStrengthEnabledFor(player.getName()))
      {
        event.setDamage(0);
        player.setHealth(20);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerLogin(PlayerLoginEvent event)
  {
    Player player = event.getPlayer();
    if ((DisdFreedom.lockdownMode) && (!DF_Util.isSuperAdmin(player)))
    {









      if (DF_Util.isNewPlayer(player))
      {
        event.setResult(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_BANNED);
        event.setKickMessage(ChatColor.RED + "Server is currently in lockdown mode, please come back in a few minutes.");
      }
    }
  }
  
  String[] welcomeMessages = { "HOW ARE YOU TODAY?", "HEY LOOK IT'S YOU!", "WHAT'S UP?", "LONG TIME NO SEE" };
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Player player = event.getPlayer();
    if ((player.getWorld().getName().equalsIgnoreCase("adminworld")) && (!DF_Util.isSuperAdmin(player)))
    {
      String playerIP = player.getAddress().getHostName();
      try
      {
        if (org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("TotalFreedomMod"))
        {
          if (!me.StevenLawson.TotalFreedomMod.TFM_SuperadminList.getSuperadminIPs().contains(playerIP))
          {
            try
            {
              World firstAvailableWorld = (World)DF_Util.getAvailableWorlds().get(0);
              Location spawnLocation = firstAvailableWorld.getSpawnLocation();
              player.teleport(spawnLocation);
            }
            catch (Exception ex)
            {
              player.kickPlayer("You cannot enter this world.");
            }
          }
        }
        





        if (event.getPlayer().hasPlayedBefore()) break label168;
      } catch (Exception localException1) {} }
    player.setOp(true);
    player.sendMessage(ChatColor.YELLOW + "You are now op!");
    
    label168:
    if (new Random().nextInt(1000) < 100) {
      player.sendMessage(ChatColor.GREEN + "\"" + ChatColor.AQUA + welcomeMessages[random.nextInt(welcomeMessages.length)] + ChatColor.GREEN + "\"");
    }
  }
  
  DeathMessages deathMessages = new DeathMessages();
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerDeath(PlayerDeathEvent event) {
    deathMessages.getEvent(event);
  }
  
  WorldEdit_Check worldEditCheck = new WorldEdit_Check();
  
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPreprocessCommand(PlayerCommandPreprocessEvent event) {
    if (event.getMessage().toLowerCase().startsWith("/plugincontrol")) {
      boolean block = net.ultimatemayhem.disdfreedom.DF_PreCommandCheck.checkCommand(event.getMessage());
      event.setCancelled(block);
      if (block) {
        CommandSender sender = event.getPlayer();
        sender.sendMessage(ChatColor.RED + "LOL NO!");
      }
    } else if ((event.getMessage().startsWith("//")) && (event.getMessage() != "//") && (!DF_Util.isSuperAdmin(event.getPlayer()))) {
      boolean block = worldEditCheck.getPreprocessCommandEvent(event);
      event.setCancelled(block);
      if (block) {
        event.getPlayer().sendMessage(ChatColor.RED + "Size too big.");
      }
    }
  }
}
