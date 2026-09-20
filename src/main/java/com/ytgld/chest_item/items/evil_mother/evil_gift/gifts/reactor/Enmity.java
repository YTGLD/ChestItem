package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.reactor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 *
 * 仇恨插件
 * <p>
 * 给反应炉“喂食”瓶子<抗旨>
 * <p>
 * 会使反应炉激活仇恨组件
 * <p>
 * 使得反应炉吸引附近怪物仇恨
 * <p>
 * 且反应炉自我恢复和难以破坏
 *
 */
public class Enmity extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"enmity");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }
    public static final int max = 1;

    public static void doEnmity(Reactor reactor) {
        if (hasEnmity(reactor)
                && !reactor.level().isClientSide()
                && reactor.tickCount % 10 == 1
                && reactor.getOwner() instanceof Player player) {
            Vec3 playerPos = reactor.position().add(0, 1, 0);
            int range = 12;

            List<Mob> entities = reactor.level().getEntitiesOfClass(
                    Mob.class,
                    new AABB(
                            playerPos.x - range, playerPos.y - range, playerPos.z - range,
                            playerPos.x + range, playerPos.y + range, playerPos.z + range
                    )
            );

            reactor.heal(1);
            reactor.getAttributes().addTransientAttributeModifiers(theAttrib());


            for (Mob mob : entities) {
                if (mob == reactor) {
                    continue;
                }

                if (mob.getLastHurtByMob() == player) {
                    mob.setTarget(player);
                    continue;
                }

                if (mob.getTarget() == player) {
                    mob.setTarget(reactor);
                }
            }
        }
    }
    private static Multimap<Holder<Attribute>, AttributeModifier> theAttrib() {
        Multimap<Holder<Attribute> , AttributeModifier> attributeModifierMultimap = HashMultimap.create();
        Identifier identifier = Identifier.parse(Chestitem.MODID + "_enmity");

        attributeModifierMultimap.put(Attributes.ARMOR, new AttributeModifier(identifier,
                10, AttributeModifier.Operation.ADD_VALUE));

        return attributeModifierMultimap;
    }


    private static boolean hasEnmity(Reactor reactor){
        if (reactor.getOwner() != null
                && reactor.getOwner() instanceof Player player) {

            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (IEvilGift.isHasEvilGift(stack, EvilGifts.enmity.get())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
