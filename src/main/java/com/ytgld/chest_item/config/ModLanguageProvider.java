package com.ytgld.chest_item.config;


import com.ytgld.chest_item.Chestitem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, Chestitem.MODID, "ci_test_lang");
    }

    @Override
    protected void addTranslations() {
        for (RegisterItemConfig registerItemConfig : ConfigPluginFinder.getModPlugins()){
            for (RegisterItemConfig.CIString theLanguageProvider : registerItemConfig.theLanguageProvider()) {
                add("chest_item.configuration." + theLanguageProvider.path(), theLanguageProvider.doIt());
                add("chest_item.config." + theLanguageProvider.path(), theLanguageProvider.doName());
            }
        }
    }
}