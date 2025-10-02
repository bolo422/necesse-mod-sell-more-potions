package bolo.sellmorepotions.patches;

import bolo.sellmorepotions.SellMorePotionsMod;
import bolo.sellmorepotions.model.PotionConfigData;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.engine.registries.ItemRegistry;
import necesse.entity.mobs.friendly.human.humanShop.AlchemistHumanMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(
        target = AlchemistHumanMob.class,
        arguments = {}
)
public class AlchemistHumanPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This AlchemistHumanMob mob) {
        for (PotionConfigData potion : SellMorePotionsMod.config.getPotions()) {
            if (ItemRegistry.getItem(potion.name) != null) {
                System.out.println("(INFO) [SellMorePotions] SellMorePotions: Adding item " + potion.name + " to Alchemist shop");
                mob.shop.addSellingItem(potion.name, potion.buildSellingShopItem());
            } else {
                System.out.println("(WARN) [SellMorePotions] SellMorePotions: Item " + potion.name + " not found in ItemRegistry!");
            }
        }
    }
}