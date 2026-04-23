package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IGUILight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *混沌构造器
 * <p>
 * <p>
 * 	连续受到伤害时逐渐提高抗性
 * <p>
 * 	每次提升1%，但不超过30%
 * <p>
 */
public class ChaosConstructor extends ItemBlackShadow  implements IGUILight {
    public static final String leadHurtSize = "leadHurtSize";
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("ChaosConstructor");
            intValue =  builder.translation("chest_item.config.ChaosConstructor")
                    .defineInRange("number",30,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.ChaosConstructor2")
                    .defineInRange("number2",20f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("ChaosConstructor",
                            "混沌构造器","受伤累计的最大抗性"),
                    new CIString("ChaosConstructor2",
                            "混沌构造器2","增加的侵蚀装甲")
            );
        }
    }

    public ChaosConstructor(Properties properties) {
        super(properties);
    }

    public static void hurtOfBlood(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.ChaosConstructor_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {

                                if (compoundTag.getInt(leadHurtSize) < ConfigItem.intValue.get().intValue()) {
                                    compoundTag.putInt(leadHurtSize,compoundTag.getInt(leadHurtSize)+1);
                                }
                                float s =((float)(compoundTag.getInt(leadHurtSize))*0.01f);

                                event.setNewDamage(event.getNewDamage()*(1-s));

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

    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.ChaosConstructor_)) {
                    CompoundTag compoundTag = stack.get(DataReg.tag);
                    if (compoundTag != null) {
                        if (player.tickCount%40==1) {
                            if (compoundTag.getInt(leadHurtSize) > 0) {
                                compoundTag.putInt(leadHurtSize, compoundTag.getInt(leadHurtSize) - 1);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.chaos_armor, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ChaosConstructor_.asItem().getDescriptionId()),
                ConfigItem.intValue2.get().floatValue(), AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.chaos_armor_damage, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ChaosConstructor_.asItem().getDescriptionId()),
                0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        if (flag.hasShiftDown()) {
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80ff5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.8",ConfigItem.intValue.getAsInt()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80ff5ACD))));
        }else {
            tooltipAdder.add(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
            tooltipAdder.add(Component.literal(""));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.1").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.2").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.3").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.4").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.5").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.chaos_constructor.string.6").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }

    @Override
    public int guiColor(ItemStack stack) {
        return 0;
    }

    @Override
    public Vec2 posOffset() {
        return new Vec2(0,0);
    }
}
