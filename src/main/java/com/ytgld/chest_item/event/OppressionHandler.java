package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import javax.annotation.Nonnull;
import java.util.List;

public class OppressionHandler {

    public static void lightHurtEmp(PlayerInteractEvent.LeftClickEmpty event){
        Player player = event.getEntity();
        if (player.getAttributeValue(AttReg.oppression) > 0){
            ClientPacketDistributor.sendToServer(new UseOppression());
        }
    }


    public static void attack(Player player){
        if (player.getAttributeValue(AttReg.oppression) > 0) {
            if (getPlayerLookTarget(player.level(), player) instanceof LivingEntity living) {
                living.hurt(living.damageSources().playerAttack(player),(float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
        }
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                UseOppression.TYPE,
                UseOppression.STREAM_CODEC,
                UseOppressionHandler::handle
        );
    }
    public static class UseOppressionHandler {
        public static void handle(UseOppression payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                attack(player);
            });
        }
    }
    public record UseOppression() implements CustomPacketPayload {
        public static final Type<UseOppression> TYPE =
                new Type<>(Identifier.fromNamespaceAndPath(Chestitem.MODID, "oppression"));

        public static final StreamCodec<RegistryFriendlyByteBuf, UseOppression> STREAM_CODEC =
                StreamCodec.unit(new UseOppression());

        @Nonnull
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    public static void tickCanNotLooking(EntityTickEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (player.getAttributeValue(AttReg.oppression) > 0) {
                float oppressionAngle = (float) player.getAttributeValue(AttReg.oppression);
                Vec3 playerPos = player.position().add(0, 1, 0);
                int range = 12;
                List<Entity> entities = player.level().getEntitiesOfClass(Entity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                float offSetXZ = (player.getDimensions(player.getPose())).width() / 2;
                float offSetY =  (player.getDimensions(player.getPose()).height()) / 2;
                Vec3 myPos = player.position().add(offSetXZ, -offSetY, offSetXZ);
                for (Entity baseEntity : entities){
                    if (baseEntity instanceof LivingEntity entity) {
                        if (!entity.is(player)) {
                            Vec3 eyePos = entity.getEyePosition();
                            Vec3 lookVec = entity.getViewVector(1.0F).normalize();
                            Vec3 toPlayer = myPos.subtract(eyePos).normalize();

                            Vec3 axis = lookVec.cross(toPlayer);
                            if (axis.lengthSqr() < 1e-6) {
                                continue;
                            }
                            axis = axis.normalize();
                            double angle = Math.toDegrees(Math.acos(Mth.clamp(
                                    lookVec.dot(toPlayer), -1.0, 1.0)));
                            double targetAngle = Math.min(angle, oppressionAngle);
                            float minSpeed = 0.1f;
                            float maxSpeed = 3.0F;

                            float rotate = (float) (targetAngle / oppressionAngle);
                            rotate = minSpeed + (maxSpeed - minSpeed) * rotate;
                            rotate = Math.min(rotate, (float) targetAngle);

                            double rad = Math.toRadians(rotate);
                            Vec3 newLook = rotate(lookVec, axis, -rad).normalize();

                            double x = newLook.x;
                            double y = newLook.y;
                            double z = newLook.z;

                            float yaw = (float) (Mth.atan2(-x, z) * Mth.RAD_TO_DEG);
                            float pitch = (float) (-(Mth.atan2(y, Math.sqrt(x * x + z * z)) * Mth.RAD_TO_DEG));

                            entity.setYRot(yaw);
                            entity.setXRot(pitch);

                            entity.yHeadRot = yaw;
                            entity.yBodyRot = yaw;


                        }
                    }
                    if (baseEntity instanceof Projectile projectile) {
                        if (!projectile.is(player)) {
                            if (projectile.getOwner() instanceof Player player1) {
                                if (player1.is(player)) {
                                    continue;
                                }
                            }
                            float min = 0.8f;
                            projectile.setDeltaMovement(projectile.getKnownMovement().multiply(min,1,min));
                        }
                    }
                }
            }
        }
    }
    public static Vec3 rotate(Vec3 v, Vec3 axis, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        return v.scale(cos)
                .add(axis.cross(v).scale(sin))
                .add(axis.scale(axis.dot(v) * (1 - cos)));
    }
    public static Entity getPlayerLookTarget(Level level, Player living) {
        Entity pointedEntity = null;
        double range = living.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
        Vec3 srcVec = living.getEyePosition();
        Vec3 lookVec = living.getViewVector(1.0F);

        float maxAngle = (float) living.getAttributeValue(AttReg.oppression);
        Vec3 targetVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);

        AABB aabb = new AABB(srcVec.x, srcVec.y, srcVec.z, targetVec.x, targetVec.y, targetVec.z);

        List<Entity> entitiesInRange = level.getEntities(living, aabb);

        for (Entity entity : entitiesInRange) {
            if (entity instanceof LivingEntity livingEntity) {

                float offSetXZ = (livingEntity.getDimensions(livingEntity.getPose())).width() / 2;
                float offSetY =  (livingEntity.getDimensions(livingEntity.getPose()).height()) / 2;

                Vec3 entityPosition = entity.position().add(offSetXZ, -offSetY, offSetXZ);
                Vec3 directionToEntity = entityPosition.subtract(srcVec).normalize();

                double dotProduct = lookVec.dot(directionToEntity);
                double angle = Math.acos(dotProduct) * (180.0 / Math.PI);
                if (angle <= maxAngle) {
                    pointedEntity = entity;
                    break;
                }
            }
        }
        return pointedEntity;
    }
}
