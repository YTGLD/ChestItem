package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.memory.items.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class MemoryEvent {
    @SubscribeEvent
    public void LivingChangeTargetEvent(LivingChangeTargetEvent event) {
        Peace.PeaceTooltip.LivingChangeTargetEvent(event);
    }
    @SubscribeEvent
    public  void tick(EntityTickEvent.Post event){
        ForeverCurtain.ForeverCurtainTooltip.tick(event);
        TheFox.TheFoxTooltip.tick(event);
    }
    @SubscribeEvent
    public  void tick(ItemStackTickEvent event){
        Protest.ProtestTooltip.protestTooltipHurtAndEffect(event);
        Protest.ProtestTooltip.tick(event);
        Crave.CraveTooltip.craveCauseFood(event);
    }
    @SubscribeEvent
    public void Finish(LivingEntityUseItemEvent.Finish event){
        Crave.CraveTooltip.craveCauseFood(event);
    }
    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre event){
        Bluster.BlusterTooltip.damage(event);
        Martyrdom.MartyrdomTooltip.boom(event);
        Peace.PeaceTooltip.damagePre(event);
        War.WarTooltip.damagePre(event);
    }
    @SubscribeEvent
    public void LivingDamageEvent(LivingIncomingDamageEvent event){
        Contradiction.ContradictionTooltip.damageRes(event);
    }
    @SubscribeEvent
    public void LivingExperienceDropEvent(LivingExperienceDropEvent event) {
        Extreme.ExtremeTooltip.expDrop(event);
    }
}
