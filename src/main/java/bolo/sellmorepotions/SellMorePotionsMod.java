package bolo.sellmorepotions;

import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class SellMorePotionsMod {

    public static Config config;

    public void preInit() {
        System.out.println("Sell More Potions Mod pre-initialization started!");
        config = new Config("settings.cfg");
        String potionsAdded = config.getPotions().stream()
                .map(potion -> potion.name)
                .reduce((a, b) -> a + ", " + b)
                .orElse("none");
        System.out.println(
                "Sell More Potions Mod initialization completed! The potions loaded are {" + potionsAdded + "}");
    }
}
