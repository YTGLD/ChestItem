package com.ytgld.chest_item.tip.an_element.elements;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 对满生命值的生物造成大量额外伤害
 */
public class Decisively extends SkillBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return theConfigCategory;
        }
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Decisively");
            intValue =  builder.translation("chest_item.config.Decisively")
                    .defineInRange("number",0.2f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(new CIString("Decisively",
                    "决断","每级的伤害倍率"));
        }
    }
    public Decisively(){
    }
    public static void useSkill(LivingDamageEvent.Pre event, ItemStack stack){
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                if (!stack.isEmpty()) {
                    if (SkillBase.isHasElement(stack, SkillList.pDecisively)) {
                        LivingEntity living = event.getEntity();
                        if (living.getHealth() >=living.getMaxHealth()){

                            int lvl = SkillBase.getHasElementLevel(stack, SkillList.pDecisively);
                            lvl ++;
                            event.setNewDamage(event.getNewDamage() * (1+(lvl*SkillList.pDecisively.aneLvlForModify())));

                            SkillBase.addXP(stack, SkillList.pDecisively, 1, 30, SkillList.pDecisively.levelMax());



                        }
                    }
                }
            }
        }
    }
    @Override
    public String baneName() {
        return "decisively";
    }

    @Override
    public boolean isPercentage() {
        return true;
    }

    @Override
    public float aneLvlForModify() {
        return  ConfigItem.intValue.get().floatValue();
    }

    @Override
    public int levelMax() {
        return 3;
    }
}
