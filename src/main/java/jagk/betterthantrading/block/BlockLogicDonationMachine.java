package jagk.betterthantrading.block;

import jagk.betterthantrading.block.entity.TileEntityDonationMachine;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicDonationMachine extends BlockLogic {

	public BlockLogicDonationMachine(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
		if (!world.isClientSide) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityDonationMachine) {
				((TileEntityDonationMachine) te).openGui(player);
			}
		}
		return true;
	}
}
