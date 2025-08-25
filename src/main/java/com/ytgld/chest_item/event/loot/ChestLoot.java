package com.ytgld.chest_item.event.loot;


import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.chest_item.items.other.Battery;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ChestLoot extends LootModifier {
    public static final Supplier<MapCodec<ChestLoot>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst,
                    ChestLoot::new)));

    public ChestLoot(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        Entity entity = lootContext.getParameter(LootContextParams.THIS_ENTITY);

        Battery.objectArrayList(objectArrayList,entity);
        return objectArrayList;
    }
    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }


}
