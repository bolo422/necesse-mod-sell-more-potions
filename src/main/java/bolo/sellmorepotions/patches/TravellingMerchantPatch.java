package bolo.sellmorepotions.patches;

import bolo.sellmorepotions.SellMorePotionsMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.AlchemistHumanMob;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.Map;

@ModMethodPatch(target = AlchemistHumanMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class TravellingMerchantPatch {

    @Advice.OnMethodExit()
    static void onExit(@Advice.This AlchemistHumanMob merchant, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> list) {
        GameRandom random = new GameRandom(merchant.getShopSeed());
        for (Map.Entry<String, Integer> potion : SellMorePotionsMod.config.getPotions().entrySet()) {
            list.add(ShopItem.item(potion.getKey(), random.nextSeeded().getIntBetween((int)(potion.getValue() * 0.9), (int)(potion.getValue() * 1.1))));
        }
    }
}