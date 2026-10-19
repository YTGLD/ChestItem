package com.ytgld.chest_item.client_utils.network;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.client_utils.CIPacketPayload;
import com.ytgld.chest_item.client_utils.RenderObjectManager;
import com.ytgld.chest_item.client_utils.dout.Scratch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.joml.Vector3f;

public record ScratchPacket (double x, double y, double z,
                             float rotationX, float rotationY, float rotationZ,
                             int color, int size,int colorEnd)
        implements CIPacketPayload {

    public static final Type<ScratchPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Chestitem.MODID,
                    "scratch"
            ));

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            ScratchPacket
            > STREAM_CODEC = StreamCodec.composite(

            ByteBufCodecs.DOUBLE,
            ScratchPacket::x,

            ByteBufCodecs.DOUBLE,
            ScratchPacket::y,

            ByteBufCodecs.DOUBLE,
            ScratchPacket::z,

            ByteBufCodecs.FLOAT,
            ScratchPacket::rotationX,

            ByteBufCodecs.FLOAT,
            ScratchPacket::rotationY,

            ByteBufCodecs.FLOAT,
            ScratchPacket::rotationZ,

            ByteBufCodecs.INT,
            ScratchPacket::color,

            ByteBufCodecs.INT,
            ScratchPacket::size,

            ByteBufCodecs.INT,
            ScratchPacket::colorEnd,

            ScratchPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public Vec3 position() {
        return new Vec3(x, y, z);
    }
    public static void handle(
            ScratchPacket packet,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            Scratch sword = new Scratch(
                    packet.position(),
                    new Vec3(packet.rotationX(), packet.rotationY(), packet.rotationZ()),
                    packet.size());

            sword.vector3f = new Vector3f(0, 0, 0);
            sword.startColor = packet.color();
            sword.endColor = packet.colorEnd();
            RenderObjectManager.add(sword);
        });
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                ScratchPacket.TYPE,
                ScratchPacket.STREAM_CODEC,
                ScratchPacket::handle
        );
    }
}
