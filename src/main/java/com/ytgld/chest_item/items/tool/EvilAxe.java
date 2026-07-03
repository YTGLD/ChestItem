package com.ytgld.chest_item.items.tool;

import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.EvilMotherSpirit;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class EvilAxe extends AxeItem implements IEvil {
    public EvilAxe(Properties properties) {
        super(new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,8000,
                11,
                5,
                30,
                ItemTags.AXES),11,-3.55f,properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept(Component.translatable("item.chest_item.evil_axe.string.1").withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 32;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        entity.level().playSound(entity,entity.getX(),entity.getY(),entity.getZ(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS,1,1);
        for (int i = 0; i < 5; i++) {
            EvilMotherSpirit spirit = new EvilMotherSpirit(Entitys.EvilMotherSpirit_.get(), entity.level());
            spirit.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
            spirit.setOwner(entity);
            Vec3 lookVec = entity.getLookAngle();
            double spread = 0.1;
            double offsetX = (Math.random() - 0.5) * spread;
            double offsetY = (Math.random() - 0.5) * spread;
            double offsetZ = (Math.random() - 0.5) * spread;

            Vec3 velocity = lookVec.add(offsetX, offsetY, offsetZ).normalize().scale(2.2);
            spirit.setDeltaMovement(velocity);
            entity.level().addFreshEntity(spirit);
        }
        return itemStack;
    }
    @Override
    public Component getName(ItemStack itemStack) {
        Component component = super.getName(itemStack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));
        return co;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return color;
    }
}
