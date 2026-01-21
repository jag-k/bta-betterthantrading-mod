package jagk.betterthantrading.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.context.CommandContext;
import jagk.betterthantrading.ModConfig;
import jagk.betterthantrading.classes.ClassManager;
import jagk.betterthantrading.classes.PlayerClass;
import jagk.betterthantrading.data.ModDataManager;
import jagk.betterthantrading.wallet.ContainerWallet;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.gamemode.Gamemode;

import java.util.HashMap;
import java.util.Map;

public class CommandBTT implements CommandManager.CommandRegistry {

	private static final Map<String, Long> lastResetTime = new HashMap<>();

	public static void register() {
		CommandManager.registerCommand(new CommandBTT());
	}

	public static Map<String, Long> getCooldowns() {
		return new HashMap<>(lastResetTime);
	}

	public static void loadCooldowns(Map<String, Long> data) {
		lastResetTime.clear();
		lastResetTime.putAll(data);
	}

	@Override
	public void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(
			ArgumentBuilderLiteral.<CommandSource>literal("btt")
				.then(ArgumentBuilderLiteral.<CommandSource>literal("class")
					.executes(this::executeClassInfo)
					.then(ArgumentBuilderLiteral.<CommandSource>literal("reset")
						.executes(this::executeClassReset)
					)
				)
				.then(ArgumentBuilderLiteral.<CommandSource>literal("wallet")
					.executes(this::executeWallet)
				)
				.then(ArgumentBuilderLiteral.<CommandSource>literal("save")
					.executes(this::executeSave)
				)
				.then(ArgumentBuilderLiteral.<CommandSource>literal("load")
					.executes(this::executeLoad)
				)
				.executes(this::executeHelp)
		);
	}

	private int executeHelp(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		source.sendMessage(TextFormatting.YELLOW + "=== Better Than Trading ===");
		source.sendMessage(TextFormatting.WHITE + "/btt class" + TextFormatting.GRAY + " - View your current class");
		source.sendMessage(TextFormatting.WHITE + "/btt class reset" + TextFormatting.GRAY + " - Reset your class");
		source.sendMessage(TextFormatting.WHITE + "/btt wallet" + TextFormatting.GRAY + " - Open your coin wallet");
		return 1;
	}

	private int executeClassInfo(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		Player player = source.getSender();

		if (player == null) {
			source.sendMessage(TextFormatting.RED + "This command can only be used by players!");
			return 0;
		}

		PlayerClass playerClass = ClassManager.getClass(player);

		if (playerClass == PlayerClass.NONE) {
			source.sendMessage(TextFormatting.YELLOW + "You don't have a class yet!");
			source.sendMessage(TextFormatting.GRAY + "Use a class token to choose one.");
		} else {
			source.sendMessage(TextFormatting.GREEN + "Your class: " + TextFormatting.WHITE + playerClass.getDisplayName());
			source.sendMessage(TextFormatting.GRAY + playerClass.getDescription());
		}

		return 1;
	}

	private int executeClassReset(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		Player player = source.getSender();

		if (player == null) {
			source.sendMessage(TextFormatting.RED + "This command can only be used by players!");
			return 0;
		}

		String playerName = player.username;

		if (!ClassManager.hasClass(player)) {
			source.sendMessage(TextFormatting.YELLOW + "You don't have a class yet! Use a class token to choose one.");
			return 0;
		}

		boolean isCreative = player.gamemode == Gamemode.creative;
		long currentTime = System.currentTimeMillis();
		Long lastReset = lastResetTime.get(playerName);

		if (lastReset != null && !isCreative) {
			long timeSinceReset = currentTime - lastReset;
			if (timeSinceReset < ModConfig.classResetCooldownMs) {
				long remainingMs = ModConfig.classResetCooldownMs - timeSinceReset;
				long remainingHours = remainingMs / (60 * 60 * 1000);
				long remainingMinutes = (remainingMs % (60 * 60 * 1000)) / (60 * 1000);

				source.sendMessage(TextFormatting.RED + "You must wait " + remainingHours + "h " + remainingMinutes + "m before resetting your class again!");
				return 0;
			}
		}

		PlayerClass oldClass = ClassManager.getClass(player);
		ClassManager.resetClass(player);
		lastResetTime.put(playerName, currentTime);

		long cooldownHours = ModConfig.classResetCooldownMs / (60 * 60 * 1000);
		source.sendMessage(TextFormatting.GREEN + "Your class has been reset! You were a " + oldClass.getDisplayName() + ".");
		source.sendMessage(TextFormatting.YELLOW + "Use a class token to choose a new class. Next reset available in " + cooldownHours + " hours.");

		return 1;
	}

	private int executeWallet(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		Player player = source.getSender();

		if (player == null) {
			source.sendMessage(TextFormatting.RED + "This command can only be used by players!");
			return 0;
		}

		ContainerWallet wallet = new ContainerWallet(player);
		player.displayContainerScreen(wallet);
		return 1;
	}

	private int executeSave(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		ModDataManager.saveAll();
		source.sendMessage(TextFormatting.GREEN + "All mod data saved successfully!");
		return 1;
	}

	private int executeLoad(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		ModDataManager.loadAll();
		source.sendMessage(TextFormatting.GREEN + "All mod data loaded successfully!");
		return 1;
	}
}
