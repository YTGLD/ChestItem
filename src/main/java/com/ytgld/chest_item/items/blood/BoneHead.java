package com.ytgld.chest_item.items.blood;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class BoneHead extends ItemBase implements SkillList{

    public BoneHead(Properties properties) {
        super(properties);
    }
    public static void event(LivingEntityUseItemEvent.Start event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Bone_Head)) {
                            if (event.getItem().getUseAnimation() == ItemUseAnimation.EAT){
                                event.setDuration((int) (event.getDuration()*0.66f));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.bone_head.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.bone_head.string.1").withStyle(ChatFormatting.GOLD));
    }

    @Nullable
    @Override
    public Map<SkillBase, ResourceLocation> name() {
        Map<SkillBase, ResourceLocation> map = new HashMap<>();
        map.put(pDecisively, SkillList.pDecisively.baneImage());
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pDecisively,Component.translatable("item.chest_item.skill."+pDecisively.baneName()));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();
        SkillBase.getElementMap(stack,map,pDecisively);

        return map;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,100,185,185);
    }
}
