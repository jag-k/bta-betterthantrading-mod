package jagk.betterthantrading.mixin;

import jagk.betterthantrading.ModConfig;
import jagk.betterthantrading.classes.ClassManager;
import jagk.betterthantrading.economy.EraManager;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = BlockLogic.class, remap = false)
public abstract class MixinBlockLogic {

	private static final Random RANDOM = new Random();

	@Shadow
	public Block<?> block;

	@Shadow
	protected Material material;

	@Inject(method = "blockStrength", at = @At("RETURN"), cancellable = true)
	private void onBlockStrength(World world, int x, int y, int z, Side side, Player player, CallbackInfoReturnable<Float> cir) {
		if (player == null) return;

		float originalStrength = cir.getReturnValue();
		if (originalStrength <= 0) return;

		// Check era restrictions for ores - these are hard blocks
		if (isOreBlock() && !canMineInCurrentEra()) {
			player.sendMessage(TextFormatting.RED + "This ore is not available in the current era!");
			cir.setReturnValue(-1.0f);
			return;
		}

		// Crop blocks - Only Farmers can harvest (hard block)
		if (isCropBlock()) {
			if (!ClassManager.canFarm(player)) {
				player.sendMessage(TextFormatting.RED + "Only Farmers can harvest crops!");
				cir.setReturnValue(-1.0f);
			}
		}
	}

	@Inject(method = "dropBlockWithCause", at = @At("HEAD"), cancellable = true)
	private void onDropBlockWithCause(World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tileEntity, Player player, CallbackInfo ci) {
		if (player == null || world.isClientSide) return;

		float dropChance = getDropChanceForPlayer(player);

		if (dropChance < 1.0f && RANDOM.nextFloat() > dropChance) {
			ci.cancel();
		}
	}

	private float getDropChanceForPlayer(Player player) {
		// Ore blocks only - Miners get 100%, others get reduced chance
		// Stone and other blocks are NOT affected
		if (isOreBlock()) {
			if (!ClassManager.canMine(player)) {
				return ModConfig.dropChanceMultiplier;
			}
		}

		// Log blocks - Lumberjacks get 100%, others get reduced chance
		if (isLogBlock()) {
			if (!ClassManager.canChopTrees(player)) {
				return ModConfig.dropChanceMultiplier;
			}
		}

		// Crop blocks - handled in blockStrength (hard block for non-farmers)
		// No drop chance reduction needed here since they can't break crops at all

		// All other blocks (stone, chests, crafting tables, etc.) drop 100% for everyone
		return 1.0f;
	}

	private boolean isOreBlock() {
		String key = block.getKey();
		return key != null && key.contains("ore.");
	}

	private boolean isLogBlock() {
		return material == Material.wood && block.getKey() != null && block.getKey().contains("log");
	}

	private boolean isStoneBlock() {
		int id = block.id();
		return id == Blocks.STONE.id() ||
			id == Blocks.COBBLE_STONE.id();
	}

	private boolean isCropBlock() {
		String key = block.getKey();
		return key != null && (key.contains("crops.") || key.contains("crop."));
	}

	private boolean canMineInCurrentEra() {
		String key = block.getKey();
		if (key == null) return true;

		// Coal is always available
		if (key.contains("ore.coal")) {
			return EraManager.canMine("coal");
		}

		// Iron requires Iron Age
		if (key.contains("ore.iron")) {
			return EraManager.canMine("iron");
		}

		// Gold requires Gold Age
		if (key.contains("ore.gold")) {
			return EraManager.canMine("gold");
		}

		// Diamond requires Diamond Age
		if (key.contains("ore.diamond")) {
			return EraManager.canMine("diamond");
		}

		// Redstone and Lapis available from Iron Age
		if (key.contains("ore.redstone") || key.contains("ore.lapis")) {
			return EraManager.canMine("iron");
		}

		return true;
	}
}
