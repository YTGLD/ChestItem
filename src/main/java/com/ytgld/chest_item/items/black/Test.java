package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.LaserColumn;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.SkillItem;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

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
