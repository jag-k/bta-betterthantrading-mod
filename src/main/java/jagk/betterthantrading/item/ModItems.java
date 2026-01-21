package jagk.betterthantrading.item;

import jagk.betterthantrading.BetterThanTrading;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

public class ModItems {
	public static Item coin;
	public static Item classTokenMiner;
	public static Item classTokenLumberjack;
	public static Item classTokenFarmer;
	public static Item classTokenHunter;
	public static Item classTokenBuilder;
	public static Item classTokenMerchant;
	private static int itemId = 17000;

	public static void initNamespaces() {
	}

	public static String itemKey(String string) {
		return BetterThanTrading.MOD_ID + ":item/" + string;
	}

	public static void register() {
		BetterThanTrading.LOGGER.info("Registering items...");

		coin = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(64)
			.build(new Item("coin", itemKey("coin"), itemId++));
//			.build(new ItemCoin(itemId++));

		classTokenMiner = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(1)
			.build(new ItemClassToken("class_token_miner", itemKey("class_token_miner"), itemId++, jagk.betterthantrading.classes.PlayerClass.MINER));

		classTokenLumberjack = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(1)
			.build(new ItemClassToken("class_token_lumberjack", itemKey("class_token_lumberjack"), itemId++, jagk.betterthantrading.classes.PlayerClass.LUMBERJACK));

		classTokenFarmer = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(1)
			.build(new ItemClassToken("class_token_farmer", itemKey("class_token_farmer"), itemId++, jagk.betterthantrading.classes.PlayerClass.FARMER));

		classTokenHunter = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(1)
			.build(new ItemClassToken("class_token_hunter", itemKey("class_token_hunter"), itemId++, jagk.betterthantrading.classes.PlayerClass.HUNTER));

		classTokenBuilder = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(1)
			.build(new ItemClassToken("class_token_builder", itemKey("class_token_builder"), itemId++, jagk.betterthantrading.classes.PlayerClass.BUILDER));

		classTokenMerchant = new ItemBuilder(BetterThanTrading.MOD_ID)
			.setStackSize(1)
			.build(new ItemClassToken("class_token_merchant", itemKey("class_token_merchant"), itemId++, jagk.betterthantrading.classes.PlayerClass.MERCHANT));

		BetterThanTrading.LOGGER.info("Items registered.");
	}
}
