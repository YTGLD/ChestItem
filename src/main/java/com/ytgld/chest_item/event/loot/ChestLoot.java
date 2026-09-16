package com.ytgld.chest_item.event.loot;


import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.chest_item.event.SoulBottleHandler;
import com.ytgld.chest_item.items.other.Battery;
import com.ytgld.chest_item.items.reinforced.items.Diffusion;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ChestLoot extends LootModifier {
    public static final Supplier<MapCodec<ChestLoot>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst,
                    ChestLoot::new)));

    protected ChestLoot(Optional<Holder<LootItemCondition>> condition, int priority) {
        super(condition, priority);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        @Nullable Entity entity = lootContext.getOptional(LootContextParams.THIS_ENTITY);
        Identifier s = lootContext.getQueriedLootTableId();
        String idSting = String.valueOf(s);
        if (idSting.contains("chests/")) {
            if (entity != null) {
                Diffusion.addLoot(objectArrayList,lootContext,entity);
                Battery.objectArrayList(objectArrayList, entity);
                SoulBottleHandler.loot(entity);
            }
        }
        return objectArrayList;
    }
    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }


}
