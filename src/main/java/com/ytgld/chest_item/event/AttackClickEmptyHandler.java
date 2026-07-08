package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import javax.annotation.Nonnull;
import java.util.List;

public class AttackClickEmptyHandler {

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

                float offSetXZ = (livingEntity.getDimensions(living.getPose())).width() / 2;
                float offSetY =  (livingEntity.getDimensions(living.getPose()).height()) / 2;

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
