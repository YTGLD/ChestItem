package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
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
                            List<ItemStack> itemStacks = new ArrayList<>();
                            for (int j = 0; j < objectArrayList.size(); j++) {
                                if (!objectArrayList.get(i).isEmpty()) {
                                    itemStacks.add(objectArrayList.get(i));
                                }
                            }
                            objectArrayList.add(new ItemStack(Items.EMERALD,Mth.nextInt(RandomSource.create(),1,2)));
                            float xpAdd = itemStacks.size();
                            float xp =100f;
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
        tooltipAdder.add(Component.translatable("item.chest_item.battery.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.battery.string.2").withStyle(ChatFormatting.GOLD));
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
