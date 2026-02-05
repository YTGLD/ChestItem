package com.ytgld.chest_item.items.black.soul;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * 异变
 * <p>
 * <p>
 * “传说中的人族也不过如此.....”
 * <p>
 *  每少穿着一件装备护甲增加4点
 * <p>
 *  若没有穿着装备，则抗性提高10%
 * <p>
 *  减少30%受到的爆炸和灼烧伤害
 * <p>
 * <p>
 *  “种下奴印，永生永世效忠我们异魔一族...”
 * <p>
 *  身着装备自行瓦解
 * <p>
 *  每穿着一件装备受到的伤害都会增加
 * <p>
 *  受到的魔法伤害增加100%
 */
public class Mutation extends TheImprintOfTheSoul {
    public Mutation(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        float armor = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()){
            armor += 4;
        }
        if (player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()){
            armor += 4;
        }
        if (player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()){
            armor += 4;
        }
        if (player.getItemBySlot(EquipmentSlot.FEET).isEmpty()){
            armor += 4;
        }

        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Mutation_.asItem().getDescriptionId()),
                armor, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
    public static void  attrib(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (player!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Mutation_)) {
                        if (player.getItemBySlot(EquipmentSlot.HEAD).getMaxDamage() != 0) {
                            player.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(1,player,EquipmentSlot.HEAD);
                        }
                        if (player.getItemBySlot(EquipmentSlot.CHEST).getMaxDamage() != 0) {
                            player.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak(1,player,EquipmentSlot.CHEST);
                        }
                        if (player.getItemBySlot(EquipmentSlot.LEGS).getMaxDamage() != 0) {
                            player.getItemBySlot(EquipmentSlot.LEGS).hurtAndBreak(1,player,EquipmentSlot.LEGS);
                        }
                        if (player.getItemBySlot(EquipmentSlot.FEET).getMaxDamage() != 0) {
                            player.getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1,player,EquipmentSlot.FEET);
                        }
                        break;
                    }
                }
            }
        }
    }
    public static void notMagicDamage(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Mutation_)) {
                            float DAMAGE = 0;
                            if (!player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()){
                                DAMAGE += 0.25F;
                            }
                            if (!player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()){
                                DAMAGE += 0.25F;
                            }
                            if (!player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()){
                                DAMAGE += 0.25F;
                            }
                            if (!player.getItemBySlot(EquipmentSlot.FEET).isEmpty()){
                                DAMAGE += 0.25F;
                            }
                            float magic = 0;
                            if (event.getSource().is(DamageTypes.MAGIC)) {
                                magic+=1;
                            }



                            event.setAmount(event.getAmount()*(1+DAMAGE+magic));

                            if (notEq(player)) {
                                event.setAmount(event.getAmount()*0.9f);
                            }
                            if (event.getSource().is(DamageTypes.IN_FIRE)||
                                    event.getSource().is(DamageTypes.ON_FIRE)||
                                    event.getSource().is(DamageTypes.CAMPFIRE)||
                                    event.getSource().is(DamageTypes.LAVA)||
                                    event.getSource().is(DamageTypes.EXPLOSION)||
                                    event.getSource().is(DamageTypes.PLAYER_EXPLOSION)) {
                                event.setAmount(event.getAmount()*0.7f);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }


    public static boolean notEq(Player player){
        if (player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()){
            if (player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()){
                if (player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()){
                    if (player.getItemBySlot(EquipmentSlot.FEET).isEmpty()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        if (stack.get(DataReg.tag)==null){
            tooltipAdder.accept(Component.translatable("chest_item.the_soul.give").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("chest_item.the_soul.give.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.literal(""));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.9").withStyle(ChatFormatting.GOLD));

        }else {
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.literal(""));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))).withStyle(ChatFormatting.ITALIC));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
            tooltipAdder.accept(Component.translatable("item.chest_item.mutation.string.8").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
        }
    }

    public static void die(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (event.getEntity() instanceof WitherBoss) {
                    give(chestInventory, player);
                }
            }
        }
    }
    public static void give(ChestInventory chestInventory,Player player){
        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            ItemStack stack = chestInventory.getItem(i);
            if (stack.is(InitItems.Mutation_)) {
                return;
            }
        }
        List<Integer> list = new ArrayList<>();
        boolean isCoin = false;
        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            if (chestInventory.getItem(i).isEmpty()){
                list.add(1);
            }
            if (chestInventory.getItem(i).is(InitItems.DevilCoins_)){
                isCoin = true;
            }
        }
        int o = 0;
        for (int ignored : list){
            o++;
        }

        if (notEq(player)&&o>=8&&isCoin) {
            ChestInventory inventory = Handler.getItem(player);
            if (inventory != null) {
                ItemStack itemStack10 = inventory.getItem(9);
                ItemStack itemStack11 = inventory.getItem(10);
                ItemStack itemStack12 = inventory.getItem(11);

                ItemStack soul = new ItemStack(InitItems.Mutation_.asItem());
                if (soul.get(DataReg.tag) == null) {
                    soul.set(DataReg.tag,new CompoundTag());
                }

                if (itemStack10.isEmpty()) {
                    inventory.setItem(9,soul);
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    return;
                }
                if (itemStack11.isEmpty()){
                    inventory.setItem(10,soul);
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    return;
                }
                if (itemStack12.isEmpty()) {
                    inventory.setItem(11,soul);
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    return;
                }

            }
        }
    }
    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/mutation.png");
    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(255,100,255,100);
    }
}
