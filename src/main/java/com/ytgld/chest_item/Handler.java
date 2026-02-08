package com.ytgld.chest_item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.other.*;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Handler {
    public static boolean has(Player player, Item item){
        ChestInventory chestInventory= getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack= chestInventory.getItem(i);
                if (stack.is(item)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void openItemChest(Player player){
        if (player instanceof IPlayer iPlayer) {
            player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.CHEST_OPEN, SoundSource.AMBIENT,1,1);
            player.openMenu(new SimpleMenuProvider(
                    (i, inventory, p_53126_) -> new ChestItemMenu(i,inventory,
                            iPlayer.chest_item$chestInventory().get(),2), Component.translatable("container.chest_item.chest")
            ));

        }
    }
    public static @Nullable ChestInventory getItem(Player player){
        if (player instanceof IPlayer iPlayer) {
            return iPlayer.chest_item$chestInventory().get();
        }
        return null;
    }
    public static boolean chestEntity(LivingEntity living, Entity owner){
        if (living != null){
            if (living instanceof OwnableEntity entity) {
                if (entity.getOwner() != null && owner != null) {
                    if (entity.getOwner().is(owner)){
                        return false;
                    }
                }
            }
            Identifier entity = BuiltInRegistries.ENTITY_TYPE.getKey(living.getType());
            if (entity.getNamespace().equals(Chestitem.MODID)) {
                return false;
            }
        }
        return  true;
    }
    public static int isBlackAddPower(ItemStack stack,int add ){
        if (isBlackChaos(stack)){
            return add ;
        }
        return 1;
    }
    public static int blackLevel(ItemStack stack,int add ,int def){
        if (isBlackChaos(stack)){
            return add + def ;
        }
        return def;
    }
    public static boolean isBlackChaos(ItemStack stack){
        CompoundTag tag = stack.get(DataReg.tag);
        if (tag != null) {
            return tag.getBooleanOr(IBlackLight.blackName, false);
        }
        return false;
    }







    public static void renderBlood(PoseStack.Pose poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, float a, float r) {
        int segmentCount = 16; // 圆柱横向细分数

        for (int i = 0; i < segmentCount; i++) {
            double angle1 = (2 * Math.PI * i) / segmentCount;
            double angle2 = (2 * Math.PI * (i + 1)) / segmentCount;

            double x1 = Math.cos(angle1) * r;
            double z1 = Math.sin(angle1) * r;
            double x2 = Math.cos(angle2) * r;
            double z2 = Math.sin(angle2) * r;

            Vec3 up1 = start.add(x1, 0, z1);
            Vec3 up2 = start.add(x2, 0, z2);
            Vec3 down1 = end.add(x1, 0, z1);
            Vec3 down2 = end.add(x2, 0, z2);


            addSquare(vertexConsumer, poseStack, up1, up2, down1, down2, a);
        }
    }


    private static void addSquare(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 up1, Vec3 up2, Vec3 down1, Vec3 down2, float alpha) {
        // 添加四个顶点来绘制一个矩形
        vertexConsumer.addVertex(poseStack, (float) up1.x, (float) up1.y, (float) up1.z)
                .setColor(100, 100, 255, (int) (alpha * 255))
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(100, 100, 255, (int) (alpha * 255))
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(100, 100, 255, (int) (alpha * 255))
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(100, 100, 255, (int) (alpha * 255))
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);
    }

}
