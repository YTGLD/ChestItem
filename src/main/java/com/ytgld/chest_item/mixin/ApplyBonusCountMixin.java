package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.AttReg;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @ModifyVariable(method = "run", at = @At(value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ItemInstance;)I",
            ordinal = 0), index = 4)
    private int curios$applyEnchantBonus(int enchantmentLevel, ItemStack itemStack, LootContext context) {
        Entity entity = context.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player) {
            AttributeInstance s = player.getAttribute(AttReg.fortune);
            if (s !=null ){
                int level = (int) s.getValue();
                if (enchantment.is(Enchantments.FORTUNE)) {
                    return enchantmentLevel + level;
                }
            }
        }
        return enchantmentLevel;
    }
}