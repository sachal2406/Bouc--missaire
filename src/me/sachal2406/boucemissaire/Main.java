package me.sachal2406.boucemissaire;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Vector;

import me.sachal2406.boucemissaire.FileManager;
public class Main extends JavaPlugin implements Listener{
	public final static Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;	
	
	FileManager settings = FileManager.getInstance();
	
	FileConfiguration config = settings.getConfig();
	FileConfiguration data = settings.getGameData();
	
	public static boolean inGame;
	
	public static int gameLoop = 0;
	
	public static int tpLoop = 0;
	
	public static int timeInSeconds;
	
	public static int tpTime;
	
	public static Player be;
	
	public void onEnable(){
		tpTime = 0;
		
		gameLoop = getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new startCountDown(), 20L, 20L);
		
		tpLoop = getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new TPCountDown(), 20L, 20L);
		
		settings.setup(this);
		
		inGame = false;
		
		logger.info("-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=");
		logger.info("[Bouc-Émissaire] enabled");
		logger.info("-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=");
		
		getServer().getPluginManager().registerEvents(this, this);
		
		
	}
	
	
	public void onDisable(){
		logger.info("-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=");
		logger.info("[Bouc-Émissaire] enabled");
		logger.info("-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=-=+=");
		getServer().getScheduler().cancelTask(gameLoop);
	
		File world = new File("world");
		File players = new File("world/players");
		File data = new File("world/data");
		File region = new File("world/region");
		File stats = new File("world/stats");
		if (world.isDirectory()){
		    for (File file : world.listFiles()){
		        file.delete();
		        Bukkit.getLogger().info("File: " + file + " is being removed!");
		    }
		    for (File file : players.listFiles()){
		        file.delete();
		        Bukkit.getLogger().info("File: " + file + " is being removed!");
		    }
		    for (File file : data.listFiles()){
		        file.delete();
		        Bukkit.getLogger().info("File: " + file + " is being removed!");
		    }
		    for (File file : region.listFiles()){
		        file.delete();
		        Bukkit.getLogger().info("File: " + file + " is being removed!");
		    }
		    for (File file : stats.listFiles()){
		        file.delete();
		        Bukkit.getLogger().info("File: " + file + " is being removed!");
		    }
		    world.delete();
		    players.delete();
		    data.delete();
		    region.delete();
		    stats.delete();
		}
	      
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		Inventory inv = p.getInventory();
	
		p.getWorld().setTime(0);
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookmeta = (BookMeta) book.getItemMeta();
		bookmeta.addPage("1", "2", "3");
		bookmeta.setPage(1, ("   §b§l§nBouc-Émissaire" + "\n" + "\n" + "§c§nRègles:" + "\n" + "\n" + "§4- Pas de kill no-stuff." + "\n" + "§4- Pas d'insultes" + "\n" + "§4- Bonne chance !" + "\n" +  "\n" +  "\n" + "\n" +  "\n" + "\n" + "§7Principe à la page suivante"));
		bookmeta.setPage(2, ("   §b§l§nBouc-Émissaire" + "\n" + "\n" + 
									"§c§nPrincipe:" + "\n" + "\n" + "§3Entre 3 et 10 joueurs s'affrontent dans un monde normal, mais pas si normal que ça.. Toutes les 3 minutes, "
									+ "un joueur choisit aléatoirement est téléporté au Bouc-Émissaire qui, lui même,"));
		bookmeta.setPage(3, ("§3est choisit aléatoirement au début de la partie. "
									+ "Dès qu'un joueur tue le bouc-emissaire, il devient lui même bouc-émissaire. Le dernier qui survit gagne. Bonne chance !"));
		bookmeta.setAuthor("§6Le créateur");
		bookmeta.setDisplayName("§b§lRègles et principe");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Règles et principe dans ce livre !");
		bookmeta.setLore(lore);
		book.setItemMeta(bookmeta);
		ItemStack launcher = new ItemStack(Material.FIREWORK);
		ItemMeta launchermeta = launcher.getItemMeta();
		launchermeta.setDisplayName("§6§lLauncher !");
		launcher.setItemMeta(launchermeta);
		
		inv.clear();
		inv.addItem(book);
		inv.addItem(launcher);
		p.setGameMode(GameMode.ADVENTURE);
		e.setJoinMessage("§b§l" + p.getName() + "§a a rejoint la partie");
		p.setFoodLevel(20);
		p.setHealth((double)20);
		p.teleport(p.getWorld().getSpawnLocation().add(0, 50, 0));
		
		if(inGame != false){
			p.kickPlayer("§c§lPartie en cours !");
		}
		
		if(Bukkit.getOnlinePlayers().length > 1){
			timeInSeconds = 121;
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §aLe compte-à-rebours de départ commence (120sec) ! Dispersez-vous !");
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){

				@Override
				public void run() {
					for(Player pl : Bukkit.getOnlinePlayers()){
						pl.getInventory().clear();
					}
					
				}
				
			}, 160);
			
		}
	    	
		//SCOREBOARD
		if(inGame != true){
			ScoreboardManager manager = Bukkit.getScoreboardManager();
	    	Scoreboard board = manager.getNewScoreboard();
	    	Objective objective = board.registerNewObjective("sbjoin", "dummy");
	    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    	objective.setDisplayName("§lBouc-Émissaire");
	    
    		objective.getScore(Bukkit.getOfflinePlayer("§lJoueurs:")).setScore(186);
    		objective.getScore(Bukkit.getOfflinePlayer("§c"+Bukkit.getOnlinePlayers().length+"§b/" + getServer().getMaxPlayers())).setScore(185);
    		objective.getScore(Bukkit.getOfflinePlayer("")).setScore(184);
    		objective.getScore(Bukkit.getOfflinePlayer("§lStatut:")).setScore(183);
    		objective.getScore(Bukkit.getOfflinePlayer("§bAttente..")).setScore(182);
    		objective.getScore(Bukkit.getOfflinePlayer("§n")).setScore(181);
    		objective.getScore(Bukkit.getOfflinePlayer("§lTemps:")).setScore(0);
    	
    		for(Player pl : Bukkit.getOnlinePlayers()){
    		pl.setScoreboard(board);
    		}
		}
		//SCOREBOARD END
		
		
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		if(Bukkit.getOnlinePlayers().length < 2){
			timeInSeconds = 0;
		}
		
		e.setQuitMessage("§b§l" + p.getName() + "§c a quitté la partie");
		
		if(inGame != false){
		if(Bukkit.getOnlinePlayers().length-1 < 2){
			for(final Player pl : Bukkit.getOnlinePlayers()){
				pl.sendMessage("§9§lBouc-Émissaire §4§l> §b§l" + pl.getName() + " §aa gagné la partie !");
				e.setQuitMessage("§b§l" + p.getName() + "§c a quitté la partie et est éliminé");
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
					public void run(){
					 pl.kickPlayer("§aGG !");
					getServer().shutdown();
					}
				}, 40);
			}
		}
		}
		
		//SCOREBOARD
		if(inGame != true){
			ScoreboardManager manager = Bukkit.getScoreboardManager();
	    	Scoreboard board = manager.getNewScoreboard();
	    	Objective objective = board.registerNewObjective("sbquit", "dummy");
	    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    	objective.setDisplayName("§lBouc-Émissaire");
	    
    		objective.getScore(Bukkit.getOfflinePlayer("§lJoueurs:")).setScore(186);
    		objective.getScore(Bukkit.getOfflinePlayer("§c"+(Bukkit.getOnlinePlayers().length-1)+"§b/" + getServer().getMaxPlayers())).setScore(185);
    		objective.getScore(Bukkit.getOfflinePlayer("")).setScore(184);
    		objective.getScore(Bukkit.getOfflinePlayer("§lStatut:")).setScore(183);
    		objective.getScore(Bukkit.getOfflinePlayer("§bAttente..")).setScore(182);
    		objective.getScore(Bukkit.getOfflinePlayer("§n")).setScore(181);
    		objective.getScore(Bukkit.getOfflinePlayer("§lTemps:")).setScore(0);
    	
    		for(Player pl : Bukkit.getOnlinePlayers()){
    		pl.setScoreboard(board);
    		}
		}
		//SCOREBOARD END
		
	}
	
	@EventHandler
	public void onPlayerPingList(ServerListPingEvent e){
		
		if(inGame != false){
			e.setMotd("§c§lEn cours");
		} else {
			e.setMotd("§b§lEn Attente");
		}
	}
	
	@EventHandler
	public void onPlayerPickAnItem(PlayerPickupItemEvent e){
		if(inGame != true){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropAnItem(PlayerDropItemEvent e){
		if(inGame != true){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(inGame != true){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e){
		if(inGame != true){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e){
		if(inGame != true){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		Player p = (Player)e.getEntity();
	
	  if(inGame != true){
		  e.setFoodLevel(20);
		  p.setFoodLevel(20);
	  }
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = (Player)e.getEntity();
		
		if(p.getName() == be.getName()){
			be = p;
			Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §b§l" + p.getName() + " §aest le nouveau bouc-émissaire !");
		}
		
		if(inGame != false){
			try {
				Thread.sleep(400);
				p.kickPlayer("§cTu es éliminé ! §7(" + e.getDeathMessage() + "§7)");
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}
		}
		
		if(Bukkit.getOnlinePlayers().length < 2){
			for(Player pl : Bukkit.getOnlinePlayers()){
				pl.sendMessage("§9§lBouc-Émissaire §4§l> §b§l" + pl.getName() + " §aa gagné la partie !");
				try {
					Thread.sleep(2000);
					pl.kickPlayer("§aGG !");
					getServer().shutdown();
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}
			}
		}
	}
	
    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		
    	Player p = (Player)sender;
    	
    	final String prefix = "§9§lBouc-Émissaire §4§l> §a";
	
	if(cmd.getName().equalsIgnoreCase("be")
			|| cmd.getName().equalsIgnoreCase("boucemissaire")){
		if(args.length == 0){
			sender.sendMessage("§4§l§m*********************************************");
			sender.sendMessage("§6§l§nBouc-Émissaire");
			sender.sendMessage("§3§lplugin par §c§nsachal2406");
			p.sendMessage("§4§l§m*********************************************");
			return true;
		}
		if(args[0].equalsIgnoreCase("fstart")
			|| args[0].equalsIgnoreCase("forcestart")
			|| args[0].equalsIgnoreCase("fs")){
				if(p.isOp()){
					if(Bukkit.getOnlinePlayers().length > 1){
						if(Main.inGame != true){

						if(Bukkit.getOnlinePlayers().length >= 2){
							int i = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().length);
				            Player r = Bukkit.getServer().getOnlinePlayers()[i];
				            Main.be = r;
							
							for(Player pl : Bukkit.getOnlinePlayers()){
								pl.playEffect(p.getLocation(), Effect.CLICK1, 1);
								be.sendMessage("§9§lBouc-Émissaire §4§l> §cTu es le bouc-émissaire !");
							}
							
							inGame = true;
							Bukkit.broadcastMessage("§9§lBouc-Émissaire §4§l> §B§lLa partie a commencé ! Bonne chance !");
							tpTime = 181;
							timeInSeconds = 0;
							
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
							
							for(Player pl : Bukkit.getOnlinePlayers()){
								pl.getInventory().clear();
								pl.setGameMode(GameMode.SURVIVAL);
					    		pl.setScoreboard(board);
								}
						}
					} else {
						p.sendMessage(prefix+"§cPartie déjà commencée !");
					}
					} else {
						p.sendMessage(prefix+"§cPas assez de joueurs !");
					}
				} else {
					p.sendMessage(prefix+"§cTu n'as pas la permission !");
				}
			}
		}
	return true;	
	}
	
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
    	Player p = e.getPlayer();
    	ItemStack clicked = e.getItem();
    	
		ItemStack launcher = new ItemStack(Material.FIREWORK);
		ItemMeta launchermeta = launcher.getItemMeta();
		launchermeta.setDisplayName("§6§lLauncher !");
		launcher.setItemMeta(launchermeta);
    	
    	if(clicked != null){
    		if(clicked.hasItemMeta()){
    			if(e.getAction() == Action.RIGHT_CLICK_AIR
    					|| e.getAction() == Action.RIGHT_CLICK_BLOCK){
    				if(clicked.getType().equals(Material.FIREWORK)){
    					if(clicked.getItemMeta().equals(launchermeta)){
    						e.setCancelled(true);
    				          p.setVelocity(new Vector(0D, 1.5D, 0D));
    					}
    				}
    			}
    		}
    	}
    	
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
    	if(e.getPlayer().hasPermission("be.admin")){
    		e.setFormat("§c§lADMIN §6" + e.getPlayer().getName() + " §3> §f§r" + e.getMessage());
    	} else if(e.getPlayer().hasPermission("be.modo")){
    		e.setFormat("§a§lMODO §6" + e.getPlayer().getName() + " §3> §f§r" + e.getMessage());
    	} else if(e.getPlayer().hasPermission("be.helper")){
    		e.setFormat("§9§lHELPER §6" + e.getPlayer().getName() + " §3> §f§r" + e.getMessage());
    	} else if(e.getPlayer().hasPermission("be.pro")){
    		e.setFormat("§5§lPRO §6" + e.getPlayer().getName() + " §3> §7" + e.getMessage());
    	} else if(e.getPlayer().hasPermission("be.vip")){
    		e.setFormat("§e§lVIP §6" + e.getPlayer().getName() + " §3> §7" + e.getMessage());
    	} else {
    		e.setFormat("§6" + e.getPlayer().getName() + " §3> §f§r");
    	}
    }
}
    