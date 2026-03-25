package com.ytgld.chest_item.items.black.celestial;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class Samsara extends TheCelestial{

    public Samsara(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Samsara");
            intValue =  builder.translation("chest_item.config.Samsara")
                    .defineInRange("number",10,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(new CIString("Samsara",
                    "轮回道体","免伤概率"));
        }
    }
    public static void event(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Samsara_)) {
                            if (Mth.nextInt(RandomSource.create(),0,100) <= ConfigItem.intValue.get().intValue()){
                                if (event.getSource().getEntity() instanceof LivingEntity living) {
                                    living.hurt(living.damageSources().magic(), (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.8));
                                }
                                player.level().playSound(null,player.blockPosition(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.AMBIENT,1,1);
                                event.setAmount(0);
                            }
                        }
                    }
                }
            }
        }
    }

    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.samsara.string.1",ConfigItem.intValue.getAsInt()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
    }
    @Override
    public Identifier img(ItemStack stack) {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/celestial/samsara.png");
    }

    @Override
    public int soulColor(ItemStack stack) {
        return Light.ARGB.color(255,128,255,80);
    }
}
