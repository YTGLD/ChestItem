package com.ytgld.chest_item.items.black.chaos_item;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.Tombstone;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.SkillItem;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.client_utils.AddUtil;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class BurialDemonStet extends ItemBlackShadow implements SkillItem {
    public BurialDemonStet(Properties properties) {
        super(properties);
    }
    public static void event(Player player){
        if (!Handler.has(player, InitItems.BurialDemonStet_.asItem())) {
            return;
        }
        if (player.getCooldowns().isOnCooldown(InitItems.BurialDemonStet_.asItem().getDefaultInstance())) {
            return;
        }
        int count = 3;
        double radius = 8;

        for (int i = 0; i < count; i++) {
            double angle = 2.0 * Math.PI * i / count;

            double x = player.getX() + radius * Math.cos(angle);
            double z = player.getZ() + radius * Math.sin(angle);

            Tombstone tombstone = new Tombstone(
                    Entitys.Tombstone_.get(),
                    player.level()
            );
            tombstone.setPos(x, player.getY() + 12, z);
            tombstone.setOwner(player);
            tombstone.tame(player);


            AddUtil.addWrathHeaven(player,new Vec3(x,player.getY() + 0.1f,z),
                    Light.ARGB.color(255,255,50,255),12,false);

            player.level().addFreshEntity(tombstone);
        }
        player.level().playSound(null,player.blockPosition(), Sounds.bass.value(), SoundSource.MASTER,1,0.8f);
        player.level().playSound(null,player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.MASTER,0.5F,0.25F);

        player.getCooldowns().addCooldown(InitItems.BurialDemonStet_.asItem().getDefaultInstance(),600);
    }
    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.burial_demon_stet.string.1").withStyle(Style.EMPTY.withColor(color(stack))));
    }
}

