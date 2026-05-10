package com.ytgld.chest_item.items.memory.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 抗旨
 * <p>
 * 欲要攻击你的生物会受到缓慢和虚弱，若无法施加效果，则造成巨大伤害
 * <p>
 * 但大范围内的生物会主动攻击于你
 * <p>
 * 让无形的黑暗与仇恨笼罩道心
 * <p>
 * 举世为敌，但欲要攻击你的目标会持续受到削弱
 */
public class Protest extends MemoryBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Protest");
            intValue =  builder.translation("chest_item.config.Protest")
                    .defineInRange("number",20,0,100);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Protest",
                            "信仰：抗旨","检查半径")
            );
        }
    }
    public Protest(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"protest_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.ProtestTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
        tooltipComponents.accept(Component.translatable("item.chest_item.protest.string.1").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isHasActivated() {
        return true;
    }

    @Override
    public void doTextGive(ItemStack stack, Consumer<Component> tooltipComponents) {
        tooltipComponents.accept(Component.translatable("item.chest_item.protest.string.give").withStyle(ChatFormatting.GRAY));
    }
    public static class ProtestTooltip extends BaseTooltip {
        public ProtestTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,255,255,0);
        }
        @Override
        public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
            tooltipComponents.accept(Component.translatable("item.chest_item.protest_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.accept(Component.translatable("item.chest_item.protest_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.protest_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void tick(ItemStackTickEvent event){
            Player player = event.getPlayer();
            if (MemoryBase.isHasEnabled(player, "chest_item:protest_tooltip")) {
                if (player.level() instanceof ServerLevel serverLevel){
                    Raid serverLevelRaidAt  =serverLevel.getRaidAt(player.blockPosition());
                    if (serverLevelRaidAt != null && serverLevelRaidAt.isActive()) {
                        MemoryBase.addCounter(player,"chest_item:protest_tooltip",1);
                    }
                }
                if (MemoryBase.getCounter(player, "chest_item:protest_tooltip") >= 3) {
                    MemoryBase.addMemoryIt(player,"chest_item:protest_tooltip");
                }
            }
        }
        public static void protestTooltipHurtAndEffect(ItemStackTickEvent event){
            Player player = event.getPlayer();
            if (MemoryBase.hasMemory(player,"chest_item:protest_tooltip")){
                if (player.tickCount % 20 == 1){
                    Vec3 playerPos = player.position();
                    int range = ConfigItem.intValue.getAsInt();
                    List<Mob> list = player.level().getEntitiesOfClass(Mob.class, new AABB(
                            playerPos.x - range, playerPos.y - range,
                            playerPos.z - range, playerPos.x + range,
                            playerPos.y + range, playerPos.z + range)
                    );
                    for (Mob mob : list) {
                        if (mob.getTarget() == null || (mob.getTarget() instanceof LivingEntity entity && !entity.isAlive())) {
                            mob.setTarget(player);
                        }
                        if (mob.getTarget() instanceof Player me && me.is(player)){
                            if (!mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,1,false,false))){
                                mob.hurt(mob.damageSources().playerAttack(player),
                                        (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.33f);
                            }
                            if (!mob.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,200,2,false,false))){
                                mob.hurt(mob.damageSources().playerAttack(player),
                                        (float) player.getAttributeValue(Attributes.MAX_HEALTH) * 0.5f);
                            }
                        }
                    }
                }
            }
        }
    }
}







