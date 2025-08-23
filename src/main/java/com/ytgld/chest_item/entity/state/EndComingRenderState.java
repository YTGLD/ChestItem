package com.ytgld.chest_item.entity.state;

import com.ytgld.chest_item.entity.AttackEndComing;
import com.ytgld.chest_item.entity.EndComing;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class EndComingRenderState extends EntityRenderState {
    public EndComing entity;
    public float partialTick;

    public EndComingRenderState() {
    }
}

