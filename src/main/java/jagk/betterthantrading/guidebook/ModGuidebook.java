package jagk.betterthantrading.guidebook;

import jagk.betterthantrading.BetterThanTrading;
import net.minecraft.client.gui.guidebook.GuidebookSections;

public class ModGuidebook {

	private static boolean registered = false;

	public static void register() {
		if (registered) return;
		registered = true;

		GuidebookSections.init();
		BetterThanTrading.LOGGER.info("Registering guidebook section...");
		GuidebookSections.register(new GuidebookSectionTrading());
		BetterThanTrading.LOGGER.info("Guidebook section registered.");
	}
}
