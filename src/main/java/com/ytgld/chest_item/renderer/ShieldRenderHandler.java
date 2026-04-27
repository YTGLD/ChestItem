package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.joml.Matrix3x2fStack;

import javax.annotation.Nullable;
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
    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            double max = player.getAttributeValue(AttReg.painShield_number);
            double now = player.getData(AttReg.painShield);
            if (max > 0) {

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
        if (!minecraft.options.hideGui) {
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

                        if (delta > 1) {
                            delta = 1;
                        }

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


                        renderECG(guiGraphics);
                    }
                }
            }
        }
    }
    public static void renderECG(GuiGraphicsExtractor guiGraphics) {
//        Minecraft minecraft = Minecraft.getInstance();
//        if (minecraft.options.hideGui) return;
//
//        var player = minecraft.player;
//        if (player == null || minecraft.level == null) return;
//
//        Matrix3x2fStack poseStack = guiGraphics.pose();
//        poseStack.pushMatrix();
//        float s = aFloat;
//        if (s < 0) {
//            s = 0;
//        }
//        int left = 0;
//        int top = 0;
//        int size = 32;
//        guiGraphics
//                .blit(MRender.RenderPs.GUI_TEXTURED,
//                        ECG_TEXTURE,
//
//                        0,0,
//                        0,0,
//
//                        size - scrollX, size,
//                        size, size,
//
//                        Light.ARGB.color((int) Math.min(s,Math.max(0,Math.min(255,ecgAlpha * 255))),255,255,255));
//
//        poseStack.popMatrix();
    }

    public static void thepainShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance stronger = player.getAttribute(AttReg.painShield_number);
            AttributeInstance number = player.getAttribute(AttReg.painShield_number);
            float base = 0.15f;
            float minDamage = 0.3f;
            if (stronger != null && number != null) {
                float value = (float) stronger.getValue();
                float theNumber = (float) number.getValue();
                float data = player.getData(AttReg.painShield);
                if (data > 0) {
                    float damage = event.getNewDamage();
                    float newData = data - 0.5f - (damage * 0.2f);
                    Handler.setDataValue(AttReg.painShield,player,(float)newData);
                    float modify = (float) Math.sqrt(value) * 1.25f;
                    if (modify < minDamage) {
                        modify = minDamage;
                    }
                    float newDamage = damage * (base / modify);
                    float x = 3;
                    x -= (float) Math.sqrt(value);
                    if (x < 1) {
                        x = 1;
                    }
                    if (data > 4+x) {
                        if (event.getSource().getEntity() instanceof LivingEntity living1) {
                            living1.hurt(living1.damageSources().playerAttack(player),
                                    (float) (newDamage
                                            + player.getAttributeValue(Attributes.MAX_HEALTH) * 0.15f
                                            + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75f));
                        }
                        int timeMeet = (int) (1200 * Math.sqrt(Math.sqrt(theNumber)));
                        int maxMeet = (int) (7 + theNumber / 5);

                        player.addEffect(new MobEffectInstance(Effects.Pain,timeMeet,0));
                        @Nullable MobEffectInstance mobEffectInstance = player.getEffect(Effects.Pain);
                        if (mobEffectInstance != null) {
                            if (mobEffectInstance.getAmplifier()<maxMeet) {
                                player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getDuration()+timeMeet,
                                        mobEffectInstance.getAmplifier() + 1,false,false));
                            }else {
                                player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),timeMeet*maxMeet, maxMeet,false,false));
                            }
                        }

                        event.setNewDamage(0);
                    }else {
                        event.setNewDamage(newDamage);
                    }
                    player.level().playSound(null, player.blockPosition(), Sounds.Heart.value(), SoundSource.PLAYERS, 1.5f, 1);
                    aFloat = 1;
                    aFloatCool = 40;
                } else {
                    Handler.setDataValue(AttReg.painShield,player, 0f);
                    int time = 200;
                    addCooldown(player, (int) player.getAttributeValue(AttReg.shield_cooldown));
                }
            }
        }
    }
    public static void tickShield(LivingEntity living){
        tickCooldown(living);
        if (!isInHeartShieldCooldown(living)) {
            if (living instanceof Player player && !player.level().isClientSide()) {
                AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
                AttributeInstance speed = player.getAttribute(AttReg.painShield_speed);
                if (maxShield != null && speed != null) {
                    if (maxShield.getValue() <= 0) {
                        return;
                    }
                    Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
                    if (player.getData(supplier) <= maxShield.getValue()) {
                        {
                            float other = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                            if (other > 0) {
                                addPain(player, speed.getValue(), other / 3);
                                Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES,player, 0f);
                            }
                        }
                        {
                            float other = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                            if (other > 0) {
                                addPain(player, speed.getValue(), other);
                                Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player, 0f);
                            }
                        }
                        {
                            float other = player.getData(AttReg.chaosWinds);
                            if (other > 0) {
                                addPain(player, speed.getValue(), other / 4);
                                Handler.setDataValue(AttReg.chaosWinds,player, 0f);
                            }
                        }
                    } else {
                        Handler.setDataValue(AttReg.chaosWinds,player, 0f);
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player, 0f);
                        Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES,player, 0f);

                    }
                }
            }
        }
    }
    public static boolean canHeal(LivingEntity living){
        if (isInHeartShieldCooldown(living)) {
            return true;
        }
        if (living instanceof Player player) {
            AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
            if (maxShield != null) {
                Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
                return !(player.getData(supplier) > maxShield.getValue());
            }
        }
        return true;
    }

    public static void tickCooldown(LivingEntity living){
        if (living instanceof Player player) {
            if (!player.level().isClientSide()) {
                if (isInHeartShieldCooldown(living)) {
                    Handler.setDataValue(AttReg.theHeartCooldown,player, player.getData(AttReg.theHeartCooldown) - 1);
                }
                if (player.getData(AttReg.theHeartCooldown) < 0) {
                    Handler.setDataValue(AttReg.theHeartCooldown,player, 0);
                }
            }
        }
    }
    public static void addCooldown(LivingEntity living,int time){
        if (living instanceof Player player) {
            if (!player.level().isClientSide()) {
                Handler.setDataValue(AttReg.theHeartCooldown,player, time);
            }
        }
    }
    public static void addPain(Player player, double speed, float add) {
        Handler.addHeartShield(player,add * (float) speed);
    }
}
