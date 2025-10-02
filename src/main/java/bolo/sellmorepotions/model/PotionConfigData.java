package bolo.sellmorepotions.model;

import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;

import java.util.Optional;

public class PotionConfigData {
    public final String name;
    public final int maxStock;
    public final int restockPerDay;
    public final int bestPrice;
    public final int worstPrice;

    public PotionConfigData(String name, Integer maxStock, Integer restockPerDay, Integer bestPrice, Integer worstPrice) {
        this.name = name;
        this.maxStock = Optional.ofNullable(maxStock).orElse(50);
        this.restockPerDay = Optional.ofNullable(restockPerDay).orElse(10);
        this.bestPrice = Optional.ofNullable(bestPrice).orElse(5);
        this.worstPrice = Optional.ofNullable(worstPrice).orElse(25);
    }

    public SellingShopItem buildSellingShopItem() {
        return new SellingShopItem(this.maxStock, this.restockPerDay)
                .setStaticPriceBasedOnHappiness(this.bestPrice, this.worstPrice, 5);
    }
}
