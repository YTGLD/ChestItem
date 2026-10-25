package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * 腐烂医疗箱
 * <p>
 * 持续的生命恢复效果
 * <p>
 * 恢复生命值时同时恢复幽影护盾
 * <p>
 * 在幽影护盾的击碎反应时间内自身治疗效果减少%d%%
 */
public class FirstAidKit extends EvilMother{
    public FirstAidKit(Properties properties) {
        super(properties);
    }
    public static void event(LivingHealEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player,InitItems.FirstAidKit_.asItem())) {
                int heal = (int) (event.getAmount() + 1);
                Handler.addShadowBlackShieldData(player,heal);
                boolean hasCooldown = player.getData(AttReg.shadowShieldCooldownType) > 1;
                float downHeal = ConfigItem.intValue.getAsInt() / 100f;
                if (hasCooldown) {
                    event.setAmount(event.getAmount() * (1 - downHeal));
                }
            }
        }
    }
    @Override
    public int getSanity() {
        return -3;
    }

    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        if (!player.level().isClientSide() && player.tickCount % 80 == 1) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false), player);
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        int downHeal = ConfigItem.intValue.getAsInt();
        tooltipComponents.accept(Component.translatable("item.chest_item.first_aid_kit.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.first_aid_kit.string.2").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.first_aid_kit.string.3",downHeal).withStyle(Style.EMPTY.withColor(color)));
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "EvilMother";
        }
        public static ModConfigSpec.IntValue intValue;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("FirstAidKit");
            intValue = builder.translation("chest_item.config.FirstAidKit")
                    .defineInRange("number", 50, 0, 100);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("FirstAidKit",
                            "腐烂医疗箱", "治疗减少")
            );
        }
    }
}
