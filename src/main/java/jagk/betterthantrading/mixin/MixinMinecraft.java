package jagk.betterthantrading.mixin;

import jagk.betterthantrading.wallet.WalletKeyHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MixinMinecraft {

	@Inject(method = "runTick", at = @At("HEAD"))
	private void onClientTick(CallbackInfo ci) {
		WalletKeyHandler.handleKeyPress();
	}
}
