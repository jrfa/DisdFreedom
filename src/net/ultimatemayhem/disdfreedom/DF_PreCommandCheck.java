package net.ultimatemayhem.disdfreedom;



public class DF_PreCommandCheck
{
  public DF_PreCommandCheck() {}
  

  public static boolean checkCommand(String cmd)
  {
    boolean block = false;
    cmd = cmd.toLowerCase();
    String[] args = cmd.split(" ");
    if ((cmd.startsWith("/plugincontrol disable")) && (cmd.contains("disdfreedom"))) {
      block = true;
    }
    return block;
  }
}
