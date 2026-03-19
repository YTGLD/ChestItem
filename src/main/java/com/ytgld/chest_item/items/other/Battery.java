package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

    /**
 * 充能电池
 * <p>
 * 每次打开战利品箱子会出现1~2个绿宝石
 * <p>
 * 每次打开战利品箱子都会给予100点经验值
 * <p>
 * 战利品的数量越多，经验值奖励越多
 */
public class Battery extends ItemBase {
        @ConfigPlugin
        public static class ConfigItem implements RegisterItemConfig {
            public static ModConfigSpec.IntValue intValue ;
            public static ModConfigSpec.IntValue intValue2 ;
            @Override
            public void config(ModConfigSpec.Builder builder) {
                builder.push("Battery");
                intValue =  builder.translation("chest_item.config.Battery")
                        .defineInRange("number",100,0,Integer.MAX_VALUE);
                intValue2 =  builder.translation("chest_item.config.Battery2")
                        .defineInRange("number2",1,0,Integer.MAX_VALUE);
                builder.pop();
            }

            @Override
            public List<CIString> theLanguageProvider() {
                return List.of(
                        new CIString("Battery",
                                "充能电池","每个箱子的经验值"),
                        new CIString("Battery2",
                                "充能电池2","额外经验值")
                );
            }
        }
    public Battery(Properties properties) {
        super(properties);
    }

    public static final String chestBattery  = "ChestBattery";
    public static void objectArrayList(ObjectArrayList<ItemStack> objectArrayList, Entity entity){
        if (entity instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        CompoundTag compoundTag = stack.get(DataReg.tag);
                        if (stack.is(InitItems.Battery_)) {
                            float xpAdd = objectArrayList.size() * ConfigItem.intValue2.getAsInt();
                            float xp =ConfigItem.intValue.getAsInt();
                            player.giveExperiencePoints((int) (xp+xpAdd));
                            if (compoundTag != null) {
                                compoundTag.putInt(chestBattery, (int) (compoundTag.getInt(chestBattery)+ (xp+xpAdd)));
                            }else {
                                stack.set(DataReg.tag,new CompoundTag());
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.battery.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.battery.string.2",ConfigItem.intValue.getAsInt()).withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.battery.string.3").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.literal(""));
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag!=null) {
            tooltipAdder.add(Component.translatable("item.chest_item.battery.string.4").
                    append(String.valueOf(compoundTag.getInt(chestBattery))).withStyle(ChatFormatting.YELLOW));
        }else {
            tooltipAdder.add(Component.translatable("item.chest_item.battery.string.4").append(String.valueOf(0)).withStyle(ChatFormatting.YELLOW));

        }
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,0,255,255);
    }
}
