package jagk.betterthantrading.block.entity;

import jagk.betterthantrading.economy.DonationManager;
import jagk.betterthantrading.economy.EconomyManager;
import jagk.betterthantrading.economy.EraManager;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;

public class TileEntityDonationMachine extends TileEntity {

	public void openGui(Player player) {
		showDonationInfo(player);
	}

	public void showDonationInfo(Player player) {
		player.sendMessage(TextFormatting.YELLOW + "=== Donation Machine ===");
		player.sendMessage(TextFormatting.WHITE + "Current Era: " + EraManager.getCurrentEra().getName());
		player.sendMessage(TextFormatting.WHITE + "Progress: " + EraManager.getProgressPercent() + "%");
		player.sendMessage(TextFormatting.WHITE + "Total Donations: " + DonationManager.getTotalDonations());
		player.sendMessage(TextFormatting.GRAY + "Use /donate <amount> to donate coins.");
		player.sendMessage(TextFormatting.GRAY + "Use /donations to see the leaderboard.");
	}

	public void donate(Player player, int amount) {
		if (amount <= 0) {
			player.sendMessage(TextFormatting.RED + "Invalid amount.");
			return;
		}

		if (!EconomyManager.hasMoney(player, amount)) {
			player.sendMessage(TextFormatting.RED + "Not enough money!");
			return;
		}

		EconomyManager.removeMoney(player, amount);
		DonationManager.addDonation(player.username, amount);
		EraManager.addProgress(amount);

		player.sendMessage(TextFormatting.GREEN + "Donated " + amount + " coins! Thank you!");
		player.sendMessage(TextFormatting.WHITE + "Era Progress: " + EraManager.getProgressPercent() + "%");
	}

	@Override
	public void readFromNBT(com.mojang.nbt.tags.CompoundTag tag) {
		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(com.mojang.nbt.tags.CompoundTag tag) {
		super.writeToNBT(tag);
	}
}
