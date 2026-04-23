package com.ytgld.chest_item.items.reinforced.meat;

import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.renderer.light.Light;

public class MeatBaseItem extends ReinforcedBaseItem {
    public MeatBaseItem(Properties properties) {
        super(properties);
    }
    @Override
    public int theColor() {
        return Light.ARGB.color(255,145,140,100);
    }
    @Override
    public void addLoot() {}
    @Override
    public int sanDown() {
        return 0;
    }
}
