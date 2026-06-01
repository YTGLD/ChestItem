package com.ytgld.chest_item.renderer.book.tool;

import com.ytgld.chest_item.renderer.book.CIBookScreen;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public interface RegisterBookPage {
    void addPage(List<CIBookScreen.CIBookGuiAdd> list);
}