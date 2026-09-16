package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.joml.Vector2f;

import java.util.function.Supplier;

import static com.ytgld.chest_item.Handler.isInHeartShieldCooldown;

public class ShieldRenderHandler {

    private static final Identifier ECG_TEXTURE = Identifier.fromNamespaceAndPath(Chestitem.MODID
            , "textures/gui/beat1.png");
    public static Identifier SOUL_WARD = Identifier.fromNamespaceAndPath(Chestitem.MODID
            ,"textures/gui/pain.png");


    public static int glow;
    public static int fadeout;

    public static double lastShield;
    public static float displayedShield;


    public static float sizeHeartBeat;
    public static float theAlpha = 0;

    public static float heartBeat = 0;
    public static boolean isHeartBeat = false;
    public static float ecgAlpha = 1;
    public static boolean isSEcg = false;
    private static int scrollX = 0;

    public static float time = 0;


    public static float lastHyperplasia;
    public static boolean doHyperplasiaParticle  = false;

    public static float lastBlackShadow;
    public static boolean doBlackShadowParticle  = false;

    public static float lastChaosWinds;
    public static boolean doChaosWindsParticle  = false;

    public static Vec2 heartVec2 = null;
    public static boolean doZeroPart = false;


    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            double max = player.getAttributeValue(AttReg.painShield_number);
            double now = player.getData(AttReg.painShield);
            if (max > 0) {
                float hyperplasiaDataType = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                float blackShadowDataType = player.getData(AttReg.black_shadowAttachmentType);
                float chaosWindsDataType = player.getData(AttReg.chaosWinds);

                if (!isInHeartShieldCooldown(player)) {
                    doHyperplasiaParticle = lastHyperplasia != hyperplasiaDataType;
                    lastHyperplasia = hyperplasiaDataType;

                    doBlackShadowParticle = lastBlackShadow != blackShadowDataType;
                    lastBlackShadow = blackShadowDataType;

                    doChaosWindsParticle = lastChaosWinds != chaosWindsDataType;
                    lastChaosWinds = chaosWindsDataType;
                }



                if (player.hurtDuration == 10) {
                    aFloat = 1;
                    aFloatCool = 40;
                }
                if (aFloatCool > 0) {
                    aFloatCool--;
                } else {
                    aFloatCool = 1;
                }
                if (aFloatCool <= 0) {
                    if (aFloat > 0) {
                        aFloat -= 0.025f;
                    }

                    if (aFloat <= 0) {
                        aFloat = 0;
                    }
                }
                if (now <= max) {
                    aFloat = 1;
                    aFloatCool = 40;
                }
                if (now < max) {
                    if (glow > 0) {
                        glow--;
                    }
                }
                if (lastShield != now) {
                    glow = 15;
                    theAlpha = 1f;
                }
                if (theAlpha > 0.05f) {
                    theAlpha -= 0.05f;
                }else {
                    theAlpha = 0;
                }
                if (now <= 0 && lastShield != now) {
                    doZeroPart = true;
                }else {
                    doZeroPart = false;
                }

                lastShield = now;
                displayedShield = Mth.lerp(0.2f, displayedShield, (float) now);
                if (now > 0 && now < max) {
                    if (fadeout > 0) {
                        fadeout = Math.max(0, fadeout - 10);
                    }
                } else {
                    if (fadeout < 80) {
                        fadeout++;
                    }
                }
                float sin = (float) Math.sin((player.tickCount / 3f) / Math.max(Math.sqrt(now), 1));
                if (sin < 0) {
                    sin = -sin;
                }
                if (sin > 0.8) {
                    sizeHeartBeat = 1.05f;
                    isHeartBeat = true;
                    if (sin > 0.85) {
                        sizeHeartBeat = 1.1f;
                        if (sin > 0.9f) {
                            sizeHeartBeat = 1.15f;
                        }
                    }
                } else {
                    isHeartBeat = false;
                    sizeHeartBeat = 1f;
                }

                if (isSEcg) {
                    scrollX = 32;
                    ecgAlpha = 1F;
                }

                if (scrollX > 0) {
                    scrollX -= 4;
                    if (ecgAlpha > 0) {
                        ecgAlpha -= 0.125f;
                    }
                }
                time += 1;
                if (time % 32 == 1) {
                    isSEcg = true;
                }else {
                    isSEcg = false;
                }

                if (isHeartBeat) {
                    if (heartBeat < 0.9) {
                        heartBeat += 0.3f;
                    }
                }else {
                    if (heartBeat > 0.2f) {
                        heartBeat -= 0.2f;
                    }
                }
                if (soundCool > 0) {
                    soundCool--;
                }
            }
        }
    }
    public static float aFloat = 1;
    public static float aFloatCool = 40;
    public static int soundCool = 1;
    public static void renderShield(GuiGraphicsExtractor guiGraphics) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        var player = minecraft.player;
        if (player != null && minecraft.level != null) {
            if (!player.isCreative() && !player.isSpectator()) {
                double maxShield = player.getAttributeValue(AttReg.painShield_number);
                int left = guiGraphics.guiWidth() / 2;
                int top = guiGraphics.guiHeight() - 47;
                if (displayedShield > 0 && maxShield > 0) {
                    float s = aFloat;
                    if (s < 0) {
                        s = 0;
                    }

                    float delta = (float) (displayedShield / maxShield);
                    delta/= 1.55f;
                    if (doZeroPart) {
                        for (int size = 0; size < 25; size++) {
                            BlackParticlesAdd.markSeen((int) (left - (32 * delta * sizeHeartBeat) /2), (int) (top -  (32 * delta * sizeHeartBeat) /2), new BlackKey.ImageColorAndRenderPipeline(32,
                                    new BlackKey.ColorImage(255, 185, 75, 105),
                                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED,
                                    new Vector2f(), new Vector2f((float) (Math.cos(size) / 25f), (float) (Math.sin(size) / 25f)), new Vector2f(), false));
                        }
                    }
                    if (delta > 1) {
                        delta = 1;
                    }
                    heartVec2 = new Vec2(left - (32 * delta * sizeHeartBeat) /2, top -  (32 * delta * sizeHeartBeat) /2);
                    poseStack.pushMatrix();
                    poseStack.translate(left - (32 * delta * sizeHeartBeat) /2, top -  (32 * delta * sizeHeartBeat) /2);
                    poseStack.scale(delta * sizeHeartBeat,delta * sizeHeartBeat);
                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                            SOUL_WARD,
                            0,0,
                            (float) 0, (float) 0,
                            32, 32,
                            32, 32,
                            Light.ARGB.color((int) (s*255),255,255,255));
                    poseStack.popMatrix();

                    int size = 64;
                    poseStack.pushMatrix();
                    poseStack.translate(left - (size * delta * sizeHeartBeat) /2, top -  (size * delta * sizeHeartBeat) /2);
                    poseStack.scale(delta * sizeHeartBeat,delta * sizeHeartBeat);
                    float value = (float) player.getAttributeValue(AttReg.theSanity);
                    float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
                    if (value < base) {

                        float lvl = value / base;
                        float now = (1 - (lvl));

                        if (now < 0) {
                            now = 0;
                        }
                        if (now > 1) {
                            now = 1;
                        }
                        guiGraphics
                                .blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID
                                                ,"textures/gui/sanity_heart.png"),
                                        0,0,
                                        0, 0,
                                        size, size,
                                        size, size,
                                        Light.ARGB.color((int) Math.min(s * 255f,now * 255f), 255, 255,255));
                    }
                    poseStack.popMatrix();
                }
            }
        }
    }

    public static void rednerPart(int x, int y, boolean t, BlackKey.ColorImage colorImage){
        if (ShieldRenderHandler.heartVec2 !=null && t) {
            Vector2f posA = new Vector2f(x + 4, y + 4);

            Vector2f posB = new Vector2f(ShieldRenderHandler.heartVec2.x, ShieldRenderHandler.heartVec2.y);

            Vector2f velocity = posB.sub(posA, new Vector2f())
                    .normalize()
                    .mul(0.125f);
            BlackParticlesAdd.markSeen(x + 4, y + 4, new BlackKey.ImageColorAndRenderPipeline(16,
                    colorImage,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED,
                    new Vector2f(), velocity, new Vector2f(), false));
        }
    }

}
