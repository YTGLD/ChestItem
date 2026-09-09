package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

/**
 * 要塞角锥
 * <p>
 * 当理智降低到正常水平以下
 * <p>
 * 在持有者存在5级以上的增生状态
 * <p>
 * 且佩戴钢铁之心后食用金苹果进行“侵蚀”
 * <p>
 * 侵蚀后的相关物品在吃下食物后治愈持有者
 */
public class FortressCone extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"fortress_cone");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }
    public static void event_2(LivingEntityUseItemEvent.Finish event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (IEvilGift.isHasEvilGift(stack, EvilGifts.fortress_cone.get())) {
                        float heal = 2;
                        player.heal(heal);
                    }
                }
            }
        }
    }
    public static void event(LivingEntityUseItemEvent.Finish event){
        if (event.getEntity() instanceof Player player) {
            if (!(EvilMother.getSanValue(player) < 0)) {
                return;
            }
            MobEffectInstance effect = player.getEffect(Effects.IncreasingMeat_);

            if (Handler.has(player, InitItems.SpeedHeart_.asItem())
                    && effect != null
                    && effect.getAmplifier() > 4
            ){
                if (event.getItem().is(Items.GOLDEN_APPLE)) {
                    ChestInventory chestInventory = Handler.getItem(player);
                    if (chestInventory != null) {
                        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                            ItemStack stack = chestInventory.getItem(i);
                            if (stack.getItem() instanceof IEvilGift iEvilGift) {
                                if (iEvilGift.addGift(stack, EvilGifts.fortress_cone.get())) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
