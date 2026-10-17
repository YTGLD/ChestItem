package com.ytgld.chest_item.entity.state;

import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.entity.Tombstone;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class TombstoneRenderState extends EntityRenderState {
    public Tombstone entity;
    public final ItemStackRenderState item;
    public float partialTick;

    public TombstoneRenderState() {
        item = new ItemStackRenderState();
    }

}

