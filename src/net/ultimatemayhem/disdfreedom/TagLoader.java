package net.ultimatemayhem.disdfreedom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import org.bukkit.ChatColor;

public class TagLoader
{
  public TagLoader() {}
  
  public static void saveTagList()
  {
    if (DisdFreedom.playerTags.size() > 0) {
      File file = new File(DisdFreedom.plugin.getDataFolder(), "/taglist.yml");
      



      try
      {
        ArrayList<String> playerNames = new ArrayList();
        ArrayList<String> playerTags = new ArrayList();
        
        List<String> keySet = new ArrayList(DisdFreedom.playerTags.keySet());
        for (String str : keySet) {
          playerNames.add(str);
        }
        
        List<String> values = new ArrayList(DisdFreedom.playerTags.values());
        for (String str : values) {
          playerTags.add(str);
        }
        
        for (int i = 0; i < keySet.size(); i++) {
          PrintWriter out = new PrintWriter(new java.io.BufferedWriter(new FileWriter(file, true)));
          
          String playerName = (String)playerNames.get(i);
          String playerTag = (String)playerTags.get(i);
          


          String newLine = "";
          
          if (file.length() > 0L) {
            newLine = "\n";
          }
          
          DisdFreedom.logger.info(playerName);
          


          if (!tagExists(playerName)) {
            out.write(newLine + playerName + " " + playerTag.replaceAll(" ", "").replaceAll(playerName, ""));
          } else {
            setTag(playerName, playerTag);
          }
          
          out.close();
        }
      } catch (java.io.IOException e) { DisdFreedom.logger.warning("Error saving tags!");
      }
    }
  }
  
  private static boolean tagExists(String s) { File file = new File(DisdFreedom.plugin.getDataFolder(), "/taglist.yml");
    try
    {
      Scanner scanner = new Scanner(file);
      
      String curLine = "";
      while (scanner.hasNextLine()) {
        curLine = scanner.nextLine();
        if (curLine.split(" ")[0].equalsIgnoreCase(s)) {
          return true;
        }
      }
    }
    catch (Exception localException) {}
    return false;
  }
  
  private static String getTag(String s) {
    File file = new File(DisdFreedom.plugin.getDataFolder(), "/taglist.yml");
    try
    {
      Scanner scanner = new Scanner(file);
      
      String curLine = "";
      while (scanner.hasNextLine()) {
        curLine = scanner.nextLine();
        if (curLine.split(" ")[0].equalsIgnoreCase(s)) {
          return curLine.split(" ")[1];
        }
      }
    }
    catch (Exception localException) {}
    return null;
  }
  
  private static void setTag(String name, String newTag) {
    if (tagExists(name))
    {
      try
      {
        File file = new File(DisdFreedom.plugin.getDataFolder(), "/taglist.yml");
        
        BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
        String line = "";String prevText = "";
        while ((line = reader.readLine()) != null) {
          prevText = prevText + line + System.getProperty("line.separator");
        }
        reader.close();
        
        String newText = name + " " + newTag;
        
        FileWriter writer = new FileWriter(file);
        writer.write(newText);
        writer.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } }
  }
  
  public static void loadTagList() {
    File file = new File(DisdFreedom.plugin.getDataFolder(), "/taglist.yml");
    try
    {
      Scanner scanner = new Scanner(file);
      
      int tagCount = 0;
      
      while (scanner.hasNextLine()) {
        String curLine = scanner.nextLine();
        
        String[] tagArgs = curLine.split(" ");
        
        String playerName = tagArgs[0];
        String playerTag = tagArgs[1];
        
        playerTag = ChatColor.translateAlternateColorCodes('&', playerTag);
        
        DisdFreedom.playerTags.put(playerName, playerTag + " " + ChatColor.RED + playerName + ChatColor.WHITE);
        
        tagCount++;
      }
      
      DisdFreedom.logger.info(tagCount + " tag(s) loaded.");
    } catch (java.io.FileNotFoundException e) { DisdFreedom.logger.warning("Error loading tags!");
    }
  }
}
