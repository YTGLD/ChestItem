package com.ytgld.chest_item.entity.state;

import com.ytgld.chest_item.entity.Reactor;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class ReactorRenderState extends EntityRenderState {
    public Reactor entity;
    public final ItemStackRenderState item;
    public float partialTick;

    public ReactorRenderState() {
        item = new ItemStackRenderState();
    }

}
