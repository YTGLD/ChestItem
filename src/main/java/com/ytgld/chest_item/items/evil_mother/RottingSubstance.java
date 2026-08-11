package com.ytgld.chest_item.items.evil_mother;


import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 腐烂物质
 * <p>
 * 舍弃所有的护盾来换取纯粹的抗性与伤害
 */
public class RottingSubstance extends EvilMother{
    public RottingSubstance(Properties properties) {
        super(properties);
    }
    public static boolean notHeal(Player player){
        return !Handler.has(player, InitItems.RottingSubstance_.asItem());
    }
    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        player.setData(AttReg.chaosWinds,0f);
        player.setData(AttReg.hyperplasiaATTACHMENT_TYPES,0f);
        player.setData(AttReg.black_shadowAttachmentType,0f);
        player.setData(AttReg.painShield,0f);
    }

    @Override
    public int getSanity() {
        return -3;
    }
}
