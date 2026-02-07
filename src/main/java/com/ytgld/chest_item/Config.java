package com.ytgld.chest_item;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {
    private static final Pair<Config, ModConfigSpec> BUILDER = new ModConfigSpec.Builder().configure(Config::new);
    public static Config config = BUILDER.getKey();
    public static ModConfigSpec fc = BUILDER.getRight();
    public Config(ModConfigSpec.Builder BUILDER){
        BUILDER.push("Common");
        {
            doEndComingUp =  BUILDER
                    .translation("chest_item.config.doEndComingUp")
                    .define("doEndComingUp", true);
            chaosFortress =  BUILDER
                    .translation("chest_item.config.chaosFortress")
                    .defineInRange("chaosFortress", 5000,1,Integer.MAX_VALUE);

            RunawayLiningMin =  BUILDER
                    .translation("chest_item.config.RunawayLiningMin")
                    .defineInRange("RunawayLiningMin", -0.1F,0,Integer.MAX_VALUE);
            RunawayLiningMax =  BUILDER
                    .translation("chest_item.config.RunawayLiningMax")
                    .defineInRange("RunawayLiningMax", 0.25,0,Integer.MAX_VALUE);

        }
        BUILDER.pop();
    }
    public final ModConfigSpec.BooleanValue doEndComingUp;
    public final ModConfigSpec.IntValue chaosFortress;

    public final ModConfigSpec.DoubleValue RunawayLiningMin;
    public final ModConfigSpec.DoubleValue RunawayLiningMax;
}
