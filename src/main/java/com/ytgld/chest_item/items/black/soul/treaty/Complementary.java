package com.ytgld.chest_item.items.black.soul.treaty;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 互补（条约）
 * <p>
 * <p>
 * 	若造成的实际伤害低于面板的80%
 * <p>
 * 	则强制修正为100%
 * <p>
 * <p>
 * 	如果实际伤害大于面板伤害的100%
 * <p>
 * 	则减少至面板的80%
 */
public class Complementary extends TheImprintOfTheSoul {
    public Complementary(Properties properties) {
        super(properties);
    }

    public static void damage(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Complementary_.asItem())) {
                float s = event.getNewDamage();
                float attrib = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                if (s < attrib * 0.8f) {
                    event.setNewDamage(attrib);
                }else {
                    event.setNewDamage(attrib * 0.8f);
                }
            }
        }
    }

    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.complementary.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.complementary.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.complementary.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.complementary.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));

    }

    @Override
    public boolean canRemove(ItemStack stack) {
        return true;
    }
    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/complementary.png");
    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(255,200,100,200);
    }
}
