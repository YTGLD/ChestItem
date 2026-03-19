package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlienationDiodes extends ItemBone {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("AlienationDiodes");
            intValue =  builder.translation("chest_item.config.AlienationDiodes")
                    .defineInRange("number",1.1f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.AlienationDiodes2")
                    .defineInRange("number2",1f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("AlienationDiodes",
                            "异化二极管","暴击伤害"),
                    new CIString("AlienationDiodes2",
                            "异化二极管2","暴击伤害造成的治疗")
            );
        }
    }
    public AlienationDiodes(Properties properties) {
        super(properties);
    }
    @Override
    public GUILight guiLight() {
        int lightNumber = 4;
        Map<Integer,Integer> listGUIColor = new HashMap<>();

        //蓝色
        listGUIColor.put(0, Light.ARGB.color(125,80,120,255));
        listGUIColor.put(1, Light.ARGB.color(125,80,120,255));
        //绿色
        listGUIColor.put(2, Light.ARGB.color(125,120,255,60));
        listGUIColor.put(3, Light.ARGB.color(125,120,255,60));

        Map<Integer, Vec2> listPosOffset = new HashMap<>();

        //蓝色
        listPosOffset.put(0, new Vec2(0,-5));
        listPosOffset.put(1, new Vec2(5,0));
        //绿色
        listPosOffset.put(2, new Vec2(0,5));
        listPosOffset.put(3, new Vec2(-5,0));



        Map<Integer, ResourceLocation> listImg = new HashMap<>();

        for (int i = 0; i < lightNumber; i++) {
            ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
            listImg.put(i,identifier);
        }

        return new GUILight(listGUIColor,listPosOffset,listImg,true,lightNumber);
    }
    public static void CriticalHitEvent(CriticalHitEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.AlienationDiodes_)) {
                            event.setDamageMultiplier(event.getDamageMultiplier()*ConfigItem.intValue.get().floatValue());
                            player.heal(ConfigItem.intValue2.get().floatValue());
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.alienation_diodes.string.1",ConfigItem.intValue.get().floatValue() * 100 - 100).withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.alienation_diodes.string.2").withStyle(ChatFormatting.GOLD));
    }

}
