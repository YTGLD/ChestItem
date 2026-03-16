package com.ytgld.chest_item.items.memory.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * 狐假虎威
 * <p>
 * 遮蔽天机，隐瞒大道的代价
 * <p>
 * 大量属性会随着生命值的提高而变高
 * <p>
 * 大量属性会随着生命值会随着生命值降低而边少
 *
 */
public class TheFox extends MemoryBase {
    public TheFox(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"the_fox_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.TheFoxTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.the_fox.string.1").withStyle(ChatFormatting.GRAY));
    }

    public static class  TheFoxTooltip extends BaseTooltip {
        public TheFoxTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,255,100,0);
        }
        @Override
        public void doText(ItemStack stack, List<Component> tooltipComponents) {
            tooltipComponents.add(Component.translatable("item.chest_item.the_fox_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.the_fox_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.the_fox_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void tick(EntityTickEvent event){
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:the_fox_tooltip")) {
                    player.getAttributes().addTransientAttributeModifiers(addHeartPain(player));
                } else {
                    player.getAttributes().removeAttributeModifiers(addHeartPain(player));
                }
            }
        }
        private static final ResourceLocation resourceLocation = ResourceLocation.parse(Chestitem.MODID + "the_fox_tooltip");

        public static Multimap<Holder<Attribute>, AttributeModifier> addHeartPain(Player player) {
            Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
            float lv = player.getHealth() / player.getMaxHealth();
            if (lv > 1) {
                lv = 1;
            }
            lv *= 100;
            float now = (int) (lv);
            if (now < 0) {
                now = 0;
            }
            now /= 100f;
            float damage = (now * 0.4f  * 2f)- 0.4f;
            float speed = (now * 0.35f  * 2f)- 0.35f;
            float attackSpeed = (now * 0.3f  * 2f)- 0.3f;
            float armor = (now * 0.25f  * 2f)- 0.25f;
            float heal = (now * 0.2f  * 2f)- 0.2f;

            float size = (now * 0.7f * 2f) - 0.7f;
            if (size > 0) {
                size = 0;
            }

            modifiers.put(Attributes.ARMOR, new AttributeModifier(resourceLocation,
                    armor, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(resourceLocation,
                    damage, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation,
                    speed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(resourceLocation,
                    attackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            modifiers.put(AttReg.heal, new AttributeModifier(resourceLocation,
                    heal, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            modifiers.put(Attributes.SCALE, new AttributeModifier(resourceLocation,
                    size, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

            return modifiers;
        }

    }

}

