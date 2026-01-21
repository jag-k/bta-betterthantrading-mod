package jagk.betterthantrading.economy;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;

import java.util.HashMap;
import java.util.Map;

public class PriceRegistry {
	private static final Map<Item, Integer> basePrices = new HashMap<>();
	private static final Map<Item, Integer> junkPrices = new HashMap<>();
	private static final Map<Item, Integer> marketSupply = new HashMap<>();

	static {
		// Base prices for key resources (market prices)
		basePrices.put(Items.COAL, 5);
		basePrices.put(Items.INGOT_IRON, 20);
		basePrices.put(Items.INGOT_GOLD, 50);
		basePrices.put(Items.DIAMOND, 100);
		basePrices.put(Items.DUST_REDSTONE, 10);
		basePrices.put(Items.DYE, 15); // Lapis is a dye
		basePrices.put(Items.STRING, 3);
		basePrices.put(Items.LEATHER, 8);
		basePrices.put(Items.SLIMEBALL, 12);
		basePrices.put(Items.BONE, 4);
		basePrices.put(Items.SULPHUR, 15);
		basePrices.put(Items.FEATHER_CHICKEN, 2);
		basePrices.put(Items.FLINT, 2);
		basePrices.put(Items.CLAY, 3);
		basePrices.put(Items.DUST_GLOWSTONE, 25);

		// Wood items
		basePrices.put(Blocks.LOG_OAK.asItem(), 2);
		basePrices.put(Blocks.LOG_BIRCH.asItem(), 2);
		basePrices.put(Blocks.LOG_PINE.asItem(), 2);
		basePrices.put(Blocks.PLANKS_OAK.asItem(), 1);

		// Stone items
		basePrices.put(Blocks.COBBLE_STONE.asItem(), 1);
		basePrices.put(Blocks.STONE.asItem(), 2);

		// Junk prices (lower than market prices)
		for (Map.Entry<Item, Integer> entry : basePrices.entrySet()) {
			junkPrices.put(entry.getKey(), Math.max(1, entry.getValue() / 3));
		}

		// Additional junk items
		junkPrices.put(Blocks.DIRT.asItem(), 1);
		junkPrices.put(Blocks.GRAVEL.asItem(), 1);
		junkPrices.put(Blocks.SAND.asItem(), 1);
	}

	public static int getBasePrice(Item item) {
		return basePrices.getOrDefault(item, 1);
	}

	public static int getJunkPrice(Item item) {
		return junkPrices.getOrDefault(item, 1);
	}

	public static int getMarketPrice(Item item) {
		int basePrice = getBasePrice(item);
		int supply = marketSupply.getOrDefault(item, 0);

		// Dynamic pricing: price decreases with more supply
		// Price = basePrice * (1 - supply/1000), minimum 50% of base
		double supplyFactor = Math.max(0.5, 1.0 - (supply / 1000.0));
		return Math.max(1, (int) (basePrice * supplyFactor));
	}

	public static void addToSupply(Item item, int amount) {
		int current = marketSupply.getOrDefault(item, 0);
		marketSupply.put(item, current + amount);
	}

	public static void removeFromSupply(Item item, int amount) {
		int current = marketSupply.getOrDefault(item, 0);
		marketSupply.put(item, Math.max(0, current - amount));
	}

	public static int getSupply(Item item) {
		return marketSupply.getOrDefault(item, 0);
	}

	public static boolean hasBasePrice(Item item) {
		return basePrices.containsKey(item);
	}

	public static Map<Item, Integer> getMarketSupply() {
		return new HashMap<>(marketSupply);
	}

	public static void loadSupplyData(Map<Item, Integer> data) {
		marketSupply.clear();
		marketSupply.putAll(data);
	}
}
