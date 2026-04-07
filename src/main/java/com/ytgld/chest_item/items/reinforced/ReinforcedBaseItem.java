package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReinforcedBaseItem extends Item implements IEvil {
    public ReinforcedBaseItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        addReinforcedItem(player);
        player.level().playSound(null,player.blockPosition(), SoundEvents.ARMOR_EQUIP_NETHERITE.value(), SoundSource.AMBIENT,1,1);
        return super.use(level, player, usedHand);
    }

    private void addReinforcedItem(Player player){
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        List<Integer> integers = new ArrayList<>();
        for (String ignored : strings){
            integers.add(1);
        }
        int  s = 0;
        for (Integer ignored : integers){
            s++;
        }
        if (s < max(player)){
            strings.add(nameSResourceLocation().toString());
        }
    }
    private int max(Player player){
        return (int) (player.getAttributeValue(ReinforcedAttreg.maxReinforced));
    }

    private ResourceLocation nameSResourceLocation(){
        return BuiltInRegistries.ITEM.getKey(this.name());
    }

    public static List<ItemStack> getItems(Player player) {
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        List<ItemStack> list = new ArrayList<>();
        for (String string : strings) {
            String[] parts = string.split(":");
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]));
            list.add(item.getDefaultInstance());
        }
        return list;
    }

    public Item name(){
        return this;
    };

    @Override
    public @NotNull Component getName(ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(color));
        return co;
    }
    public static boolean hasReinforcedItem(Player player ,String string){
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        return strings.contains(Chestitem.MODID + ":" + string);
    }
}
