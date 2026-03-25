package com.ytgld.chest_item.items.gold;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class Ring extends ItemBase implements IGold {
    public Ring(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Ring");
            intValue =  builder.translation("chest_item.config.Ring")
                    .defineInRange("number",1,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(new CIString("Ring",
                    "矿工戒指","药水等级"));
        }
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Ring_)) {
                    if (player.tickCount % 80 == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.HASTE, 100, ConfigItem.intValue.getAsInt(), false, false), player);
                        break;
                    }
                }
            }
        }
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.ring.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.ring.string.1").withStyle(ChatFormatting.GOLD));
    }




    @Override
    public Vec2 posOffset() {
        return new Vec2(4,4);
    }

    @Override
    public int guiColor(ItemStack stack) {
        return Light.ARGB.color(255,50,120,255);
    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,20);
    }
}

