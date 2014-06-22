package me.sachal2406.boucemissaire;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import me.sachal2406.boucemissaire.Config;
import me.sachal2406.boucemissaire.GameData;
 
public class FileManager {
 
        private FileManager() { }
       
        static FileManager instance = new FileManager();
       
        public static FileManager getInstance() {
                return instance;
        }
       
        public void setup(Plugin p) {
        		if(!p.getDataFolder().exists()) {
                    p.getDataFolder().mkdir();
                }
                
                Config.reload();
                Config.load();
                Config.save();
                Config.reload();	
                
                GameData.reload();
                GameData.load();
                GameData.save();
                GameData.reload();
        }
       
        public FileConfiguration getConfig() {
                return Config.getConfig();
        }
        
        
        public FileConfiguration getGameData() {
            return GameData.getGameData();
        }
        
        
        public int getMaxPlayers() {
        	return getConfig().getInt("maxplayers");
        }
        
        public int getMinPlayers(){
        	return getConfig().getInt("minplayers");
        }
       
       
        public void saveConfig() {
        	Config.save();
        	GameData.save();
        }
       
        public void reloadConfig() {
        	Config.reload();
        	GameData.reload();
        }
}
