package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.utils.AddUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class Test extends ItemBlackShadow implements SkillItem {
    public Test(Properties properties) {
        super(properties);
    }
    public static void event(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Test_.asItem())) {
                AddUtil.addFatalEffect(
                        event.getEntity(),
                        Light.ARGB.color(255,150,150,250),
                        6
                );
            }
        }
    }
}
