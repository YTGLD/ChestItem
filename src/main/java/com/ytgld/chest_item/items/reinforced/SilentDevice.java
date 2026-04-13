package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Set;
import java.util.function.Consumer;

public class SilentDevice extends EvilMother {

    public SilentDevice(Properties properties) {
        super(properties);
    }
    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.silent_device.string.0").withStyle(Style.EMPTY.withColor(colorBlack().color())));
    }
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        if (!strings.isEmpty()) {
            for (String itemName : strings) {
                if (strings.remove(itemName)) {
                    String[] parts = itemName.split(":");
                    Item item = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(parts[0], parts[1]));
                    ItemEntity entity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), item.getDefaultInstance());
                    player.level().addFreshEntity(entity);
                    Handler.upDATA(player);
                    break;
                }
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public int getSanity() {
        return 6;
    }
}
