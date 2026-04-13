package com.ytgld.chest_item.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.other.AttributeDataType;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayer {
    @Unique
    protected final AtomicReference<ChestInventory> chest_item$chestInventory = new AtomicReference<>(new ChestInventory((Player) (Object) this));

    @Inject(method = "readAdditionalSaveData", at = @At(value = "RETURN"))
    private void readAdditionalSaveData(ValueInput p_422427_, CallbackInfo ci) {
        this.chest_item$chestInventory.get().fromSlots(p_422427_.listOrEmpty("ChestItems", ItemStackWithSlot.CODEC));

    }
    @Inject(method = "addAdditionalSaveData", at = @At(value = "RETURN"))
    private void addAdditionalSaveData(ValueOutput p_421801_, CallbackInfo ci) {
        this.chest_item$chestInventory.get().storeAsSlots(p_421801_.list("ChestItems", ItemStackWithSlot.CODEC));
    }
    @Unique
    private Map<ItemStack, Multimap<Holder<Attribute>, AttributeModifier>> cI1_21_11$attributeCache = new HashMap<>();
    @Unique
    private Map<Item, Multimap<Holder<Attribute>, AttributeModifier>> cI1_21_11$reinforceCache = new HashMap<>();

    @Unique
    private void cI1_21_11$updateAttribute() {
        Player player = (Player) (Object) this;
        ChestInventory inventory = Handler.getItem(player);
        if (inventory != null) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.getItem() instanceof ItemBase itemBase) {
                    Multimap<Holder<Attribute>, AttributeModifier> doAttribute = itemBase.doAttribute(stack, player);
                    AttributeDataType attributeDataType = stack.get(DataReg.attributeType);
                    if (attributeDataType!=null) {
                        for (AttributeDataType.Entry entry :attributeDataType.modifiers()){
                            doAttribute.put(entry.attribute(),entry.modifier());
                        }
                    }

                    cI1_21_11$attributeCache.getOrDefault(stack, HashMultimap.create()).forEach((attributeHolder, attributeModifier)->{
                        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
                        modifiers.put(attributeHolder,attributeModifier);
                        player.getAttributes().removeAttributeModifiers(modifiers);
                    });
                    player.getAttributes().addTransientAttributeModifiers(doAttribute);

                    cI1_21_11$attributeCache.put(stack, doAttribute);
                }
            }
        }
    }

    @Unique
    private void cI1_21_11$updateReinforceAttribute() {
        Player player = (Player) (Object) this;
        List<Item> items = ReinforcedBaseItem.getItems(player);
        for (int i = 0; i  < items.size() ; i++) {
            Item item = items.get(i);
            if (item instanceof ReinforcedBaseItem reinforcedBaseItem) {
                Multimap<Holder<Attribute>, AttributeModifier> doAttribute = reinforcedBaseItem.doAttribute(player);
                cI1_21_11$reinforceCache.getOrDefault(item, HashMultimap.create()).forEach((attributeHolder, attributeModifier)->{
                    Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
                    modifiers.put(attributeHolder,attributeModifier);
                    player.getAttributes().removeAttributeModifiers(modifiers);
                });
                player.getAttributes().addTransientAttributeModifiers(doAttribute);

                cI1_21_11$reinforceCache.put(item, doAttribute);
            }
        }
    }
    @Override
    public void cI1_21_11$onRemoveItem(ItemStack itemStack) {
        Player player = (Player) (Object) this;
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = cI1_21_11$attributeCache.remove(itemStack);
        if (attributeModifiers != null) {
            player.getAttributes().removeAttributeModifiers(attributeModifiers);
        }
    }

    @Override
    public void cI1_21_11$upDATA() {
        Player player = (Player) (Object) this;
        for (Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap : cI1_21_11$reinforceCache.values()){
            player.getAttributes().removeAttributeModifiers(attributeModifierMultimap);
        }
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void tick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        cI1_21_11$updateAttribute();
        cI1_21_11$updateReinforceAttribute();
        if (!((Player) (Object) this).level().isClientSide()) {
            if (player.isAlive()) {
                if (((Player) (Object) this).hasContainerOpen()) {
                    if (((Player) (Object) this).getData(AttReg.black_shadowAttachmentType) < 255) {
                        ((Player) (Object) this).setData(AttReg.black_shadowAttachmentType, ((Player) (Object) this).getData(AttReg.black_shadowAttachmentType) + 25);
                    }
                } else {
                    if (((Player) (Object) this).getData(AttReg.black_shadowAttachmentType) > 5) {
                        ((Player) (Object) this).setData(AttReg.black_shadowAttachmentType, ((Player) (Object) this).getData(AttReg.black_shadowAttachmentType) - 10);
                    } else {
                        ((Player) (Object) this).setData(AttReg.black_shadowAttachmentType, 0f);
                    }
                }
            }
        }
    }
    @Override
    public AtomicReference<ChestInventory> chest_item$chestInventory() {
        return chest_item$chestInventory;
    }
}
