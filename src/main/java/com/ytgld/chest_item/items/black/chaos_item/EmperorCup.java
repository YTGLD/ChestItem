package com.ytgld.chest_item.items.black.chaos_item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * 帝王槃
 * <p>
 * 将受到的伤害转移到此物当中
 * <p>
 * 最多可容纳%d点伤害
 * <p>
 * 若达到上限则将伤害平均分配给玩家和附近的实体
 * <p>
 * 并给予玩家%d秒无敌
 * <p>
 * 此物可随时间减少内部寄存伤害的数量
 * <p>
 * 在无敌帧期间无法受到治疗
 * <p>
 * 四种护盾将陷入混沌中而无法使用
 */
public class EmperorCup extends ItemBlackShadow implements ITheChaos {
    public EmperorCup(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "BlackShadow";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("EmperorCup");
            intValue =  builder.translation("chest_item.config.EmperorCup")
                    .defineInRange("number",50f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.EmperorCup2")
                    .defineInRange("number2",5f,0,Integer.MAX_VALUE);
            intValue3 =  builder.translation("chest_item.config.EmperorCup3")
                    .defineInRange("number3",20f,1,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("EmperorCup",
                            "帝王槃","帝王槃可储存的最大伤害数"),
                    new CIString("EmperorCup2",
                            "帝王槃2","帝王槃给予的玩家无敌时间"),
                    new CIString("EmperorCup3",
                            "帝王槃3","每隔多少游戏刻减少2%内部储存伤害")
            );
        }
    }

    public static final String theHurtNumber = "EmperorCupHurtNumber";
    public static final String doHurtDamageTag = "EmperorCupDoHurtDamageTag";

    /**
     * 将受到的伤害转移到此物当中
     * <p>
     * 最多可容纳%d点伤害
     */
    public static void event(LivingHealEvent event){
        LivingEntity living = event.getEntity();
        if (!(living instanceof Player player)) {
            return;
        }
        if (player.level().isClientSide()) {
            return;
        }
        if (Handler.has(player,InitItems.EmperorCup_.asItem())) {
            if (player.invulnerableTime > 0) {
                event.setAmount(0);
                event.setCanceled(true);
            }
        }
    }
    public static void event(LivingDamageEvent.Pre event){
        LivingEntity living = event.getEntity();
        if (!(living instanceof Player player)) {
            return;
        }
        if (player.level().isClientSide()) {
            return;
        }
        if (event.getSource().is(DamageTypes.GENERIC_KILL)) {
            return;
        }
        if (Handler.has(player,InitItems.EmperorCup_.asItem())){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.EmperorCup_)) {
                        CompoundTag component = stack.get(DataReg.tag);
                        if (component == null) {
                            stack.set(DataReg.tag,new CompoundTag());
                        }
                        if (component != null) {
                            float max = ConfigItem.intValue.get().floatValue();
                            float theEmperorCupDamage = component.getFloatOr(theHurtNumber,0);
                            float dmg = event.getNewDamage();


                            float mixin = theEmperorCupDamage + dmg;

                            if (mixin > max) {
                                mixin = max;
                            }
                            component.putFloat(theHurtNumber,mixin);
                            if (theEmperorCupDamage >= max) {
                                setCanHurt(stack,true);
                                return;
                            }
                            event.setNewDamage(0);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**若达到上限则将伤害平均分配给玩家和附近的实体
     * <p>
     * 并给予玩家%d秒无敌
     */
    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        if (player.level().isClientSide()) {
            return;
        }
        if (isCanHurt(stack)) {
            Vec3 playerPos = player.getEyePosition();
            int range = 10;
            CompoundTag component = stack.get(DataReg.tag);
            if (component == null) {
                stack.set(DataReg.tag, new CompoundTag());
            }
            if (component != null) {
                float theEmperorCupDamage = component.getFloatOr(theHurtNumber, 0);

                //此物可随时间减少内部寄存伤害的数量
                int clearTime = ConfigItem.intValue3.get().intValue();
                if (clearTime < 1) {
                    clearTime = 1;
                }
                if (player.tickCount % clearTime == 1) {
                    component.putFloat(theHurtNumber, theEmperorCupDamage * 0.98f);
                }

                List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class,
                        new AABB(playerPos.x - range, playerPos.y - range,
                                playerPos.z - range, playerPos.x + range,
                                playerPos.y + range, playerPos.z + range));

                int invTime = (int) (ConfigItem.intValue2.get().floatValue() * 20);
                if (!entities.isEmpty()) {
                    int size = entities.size();
                    float damage = theEmperorCupDamage / size;
                    for (LivingEntity entity : entities) {
                        if (entity.is(player)) {
                            entity.hurt(entity.damageSources().genericKill(), damage);
                            player.invulnerableTime = invTime;
                        } else {
                            entity.hurt(entity.damageSources().playerAttack(player), damage);
                        }
                    }
                }
                setCanHurt(stack,false);
            }
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.hyperplasia, new AttributeModifier(identifier(),
                -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.shadow_shield, new AttributeModifier(identifier(),
                -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.chaos_armor, new AttributeModifier(identifier(),
                -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.painShield_number, new AttributeModifier(identifier(),
                -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return modifiers;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        int max = ConfigItem.intValue.get().intValue();
        int timeInv = ConfigItem.intValue2.get().intValue();
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.2",max).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.4",timeInv).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.emperor_cup.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));

    }

    private static boolean isCanHurt(ItemStack stack ){
        CompoundTag component = stack.get(DataReg.tag);
        if (component != null) {
            return component.getBooleanOr(doHurtDamageTag,false);
        }
        return false;
    }
    private static void setCanHurt(ItemStack stack ,boolean b){
        CompoundTag component = stack.get(DataReg.tag);
        if (component != null) {
            component.putBoolean(doHurtDamageTag,b);
            if (!b) {
                component.putFloat(theHurtNumber, 0);
            }
        }
    }
}
