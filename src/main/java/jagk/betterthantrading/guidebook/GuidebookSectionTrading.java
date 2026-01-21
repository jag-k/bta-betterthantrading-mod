package jagk.betterthantrading.guidebook;

import jagk.betterthantrading.item.ModItems;
import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuidebookSectionTrading extends GuidebookSection {

	private final List<GuidebookPage> pages = new ArrayList<>();

	public GuidebookSectionTrading() {
		super("betterthantrading.trading", new ItemStack(ModItems.coin), 0xFFD700, 0x8B4513);
		initPages();
	}

	private void initPages() {
		pages.add(new GuidebookPageInfo(this, "overview",
			"Better Than Trading",
			"Welcome to the economy mod!\n" +
				"Players specialize in professions.\n" +
				"Non-specialists have reduced drop chance!"
		));

		pages.add(new GuidebookPageInfo(this, "classes",
			"Player Classes",
			"MINER - 100% ore drops\n" +
				"LUMBERJACK - 100% log drops\n" +
				"FARMER - Crops (exclusive!)\n" +
				"HUNTER - Extra damage\n" +
				"BUILDER - Fast building\n" +
				"MERCHANT - Better prices\n\n" +
				"Non-specialists: ~30% drops"
		));

		pages.add(new GuidebookPageInfo(this, "economy",
			"Economy",
			"COINS - Currency\n\n" +
				"Earn at Junk Dealer\n" +
				"Trade with players\n\n" +
				"Spend at Market\n" +
				"Donate to advance eras"
		));

		pages.add(new GuidebookPageInfo(this, "eras",
			"Eras",
			"1. STONE - Coal only\n" +
				"2. IRON - +Redstone, Lapis\n" +
				"3. GOLD - +Gold ore\n" +
				"4. DIAMOND - +Diamonds\n" +
				"5. NETHER - +Nether\n" +
				"6. END - +End content\n\n" +
				"Donate to advance!"
		));

		pages.add(new GuidebookPageInfo(this, "blocks",
			"Blocks",
			"JUNK DEALER\n" +
				"Sell junk for coins\n\n" +
				"TRADE BLOCK\n" +
				"List items for sale\n\n" +
				"MARKET\n" +
				"Buy player items\n\n" +
				"DONATION MACHINE\n" +
				"Donate & leaderboard"
		));

		pages.add(new GuidebookPageInfo(this, "commands",
			"Commands",
			"/btt - Show help\n" +
				"/btt class - View class\n" +
				"/btt class reset - Reset class\n\n" +
				"Reset has a cooldown (configurable)."
		));
	}

	@Override
	public List<GuidebookPage> getPages() {
		return pages;
	}

	@Override
	public List<Index> getIndices() {
		List<Index> indices = new ArrayList<>();
		indices.add(new Index("overview", pages.get(0)));
		indices.add(new Index("classes", pages.get(1)));
		indices.add(new Index("economy", pages.get(2)));
		indices.add(new Index("eras", pages.get(3)));
		indices.add(new Index("blocks", pages.get(4)));
		indices.add(new Index("commands", pages.get(5)));
		return indices;
	}
}
