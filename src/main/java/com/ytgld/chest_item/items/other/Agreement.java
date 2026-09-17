package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashSet;

public class Agreement extends ItemBase {
    public Agreement(Properties properties) {
        super(properties);
    }
    public static final String chestHasReactor= "ChestHasReactor";
    public static void onKeyIsDown(Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Agreement_)) {
                        CompoundTag compoundTag = stack.get(DataReg.tag);
                        if (compoundTag!=null) {
                            if (!compoundTag.getBooleanOr(chestHasReactor,false)) {
                                Reactor reactor = new Reactor(Entitys.Reactor_.get(), player.level());
                                reactor.setPos(player.position());
                                reactor.setOwner(player);
                                reactor.tame(player);
                                player.level().addFreshEntity(reactor);
                                compoundTag.putBoolean(chestHasReactor,true);
                                break;
                            }else {
                                compoundTag.putBoolean(chestHasReactor,false);
                            }
                        }else {
                            stack.set(DataReg.tag, new CompoundTag());
                        }
                    }
                }
            }
        }
    }

    @Override
    public int maxGiftNumber(ItemStack stack) {
        return 3;
    }

    @Override
    public HashSet<EvilGiftBase> canHasEvilGift() {
        HashSet<EvilGiftBase> evilGiftBases = new HashSet<>();
        evilGiftBases.add(EvilGifts.destruction.get());
        evilGiftBases.add(EvilGifts.calciner.get());
        return evilGiftBases;
    }

    @Override
    public void text(ItemStack stack, java.util.function.Consumer<Component> tooltipComponents, TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.agreement.string.1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.agreement.string.2").withStyle(ChatFormatting.GOLD));
    }
}

