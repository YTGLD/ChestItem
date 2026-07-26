package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

import java.util.List;
import java.util.Random;

/**
 * 弥散机件
 * <p>
 * 增加%d%%的获取经验值
 * <p>
 * 战利品箱有%d%%的概率额外发现一个神秘金属板
 */
public class Diffusion extends ReinforcedBaseItem{
    public Diffusion(Properties properties) {
        super(properties,false);
    }

    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Reinforced";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Diffusion");
            intValue =  builder.translation("chest_item.config.Diffusion")
                    .defineInRange("number",0.2f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Diffusion2")
                    .defineInRange("number2",20,0,100);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Diffusion",
                            "弥散机件","额外经验值获取"),
                    new CIString("Diffusion2",
                            "弥散机件2","战利品里的神秘金属板的发现概率")
            );
        }
    }
    public static void event(PlayerXpEvent.XpChange event){
        if (event.getEntity() instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Diffusion_.asItem())) {
                float xp = ConfigItem.intValue.get().floatValue();
                event.setAmount((int) (event.getAmount() * (1 + xp)));
            }
        }
    }
    public static void addLoot(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext, Entity entity){
        int lvl = ConfigItem.intValue2.get();
        if (entity instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Diffusion_.asItem())) {
                RandomSource source = lootContext.getRandom();
                if (source.nextInt(100) <= lvl) {
                    objectArrayList.add(new ItemStack(InitItems.MAGIC_IRON.asItem(),1));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.chest_item.diffusion.string.0", ConfigItem.intValue.get().floatValue() * 100).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.add(Component.translatable("item.chest_item.diffusion.string.1", ConfigItem.intValue2.get().floatValue()).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int sanDown() {
        return -1;
    }
}
