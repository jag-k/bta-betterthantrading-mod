package jagk.betterthantrading;

import jagk.betterthantrading.item.ModItems;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import java.util.HashMap;

import static jagk.betterthantrading.BetterThanTrading.MOD_ID;

public class BetterThanTradingModelEntrypoint implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		HashMap<Item, String> itemTextureMap = new HashMap<>();
		itemTextureMap.put(ModItems.coin, "coin");
		itemTextureMap.put(ModItems.classTokenMiner, "class_token_miner");
		itemTextureMap.put(ModItems.classTokenLumberjack, "class_token_lumberjack");
		itemTextureMap.put(ModItems.classTokenFarmer, "class_token_farmer");
		itemTextureMap.put(ModItems.classTokenHunter, "class_token_hunter");
		itemTextureMap.put(ModItems.classTokenBuilder, "class_token_builder");
		itemTextureMap.put(ModItems.classTokenMerchant, "class_token_merchant");

		for (Item item : itemTextureMap.keySet()) {
			ModelHelper.setItemModel(item, () -> {
				ItemModelStandard im = new ItemModelStandard(item, MOD_ID);
				im.icon = TextureRegistry.getTexture(NamespaceID.getPermanent(MOD_ID, "item/" + itemTextureMap.get(item)));
				return im;
			});
		}
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
