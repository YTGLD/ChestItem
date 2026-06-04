package com.ytgld.chest_item;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.BlackShieldRenderHandler;
import com.ytgld.chest_item.renderer.model.WallingAxeModel;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.ShieldRenderHandler;
import com.ytgld.chest_item.renderer.particle.ColorPart;
import com.ytgld.chest_item.renderer.particle.FireBlock;
import com.ytgld.chest_item.renderer.particle.OrbPart;
import com.ytgld.chest_item.renderer.particle.evilmother.ColorPartEvil;
import com.ytgld.chest_item.renderer.particle.evilmother.CubeEvil;
import com.ytgld.chest_item.renderer.particle.evilmother.EvilTailing;
import com.ytgld.chest_item.renderer.particle.evilmother.OrbPartEvil;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

@Mod(value = Chestitem.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Chestitem.MODID, value = Dist.CLIENT)
public class ChestitemClient{
    public ChestitemClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void ItemTooltipEvent(ClientTickEvent.Pre event){
        EventMain.time++;
    }
    @SubscribeEvent
    public static void registerFactories(ViewportEvent.RenderFog event) {
        if (event.getCamera().entity() instanceof Player player){
            float number = (float) player.getAttributeValue(AttReg.chaos_consciousness);
            if (number > 0) {
                event.setFarPlaneDistance(event.getFarPlaneDistance() * number);
            }
        }
    }
    @SubscribeEvent
    public static void clientTickEvent(ClientTickEvent.Pre event) {
        ShieldRenderHandler.tick(event);
        BlackShieldRenderHandler.tick(event);
    }
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, Identifier.fromNamespaceAndPath(Chestitem.MODID,"pain_shield"),
                (guiGraphics,tracker)->ShieldRenderHandler.renderShield(guiGraphics));
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(Chestitem.MODID,"black_shield"),
                (guiGraphics,tracker)-> BlackShieldRenderHandler.renderShield(guiGraphics));
    }
    @SubscribeEvent
    public static void regMenu(RegisterMenuScreensEvent event){
        event.register(ChestMenuTypes.GENERIC_12.get(), ChestMenuScreen::new);
    }
    @SubscribeEvent
    public static <T extends Avatar & ClientAvatarEntity> void regMenu(RenderPlayerEvent.Pre<T> event) {
        if (Minecraft.getInstance().player instanceof Player living) {
            int value = (int) ((float) living.getData(AttReg.attachmentTypeBLOOD_Model));
            if (value>0) {
                HandlerClient.doPass = true;
                HandlerClient.showOutline = true;
                event.getSubmitNodeCollector().submitCustomGeometry(event.getPoseStack(), MRender.colorOutline(false), (pose, var) -> {
                            float s = value * 0.1f;
                            HandlerClient.renderBlood(pose, (float) Math.sin(living.tickCount / 10f) / 7f,
                                    new Vec3(0, 2.25 + s, 0),
                                    s, var);
                        }
                );
                event.getSubmitNodeCollector().submitCustomGeometry(event.getPoseStack(), MRender.colorOutline(true), (pose, var) -> {
                    float s = value * 0.1f;
                    HandlerClient.renderBlood(pose, (float) Math.sin(living.tickCount / 10f) / 7f,
                            new Vec3(0, 2.25 + s, 0),
                            s, var);
                });
            }
        }
    }
    @SubscribeEvent
    public static void registerItemModels(RegisterItemModelsEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Chestitem.MODID,"model"), WallingAxeModel.Unbaked.MAP_CODEC);
    }


    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.colorPart.get(), ColorPart.Provider::new);
        event.registerSpriteSet(Particles.FireBlock_.get(), FireBlock.Provider::new);
        event.registerSpriteSet(Particles.orbAPart.get(), OrbPart.Provider::new);

        event.registerSpriteSet(Particles.orbAPart_evil.get(), OrbPartEvil.Provider::new);
        event.registerSpriteSet(Particles.colorPart_evil.get(), ColorPartEvil.Provider::new);
        event.registerSpriteSet(Particles.cube_evil.get(), CubeEvil.Provider::new);
        event.registerSpriteSet(Particles.evil_tailing.get(), EvilTailing.Provider::new);
    }
    @SubscribeEvent
    public static void RegisterKeyMappingsEvent(RegisterKeyMappingsEvent event){
        event.registerCategory(Keys.chest);
    }
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(InitItems.TagsProvider::new);
    }

    @SubscribeEvent
    public static void RenderLevelStageEvent(RenderLevelStageEvent.AfterTranslucentParticles event){
//        if (Minecraft.getInstance().player != null) {
//            Vec3 playerPos = Minecraft.getInstance().player.position();
//            int range = 10;
//            List<EndComing> imperialHematomas = Minecraft.getInstance().player.level().getEntitiesOfClass(EndComing.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
//            for (EndComing imperialHematoma : imperialHematomas){
//                double x = imperialHematoma.getX();
//                double y = imperialHematoma.getY();
//                double z = imperialHematoma.getZ();
//                Vec3 camPos = event.getLevelRenderState().cameraRenderState.pos;
//                double relX = x - camPos.x;
//                double relY = y - camPos.y;
//                double relZ = z - camPos.z;
//                printScreenPercent(event.getPoseStack(), relX, relY, relZ, event.getLevelRenderState().cameraRenderState);
//                break;
//            }
//         }
    }

    public static void printScreenPercent(PoseStack modelView, double relX, double relY, double relZ, CameraRenderState camera) {
        // 保存当前矩阵状态
        modelView.pushPose();
        modelView.translate(relX, relY, relZ);

        // 获取模型视图矩阵
        Matrix4f modelViewMatrix = modelView.last().pose();

        // 获取投影矩阵
        Matrix4f projectionMatrix = camera.projectionMatrix;

        // 世界坐标 -> 视图空间 -> 裁剪空间
        Vector4f pos = new Vector4f((float) relX, (float) relY, (float) relZ, 1f);
        pos.mul(modelViewMatrix);     // 世界 -> 相机空间
        pos.mul(projectionMatrix);    // 相机空间 -> 裁剪空间

        // 检查在相机后方
        if (pos.w <= 0) {
            System.out.println("实体在相机后方");
            System.out.println("1");
            modelView.popPose();
            return;
        }

        // NDC 坐标
        float ndcX = pos.x / pos.w;
        float ndcY = pos.y / pos.w;

        // 检查是否在视锥内
        boolean insideFrustum = ndcX >= -1 && ndcX <= 1 && ndcY >= -1 && ndcY <= 1;
        if (!insideFrustum) {
            System.out.println("实体在视锥外");
            System.out.println("2");
        }

        // 屏幕坐标
        int screenWidth = Minecraft.getInstance().getWindow().getWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getHeight();
        float screenX = (ndcX + 1f) / 2f * screenWidth;
        float screenY = (1f - ndcY) / 2f * screenHeight; // Y轴翻转

        // 屏幕百分比，限制在0~1
        float screenXPercent = Math.clamp(screenX / screenWidth, 0f, 1f);
        float screenYPercent = Math.clamp(screenY / screenHeight, 0f, 1f);

        System.out.println("实体屏幕位置(百分比): X=" + screenXPercent + ", Y=" + screenYPercent);
        // 恢复矩阵
        modelView.popPose();
    }

}
