package jagk.betterthantrading.wallet;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import jagk.betterthantrading.item.ModItems;
import net.minecraft.core.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WalletManager {
	private static final Map<String, PlayerWallet> wallets = new HashMap<>();
	private static final int INITIAL_SLOTS = 9;
	private static final int SLOT_INCREMENT = 9;
	private static final int MAX_SLOTS = 54;

	public static PlayerWallet getWallet(String playerName) {
		return wallets.computeIfAbsent(playerName, k -> new PlayerWallet(INITIAL_SLOTS));
	}

	public static Map<String, CompoundTag> saveData() {
		Map<String, CompoundTag> data = new HashMap<>();
		for (Map.Entry<String, PlayerWallet> entry : wallets.entrySet()) {
			CompoundTag tag = new CompoundTag();
			entry.getValue().writeToNBT(tag);
			data.put(entry.getKey(), tag);
		}
		return data;
	}

	public static void loadData(Map<String, CompoundTag> data) {
		wallets.clear();
		for (Map.Entry<String, CompoundTag> entry : data.entrySet()) {
			PlayerWallet wallet = new PlayerWallet(INITIAL_SLOTS);
			wallet.readFromNBT(entry.getValue());
			wallets.put(entry.getKey(), wallet);
		}
	}

	public static class PlayerWallet {
		private final ItemStack[] inventory;
		private int slotCount;

		public PlayerWallet(int initialSlots) {
			this.slotCount = initialSlots;
			this.inventory = new ItemStack[MAX_SLOTS];
		}

		public int getSlotCount() {
			return slotCount;
		}

		public ItemStack[] getInventory() {
			return inventory;
		}

		public ItemStack getItem(int slot) {
			if (slot >= 0 && slot < inventory.length) {
				return inventory[slot];
			}
			return null;
		}

		public void setItem(int slot, ItemStack stack) {
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = stack;
				checkAndExpandSlots();
			}
		}

		public ItemStack removeItem(int slot, int amount) {
			if (slot >= 0 && slot < inventory.length && inventory[slot] != null) {
				ItemStack stack;
				if (inventory[slot].stackSize <= amount) {
					stack = inventory[slot];
					inventory[slot] = null;
				} else {
					stack = inventory[slot].splitStack(amount);
					if (inventory[slot].stackSize == 0) {
						inventory[slot] = null;
					}
				}
				checkAndExpandSlots();
				return stack;
			}
			return null;
		}

		private void checkAndExpandSlots() {
			if (slotCount < MAX_SLOTS) {
				boolean allFilled = true;
				for (int i = 0; i < slotCount; i++) {
					if (inventory[i] == null) {
						allFilled = false;
						break;
					}
				}

				if (allFilled) {
					slotCount = Math.min(slotCount + SLOT_INCREMENT, MAX_SLOTS);
					return;
				}
			}

			if (slotCount > INITIAL_SLOTS) {
				int lastRowStart = slotCount - SLOT_INCREMENT;
				boolean lastRowEmpty = true;
				for (int i = lastRowStart; i < slotCount; i++) {
					if (inventory[i] != null) {
						lastRowEmpty = false;
						break;
					}
				}

				if (lastRowEmpty) {
					slotCount = Math.max(slotCount - SLOT_INCREMENT, INITIAL_SLOTS);
				}
			}
		}

		public int getTotalCoins() {
			int total = 0;
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null && isCoinItem(inventory[i])) {
					total += inventory[i].stackSize;
				}
			}
			return total;
		}

		private boolean isCoinItem(ItemStack stack) {
			return stack.getItem() == ModItems.coin;
		}

		public void writeToNBT(CompoundTag tag) {
			tag.putInt("SlotCount", slotCount);
			ListTag items = new ListTag();
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null) {
					CompoundTag item = new CompoundTag();
					item.putByte("Slot", (byte) i);
					inventory[i].writeToNBT(item);
					items.addTag(item);
				}
			}
			tag.put("Items", items);
		}

		public void readFromNBT(CompoundTag tag) {
			slotCount = tag.getInteger("SlotCount");
			if (slotCount == 0) {
				slotCount = INITIAL_SLOTS;
			}
			ListTag items = tag.getList("Items");
			for (int i = 0; i < items.tagCount(); i++) {
				CompoundTag item = (CompoundTag) items.tagAt(i);
				int slot = item.getByte("Slot") & 255;
				if (slot >= 0 && slot < inventory.length) {
					inventory[slot] = ItemStack.readItemStackFromNbt(item);
				}
			}
		}
	}
}
