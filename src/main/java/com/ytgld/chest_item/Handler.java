package com.ytgld.chest_item;

import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.reinforced.items.Excite;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.ChestItemMenu;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class Handler {
    public static void upDATA(Player player) {
        if (player instanceof IPlayer iPlayer) {
            iPlayer.cI1_21_11$upDATA();
        }
    }

    public static void addHeartShield(Player player, float number) {
        if (isInHeartShieldCooldown(player)) {
            return;
        }
        Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
        AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
        if (maxShield != null) {
            if (player.getData(supplier) <= maxShield.getValue()) {

                number = Excite.doINtHeal(number, player);

                player.setData(supplier, player.getData(supplier) + number);
            }
        }
    }

    public static boolean isInHeartShieldCooldown(LivingEntity living) {
        if (living instanceof Player player) {
            int cooldown = player.getData(AttReg.theHeartCooldown);
            return cooldown > 0;
        }
        return false;
    }

    public static boolean has(Player player, Item item) {
        ChestInventory chestInventory = getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void openItemChest(Player player) {
        if (player instanceof IPlayer iPlayer) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHEST_OPEN, SoundSource.AMBIENT, 1, 1);
            player.openMenu(new SimpleMenuProvider(
                    (i, inventory, p_53126_) -> new ChestItemMenu(i, inventory,
                            iPlayer.chest_item$chestInventory().get(), 2), Component.translatable("container.chest_item.chest")
            ));

        }
    }

    public static @Nullable ChestInventory getItem(Player player) {
        if (player instanceof IPlayer iPlayer) {
            return iPlayer.chest_item$chestInventory().get();
        }
        return null;
    }

    public static boolean chestEntity(LivingEntity living, Entity owner) {
        if (living != null) {
            if (living instanceof OwnableEntity entity) {
                if (entity.getOwner() != null && owner != null) {
                    if (entity.getOwner().is(owner)) {
                        return false;
                    }
                }
            }
            Identifier entity = BuiltInRegistries.ENTITY_TYPE.getKey(living.getType());
            if (entity.getNamespace().equals(Chestitem.MODID)) {
                return false;
            }
        }
        return true;
    }

    public static float isBlackAddPower(ItemStack stack, float add) {
        if (isBlackChaos(stack)) {
            return add;
        }
        return 1;
    }

    public static int blackLevel(ItemStack stack, int add, int def) {
        return def;
    }

    public static boolean isBlackChaos(ItemStack stack) {
        CompoundTag tag = stack.get(DataReg.tag);
        if (tag != null) {
            return tag.getBooleanOr(IBlackLight.blackName, false);
        }
        return false;
    }

}
