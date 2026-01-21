package jagk.betterthantrading.economy;

import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketManager {
	private static final List<MarketListing> listings = new ArrayList<>();
	private static final Map<String, Integer> pendingEarnings = new HashMap<>();

	public static void listItem(String sellerName, ItemStack stack, int pricePerItem) {
		listings.add(new MarketListing(sellerName, stack, pricePerItem));
		PriceRegistry.addToSupply(stack.getItem(), stack.stackSize);
	}

	public static List<MarketListing> getListings() {
		return new ArrayList<>(listings);
	}

	public static void completeSale(int listingIndex, int salePrice) {
		if (listingIndex >= 0 && listingIndex < listings.size()) {
			MarketListing listing = listings.remove(listingIndex);
			PriceRegistry.removeFromSupply(listing.getStack().getItem(), listing.getStack().stackSize);

			// Add earnings to seller's pending balance
			int currentEarnings = pendingEarnings.getOrDefault(listing.getSellerName(), 0);
			pendingEarnings.put(listing.getSellerName(), currentEarnings + salePrice);

			// Add progress to era based on sale
			EraManager.addProgress(salePrice / 10);
		}
	}

	public static int collectEarnings(String playerName) {
		int earnings = pendingEarnings.getOrDefault(playerName, 0);
		pendingEarnings.put(playerName, 0);
		return earnings;
	}

	public static int getPendingEarnings(String playerName) {
		return pendingEarnings.getOrDefault(playerName, 0);
	}

	public static void cancelListing(String playerName, int listingIndex) {
		if (listingIndex >= 0 && listingIndex < listings.size()) {
			MarketListing listing = listings.get(listingIndex);
			if (listing.getSellerName().equals(playerName)) {
				listings.remove(listingIndex);
				PriceRegistry.removeFromSupply(listing.getStack().getItem(), listing.getStack().stackSize);
			}
		}
	}

	public static List<MarketListing> getListingsBySeller(String sellerName) {
		List<MarketListing> result = new ArrayList<>();
		for (MarketListing listing : listings) {
			if (listing.getSellerName().equals(sellerName)) {
				result.add(listing);
			}
		}
		return result;
	}

	public static void clearAll() {
		listings.clear();
		pendingEarnings.clear();
	}
}
