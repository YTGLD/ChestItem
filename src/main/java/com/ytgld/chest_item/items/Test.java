package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.Tombstone;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.client_utils.AddUtil;
import net.minecraft.world.entity.player.Player;

public class Test extends ItemBlackShadow implements SkillItem {
    public Test(Properties properties) {
        super(properties);
    }
    public static void event(Player player){
        if (!Handler.has(player, InitItems.Test_.asItem())) {
            return;
        }
        int count = 3;
        double radius = 8;

        for (int i = 0; i < count; i++) {
            double angle = 2.0 * Math.PI * i / count;

            double x = player.getX() + radius * Math.cos(angle);
            double z = player.getZ() + radius * Math.sin(angle);

            Tombstone tombstone = new Tombstone(
                    Entitys.Tombstone_.get(),
                    player.level()
            );
            tombstone.setPos(x, player.getY(), z);
            tombstone.setOwner(player);
            tombstone.tame(player);

            player.level().addFreshEntity(tombstone);
        }

        AddUtil.addWrathHeaven(player,player.position().add(0,0.1f,0),
                Light.ARGB.color(255,235,100,245),12,false);
    }
}
