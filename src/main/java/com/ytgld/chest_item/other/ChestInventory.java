package com.ytgld.chest_item.other;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChestInventory extends SimpleContainer{

    public ChestInventory(Player player) {
        super(12);
    }

    public void fromTag(ListTag tag, HolderLookup.Provider levelRegistry) {
        int k;
        for(k = 0; k < this.getContainerSize(); ++k) {
            this.setItem(k, ItemStack.EMPTY);
        }

        for(k = 0; k < tag.size(); ++k) {
            CompoundTag compoundtag = tag.getCompound(k);
            int j = compoundtag.getByte("Slot") & 255;
            if (j < this.getContainerSize()) {
                this.setItem(j, ItemStack.parse(levelRegistry, compoundtag).orElse(ItemStack.EMPTY));
            }
        }

    }

    public @NotNull ListTag createTag(HolderLookup.Provider levelRegistry) {
        ListTag listtag = new ListTag();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                listtag.add(itemstack.save(levelRegistry, compoundtag));
            }
        }

        return listtag;
    }

    public void stopOpen(Player player) {
        player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.CHEST_CLOSE, SoundSource.AMBIENT,1,1);
        super.stopOpen(player);
    }
}

