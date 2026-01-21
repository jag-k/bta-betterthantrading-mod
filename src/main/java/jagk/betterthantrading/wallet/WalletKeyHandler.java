package jagk.betterthantrading.wallet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import org.lwjgl.input.Keyboard;

public class WalletKeyHandler {
	public static int walletKey = Keyboard.KEY_SEMICOLON;
	private static boolean wasKeyDown = false;

	public static void handleKeyPress() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc == null || mc.thePlayer == null) {
			return;
		}

		boolean isKeyDown = Keyboard.isKeyDown(walletKey);

		if (isKeyDown && !wasKeyDown) {
			if (mc.currentScreen == null) {
				Player player = mc.thePlayer;
				ContainerWallet container = new ContainerWallet(player);
				player.displayContainerScreen(container);
			}
		}

		wasKeyDown = isKeyDown;
	}
}
