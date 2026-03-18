package com.ytgld.chest_item.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public interface RegisterItemConfig {
    void config(ModConfigSpec.Builder builder);
    CIString theLanguageProvider();

    record CIString(String path, String  doIt,String doName){

    }
}