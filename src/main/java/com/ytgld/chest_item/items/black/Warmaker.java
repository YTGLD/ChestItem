package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 *战争缔造者
 * <p>
 * <p>
 * 	“用生灵的血液填满虚无之海”
 * <p>
 * 	“让混沌的莲花再次绽放......”
 * <p>
 * <p>
 * 	至多受到20%自身最大生命的伤害
 * <p>
 * 	造成伤害附加10%自身最大生命的伤害
 * <p>
 * 	若攻击的实际伤害低于面板的90%，则强制造成100%的面板伤害
 * <p>
 * <p>
 * 	杀死生物造成的超额伤害永久转换成额外生命
 * <p>
 * 	每杀死1个生物都会永久增加自身的生命上限，侵蚀装甲和装甲爆碎伤害
 * <p>
 * <p>
 * 	若超过10分钟未能击杀生物，则每秒强制扣除4点生命值，直到濒死
 * <p>
 * <p>
 * 	+30% 侵蚀装甲
 * <p>
 * 	+20% 装甲爆碎伤害
 * <p>
 * 	+10% 生命值
 * <p>
 * 	+10% 速度
 * <p>
 * 	+10% 伤害
 */
public class Warmaker extends ItemBlackShadow implements ITheChaos{

    public static final String healthStringFloat ="healthStringFloat";
    public static final String killIntString ="killIntString";
    public static final String notKillTimeInt ="notKillTimeInt";
    public static final int notKillTime =10*60;
    public static final String applyKillTimeBoolean ="applyKillTimeBoolean";

    public Warmaker(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF0000)));
        return co;
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                if (player.tickCount % 20 ==1) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Warmaker_.asItem())) {
                        CompoundTag tag = stack.get(DataReg.tag);
                        //若超过10分钟未能击杀生物，则每秒强制扣除4点生命值，直到濒死
                        if (tag != null) {
                            if (!tag.getBoolean(applyKillTimeBoolean)) {
                                tag.putInt(notKillTimeInt,notKillTime);
                                tag.putBoolean(applyKillTimeBoolean,true);
                            }
                            if (tag.getInt(notKillTimeInt) > 0) {
                                tag.putInt(notKillTimeInt, tag.getInt(notKillTimeInt) - 1);
                                break;
                            }else {
                                if (player.getHealth() > 10) {
                                    player.setHealth(player.getHealth() - 4);
                                    break;
                                }
                            }
                        } else {
                            stack.set(DataReg.tag, new CompoundTag());
                        }
                    }
                }
            }
        }
    }
    public static void die(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Warmaker_.asItem())) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Warmaker_.asItem())) {
                            CompoundTag tag  = stack.get(DataReg.tag);
                            //每杀死1个生物都会永久增加自身的生命上限，侵蚀装甲和装甲爆碎伤害
                            if (tag != null) {
                                tag.putFloat(killIntString,tag.getFloat(killIntString)+1);
                                tag.putInt(notKillTimeInt,notKillTime);
                                break;
                            }else {
                                stack.set(DataReg.tag,new CompoundTag());
                            }
                        }
                    }
                }
            }
        }
    }
    public static void hurt(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Warmaker_.asItem())) {
                //至多受到20%自身最大生命的伤害
                if (event.getNewDamage() > player.getMaxHealth() * 0.5F){
                    event.setNewDamage(player.getMaxHealth() * 0.5F);
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Warmaker_.asItem())) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Warmaker_.asItem())){
                            //杀死生物造成的超额伤害永久转换成额外生命
                            LivingEntity target = event.getEntity();
                            if (target.isDeadOrDying()) {
                                float end = event.getNewDamage();
                                CompoundTag tag  = stack.get(DataReg.tag);
                                if (tag != null) {
                                    tag.putFloat(healthStringFloat,tag.getFloat(healthStringFloat)+end);
                                    break;
                                }else {
                                    stack.set(DataReg.tag,new CompoundTag());
                                }
                            }
                        }
                    }
                }

                //若攻击的实际伤害低于面板的90%，则强制造成100%的面板伤害
                float att = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                if (event.getNewDamage() < att * 0.9f) {
                    event.setNewDamage(att);
                }
                //造成伤害附加10%自身最大生命的伤害
                float damage = player.getMaxHealth() * 0.1f;
                event.setNewDamage(event.getNewDamage() + damage);
            }
        }
    }
    public int colorText(){
        int s = (int) (100 * Math.sin(EventMain.time / 20f / 4f));
        int f = (int) (100 * Math.sin(EventMain.time / 40f / 4f));
        int c = (int) (20 * Math.sin(EventMain.time / 60f / 4f));
        if (c<0) {
            c = 0;
        }
        return Light.ARGB.color(230 + c,255, 55 + f/2, 105 + s);
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            int s = compoundTag.getInt(notKillTimeInt);
            tooltipAdder.add((Component.translatable("item.chest_item.warmaker.string.0",s)).withStyle(Style.EMPTY.withColor(Light.ARGB.color(255,255,0,0))));
            tooltipAdder.add(Component.literal(""));
        }
        if (!flag.hasShiftDown()) {
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
        }else {
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.5_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.literal(""));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.literal(""));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.8").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.8_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
//        tooltipAdder.add(Component.translatable("item.chest_item.warmaker.string.9").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap(stack);
    }

    @Override
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return attributeModifierMultimap(stack);
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.chaos_armor, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()),
                0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.chaos_armor_damage, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()),
                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        //每杀死1个生物都会永久增加自身的生命上限，侵蚀装甲和装甲爆碎伤害
        float health = 0;
        float chaosArmor = 0;
        float chaoDamage = 0;
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null){
            int size = compoundTag.getInt(killIntString);
            //4=2,9=3,16=4
            float sq = (float) Math.sqrt(size);


            chaoDamage += sq * 5;
            chaosArmor += sq;
            //杀死生物造成的超额伤害永久转换成额外生命
            float addHealth = compoundTag.getFloat(healthStringFloat);
            addHealth = (float) Math.sqrt(addHealth);
            if (addHealth > 20) {
                addHealth = 20;
            }
            health += (sq * 2) + addHealth;
        }

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()+"a"),
                health, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.chaos_armor, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()+"a"),
                chaosArmor, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.chaos_armor_damage, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.Warmaker_.asItem().getDescriptionId()+"a"),
                chaoDamage / 100F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }
}
