package com.ytgld.chest_item.client_utils.network;


import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.client_utils.CIPacketPayload;
import com.ytgld.chest_item.client_utils.RenderObjectManager;
import com.ytgld.chest_item.client_utils.dout.FatalSwordRenderObject;
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

public record FatalSwordRenderPacket(double x, double y, double z,
                                     float rotationX, float rotationY, float rotationZ,
                                     int color, int size)
        implements CIPacketPayload {

    public static final Type<FatalSwordRenderPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Chestitem.MODID,
                    "fatal_sword_render"
            ));

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            FatalSwordRenderPacket
            > STREAM_CODEC = StreamCodec.composite(

            ByteBufCodecs.DOUBLE,
            FatalSwordRenderPacket::x,

            ByteBufCodecs.DOUBLE,
            FatalSwordRenderPacket::y,

            ByteBufCodecs.DOUBLE,
            FatalSwordRenderPacket::z,

            ByteBufCodecs.FLOAT,
            FatalSwordRenderPacket::rotationX,

            ByteBufCodecs.FLOAT,
            FatalSwordRenderPacket::rotationY,

            ByteBufCodecs.FLOAT,
            FatalSwordRenderPacket::rotationZ,

            ByteBufCodecs.INT,
            FatalSwordRenderPacket::color,

            ByteBufCodecs.INT,
            FatalSwordRenderPacket::size,

            FatalSwordRenderPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public Vec3 position() {
        return new Vec3(x, y, z);
    }
    public static void handle(
            FatalSwordRenderPacket packet,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            FatalSwordRenderObject sword = new FatalSwordRenderObject(
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
                FatalSwordRenderPacket.TYPE,
                FatalSwordRenderPacket.STREAM_CODEC,
                FatalSwordRenderPacket::handle
        );
    }
}