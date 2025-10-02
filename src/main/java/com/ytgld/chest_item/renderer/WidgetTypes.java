package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.resources.ResourceLocation;

public enum WidgetTypes {
    OBTAINED(
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/box_obtained"),
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/task_frame_obtained"),
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/challenge_frame_obtained"),
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/goal_frame_obtained")),
    UNOBTAINED(
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/box_unobtained"),
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/task_frame_unobtained"),
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/challenge_frame_unobtained"),
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/goal_frame_unobtained"));


    private final ResourceLocation boxSprite;
    private final ResourceLocation taskFrameSprite;
    private final ResourceLocation challengeFrameSprite;
    private final ResourceLocation goalFrameSprite;

    WidgetTypes(ResourceLocation boxSprite, ResourceLocation taskFrameSprite, ResourceLocation challengeFrameSprite, ResourceLocation goalFrameSprite) {
        this.boxSprite = boxSprite;
        this.taskFrameSprite = taskFrameSprite;
        this.challengeFrameSprite = challengeFrameSprite;
        this.goalFrameSprite = goalFrameSprite;
    }

    public ResourceLocation boxSprite() {
        return this.boxSprite;
    }

    public ResourceLocation frameSprite(AdvancementType type) {
        return switch (type) {
            case TASK -> this.taskFrameSprite;
            case CHALLENGE -> this.challengeFrameSprite;
            case GOAL -> this.goalFrameSprite;
        };
    }
}
