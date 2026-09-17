package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.reactor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.HashMap;

/**
 * 转换煅烧器
 * <p>
 * 给反应炉“喂食”32枚钻石
 * <p>
 * 会使反应炉激活挖矿组件
 * <p>
 * 燃烧物品获得属性并恢复生命但随时间流逝
 * <p>
 * 属性包括经验获取,挖掘速度,时运
 *
 *
 */

public class Calciner extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"calciner");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }

    @Override
    public AttHolderModify attHolderModify() {
        AttHolderModify attHolderModify = new AttHolderModify(new HashMap<>());

        attHolderModify.multimap().put( AttReg.fortune,
                new AttributeModifier(this.id(),
                        1, AttributeModifier.Operation.ADD_VALUE));

        attHolderModify.multimap().put(Attributes.MINING_EFFICIENCY,
                new AttributeModifier(this.id(),
                        4, AttributeModifier.Operation.ADD_VALUE));

        return attHolderModify;
    }
    public static final int max = 32;
    public static final String fireNumber = Chestitem.MODID +  "CalcinerFireNumber";

    public static void event(EntityTickEvent.Post event){
        if (event.getEntity()  instanceof Player player) {
            if (!player.level().isClientSide() && player.tickCount % 20 == 1) {
                CompoundTag compoundTag = player.getPersistentData();
                int size = compoundTag.getIntOr(fireNumber,0);
                if (size > 0) {
                    compoundTag.putInt(fireNumber,size - 1);
                }
            }
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (IEvilGift.isHasEvilGift(stack, EvilGifts.calciner.get())) {
                        player.getAttributes().addTransientAttributeModifiers(theAttrib(player));
                        break;
                    }else {
                        player.getAttributes().removeAttributeModifiers(theAttrib(player));
                    }
                }
            }
        }
    }
    private static Multimap<Holder<Attribute>, AttributeModifier> theAttrib(Player player) {
        Multimap<Holder<Attribute> , AttributeModifier> attributeModifierMultimap = HashMultimap.create();
        Identifier identifier = Identifier.parse(Chestitem.MODID + "_calciner");
        CompoundTag compoundTag = player.getPersistentData();
        int size = compoundTag.getIntOr(fireNumber,0);
        if (size > 100) {
            size = 100;
        }
        if (size < 0) {
            size = 0;
        }
        if (!player.level().isClientSide() && player.tickCount % 20 == 1) {
            if (size > 1) {
                player.heal(1);
            }
        }
        float xp = 1 / 100f * size;
        float mine = 1 / 100f * size;
        float fortune = 1.5f / 100f * size;

        attributeModifierMultimap.put(AttReg.xp_drop, new AttributeModifier(identifier,
                xp, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        attributeModifierMultimap.put(Attributes.MINING_EFFICIENCY, new AttributeModifier(identifier,
                mine, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        attributeModifierMultimap.put(AttReg.fortune, new AttributeModifier(identifier,
                fortune, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return attributeModifierMultimap;
    }
}
