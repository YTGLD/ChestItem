package com.ytgld.chest_item.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.entity.state.BlackVexRenderState;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class BlackVexModel extends EntityModel<BlackVexRenderState> implements ArmedModel {
    private final ModelPart body = this.root.getChild("body");
    private final ModelPart rightArm = this.body.getChild("right_arm");
    private final ModelPart leftArm = this.body.getChild("left_arm");
    private final ModelPart rightWing = this.body.getChild("right_wing");
    private final ModelPart leftWing = this.body.getChild("left_wing");
    private final ModelPart head = this.root.getChild("head");

    public BlackVexModel(ModelPart root) {
        super(root.getChild("root"), RenderType::entityTranslucent);
    }

    public void setupAnim(BlackVexRenderState p_362388_) {
        super.setupAnim(p_362388_);
        this.head.yRot = p_362388_.yRot * (float) (Math.PI / 180.0);
        this.head.xRot = p_362388_.xRot * (float) (Math.PI / 180.0);
        float f = Mth.cos(p_362388_.ageInTicks * 5.5F * (float) (Math.PI / 180.0)) * 0.1F;
        this.rightArm.zRot = (float) (Math.PI / 5) + f;
        this.leftArm.zRot = -((float) (Math.PI / 5) + f);
        if (p_362388_.isCharging) {
            this.body.xRot = 0.0F;
            this.setArmsCharging(!p_362388_.rightHandItem.isEmpty(), !p_362388_.leftHandItem.isEmpty(), f);
        } else {
            this.body.xRot = (float) (Math.PI / 20);
        }

        this.leftWing.yRot = 1.0995574F + Mth.cos(p_362388_.ageInTicks * 45.836624F * (float) (Math.PI / 180.0)) * (float) (Math.PI / 180.0) * 16.2F;
        this.rightWing.yRot = -this.leftWing.yRot;
        this.leftWing.xRot = 0.47123888F;
        this.leftWing.zRot = -0.47123888F;
        this.rightWing.xRot = 0.47123888F;
        this.rightWing.zRot = 0.47123888F;
    }

    private void setArmsCharging(boolean rightArm, boolean leftArm, float chargeAmount) {
        if (!rightArm && !leftArm) {
            this.rightArm.xRot = -1.2217305F;
            this.rightArm.yRot = (float) (Math.PI / 12);
            this.rightArm.zRot = -0.47123888F - chargeAmount;
            this.leftArm.xRot = -1.2217305F;
            this.leftArm.yRot = (float) (-Math.PI / 12);
            this.leftArm.zRot = 0.47123888F + chargeAmount;
        } else {
            if (rightArm) {
                this.rightArm.xRot = (float) (Math.PI * 7.0 / 6.0);
                this.rightArm.yRot = (float) (Math.PI / 12);
                this.rightArm.zRot = -0.47123888F - chargeAmount;
            }

            if (leftArm) {
                this.leftArm.xRot = (float) (Math.PI * 7.0 / 6.0);
                this.leftArm.yRot = (float) (-Math.PI / 12);
                this.leftArm.zRot = 0.47123888F + chargeAmount;
            }
        }
    }

    @Override
    public void translateToHand(HumanoidArm p_259770_, PoseStack p_260351_) {
        boolean flag = p_259770_ == HumanoidArm.RIGHT;
        ModelPart modelpart = flag ? this.rightArm : this.leftArm;
        this.root.translateAndRotate(p_260351_);
        this.body.translateAndRotate(p_260351_);
        modelpart.translateAndRotate(p_260351_);
        p_260351_.scale(0.55F, 0.55F, 0.55F);
        this.offsetStackPosition(p_260351_, flag);
    }

    private void offsetStackPosition(PoseStack poseStack, boolean rightSide) {
        if (rightSide) {
            poseStack.translate(0.046875, -0.15625, 0.078125);
        } else {
            poseStack.translate(-0.046875, -0.15625, 0.078125);
        }
    }

}
