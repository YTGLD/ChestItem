package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BlackShieldRenderHandler {

    public static int glow;
    public static double lastShield;
    public static float time = 0;
    public static float aSize = 1;
    public static float aGlow = 1f;
    public static float aGlowMin = 0F;
    public static float aGlowDOLDOWN = 0f;

    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            double max = player.getAttributeValue(AttReg.shadow_shield);
            double now = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            if (max > 0) {

                if (aFloatCool > 0) {
                    aFloatCool--;
                } else {
                    aFloatCool = 1;
                }

                if (now < max) {
                    aFloat = 1;
                    aFloatCool = 40;
                }else {
                    if (aFloatCool <= 0) {
                        if (aFloat > 0) {
                            aFloat -= 0.025f;
                        }

                        if (aFloat <= 0) {
                            aFloat = 0;
                        }
                    }
                }
                if (glow > 0) {
                    glow--;
                }
                if (lastShield != now) {
                    glow = 20;
                    aGlowDOLDOWN = 1;
                    aGlow = 1f;
                    aGlowMin = 0f;
                }
                if (aFloat < 1) {
                    aGlow = 1f;
                    aGlowMin = 0f;
                }
                if (aGlowDOLDOWN > 0) {
                    aGlowDOLDOWN -= 0.1f;
                    if (aGlowMin < 1) {
                        aGlowMin += 0.1f;
                    }
                    if (aGlow > 0) {
                        aGlow -= 0.1f;
                    }
                }
                if (aFloat < 1) {
                    if (aSize < 1.5f) {
                        aSize += 0.003f;
                    }
                }else {
                    aSize = 1;
                }

                lastShield = now;
                time += 1;
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
                    if (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) <= 0) {
                        return;
                    }

                    poseStack.pushMatrix();
                    poseStack.translate(
                            (guiGraphics.guiWidth() / 2f) - (float) (48) / 2,
                            (guiGraphics.guiHeight() - 47f)- (float) (48) / 2);
                    evilGlow(guiGraphics,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/item_glowing/all.png"));
                    poseStack.popMatrix();

                    double max = player.getAttributeValue(AttReg.shadow_shield);
                    if (max > 0) {
                        if (aFloat < 1 && sanValue(player) < 10) {
                            poseStack.pushMatrix();
                            poseStack.translate(
                                    ((guiGraphics.guiWidth() / 2f) - (24 * aSize) / 2),
                                    ((guiGraphics.guiHeight() - 47f) - (24 * aSize) / 2));
                            poseStack.scale(aSize, aSize);

                            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                                    , Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                            "textures/gui/black_shadow_glow_4.png"),0,0,0,0,
                                    24,24,
                                    24,24,
                                    Light.ARGB.color((int) (aFloat * 255),255,255, 255));

                            poseStack.popMatrix();
                        }
                    }
                    poseStack.pushMatrix();
                    poseStack.translate(
                            (guiGraphics.guiWidth() / 2f) - (float) (24) / 2,
                            (guiGraphics.guiHeight() - 47f)- (float) (24) / 2);
                    if (max > 0) {

                        float s = aFloat;
                        if (s < 0) {
                            s = 0;
                        }
                        float delta = (float) (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) / max);

                        if (delta > 1) {
                            delta = 1;
                        }
                        if (delta <= 0.25 && delta > 0.1f) {
                            renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_1.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_1.png"));
                            renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/evil_shadow_1.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/item_glowing/all.png"));
                        }else if (delta > 0.25 && delta <= 0.5) {
                            renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_2.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_2.png"));
                            renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/evil_shadow_2.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/item_glowing/all.png"));
                        }else if (delta > 0.5 && delta <= 0.75) {
                            renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_3.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_3.png"));
                            renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/evil_shadow_3.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/item_glowing/all.png"));
                        }else if (delta > 0.75 && delta <= 1) {
                            renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_4.png"), Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_4.png"));
                            renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/evil_shadow_4.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/item_glowing/all.png"));
                        }
                    }
                    poseStack.popMatrix();
                }
            }
        }
    }

    public static void renderSs4(GuiGraphicsExtractor guiGraphics, float s, Identifier resourceLocation, Identifier glowRes){
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null && sanValue(player) < 10) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED
                    , resourceLocation,0,0,0,0,
                    24,24,
                    24,24,
                    Light.ARGB.color( (int) (s * 255),255,255,255));

            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                    , glowRes,0,0,0,0,
                    24,24,
                    24,24,
                    Light.ARGB.color( (int) ((glow / 20f) * 255),255,255,255));

            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                    , resourceLocation,0,0,0,0,
                    24,24,
                    24,24,
                    Light.ARGB.color((int) Math.min(aFloat * 255,Math.min(aGlowMin* 255,aGlow* 255)),255,255, 255));
        }
    }

    public static void renderEvil(GuiGraphicsExtractor guiGraphics, float s, Identifier resourceLocation, Identifier glowRes){
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null) {
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
                if (sanValue(player) >= 10) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                            , glowRes, 0, 0, 0, 0,
                            24, 24,
                            24, 24,
                            Light.ARGB.color( (int) ((glow / 20f) * 255),45,255,170));
                }

                guiGraphics.blit(RenderPipelines.GUI_TEXTURED
                        , resourceLocation, 0, 0, 0, 0,
                        24, 24,
                        24, 24,
                        Light.ARGB.color((int) Math.min(s * 255f,now * 255f), 255, 255, 255));


                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                        , resourceLocation, 0, 0, 0, 0,
                        24, 24,
                        24, 24,
                        Light.ARGB.color((int) Math.min(aFloat * 255, Math.min(aGlowMin * 255, aGlow * 255)), 255, 255, 255));
            }
        }
    }
    public static void evilGlow(GuiGraphicsExtractor guiGraphics, Identifier glowRes) {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null) {
            float value = (float) player.getAttributeValue(AttReg.theSanity);
            float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
            if (value < base) {
                if (sanValue(player) >= 10) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                            , glowRes, 0, 0, 0, 0,
                            48, 48,
                            48, 48,
                            Light.ARGB.color((int) ((glow / 20f) * 255), 45, 255, 170));
                }
            }
        }

    }
    public static void hurtBlackShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance shadowShield = player.getAttribute(AttReg.shadow_shield);
            if (shadowShield != null) {
                float theNumber = (float) shadowShield.getValue();
                float shadowShieldAttachment = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                if (shadowShieldAttachment > 0 && theNumber > 0) {
                    aFloat = 1;
                    aFloatCool = 40;
                }
            }
        }
    }

    public static float sanValue (Player player){
        float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
        float san = (float) player.getAttributeValue(AttReg.theSanity) - base;
        if (san < 0) {
            san = -san;
        }
        return san;
    }
//    /**
//     * <p>
//     * 更新幽影护盾
//     * <p>
//     * 幽影护盾现在存在时获得伤害抗性，其伤害抗性的值为“幽影稳固度”属性值的10%（1级的幽影护盾就是1倍，3级是3倍，4级就是4倍），
//     * <p>
//     * 并且幽影护盾在被攻击时会将自身受到的伤害转化为疤痕组织和侵蚀装甲，但幽影护盾被击碎时会获得10秒的冷却（在冷却期间无法恢复幽影护盾）。
//     * <p>
//     * 幽影护盾现在会在疤痕组织或者侵蚀装甲消耗之前进行操作（疤痕组织或者侵蚀装甲必须在幽影护盾消耗完之后才会进行计算减伤）
//     * <p>
//     * 幽影稳固度现在效果更改为了“受到伤害后保护的幽影值”
//     * <p>
//     * 幽影稳固度现在越高，每级幽影护盾提供的抗性越高
//     * <p>
//     * 添加了新的1属性：幽影转换。
//     * 幽影转换是幽影护盾在受到伤害时将幽影转换成侵蚀装甲和疤痕组织的比率，默认为1点。
//     * <p>
//     * 幽影护盾现在显示的位置与创伤之痕的位置相同，且重新更改显示模式
//     * @since 1.21.1-1.2.0.0
//     */

    /**
     * 幽影护盾现在不仅可以吸收伤害，还可以反弹伤害
     * <p>
     * 反弹的伤害为150%幽影护盾当量
     * <p>
     * 反弹伤害时会使目标施加“幽影侵蚀”效果，并且时间由幽影护盾当量决定(幽影侵蚀）：减少10%速度  伤害  攻速
     * <p>
     * 如果完全转变为邪母之盾则返回“邪母之拒”，并且时间由邪母的理智值当量决定 (邪母之拒）：减少22.5%速度  伤害  攻速  护甲  治疗  生命值
     * <p>
     * 当然，如果目标无法添加相关效果，则受到额外伤害
     * <p>
     * 但是由低理智转化的“邪母之盾”的双相盾转化效率下降，其下降效果为：每减少1理智值则减少10%转化效果
     * <p>
     * 当理智开始消失时，每减少1理智值则减少10%抵御伤害的效果
     * <p>
     * 但也不会太过于薄弱，作为舍弃双相之盾的补偿，邪母之盾会提供10%的抗性，并且每减少1点理智，获得的抗性增加2%，最多50%
     * @since 26.1.2-1.0.4.5
     */
    public static void ShadowShield (LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (player instanceof Player) {
                if (Handler.has(player, InitItems.ShadowMint_.asItem())){
                    return;
                }
            }
            //幽影稳固度
            AttributeInstance shadow_shield_stronger = player.getAttribute(AttReg.shadow_shield_stronger);
            if (shadow_shield_stronger != null) {
                float value = (float) shadow_shield_stronger.getValue();
                if (value <= 0) {
                    return;
                }
                float data = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                if (data > 0) {
                    float damage = event.getNewDamage() ;
                    //newData：是减少后的值
                    float newData = data - (damage / value);
                    if (newData > 0) {
                        AttributeInstance shadow_shield_conversion = player.getAttribute(AttReg.shadow_shield_conversion);
                        if (shadow_shield_conversion != null) {
                            float sscValue = (float) shadow_shield_conversion.getValue();
                            float theSanValue = sanValue(player);
                            float doSan = 1 - (theSanValue / 10);
                            sscValue *= doSan;
                            if (sscValue < 0) {
                                sscValue =0;
                            }

                            float hyperplasiaAValue = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                            float chaosWindsAValue = player.getData(AttReg.chaosWinds);

                            AttributeInstance max_hyperplasiaAttributeInstance = player.getAttribute(AttReg.hyperplasia);
                            AttributeInstance max_chaos_armorAttributeInstance = player.getAttribute(AttReg.chaos_armor);

                            if (max_hyperplasiaAttributeInstance != null && max_chaos_armorAttributeInstance != null) {
                                float hyperplasia = (float) max_hyperplasiaAttributeInstance.getValue();
                                float chaosArmor = (float) max_chaos_armorAttributeInstance.getValue();

                                float newValueHyp = newData + hyperplasiaAValue;
                                newValueHyp *= sscValue;
                                if (newValueHyp > hyperplasia) {
                                    newValueHyp = hyperplasia;
                                }
                                Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES,player,newValueHyp);

                                float newValueChaosArmor = newData + chaosWindsAValue;
                                newValueChaosArmor *= sscValue;
                                if (newValueChaosArmor > chaosArmor) {
                                    newValueChaosArmor = chaosArmor;
                                }

                                double max = player.getAttributeValue(AttReg.shadow_shield);
                                float attRes = (float) (player.getAttributeValue(AttReg.shadow_shield_stronger) / 10f);
                                //这里将 attRes 乘 doSan 是因为理智降低会减少抗性
                                attRes *= doSan;
                                float end = 1 - attRes;
                                if (end < 0.1f) {
                                    end = 0.1f;
                                }
                                if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                                    float damageEffect = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) * 1.5f;
                                    if (theSanValue > 0) {
                                        if (livingEntity.addEffect(new MobEffectInstance(Effects.EvilErosion, (int) (theSanValue * 20 * 4), 0))){
                                            damageEffect *= 1.5f;
                                        }
                                    }else if (livingEntity.addEffect(new MobEffectInstance(Effects.ShadowErosion_,
                                            (int) (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) * 60),0))){
                                        damageEffect *= 1.5f;
                                    }
                                    livingEntity.hurt(livingEntity.damageSources().magic(),damageEffect);
                                }
                                float resEvil = 1;
                                resEvil -= 0.1f + (theSanValue / 100f * 2f);
                                if (resEvil < 0.5f) {
                                    resEvil = 0.5f;
                                }
                                if (theSanValue >= 10) {
                                    event.setNewDamage(event.getNewDamage() * resEvil);
                                }else {
                                    event.setNewDamage(event.getNewDamage() * end);
                                }
                                Handler.setDataValue(AttReg.chaosWinds,player,newValueChaosArmor);
                                Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player,newData);
                            }
                        }
                    }else {
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player,0f);
                    }
                }else {
                    Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player, 0f);
                    Handler.setDataValue(AttReg.shadow_shield_cooldown_dataAttachmentType,player,10);
                }
            }
        }
    }

}
