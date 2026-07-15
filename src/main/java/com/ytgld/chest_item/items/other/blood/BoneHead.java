package com.ytgld.chest_item.items.other.blood;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                            if (event.getItem().getUseAnimation() == UseAnim.EAT){
                                event.setDuration((int) (event.getDuration()*ConfigItem.doubleValue1.getAsDouble()));
                                break;
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
        tooltipAdder.add(Component.translatable("item.chest_item.bone_head.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.bone_head.string.1",ConfigItem.doubleValue1.getAsDouble()  * 100f).withStyle(ChatFormatting.GOLD));
    } @Nullable
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

    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Other";
        }
        public static ModConfigSpec.DoubleValue doubleValue1 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("BoneHead");
            doubleValue1 =  builder.translation("chest_item.config.BoneHead")
                    .defineInRange("eat",0.66,0,1);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of( new CIString("BoneHead",
                    "无厌之骸骨","额外的吃东西速度"));
        }

    }

}
