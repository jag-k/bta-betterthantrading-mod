package jagk.betterthantrading.item;

import jagk.betterthantrading.classes.ClassManager;
import jagk.betterthantrading.classes.PlayerClass;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemClassToken extends Item {
	private final PlayerClass playerClass;

	public ItemClassToken(String translationKey, String namespaceId, int id, PlayerClass playerClass) {
		super(translationKey, namespaceId, id);
		this.playerClass = playerClass;
	}

	@Override
	public ItemStack onUseItem(ItemStack stack, World world, Player player) {
		if (!world.isClientSide) {
			if (!ClassManager.hasClass(player)) {
				ClassManager.setClass(player, playerClass);
				stack.stackSize--;
			} else {
				player.sendMessage("You already have a class!");
			}
		}
		return stack;
	}

	public PlayerClass getPlayerClass() {
		return playerClass;
	}
}
