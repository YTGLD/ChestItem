package com.ytgld.chest_item;

import com.ytgld.chest_item.config.ConfigPluginFinder;
import com.ytgld.chest_item.config.RegisterItemConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    private static final Pair<Config, ModConfigSpec> BUILDER = new ModConfigSpec.Builder().configure(Config::new);
    public static Config config = BUILDER.getKey();
    public static ModConfigSpec fc = BUILDER.getRight();

    public Config(ModConfigSpec.Builder builder){

        builder.push("Common");
        {
            for (RegisterItemConfig registerItemConfig : ConfigPluginFinder.getModPlugins()){
                if (registerItemConfig.theCategory().isEmpty()) {
                    registerItemConfig.config(builder);
                }else {
                    builder.push(registerItemConfig.theCategory());
                    registerItemConfig.config(builder);
                    builder.pop();

                }
            }

        }
        builder.pop();
    }
}
