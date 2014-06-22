package me.sachal2406.boucemissaire;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class startCountDown implements Runnable {
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		if(Main.timeInSeconds == 121){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.timeInSeconds / 60) + "§r§a minutes avant le début de la partie !");
		}
		if(Main.timeInSeconds == 61){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.timeInSeconds / 60) + "§r§a minute avant le début de la partie !");
		}
		if(Main.timeInSeconds == 31){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.timeInSeconds - 1) + "§r§a secondes avant le début de la partie !");
		}
		if(Main.timeInSeconds == 21){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.timeInSeconds - 1) + "§r§a secondes avant le début de la partie !");
		}
		if(Main.timeInSeconds == 16){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.timeInSeconds - 1) + "§r§a secondes avant le début de la partie !");
		}
		if(Main.timeInSeconds < 12 && Main.timeInSeconds > 1){
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + (Main.timeInSeconds - 1) + "§r§a seconde(s avant le début de la partie !");
			note();
		}
		if(Main.timeInSeconds == 1){
			if(Bukkit.getOnlinePlayers().length >= 2){
				
				int i = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().length);
	            Player r = Bukkit.getServer().getOnlinePlayers()[i];
	            Main.be = r;
				
				for(Player p : Bukkit.getOnlinePlayers()){
					p.playEffect(p.getLocation(), Effect.CLICK1, 1);
					Main.be.sendMessage("§9§lBouc-Émissaire §4§l> §cTu es le bouc-émissaire !");
				}
				
				Main.inGame = true;
				Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §B§lLa partie a commencé ! Bonne chance !");
				Main.tpTime = 182;
				Main.timeInSeconds = 0;
				
				//SCOREBOARD
					ScoreboardManager manager = Bukkit.getScoreboardManager();
			        final Scoreboard board = manager.getNewScoreboard();
			    	Objective objective = board.registerNewObjective("sbstart", "dummy");
			    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			    	objective.setDisplayName("§lBouc-Émissaire");
		    		objective.getScore(Bukkit.getOfflinePlayer("§lJoueurs:")).setScore(186);
		    		objective.getScore(Bukkit.getOfflinePlayer("§c"+Bukkit.getOnlinePlayers().length+"§b/" + Bukkit.getServer().getMaxPlayers())).setScore(185);
		    		objective.getScore(Bukkit.getOfflinePlayer("")).setScore(184);
		    		objective.getScore(Bukkit.getOfflinePlayer("§lStatut:")).setScore(183);
		    		objective.getScore(Bukkit.getOfflinePlayer("§cEn cours")).setScore(182);
		    		objective.getScore(Bukkit.getOfflinePlayer("§n")).setScore(181);
		    		objective.getScore(Bukkit.getOfflinePlayer("§lTemps:")).setScore(180);
				//SCOREBOARD END
				
				for(Player p : Bukkit.getOnlinePlayers()){
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);
		    		p.setScoreboard(board);
					}
				
				
			} else {
			   Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §cPas assez de joueurs. Le compte à rebours recommence à §b1 minute§c.");
				Main.timeInSeconds = 61;
				Main.inGame = false;
			}
		}
		if(Main.timeInSeconds == 0){
			return; 
		}
		
		if(Main.timeInSeconds > 0){
			Main.timeInSeconds--;
			Main.logger.info(""+Main.timeInSeconds);
		}
	}

	private void note() {
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playSound(p.getLocation(), Sound.NOTE_PIANO, 10, 1);
		}
	}

}
