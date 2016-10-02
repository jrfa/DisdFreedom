package net.ultimatemayhem.disdfreedom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;
import net.minecraft.server.v1_6_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_6_R2.NBTTagCompound;
import net.minecraft.server.v1_6_R2.NBTTagDouble;
import net.minecraft.server.v1_6_R2.NBTTagFloat;
import net.minecraft.server.v1_6_R2.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;






























public class ImprovedOfflinePlayer
{
  private String player;
  private File file;
  private NBTTagCompound compound;
  private boolean exists = false;
  private boolean autosave = true;
  
  public ImprovedOfflinePlayer(String playername) {
    exists = loadPlayerData(playername);
  }
  
  public ImprovedOfflinePlayer(OfflinePlayer offlineplayer) {
    exists = loadPlayerData(offlineplayer.getName());
  }
  
  private boolean loadPlayerData(String name) {
    try {
      player = name;
      for (World w : Bukkit.getWorlds()) {
        file = new File(w.getWorldFolder(), "players" + File.separator + player + ".dat");
        if (file.exists()) {
          compound = NBTCompressedStreamTools.a(new FileInputStream(file));
          player = file.getCanonicalFile().getName().replace(".dat", "");
          return true;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public void savePlayerData() {
    if (exists) {
      try {
        NBTCompressedStreamTools.a(compound, new FileOutputStream(file));
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public boolean exists() {
    return exists;
  }
  
  public boolean getAutoSave() {
    return autosave;
  }
  
  public void setAutoSave(boolean autosave) {
    this.autosave = autosave;
  }
  
  public Location getLocation() {
    NBTTagList position = compound.getList("Pos");
    NBTTagList rotation = compound.getList("Rotation");
    return new Location(
      Bukkit.getWorld(new UUID(compound.getLong("WorldUUIDMost"), compound.getLong("WorldUUIDLeast"))), 
      get0data, 
      get1data, 
      get2data, 
      get0data, 
      get1data);
  }
  
  public void setLocation(Location location)
  {
    World w = location.getWorld();
    UUID uuid = w.getUID();
    compound.setLong("WorldUUIDMost", uuid.getMostSignificantBits());
    compound.setLong("WorldUUIDLeast", uuid.getLeastSignificantBits());
    compound.setInt("Dimension", w.getEnvironment().getId());
    NBTTagList position = new NBTTagList();
    position.add(new NBTTagDouble(null, location.getX()));
    position.add(new NBTTagDouble(null, location.getY()));
    position.add(new NBTTagDouble(null, location.getZ()));
    compound.set("Pos", position);
    NBTTagList rotation = new NBTTagList();
    rotation.add(new NBTTagFloat(null, location.getYaw()));
    rotation.add(new NBTTagFloat(null, location.getPitch()));
    compound.set("Rotation", rotation);
    if (autosave) savePlayerData();
  }
  
  public String getName() {
    return player;
  }
}
