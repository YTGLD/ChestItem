package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * 邪母胃
 * <p>
 * 当理智降低到正常水平以下
 * <p>
 * 并且持有者因饥饿而死
 * <p>
 * 则令某些物品产生第二意识
 * <p>
 * 消耗经验值缓慢使持有者饱腹
 *
 *
 */
public class EvilStomach extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_stomach");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }

    @Override
    public void tickGift(Player player, ItemStack stack) {
        super.tickGift(player, stack);
        int san = (int) EvilMother.getSanValue(player);
        //10
        int baseSan = (int) EvilMother.getSanValueBase(player);

        int outSan = san - baseSan;

        outSan *= 5;

        int time = 200 - Math.abs(outSan);

        if (time < 10) {
            time = 10;
        }
        if (player.tickCount % time == 1) {
            if (player.experienceLevel > 0) {
                int xp = -2;
                int food = 1;
                float saturation = 0.5f;

                player.giveExperiencePoints(xp);
                player.getFoodData().eat(food,saturation);
            }
        }
    }

    public static void event(LivingDeathEvent event){
        if (event.getEntity() instanceof Player player && event.getSource().is(DamageTypes.STARVE)) {
            if (!(EvilMother.getSanValue(player) < 10)) {
                return;
            }

            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.getItem() instanceof IEvilGift iEvilGift) {
                        iEvilGift.addGift(stack, EvilGifts.evil_stomach.get());
                        if (iEvilGift.maxGiftNumber(stack) > 0) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
