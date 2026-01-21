package jagk.betterthantrading.economy;

import net.minecraft.core.item.ItemStack;

public class MarketListing {
	private final String sellerName;
	private final ItemStack stack;
	private final int pricePerItem;

	public MarketListing(String sellerName, ItemStack stack, int pricePerItem) {
		this.sellerName = sellerName;
		this.stack = stack;
		this.pricePerItem = pricePerItem;
	}

	public String getSellerName() {
		return sellerName;
	}

	public ItemStack getStack() {
		return stack;
	}

	public int getPricePerItem() {
		return pricePerItem;
	}

	public int getTotalPrice() {
		return pricePerItem * stack.stackSize;
	}
}
