package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 违命抗衡
 * <p>
 * 	短时间内多次死亡会使你越来越强，最多叠加10层
 * <p>
 * 	背包界面右键此物品来激活
 * <p>
 * 	激活后可在重生后传送回上一个死亡点
 */
public class DefyLife extends EvilMother{
    public static final String dieSizeKy = "DefyLifeDie";
    public static final String canUse = "DefyLifeCanUse";

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (clickAction.equals(ClickAction.SECONDARY)) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean(canUse,true);
            if (compoundTag == null) {
                stack.set(DataReg.tag,tag);
            }
            if (compoundTag != null) {
                compoundTag.putBoolean(canUse, !compoundTag.getBoolean(canUse));
                return true;
            }
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, clickAction, player, carriedItem);
    }
    public static void PlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.DefyLife_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {
                                updateTga(stack);
                                player.getCooldowns().addCooldown(stack.getItem(),200);
                                if (player.level() instanceof ServerLevel level) {
                                    if (compoundTag.getBoolean(canUse)) {
                                        player.getLastDeathLocation().ifPresent((globalPos -> {
                                            player.teleportTo(level,globalPos.pos().getX(), globalPos.pos().getY(),globalPos.pos().getZ(), Set.of(),0,0);
                                        }));
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void updateTga(ItemStack stack){
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag == null) {
            stack.set(DataReg.tag,new CompoundTag());
        }
        if (compoundTag != null) {
            if (compoundTag.getInt(dieSizeKy) < 10) {
                compoundTag.putInt(dieSizeKy,compoundTag.getInt(dieSizeKy) + 1);
            }
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        float value = 0;
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            value = (compoundTag.getInt(dieSizeKy) + 1) / 20f;
            if (!player.getCooldowns().isOnCooldown(stack.getItem()) && player.tickCount > 10) {
                if (compoundTag.getInt(dieSizeKy) > 0) {
                    compoundTag.putInt(dieSizeKy, compoundTag.getInt(dieSizeKy) - 1);
                    player.getCooldowns().addCooldown(stack.getItem(),200);
                }
            }
        }

        modifiers.put(AttReg.heal, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DefyLife_.asItem().getDescriptionId()),
                value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DefyLife_.asItem().getDescriptionId()),
                value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DefyLife_.asItem().getDescriptionId()),
                value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DefyLife_.asItem().getDescriptionId()),
                value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }

    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.add(Component.translatable("item.chest_item.defy_life.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.add(Component.translatable("item.chest_item.defy_life.string.2").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.add(Component.translatable("item.chest_item.defy_life.string.3").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.add(Component.literal(""));
        if (stack.get(DataReg.tag) !=null) {
            if (stack.get(DataReg.tag).getBoolean(canUse)) {
                tooltipComponents.add(Component.translatable("item.chest_item.defy_life.string.4").withStyle(Style.EMPTY.withColor(color)));
            }
        }
    }
    public DefyLife(Properties properties) {
        super(properties);
    }
    @Override
    public int getSanity() {
        return -5;
    }
}
