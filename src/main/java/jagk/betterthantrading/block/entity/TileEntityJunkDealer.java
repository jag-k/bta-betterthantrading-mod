package jagk.betterthantrading.block.entity;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import jagk.betterthantrading.economy.EconomyManager;
import jagk.betterthantrading.economy.PriceRegistry;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.container.Container;

public class TileEntityJunkDealer extends TileEntity implements Container {
	private final ItemStack[] inventory = new ItemStack[9];

	public void openGui(Player player) {
		player.displayContainerScreen(this);
	}

	public void sellItem(Player player, int slot) {
		ItemStack stack = inventory[slot];
		if (stack != null) {
			int basePrice = PriceRegistry.getJunkPrice(stack.getItem());
			int totalPrice = basePrice * stack.stackSize;

			EconomyManager.addMoney(player, totalPrice);
			inventory[slot] = null;
			setChanged();

			player.sendMessage(TextFormatting.GREEN + "Sold for " + totalPrice + " coins!");
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
		return "tile.betterthantrading.junkdealer";
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

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		ListTag items = tag.getList("Items");
		for (int i = 0; i < items.tagCount(); ++i) {
			CompoundTag item = (CompoundTag) items.tagAt(i);
			int slot = item.getByte("Slot") & 255;
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.readItemStackFromNbt(item);
			}
		}
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		ListTag items = new ListTag();
		for (int i = 0; i < inventory.length; ++i) {
			if (inventory[i] != null) {
				CompoundTag item = new CompoundTag();
				item.putByte("Slot", (byte) i);
				inventory[i].writeToNBT(item);
				items.addTag(item);
			}
		}
		tag.put("Items", items);
	}
}
