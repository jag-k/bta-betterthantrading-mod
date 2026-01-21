package jagk.betterthantrading;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.Screen;

import java.util.function.Function;

public class ModMenuIntegration implements ModMenuApi {

	@Override
	public Function<Screen, ? extends Screen> getConfigScreenFactory() {
		return ConfigScreen::new;
	}
}
