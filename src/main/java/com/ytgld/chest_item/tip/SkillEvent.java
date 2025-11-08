package com.ytgld.chest_item.tip;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.elements.PlagueDivinePower;
import com.ytgld.chest_item.tip.an_element.elements.TerriblePotion;
import com.ytgld.chest_item.tip.an_element.elements.Therapeutic;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class SkillEvent {
    @SubscribeEvent
    public void LivingIncomingDamageEvent(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);

                        if (stack.getItem() instanceof SkillList) {
                            if (stack.get(DataReg.tag) == null) {
                                stack.set(DataReg.tag, new CompoundTag());
                            }
                        }

                        PlagueDivinePower.PlagueDivinePowerAttack(event,stack);
                    }
                }
            }
        }

    }
    @SubscribeEvent
    public void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);

                if (stack.getItem() instanceof SkillList) {
                    if (stack.get(DataReg.tag) == null) {
                        stack.set(DataReg.tag,new CompoundTag());
                    }
                    TerriblePotion.pTerriblePotion(stack, player);
                    Therapeutic.pPlagueSpores(stack, player);
                }
            }
        }
    }
}
