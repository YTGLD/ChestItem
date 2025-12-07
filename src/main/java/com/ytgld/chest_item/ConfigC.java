package com.ytgld.chest_item;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigC {
    private static final Pair<ConfigC, ModConfigSpec> BUILDER = new ModConfigSpec.Builder().configure(ConfigC::new);
    public static ConfigC config = BUILDER.getKey();
    public static ModConfigSpec fc = BUILDER.getRight();
    public ConfigC(ModConfigSpec.Builder BUILDER){
        BUILDER.push("MusicAndRender");
        {
            hyperplasiaMusic =  BUILDER
                    .translation("chest_item.config.hyperplasiaMusic")
                    .define("hyperplasiaMusic", true);
            Render =  BUILDER
                    .translation("chest_item.config.Render")
                    .define("Render", true);
            RenderSoul =  BUILDER
                    .translation("chest_item.config.RenderSoul")
                    .define("RenderSoul", true);


            RenderGUILight =  BUILDER
                    .translation("chest_item.config.RenderGUILight")
                    .define("RenderGUILight", true);
            RenderItemTooltip =  BUILDER
                    .translation("chest_item.config.RenderItemTooltip")
                    .define("RenderItemTooltip", true);
        }
        BUILDER.pop();
    }
    public final ModConfigSpec.BooleanValue RenderGUILight;
    public final ModConfigSpec.BooleanValue RenderItemTooltip;

    public final ModConfigSpec.BooleanValue hyperplasiaMusic;
    public final ModConfigSpec.BooleanValue Render;
    public final ModConfigSpec.BooleanValue RenderSoul;
}
