package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SelfIncreasingHeart extends ItemBase implements Meat ,SkillList{
    public SelfIncreasingHeart(Properties properties) {
        super(properties);
    }

    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("SelfIncreasingHeart");
            intValue =  builder.translation("chest_item.config.SelfIncreasingHeart")
                    .defineInRange("number",9,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.SelfIncreasingHeart2")
                    .defineInRange("number2",0.2f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("SelfIncreasingHeart",
                            "自增心脏","最大BUFF等级"),
                    new CIString("SelfIncreasingHeart2",
                            "自增心脏2","减少的生命值数值")
            );
        }
    }
    public static void tick(LivingEntityUseItemEvent.Finish event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Self_Increasing_Heart)) {
                            if (event.getItem().getUseAnimation() == ItemUseAnimation.EAT) {
                                player.addEffect(new MobEffectInstance(Effects.IncreasingMeat_,1800,0));
                                @Nullable MobEffectInstance mobEffectInstance = player.getEffect(Effects.IncreasingMeat_);
                                if (mobEffectInstance != null) {
                                    if (mobEffectInstance.getAmplifier()<ConfigItem.intValue.getAsInt()) {
                                        player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),
                                                mobEffectInstance.getDuration()+1800,
                                                mobEffectInstance.getAmplifier() + 1,
                                                false,false));
                                    }else {
                                        player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),18000,
                                                ConfigItem.intValue.getAsInt() + 1,false,false));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Self_Increasing_Heart.asItem().getDescriptionId()),
                -ConfigItem.intValue2.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.self_increasing_heart.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.self_increasing_heart.string.1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.self_increasing_heart.string.2").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.self_increasing_heart.string.3").withStyle(ChatFormatting.GOLD));

    }
    @org.jetbrains.annotations.Nullable
    @Override
    public Map<SkillBase, Identifier> name() {
        Map<SkillBase, Identifier> map = new HashMap<>();
        map.put(pHyperplasia, SkillList.pHyperplasia.baneImage());
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pHyperplasia,Component.translatable("item.chest_item.skill."+pHyperplasia.baneName()));
        return map;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();
        SkillBase.getElementMap(stack,map,pHyperplasia);

        return map;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}



