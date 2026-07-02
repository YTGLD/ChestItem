package com.ytgld.chest_item;

import com.ytgld.chest_item.config.ConfigPluginFinder;
import com.ytgld.chest_item.config.RegisterItemConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    private static final Pair<Config, ModConfigSpec> BUILDER = new ModConfigSpec.Builder().configure(Config::new);
    public static Config config = BUILDER.getKey();
    public static ModConfigSpec fc = BUILDER.getRight();
    public Config(ModConfigSpec.Builder builder){
        builder.push("Common");
        {
            doEndComingUp =  builder
                    .translation("chest_item.config.doEndComingUp")
                    .define("doEndComingUp", true);
            RunawayLiningMin =  builder
                    .translation("chest_item.config.RunawayLiningMin")
                    .defineInRange("RunawayLiningMin", -0.1F,-Integer.MAX_VALUE,Integer.MAX_VALUE);
            RunawayLiningMax =  builder
                    .translation("chest_item.config.RunawayLiningMax")
                    .defineInRange("RunawayLiningMax", 0.1f,0,Integer.MAX_VALUE);
            for (RegisterItemConfig registerItemConfig : ConfigPluginFinder.getModPlugins()){
                registerItemConfig.config(builder);
            }
        }
        builder.pop();
    }
    public final ModConfigSpec.BooleanValue doEndComingUp;
    public final ModConfigSpec.DoubleValue RunawayLiningMin;
    public final ModConfigSpec.DoubleValue RunawayLiningMax;
}
