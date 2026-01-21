package jagk.betterthantrading.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.nbt.tags.CompoundTag;
import jagk.betterthantrading.BetterThanTrading;
import jagk.betterthantrading.classes.ClassManager;
import jagk.betterthantrading.command.CommandBTT;
import jagk.betterthantrading.economy.DonationManager;
import jagk.betterthantrading.economy.EconomyManager;
import jagk.betterthantrading.economy.EraManager;
import jagk.betterthantrading.wallet.WalletManager;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static jagk.betterthantrading.BetterThanTrading.MOD_ID;

public class ModDataManager {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path DATA_DIR = Paths.get("saves", MOD_ID);
	private static String currentWorldName = "world";

	public static void setWorldName(String worldName) {
		currentWorldName = worldName;
	}

	public static void saveAll() {
		try {
			Files.createDirectories(DATA_DIR);

			saveEconomy();
			saveClasses();
			saveDonations();
			saveEra();
			saveCooldowns();
			saveWallets();

			BetterThanTrading.LOGGER.info("Saved all mod data for world: {}", currentWorldName);
		} catch (Exception e) {
			BetterThanTrading.LOGGER.error("Failed to save mod data: {}", e.getMessage(), e);
		}
	}

	public static void loadAll() {
		try {
			loadEconomy();
			loadClasses();
			loadDonations();
			loadEra();
			loadCooldowns();
			loadWallets();

			BetterThanTrading.LOGGER.info("Loaded all mod data for world: {}", currentWorldName);
		} catch (Exception e) {
			BetterThanTrading.LOGGER.error("Failed to load mod data: {}", e.getMessage(), e);
		}
	}

	private static void saveEconomy() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_economy.json");
		Map<String, Integer> data = EconomyManager.saveData();
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(data, writer);
		}
	}

	private static void loadEconomy() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_economy.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				Type type = new TypeToken<Map<String, Integer>>() {
				}.getType();
				Map<String, Integer> data = GSON.fromJson(reader, type);
				if (data != null) {
					EconomyManager.loadData(data);
				}
			}
		}
	}

	private static void saveClasses() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_classes.json");
		Map<String, String> data = ClassManager.saveData();
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(data, writer);
		}
	}

	private static void loadClasses() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_classes.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				Type type = new TypeToken<Map<String, String>>() {
				}.getType();
				Map<String, String> data = GSON.fromJson(reader, type);
				if (data != null) {
					ClassManager.loadData(data);
				}
			}
		}
	}

	private static void saveDonations() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_donations.json");
		DonationData data = new DonationData();
		data.donations = DonationManager.saveData();
		data.total = DonationManager.getTotalDonations();
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(data, writer);
		}
	}

	private static void loadDonations() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_donations.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				DonationData data = GSON.fromJson(reader, DonationData.class);
				if (data != null) {
					DonationManager.loadData(data.donations, data.total);
				}
			}
		}
	}

	private static void saveEra() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_era.json");
		EraData data = new EraData();
		data.currentEra = EraManager.getCurrentEra().getName();
		data.progress = EraManager.getProgress();
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(data, writer);
		}
	}

	private static void loadEra() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_era.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				EraData data = GSON.fromJson(reader, EraData.class);
				if (data != null) {
					EraManager.loadData(data.currentEra, data.progress);
				}
			}
		}
	}


	private static void saveCooldowns() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_cooldowns.json");
		Map<String, Long> data = CommandBTT.getCooldowns();
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(data, writer);
		}
	}

	private static void loadCooldowns() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_cooldowns.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				Type type = new TypeToken<Map<String, Long>>() {
				}.getType();
				Map<String, Long> data = GSON.fromJson(reader, type);
				if (data != null) {
					CommandBTT.loadCooldowns(data);
				}
			}
		}
	}

	private static void saveWallets() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_wallets.json");
		Map<String, CompoundTag> walletData = WalletManager.saveData();

		Map<String, WalletSerializable> serializableData = new HashMap<>();
		for (Map.Entry<String, CompoundTag> entry : walletData.entrySet()) {
			serializableData.put(entry.getKey(), WalletSerializable.fromNBT(entry.getValue()));
		}

		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(serializableData, writer);
		}
	}

	private static void loadWallets() throws IOException {
		Path path = DATA_DIR.resolve(currentWorldName + "_wallets.json");
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				Type type = new TypeToken<Map<String, WalletSerializable>>() {
				}.getType();
				Map<String, WalletSerializable> serializableData = GSON.fromJson(reader, type);

				if (serializableData != null) {
					Map<String, CompoundTag> walletData = new HashMap<>();
					for (Map.Entry<String, WalletSerializable> entry : serializableData.entrySet()) {
						walletData.put(entry.getKey(), entry.getValue().toNBT());
					}
					WalletManager.loadData(walletData);
				}
			}
		}
	}

	private static class DonationData {
		Map<String, Integer> donations = new HashMap<>();
		int total = 0;
	}

	private static class EraData {
		String currentEra = "STONE_AGE";
		int progress = 0;
	}

	private static class WalletSerializable {
		int slotCount;
		Map<Integer, ItemStackSerializable> items = new HashMap<>();

		static WalletSerializable fromNBT(CompoundTag tag) {
			WalletSerializable wallet = new WalletSerializable();
			wallet.slotCount = tag.getInteger("SlotCount");

			com.mojang.nbt.tags.ListTag itemsList = tag.getList("Items");
			for (int i = 0; i < itemsList.tagCount(); i++) {
				CompoundTag itemTag = (CompoundTag) itemsList.tagAt(i);
				int slot = itemTag.getByte("Slot") & 255;

				ItemStackSerializable item = new ItemStackSerializable();
				item.id = itemTag.getShort("id");
				item.damage = itemTag.getShort("Damage");
				item.count = itemTag.getByte("Count");

				wallet.items.put(slot, item);
			}

			return wallet;
		}

		CompoundTag toNBT() {
			CompoundTag tag = new CompoundTag();
			tag.putInt("SlotCount", slotCount);

			com.mojang.nbt.tags.ListTag itemsList = new com.mojang.nbt.tags.ListTag();
			for (Map.Entry<Integer, ItemStackSerializable> entry : items.entrySet()) {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putByte("Slot", entry.getKey().byteValue());
				itemTag.putShort("id", entry.getValue().id);
				itemTag.putShort("Damage", entry.getValue().damage);
				itemTag.putByte("Count", entry.getValue().count);
				itemsList.addTag(itemTag);
			}
			tag.put("Items", itemsList);

			return tag;
		}
	}

	private static class ItemStackSerializable {
		short id;
		short damage;
		byte count;
	}
}
