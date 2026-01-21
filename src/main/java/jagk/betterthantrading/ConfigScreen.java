package jagk.betterthantrading;

import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.SliderElement;

public class ConfigScreen extends Screen {

	private SliderElement dropChanceSlider;
	private SliderElement cooldownSlider;

	public ConfigScreen(Screen parent) {
		super(parent);
	}

	@Override
	public void init() {
		int centerX = this.width / 2;
		int startY = this.height / 4;

		dropChanceSlider = new SliderElement(0, centerX - 100, startY, 200, 20,
			"Drop Chance: " + String.format("%.0f%%", ModConfig.dropChanceMultiplier * 100),
			ModConfig.dropChanceMultiplier);
		this.add(dropChanceSlider);

		cooldownSlider = new SliderElement(1, centerX - 100, startY + 30, 200, 20,
			"Reset Cooldown: " + (ModConfig.classResetCooldownMs / (60 * 60 * 1000)) + "h",
			(float) (ModConfig.classResetCooldownMs / (60 * 60 * 1000)) / 24f);
		this.add(cooldownSlider);

		this.add(new ButtonElement(2, centerX - 100, startY + 70, 95, 20, "Save"));
		this.add(new ButtonElement(3, centerX + 5, startY + 70, 95, 20, "Cancel"));
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();

		dropChanceSlider.displayString = "Drop Chance: " + String.format("%.0f%%", dropChanceSlider.sliderValue * 100);

		int cooldownHours = Math.max(1, (int) (cooldownSlider.sliderValue * 24));
		cooldownSlider.displayString = "Reset Cooldown: " + cooldownHours + "h";

		this.font.drawCenteredString("Better Than Trading - Config", this.width / 2, 20, 0xFFFFFF);
		this.font.drawCenteredString("Drop chance for non-specialists", this.width / 2, this.height / 4 - 15, 0xAAAAAA);

		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void buttonClicked(ButtonElement button) {
		if (button.id == 2) {
			ModConfig.dropChanceMultiplier = (float) dropChanceSlider.sliderValue;
			int cooldownHours = Math.max(1, (int) (cooldownSlider.sliderValue * 24));
			ModConfig.classResetCooldownMs = cooldownHours * 60 * 60 * 1000L;
			ModConfig.save();
			this.mc.displayScreen(this.parentScreen);
		} else if (button.id == 3) {
			this.mc.displayScreen(this.parentScreen);
		}
	}
}
