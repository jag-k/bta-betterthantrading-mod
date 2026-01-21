package jagk.betterthantrading;

import jagk.betterthantrading.block.ModBlocks;
import jagk.betterthantrading.command.CommandBTT;
import jagk.betterthantrading.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BetterThanTrading implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "betterthantrading";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Better Than Trading mod initialized.");
		ModConfig.init();
		CommandBTT.register();
	}

	@Override
	public void initNamespaces() {
		ModBlocks.initNamespaces();
		ModItems.initNamespaces();
	}

	@Override
	public void beforeGameStart() {
		ModBlocks.register();
		ModItems.register();
	}

	@Override
	public void afterGameStart() {
		jagk.betterthantrading.guidebook.ModGuidebook.register();
	}

	@Override
	public void onRecipesReady() {
		ModRecipes.register();
	}
}
