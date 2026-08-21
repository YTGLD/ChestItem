package com.ytgld.chest_item.items.evil_mother.soul;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.crafting.SetSoulData;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ToolTipSpiritItem;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SoulBottle extends EvilMother {
    public SoulBottle(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public int getSanity() {
        return 0;
    }

    public static void addSoul(Player player, Item spiritItem) {
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.SoulBottle_.asItem())) {
                    if (stack.get(DataReg.soulMap) == null) {
                        stack.set(DataReg.soulMap, new SetSoulData(new HashMap<>()));
                    }
                    String name = BuiltInRegistries.ITEM.getKey(spiritItem).toString();
                    SetSoulData setSoulData = stack.get(DataReg.soulMap);
                    if (setSoulData != null) {
                        Integer integer = setSoulData.soulMap().get(name);
                        if (integer == null) {
                            integer = 0;
                        }
                        setSoulData.soulMap().put(name, integer + 1);
                    }
                }
            }
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        SetSoulData setSoulData = itemStack.get(DataReg.soulMap);
        if (setSoulData == null) {
            return Optional.empty();
        }
        return Optional.of(new ToolTipSpiritItem(setSoulData, itemStack));
    }
}
