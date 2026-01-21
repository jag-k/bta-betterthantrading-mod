package jagk.betterthantrading.block.entity;

import jagk.betterthantrading.economy.EconomyManager;
import jagk.betterthantrading.economy.MarketListing;
import jagk.betterthantrading.economy.MarketManager;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;

import java.util.List;

public class TileEntityMarket extends TileEntity {

	public void openGui(Player player) {
		showMarketListings(player);
	}

	public void showMarketListings(Player player) {
		List<MarketListing> listings = MarketManager.getListings();
		if (listings.isEmpty()) {
			player.sendMessage(TextFormatting.YELLOW + "No items on the market.");
			return;
		}

		player.sendMessage(TextFormatting.YELLOW + "=== Market Listings ===");
		int index = 0;
		for (MarketListing listing : listings) {
			player.sendMessage(TextFormatting.WHITE + "[" + index + "] " +
				listing.getStack().stackSize + "x " + listing.getStack().getItem().getKey() +
				" - " + listing.getTotalPrice() + " coins (by " + listing.getSellerName() + ")");
			index++;
		}
		player.sendMessage(TextFormatting.GRAY + "Use /buy <index> to purchase.");
	}

	public void buyItem(Player player, int listingIndex) {
		List<MarketListing> listings = MarketManager.getListings();
		if (listingIndex < 0 || listingIndex >= listings.size()) {
			player.sendMessage(TextFormatting.RED + "Invalid listing index.");
			return;
		}

		MarketListing listing = listings.get(listingIndex);
		int price = listing.getTotalPrice();

		if (!EconomyManager.hasMoney(player, price)) {
			player.sendMessage(TextFormatting.RED + "Not enough money! Need " + price + " coins.");
			return;
		}

		EconomyManager.removeMoney(player, price);
		MarketManager.completeSale(listingIndex, price);

		ItemStack purchased = listing.getStack().copy();
		player.inventory.insertItem(purchased, true);

		player.sendMessage(TextFormatting.GREEN + "Purchased " + purchased.stackSize + "x " +
			purchased.getItem().getKey() + " for " + price + " coins!");
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
