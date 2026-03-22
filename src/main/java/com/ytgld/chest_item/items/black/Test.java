package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.LaserColumn;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.SkillItem;
import net.minecraft.world.entity.player.Player;

public class Test extends ItemBlackShadow implements SkillItem {
    public Test(Properties properties) {
        super(properties);
    }
    public static void onKeyIsDown(Player player){
        if (Handler.has(player, InitItems.Test_.asItem())) {
            if (!player.level().isClientSide()) {
                if (!player.getCooldowns().isOnCooldown(InitItems.Test_.asItem().getDefaultInstance())) {
                    LaserColumn laserColumn = new LaserColumn(Entitys.LaserColumn_.get(),player.level());
                    laserColumn.setPos(player.position());
                    laserColumn.setOwner(player);
                    player.level().addFreshEntity(laserColumn);
                    player.getCooldowns().addCooldown(InitItems.Test_.asItem().getDefaultInstance(),1200);
                }
            }
        }
    }
}
