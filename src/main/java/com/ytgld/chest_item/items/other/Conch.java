package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class Conch  extends ItemBase {
    public Conch(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Conch");
            intValue =  builder.translation("chest_item.config.Conch")
                    .defineInRange("number",4,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Conch2")
                    .defineInRange("number2",0.5f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Conch",
                            "闪电海螺","攻击半径"),
                    new CIString("Conch2",
                            "闪电海螺2","连锁伤害")
            );
        }
    }
    public static void event(ItemStackAttackEvent event){
        LivingIncomingDamageEvent livingIncomingDamageEvent = event.event;
        Player player = event.player;
        LivingEntity target = livingIncomingDamageEvent.getEntity();
        ChestInventory chestInventory = event.chestInventory;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Conch_)) {
                    Vec3 playerPos = target.position().add(0, 0.75, 0);
                    int range = ConfigItem.intValue.get().intValue();
                    List<LivingEntity> entities = target.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                    for (LivingEntity living : entities) {
                        if (!(living instanceof Player)) {
                            if (!living.is(target)) {
                                living.hurt(living.damageSources().magic(), livingIncomingDamageEvent.getAmount() * ConfigItem.intValue2.get().floatValue());
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.conch.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.conch.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,122,122,255);
    }
}
