package jagk.betterthantrading.economy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationManager {
	private static final Map<String, Integer> donations = new HashMap<>();
	private static int totalDonations = 0;

	public static void addDonation(String playerName, int amount) {
		int current = donations.getOrDefault(playerName, 0);
		donations.put(playerName, current + amount);
		totalDonations += amount;
	}

	public static int getDonation(String playerName) {
		return donations.getOrDefault(playerName, 0);
	}

	public static int getTotalDonations() {
		return totalDonations;
	}

	public static List<Map.Entry<String, Integer>> getLeaderboard() {
		List<Map.Entry<String, Integer>> sorted = new ArrayList<>(donations.entrySet());
		sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));
		return sorted;
	}

	public static List<Map.Entry<String, Integer>> getTopDonors(int count) {
		List<Map.Entry<String, Integer>> leaderboard = getLeaderboard();
		return leaderboard.subList(0, Math.min(count, leaderboard.size()));
	}

	public static void loadData(Map<String, Integer> data, int total) {
		donations.clear();
		donations.putAll(data);
		totalDonations = total;
	}

	public static Map<String, Integer> saveData() {
		return new HashMap<>(donations);
	}

	public static void reset() {
		donations.clear();
		totalDonations = 0;
	}
}
