package com.ytgld.chest_item.items.evil_mother.decay;

import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashSet;
import java.util.function.Consumer;

/**
 * 腐堕剑心
 * <p>
 * 无法再制造出飞散的剑气
 * <p>
 * 作为补偿，剑气造成伤害时有概率再次凝聚能量
 * <p>
 * 并且剑气被腐化后施加邪母之拒
 * <p>
 * 增加100%苍戮和审判之剑的连斩次数
 */
public class SwordHeart extends EvilMother {
    public SwordHeart(Properties properties) {
        super(properties);
    }
    @Override
    public int getSanity() {
        return -7;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.sword_heart.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.sword_heart.string.2").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.sword_heart.string.3").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.sword_heart.string.4").withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int maxGiftNumber(ItemStack stack) {
        return 1;
    }
    @Override
    public HashSet<EvilGiftBase> canHasEvilGift() {
        HashSet<EvilGiftBase> evilGiftBases = new HashSet<>();
        evilGiftBases.add(EvilGifts.dawn.get());
        return evilGiftBases;
    }
}
