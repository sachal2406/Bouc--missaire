package me.sachal2406.boucemissaire;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class TPCountDown implements Runnable {
	
	@Override
	public void run() {

		
		if(Main.tpTime > 0){
			Main.tpTime--;
			Main.logger.info("" + Main.tpTime);
			
			ScoreboardManager manager = Bukkit.getScoreboardManager();
	        final Scoreboard board = manager.getNewScoreboard();
	    	Objective objective = board.registerNewObjective("sbtp", "dummy");
	    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    	objective.setDisplayName("§lBouc-Émissaire");
			objective.getScore(Bukkit.getOfflinePlayer("§lJoueurs:")).setScore(186);
			objective.getScore(Bukkit.getOfflinePlayer("§c"+Bukkit.getOnlinePlayers().length+"§b/" + Bukkit.getServer().getMaxPlayers())).setScore(185);
			objective.getScore(Bukkit.getOfflinePlayer("")).setScore(184);
			objective.getScore(Bukkit.getOfflinePlayer("§lStatut:")).setScore(183);
			objective.getScore(Bukkit.getOfflinePlayer("§cEn cours")).setScore(182);
			objective.getScore(Bukkit.getOfflinePlayer("§n")).setScore(181);
			objective.getScore(Bukkit.getOfflinePlayer("§lTemps:")).setScore(Main.tpTime);
		
			for(Player p : Bukkit.getOnlinePlayers()){
			p.setScoreboard(board);
			}
		}
		
		if(Main.tpTime == 181){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.tpTime / 60) + "§r§a minutes avant le prochain TP !");
		}
		if(Main.tpTime == 121){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.tpTime / 60) + "§r§a minutes avant le prochain TP !");
		}
		if(Main.tpTime == 61){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l1 §r§aminute avant le prochain TP !");
		}
		if(Main.tpTime < 6 && Main.tpTime > 1){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.tpTime - 1) + "§r§a secondes avant le prochain TP !");
			note();
		}
		if(Main.tpTime == 1){
			
			int i = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().length);
            Player r = Bukkit.getServer().getOnlinePlayers()[i];
            
            if(!r.getName().equals(Main.be.getName())){
            r.teleport(Main.be.getLocation());
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l"+r.getName()+" §aa été téléporté au Bouc-Émissaire !");
            } else {
    			int i2 = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().length);
                Player r2 = Bukkit.getServer().getOnlinePlayers()[i2];
                r2.teleport(Main.be.getLocation());
				Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l"+r2.getName()+" §aa été téléporté au Bouc-Émissaire !");
            }
           
				Main.tpTime = 182;
				
		}
		if(Main.tpTime == 0){
			return; 
		}
	}

	private void note() {
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playSound(p.getLocation(), Sound.NOTE_PIANO, 10, 1);
		}
	}

}
