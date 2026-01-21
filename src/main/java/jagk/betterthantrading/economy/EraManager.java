package jagk.betterthantrading.economy;

import java.util.ArrayList;
import java.util.List;

public class EraManager {
	private static final List<Era> eras = new ArrayList<>();
	private static int currentEraIndex = 0;
	private static int currentProgress = 0;

	static {
		// Define eras with their unlock thresholds
		eras.add(new Era("Stone Age", 0, new String[]{"coal"}));
		eras.add(new Era("Iron Age", 1000, new String[]{"coal", "iron"}));
		eras.add(new Era("Gold Age", 5000, new String[]{"coal", "iron", "gold"}));
		eras.add(new Era("Diamond Age", 15000, new String[]{"coal", "iron", "gold", "diamond"}));
		eras.add(new Era("Nether Age", 30000, new String[]{"coal", "iron", "gold", "diamond", "nether"}));
		eras.add(new Era("End Age", 50000, new String[]{"coal", "iron", "gold", "diamond", "nether", "end"}));
	}

	public static Era getCurrentEra() {
		return eras.get(currentEraIndex);
	}

	public static int getCurrentEraIndex() {
		return currentEraIndex;
	}

	public static void addProgress(int amount) {
		currentProgress += amount;
		checkEraAdvancement();
	}

	private static void checkEraAdvancement() {
		while (currentEraIndex < eras.size() - 1) {
			Era nextEra = eras.get(currentEraIndex + 1);
			if (currentProgress >= nextEra.getThreshold()) {
				currentEraIndex++;
			} else {
				break;
			}
		}
	}

	public static int getProgressPercent() {
		if (currentEraIndex >= eras.size() - 1) {
			return 100;
		}
		Era currentEra = eras.get(currentEraIndex);
		Era nextEra = eras.get(currentEraIndex + 1);
		int eraStart = currentEra.getThreshold();
		int eraEnd = nextEra.getThreshold();
		int progressInEra = currentProgress - eraStart;
		int eraRange = eraEnd - eraStart;
		return Math.min(100, (progressInEra * 100) / eraRange);
	}

	public static int getCurrentProgress() {
		return currentProgress;
	}

	public static int getNextEraThreshold() {
		if (currentEraIndex >= eras.size() - 1) {
			return getCurrentEra().getThreshold();
		}
		return eras.get(currentEraIndex + 1).getThreshold();
	}

	public static boolean canMine(String resourceType) {
		Era era = getCurrentEra();
		for (String allowed : era.getAllowedResources()) {
			if (allowed.equals(resourceType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean canAccessNether() {
		return currentEraIndex >= 4; // Nether Age
	}

	public static boolean canAccessEnd() {
		return currentEraIndex >= 5; // End Age
	}

	public static List<Era> getAllEras() {
		return new ArrayList<>(eras);
	}

	public static void loadData(String eraName, int progress) {
		for (int i = 0; i < eras.size(); i++) {
			if (eras.get(i).getName().equals(eraName)) {
				currentEraIndex = i;
				break;
			}
		}
		currentProgress = progress;
	}

	public static void loadData(int eraIndex, int progress) {
		currentEraIndex = Math.min(eraIndex, eras.size() - 1);
		currentProgress = progress;
	}

	public static int getProgress() {
		return currentProgress;
	}

	public static void reset() {
		currentEraIndex = 0;
		currentProgress = 0;
	}

	public static class Era {
		private final String name;
		private final int threshold;
		private final String[] allowedResources;

		public Era(String name, int threshold, String[] allowedResources) {
			this.name = name;
			this.threshold = threshold;
			this.allowedResources = allowedResources;
		}

		public String getName() {
			return name;
		}

		public int getThreshold() {
			return threshold;
		}

		public String[] getAllowedResources() {
			return allowedResources;
		}
	}
}
