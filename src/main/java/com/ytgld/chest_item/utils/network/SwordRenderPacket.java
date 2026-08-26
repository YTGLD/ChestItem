package com.ytgld.chest_item.utils.network;


import com.ytgld.chest_item.utils.RenderObjectManager;
import com.ytgld.chest_item.utils.dout.SwordRenderObject;
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

public record SwordRenderPacket(double x, double y, double z,
                                float rotationX, float rotationY, float rotationZ,
                                int color, int size)
        implements CustomPacketPayload {

    public static final Type<SwordRenderPacket> TYPE =
            new Type<>(
                    Identifier.fromNamespaceAndPath(
                            "chest_item",
                            "sword_render"
                    )
            );

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            SwordRenderPacket
            > STREAM_CODEC = StreamCodec.composite(

            ByteBufCodecs.DOUBLE,
            SwordRenderPacket::x,

            ByteBufCodecs.DOUBLE,
            SwordRenderPacket::y,

            ByteBufCodecs.DOUBLE,
            SwordRenderPacket::z,

            ByteBufCodecs.FLOAT,
            SwordRenderPacket::rotationX,

            ByteBufCodecs.FLOAT,
            SwordRenderPacket::rotationY,

            ByteBufCodecs.FLOAT,
            SwordRenderPacket::rotationZ,

            ByteBufCodecs.INT,
            SwordRenderPacket::color,

            ByteBufCodecs.INT,
            SwordRenderPacket::size,

            SwordRenderPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public Vec3 position() {
        return new Vec3(x, y, z);
    }
    public static void handle(
            SwordRenderPacket packet,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            SwordRenderObject sword = new SwordRenderObject(
                    packet.position(),
                    new Vec3(packet.rotationX(), packet.rotationY(), packet.rotationZ()),
                    packet.size());

            sword.vector3f = new Vector3f(0, 0, 0);
            sword.color = packet.color();
            RenderObjectManager.add(sword);
        });
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SwordRenderPacket.TYPE,
                SwordRenderPacket.STREAM_CODEC,
                SwordRenderPacket::handle
        );
    }
}