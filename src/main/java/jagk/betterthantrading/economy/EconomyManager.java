package jagk.betterthantrading.economy;

import jagk.betterthantrading.item.ModItems;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EconomyManager {
	private static final Map<String, Integer> playerBalances = new HashMap<>();

	public static int getBalance(Player player) {
		return playerBalances.getOrDefault(player.username, 0);
	}

	public static int getBalance(String playerName) {
		return playerBalances.getOrDefault(playerName, 0);
	}

	public static void setBalance(Player player, int amount) {
		playerBalances.put(player.username, Math.max(0, amount));
	}

	public static void setBalance(String playerName, int amount) {
		playerBalances.put(playerName, Math.max(0, amount));
	}

	public static void addMoney(Player player, int amount) {
		int current = getBalance(player);
		setBalance(player, current + amount);
	}

	public static void addMoney(String playerName, int amount) {
		int current = getBalance(playerName);
		setBalance(playerName, current + amount);
	}

	public static boolean removeMoney(Player player, int amount) {
		int current = getBalance(player);
		if (current >= amount) {
			setBalance(player, current - amount);
			return true;
		}
		return false;
	}

	public static boolean hasMoney(Player player, int amount) {
		return getBalance(player) >= amount;
	}

	public static void giveCoins(Player player, int amount) {
		ItemStack coins = new ItemStack(ModItems.coin, amount);
		player.inventory.insertItem(coins, true);
	}

	public static Map<String, Integer> getAllBalances() {
		return new HashMap<>(playerBalances);
	}

	public static void loadData(Map<String, Integer> data) {
		playerBalances.clear();
		playerBalances.putAll(data);
	}

	public static Map<String, Integer> saveData() {
		return new HashMap<>(playerBalances);
	}
}
