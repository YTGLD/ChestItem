package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MassEnergyConverter extends ItemBone {
    public MassEnergyConverter(Properties properties) {
        super(properties);
    }
    @Override
    public GUILight guiLight() {
        int lightNumber = 3;
        Map<Integer,Integer> listGUIColor = new HashMap<>();

        //蓝色
        listGUIColor.put(0, Light.ARGB.color(100,80,120,255));
        listGUIColor.put(1, Light.ARGB.color(100,80,120,255));
        //绿色
        listGUIColor.put(2, Light.ARGB.color(100,120,255,60));


        Map<Integer, Vec2> listPosOffset = new HashMap<>();
        //蓝色
        listPosOffset.put(0, new Vec2(4,0));
        listPosOffset.put(1, new Vec2(-4,0));
        //绿色
        listPosOffset.put(2, new Vec2(0,-2));



        Map<Integer, ResourceLocation> listImg = new HashMap<>();
        for (int i = 0; i < lightNumber; i++) {
            ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
            listImg.put(i,identifier);
        }

        return new GUILight(listGUIColor,listPosOffset,listImg,true,lightNumber);
    }

    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MassEnergyConverter_)) {
                            float lv = player.getHealth() / player.getMaxHealth();
                            lv *= 100;
                            int now = (int) (100 -(lv));
                            float apply = 1 / 100f * now;
                            apply /= 3.33333F;

                            float s  = 1 -apply;
                            if (s > 1) {
                                s = 1;
                            }

                            event.setAmount(event.getAmount()*s);
                            break;
                        }
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MassEnergyConverter_)) {
                            float lv = player.getHealth() / player.getMaxHealth();
                            lv *= 100;
                            int now = (int) (100 -(lv));
                            float apply = 1 / 100f * now;
                            apply /= 2;

                            float s  = 1 + apply;


                            event.setAmount(event.getAmount()*s);
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
        tooltipAdder.add(Component.translatable("item.chest_item.mass_energy_converter.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.mass_energy_converter.string.2").withStyle(ChatFormatting.GOLD));
    }
}
