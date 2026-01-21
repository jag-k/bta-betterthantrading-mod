package jagk.betterthantrading;

import net.fabricmc.api.ClientModInitializer;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class BetterThanTradingClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void onInitializeClient() {
		BetterThanTrading.LOGGER.info("Better Than Trading client initialized.");
	}

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
	}
}
