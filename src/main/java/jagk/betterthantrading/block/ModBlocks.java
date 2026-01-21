package jagk.betterthantrading.block;

import jagk.betterthantrading.BetterThanTrading;
import jagk.betterthantrading.block.entity.TileEntityDonationMachine;
import jagk.betterthantrading.block.entity.TileEntityJunkDealer;
import jagk.betterthantrading.block.entity.TileEntityMarket;
import jagk.betterthantrading.block.entity.TileEntityTrade;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class ModBlocks {
	public static Block<BlockLogicJunkDealer> junkDealer;
	public static Block<BlockLogicTrade> tradeBlock;
	public static Block<BlockLogicMarket> market;
	public static Block<BlockLogicDonationMachine> donationMachine;
	private static int blockId = 3800;

	public static void initNamespaces() {
	}

	public static void register() {
		BetterThanTrading.LOGGER.info("Registering blocks...");

		junkDealer = new BlockBuilder(BetterThanTrading.MOD_ID)
			.setBlockSound(BlockSounds.METAL)
			.setHardness(3.5f)
			.setResistance(10.0f)
			.setTileEntity(TileEntityJunkDealer::new)
			.build("junkdealer", blockId++, (block) -> new BlockLogicJunkDealer(block, Material.metal));

		tradeBlock = new BlockBuilder(BetterThanTrading.MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setHardness(2.5f)
			.setResistance(5.0f)
			.setTileEntity(TileEntityTrade::new)
			.build("tradeblock", blockId++, (block) -> new BlockLogicTrade(block, Material.wood));

		market = new BlockBuilder(BetterThanTrading.MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setHardness(2.5f)
			.setResistance(5.0f)
			.setTileEntity(TileEntityMarket::new)
			.build("market", blockId++, (block) -> new BlockLogicMarket(block, Material.wood));

		donationMachine = new BlockBuilder(BetterThanTrading.MOD_ID)
			.setBlockSound(BlockSounds.METAL)
			.setHardness(5.0f)
			.setResistance(15.0f)
			.setTileEntity(TileEntityDonationMachine::new)
			.build("donationmachine", blockId++, (block) -> new BlockLogicDonationMachine(block, Material.metal));

		BetterThanTrading.LOGGER.info("Blocks registered.");
	}
}
