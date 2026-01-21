package jagk.betterthantrading.classes;

public enum PlayerClass {
	NONE("None", "No class bonuses - choose a class!"),
	MINER("Miner", "Mines ores and stone at full speed (others 2.5x slower)"),
	LUMBERJACK("Lumberjack", "Chops trees at full speed (others 2.5x slower)"),
	FARMER("Farmer", "Can harvest crops and breed animals (exclusive)"),
	HUNTER("Hunter", "Deals extra damage to mobs"),
	BUILDER("Builder", "Places and breaks building blocks faster"),
	MERCHANT("Merchant", "Gets better prices at shops");

	private final String displayName;
	private final String description;

	PlayerClass(String displayName, String description) {
		this.displayName = displayName;
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public boolean canMine() {
		return this == MINER;
	}

	public boolean canChopTrees() {
		return this == LUMBERJACK;
	}

	public boolean canFarm() {
		return this == FARMER;
	}

	public boolean canHunt() {
		return this == HUNTER;
	}

	public boolean canBuild() {
		return this == BUILDER || this == MINER || this == LUMBERJACK;
	}

	public boolean hasMerchantBonus() {
		return this == MERCHANT;
	}
}
