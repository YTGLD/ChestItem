package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.resources.Identifier;

public enum WidgetTypes {
    OBTAINED(
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/box_obtained"),
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/task_frame_obtained"),
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/challenge_frame_obtained"),
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/goal_frame_obtained")),
    UNOBTAINED(
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/box_unobtained"),
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/task_frame_unobtained"),
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/challenge_frame_unobtained"),
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"advancements/goal_frame_unobtained"));


    private final Identifier boxSprite;
    private final Identifier taskFrameSprite;
    private final Identifier challengeFrameSprite;
    private final Identifier goalFrameSprite;

    WidgetTypes(Identifier boxSprite, Identifier taskFrameSprite, Identifier challengeFrameSprite, Identifier goalFrameSprite) {
        this.boxSprite = boxSprite;
        this.taskFrameSprite = taskFrameSprite;
        this.challengeFrameSprite = challengeFrameSprite;
        this.goalFrameSprite = goalFrameSprite;
    }

    public Identifier boxSprite() {
        return this.boxSprite;
    }

    public Identifier frameSprite(AdvancementType type) {
        return switch (type) {
            case TASK -> this.taskFrameSprite;
            case CHALLENGE -> this.challengeFrameSprite;
            case GOAL -> this.goalFrameSprite;
        };
    }
}
