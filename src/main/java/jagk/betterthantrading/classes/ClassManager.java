package jagk.betterthantrading.classes;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;

import java.util.HashMap;
import java.util.Map;

public class ClassManager {
	private static final Map<String, PlayerClass> playerClasses = new HashMap<>();

	public static PlayerClass getClass(Player player) {
		return playerClasses.getOrDefault(player.username, PlayerClass.NONE);
	}

	public static PlayerClass getClass(String playerName) {
		return playerClasses.getOrDefault(playerName, PlayerClass.NONE);
	}

	public static void setClass(Player player, PlayerClass playerClass) {
		PlayerClass current = getClass(player);
		if (current != PlayerClass.NONE) {
			player.sendMessage(TextFormatting.RED + "You already have a class! You cannot change it.");
			return;
		}
		playerClasses.put(player.username, playerClass);
		player.sendMessage(TextFormatting.GREEN + "You are now a " + playerClass.getDisplayName() + "!");
		player.sendMessage(TextFormatting.GRAY + playerClass.getDescription());
	}

	public static void forceSetClass(String playerName, PlayerClass playerClass) {
		playerClasses.put(playerName, playerClass);
	}

	public static void resetClass(Player player) {
		playerClasses.put(player.username, PlayerClass.NONE);
	}

	public static void resetClass(String playerName) {
		playerClasses.put(playerName, PlayerClass.NONE);
	}

	public static boolean hasClass(Player player) {
		return getClass(player) != PlayerClass.NONE;
	}

	public static boolean canMine(Player player) {
		return getClass(player).canMine();
	}

	public static boolean canChopTrees(Player player) {
		return getClass(player).canChopTrees();
	}

	public static boolean canFarm(Player player) {
		return getClass(player).canFarm();
	}

	public static boolean canHunt(Player player) {
		return getClass(player).canHunt();
	}

	public static boolean canBuild(Player player) {
		return getClass(player).canBuild();
	}

	public static boolean hasMerchantBonus(Player player) {
		return getClass(player).hasMerchantBonus();
	}

	public static Map<String, PlayerClass> getAllClasses() {
		return new HashMap<>(playerClasses);
	}

	public static void loadData(Map<String, String> data) {
		playerClasses.clear();
		for (Map.Entry<String, String> entry : data.entrySet()) {
			try {
				PlayerClass pc = PlayerClass.valueOf(entry.getValue());
				playerClasses.put(entry.getKey(), pc);
			} catch (IllegalArgumentException ignored) {
			}
		}
	}

	public static Map<String, String> saveData() {
		Map<String, String> data = new HashMap<>();
		for (Map.Entry<String, PlayerClass> entry : playerClasses.entrySet()) {
			data.put(entry.getKey(), entry.getValue().name());
		}
		return data;
	}
}
