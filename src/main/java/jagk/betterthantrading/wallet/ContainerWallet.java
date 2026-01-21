package jagk.betterthantrading.wallet;

import jagk.betterthantrading.item.ModItems;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;

public class ContainerWallet implements Container {
	private final WalletManager.PlayerWallet wallet;
	private final Player player;
	private int lastSlotCount;
	private boolean needsRefresh = false;

	public ContainerWallet(Player player) {
		this.player = player;
		this.wallet = WalletManager.getWallet(player.username);
		this.lastSlotCount = wallet.getSlotCount();
	}

	public WalletManager.PlayerWallet getWallet() {
		return wallet;
	}

	public Player getPlayer() {
		return player;
	}

	public int getTotalCoins() {
		return wallet.getTotalCoins();
	}

	@Override
	public int getContainerSize() {
		int currentSlotCount = wallet.getSlotCount();
		if (currentSlotCount != lastSlotCount) {
			lastSlotCount = currentSlotCount;
			needsRefresh = true;
		}
		return currentSlotCount;
	}

	public boolean needsRefresh() {
		boolean result = needsRefresh;
		needsRefresh = false;
		return result;
	}

	@Override
	public ItemStack getItem(int slot) {
		return wallet.getItem(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		int oldSlotCount = wallet.getSlotCount();
		ItemStack result = wallet.removeItem(slot, amount);
		int newSlotCount = wallet.getSlotCount();
		if (oldSlotCount != newSlotCount) {
			lastSlotCount = newSlotCount;
			needsRefresh = true;
		}
		return result;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		if (stack != null && stack.getItem() != ModItems.coin) {
			player.inventory.insertItem(stack, true);
			return;
		}
		int oldSlotCount = wallet.getSlotCount();
		wallet.setItem(slot, stack);
		int newSlotCount = wallet.getSlotCount();
		if (oldSlotCount != newSlotCount) {
			lastSlotCount = newSlotCount;
			needsRefresh = true;
		}
	}

	@Override
	public String getNameTranslationKey() {
		return "gui.betterthantrading.wallet.name";
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public void setChanged() {
	}

	@Override
	public boolean stillValid(Player player) {
		return this.player == player;
	}

	@Override
	public void sortContainer() {
	}
}
