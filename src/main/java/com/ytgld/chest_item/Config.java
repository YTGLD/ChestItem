package com.ytgld.chest_item;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue Test = BUILDER
            .comment("Test")
            .define("Test", true);
    static final ModConfigSpec SPEC = BUILDER.build();

}
