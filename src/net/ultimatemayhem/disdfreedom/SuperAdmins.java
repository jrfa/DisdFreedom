package net.ultimatemayhem.disdfreedom;

import org.bukkit.command.CommandSender;

public class SuperAdmins
{
  public SuperAdmins() {}
  
  public static boolean isSeniorAdmin(CommandSender user)
  {
    try
    {
      if (org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("TotalFreedomMod"))
      {
        return me.StevenLawson.TotalFreedomMod.TFM_SuperadminList.isSeniorAdmin(user);
      }
    }
    catch (Exception localException) {}
    


    return false;
  }
  
  public static boolean isUserSuperadmin(CommandSender user)
  {
    try
    {
      if (org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("TotalFreedomMod"))
      {
        return me.StevenLawson.TotalFreedomMod.TFM_SuperadminList.isUserSuperadmin(user);
      }
    }
    catch (Exception localException) {}
    


    return false;
  }
}
