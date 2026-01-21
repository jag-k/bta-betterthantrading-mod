package jagk.betterthantrading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfig {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = Paths.get("config", BetterThanTrading.MOD_ID + ".json");

	public static float dropChanceMultiplier = 0.3f;
	public static long classResetCooldownMs = 4 * 60 * 60 * 1000L;

	public static void init() {
		load();
		BetterThanTrading.LOGGER.info("Config loaded: dropChanceMultiplier={}, classResetCooldownHours={}",
			dropChanceMultiplier, classResetCooldownMs / (60 * 60 * 1000L));
	}

	public static void load() {
		try {
			if (Files.exists(CONFIG_PATH)) {
				try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
					ConfigData data = GSON.fromJson(reader, ConfigData.class);
					if (data != null) {
						dropChanceMultiplier = data.dropChanceMultiplier;
						classResetCooldownMs = data.classResetCooldownHours * 60 * 60 * 1000L;
					}
				}
			} else {
				save();
			}
		} catch (Exception e) {
			BetterThanTrading.LOGGER.warn("Failed to load config, using defaults: {}", e.getMessage());
			save();
		}
	}

	public static void save() {
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			ConfigData data = new ConfigData();
			data.dropChanceMultiplier = dropChanceMultiplier;
			data.classResetCooldownHours = (int) (classResetCooldownMs / (60 * 60 * 1000L));
			try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
				GSON.toJson(data, writer);
			}
		} catch (Exception e) {
			BetterThanTrading.LOGGER.error("Failed to save config: {}", e.getMessage());
		}
	}

	private static class ConfigData {
		float dropChanceMultiplier = 0.3f;
		int classResetCooldownHours = 4;
	}
}
