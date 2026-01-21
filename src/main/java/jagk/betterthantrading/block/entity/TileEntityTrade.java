package jagk.betterthantrading.block.entity;

import jagk.betterthantrading.economy.EconomyManager;
import jagk.betterthantrading.economy.MarketManager;
import jagk.betterthantrading.economy.PriceRegistry;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.container.Container;

public class TileEntityTrade extends TileEntity implements Container {
	final private ItemStack[] inventory = new ItemStack[9];
	private String ownerName = "";

	public void openGui(Player player) {
		if (ownerName.isEmpty()) {
			ownerName = player.username;
		}
		player.displayContainerScreen(this);
	}

	public void listItemOnMarket(Player player, int slot) {
		if (!player.username.equals(ownerName)) {
			player.sendMessage(TextFormatting.RED + "This is not your trade block!");
			return;
		}

		ItemStack stack = inventory[slot];
		if (stack != null) {
			int price = PriceRegistry.getMarketPrice(stack.getItem());
			MarketManager.listItem(player.username, stack.copy(), price);
			inventory[slot] = null;
			setChanged();

			player.sendMessage(TextFormatting.GREEN + "Listed " + stack.stackSize + "x " +
				stack.getItem().getKey() + " for " + (price * stack.stackSize) + " coins!");
		}
	}

	public void collectEarnings(Player player) {
		if (!player.username.equals(ownerName)) {
			player.sendMessage(TextFormatting.RED + "This is not your trade block!");
			return;
		}

		int earnings = MarketManager.collectEarnings(player.username);
		if (earnings > 0) {
			EconomyManager.addMoney(player, earnings);
			player.sendMessage(TextFormatting.GREEN + "Collected " + earnings + " coins!");
		} else {
			player.sendMessage(TextFormatting.YELLOW + "No earnings to collect.");
		}
	}

	@Override
	public int getContainerSize() {
		return inventory.length;
	}

	@Override
	public ItemStack getItem(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		if (inventory[slot] != null) {
			ItemStack stack;
			if (inventory[slot].stackSize <= amount) {
				stack = inventory[slot];
				inventory[slot] = null;
				setChanged();
				return stack;
			} else {
				stack = inventory[slot].splitStack(amount);
				if (inventory[slot].stackSize == 0) {
					inventory[slot] = null;
				}
				setChanged();
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getMaxStackSize()) {
			stack.stackSize = getMaxStackSize();
		}
		setChanged();
	}

	@Override
	public String getNameTranslationKey() {
		return "tile.betterthantrading.tradeblock";
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public void setChanged() {
		super.setChanged();
	}

	@Override
	public boolean stillValid(Player player) {
		return worldObj.getTileEntity(x, y, z) == this &&
			player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) <= 64.0;
	}

	@Override
	public void sortContainer() {
	}

	public String getOwnerName() {
		return ownerName;
	}

	@Override
	public void readFromNBT(com.mojang.nbt.tags.CompoundTag tag) {
		super.readFromNBT(tag);
		com.mojang.nbt.tags.ListTag items = tag.getList("Items");
		for (int i = 0; i < items.tagCount(); ++i) {
			com.mojang.nbt.tags.CompoundTag item = (com.mojang.nbt.tags.CompoundTag) items.tagAt(i);
			int slot = item.getByte("Slot") & 255;
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.readItemStackFromNbt(item);
			}
		}
		ownerName = tag.getString("Owner");
	}

	@Override
	public void writeToNBT(com.mojang.nbt.tags.CompoundTag tag) {
		super.writeToNBT(tag);
		com.mojang.nbt.tags.ListTag items = new com.mojang.nbt.tags.ListTag();
		for (int i = 0; i < inventory.length; ++i) {
			if (inventory[i] != null) {
				com.mojang.nbt.tags.CompoundTag item = new com.mojang.nbt.tags.CompoundTag();
				item.putByte("Slot", (byte) i);
				inventory[i].writeToNBT(item);
				items.addTag(item);
			}
		}
		tag.put("Items", items);
		tag.putString("Owner", ownerName);
	}
}
