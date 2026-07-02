package com.ytgld.chest_item.items.reinforced;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReinforcedLoot extends LootModifier {
    public static final Supplier<MapCodec<ReinforcedLoot>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst,
                    ReinforcedLoot::new)));


    public static List<Item> lListItems = new ArrayList<>();

    /**
     * Constructs a LootModifier.
     *
     * @param conditions
     * @param priority
     */
    protected ReinforcedLoot(LootItemCondition[] conditions, int priority) {
        super(conditions, priority);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        @Nullable Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player) {
            if (ReinforcedBaseItem.getSanValue(player) != 0) {
                if (ReinforcedBaseItem.canLoot(player)) {
                    Identifier s = lootContext.getQueriedLootTableId();
                    String idSting = String.valueOf(s);
                    if (idSting.contains("chests/")) {
                        if (Mth.nextInt(lootContext.getRandom(), 0, 100) < Math.min(33, getLevel(player)) * 2) {
                            if (!lListItems.isEmpty()){
                                Item item = lListItems.get(new Random().nextInt(lListItems.size()));
                                objectArrayList.add(item.getDefaultInstance());
                            }
                        }
                    }
                }
            }
        }
        return objectArrayList;
    }
    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
    private int getLevel(Player player){
        return ReinforcedBaseItem.getSanValue(player);
    }
}
