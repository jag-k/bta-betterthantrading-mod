package jagk.betterthantrading;

import jagk.betterthantrading.block.ModBlocks;
import jagk.betterthantrading.item.ModItems;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;

public class ModRecipes {

	public static void register() {
		BetterThanTrading.LOGGER.info("Registering recipes...");

		// Junk Dealer block recipe
		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape("III", "ICI", "III")
			.addInput('I', Items.INGOT_IRON)
			.addInput('C', Blocks.CHEST_PLANKS_OAK)
			.create("junkdealer", new ItemStack(ModBlocks.junkDealer));

		// Trade Block recipe
		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape("PPP", "PCP", "PPP")
			.addInput('P', Blocks.PLANKS_OAK)
			.addInput('C', Blocks.CHEST_PLANKS_OAK)
			.create("tradeblock", new ItemStack(ModBlocks.tradeBlock));

		// Market Block recipe
		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape("GGG", "GCG", "PPP")
			.addInput('G', Items.INGOT_GOLD)
			.addInput('C', Blocks.CHEST_PLANKS_OAK)
			.addInput('P', Blocks.PLANKS_OAK)
			.create("market", new ItemStack(ModBlocks.market));

		// Donation Machine recipe
		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape("DID", "ICI", "DID")
			.addInput('D', Items.DIAMOND)
			.addInput('I', Items.INGOT_IRON)
			.addInput('C', Blocks.CHEST_PLANKS_OAK)
			.create("donationmachine", new ItemStack(ModBlocks.donationMachine));

		// Class Token recipes
		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape(" P ", "PDP", " P ")
			.addInput('P', Items.PAPER)
			.addInput('D', Items.TOOL_PICKAXE_IRON)
			.create("class_token_miner", new ItemStack(ModItems.classTokenMiner));

		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape(" P ", "PDP", " P ")
			.addInput('P', Items.PAPER)
			.addInput('D', Items.TOOL_AXE_IRON)
			.create("class_token_lumberjack", new ItemStack(ModItems.classTokenLumberjack));

		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape(" P ", "PDP", " P ")
			.addInput('P', Items.PAPER)
			.addInput('D', Items.TOOL_HOE_IRON)
			.create("class_token_farmer", new ItemStack(ModItems.classTokenFarmer));

		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape(" P ", "PDP", " P ")
			.addInput('P', Items.PAPER)
			.addInput('D', Items.TOOL_SWORD_IRON)
			.create("class_token_hunter", new ItemStack(ModItems.classTokenHunter));

		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape(" P ", "PDP", " P ")
			.addInput('P', Items.PAPER)
			.addInput('D', Blocks.BRICK_CLAY)
			.create("class_token_builder", new ItemStack(ModItems.classTokenBuilder));

		new RecipeBuilderShaped(BetterThanTrading.MOD_ID)
			.setShape(" P ", "PDP", " P ")
			.addInput('P', Items.PAPER)
			.addInput('D', Items.INGOT_GOLD)
			.create("class_token_merchant", new ItemStack(ModItems.classTokenMerchant));

		BetterThanTrading.LOGGER.info("Recipes registered.");
	}
}
