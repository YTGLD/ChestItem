package com.ytgld.chest_item.renderer.book;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.BookPageFinder;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;

public class CIBookScreen extends Screen {
    private static final Identifier window =
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/window.png");
    private static final Identifier back =
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/back.png");
    private static final Component TITLE = Component.translatable("advancements.chest_item.root.title");
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private final Player player;
    private float offsetX = 0;
    private float offsetY = 0;

    private boolean dragging = false;
    private double lastMouseX;
    private double lastMouseY;

    public CIBookScreen(Player player) {
        super(TITLE);
        this.player = player;
    }

    public final List<CIBookGuiAdd> list = new ArrayList<>();
    @Override
    protected void init() {
        for (RegisterBookPage registerItemConfig : BookPageFinder.getModPlugins()) {
            registerItemConfig.addPage(list);
        }
        this.layout.addTitleHeader(TITLE, this.font);
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, _ -> this.onClose()).width(200).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }
    @Override
    public void removed() {
        ClientPacketListener connection = this.minecraft.getConnection();
        if (connection != null) {
            connection.send(ServerboundSeenAdvancementsPacket.closedScreen());
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {
            dragging = true;
            lastMouseX = event.x();
            lastMouseY = event.y();
        }

        return super.mouseClicked(event,doubleClick);
    }
    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (dragging) {
            offsetX += (float) (event.x() - lastMouseX);
            offsetY += (float) (event.y() - lastMouseY);

            offsetX = Math.max(-200, Math.min(offsetX, 200));
            offsetY = Math.max(-100, Math.min(offsetY, 100));

            lastMouseX = event.x();
            lastMouseY = event.y();

            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }
    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        dragging = false;
        return super.mouseReleased(event);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 142 * (s)) / 2);
        graphics.nextStratum();
        graphics.nextStratum();
        this.extractWindow(graphics, xo, yo, mouseX, mouseY);
    }
    public void extractWindow(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
        float s = 1.2f;
        graphics.blit(RenderPipelines.GUI_TEXTURED, back, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (142 * s), (int) (256 * s), (int) (256 * s));
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addItem(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
        graphics.blit(RenderPipelines.GUI_TEXTURED, window, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (142 * s), (int) (256 * s), (int) (256 * s));

        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addText(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
    }

    public void addItem(CIBookGuiAdd ciBookGuiAdd, GuiGraphicsExtractor graphics, int windowLeft, int windowTop, int mouseX, int mouseY){
        Minecraft mc = Minecraft.getInstance();

        int centerX = (int)(
                windowLeft + 252 / 2f
                        + ciBookGuiAdd.vecPos.x
                        + offsetX
        );

        int centerY = (int)(
                windowTop + 140 / 2f
                        + ciBookGuiAdd.vecPos.y
                        + offsetY
        );

        int windowRight = windowLeft + 255;
        int windowBottom = windowTop + 150;

        int itemSize = 16;

        if (centerX - 30 + itemSize / 2 < windowLeft || centerX - itemSize / 2 > windowRight ||
                centerY - 30 + itemSize / 2 < windowTop || centerY - itemSize / 2 > windowBottom) {
            return;
        }

        ItemStack stack = new ItemStack(ciBookGuiAdd.item);




        graphics.item(stack, centerX - 8, centerY - 8);

        graphics.blit(RenderPipelines.GUI_TEXTURED,ciBookGuiAdd.thePage.identifier,centerX - 8, centerY - 8,0,0,16,16,16,16);
        if (!has(stack)) {
            if (!stack.is(Items.CHEST)) {
                graphics.text(mc.font, Component.translatable("chest_item.item.not_has"), centerX + 8, centerY - 4, Light.ARGB.color(255, 200, 20, 20));
            }
        }else {
            graphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item/star.png"),
                    centerX - 12, centerY - 12,0,0,16,16,16,16);
        }
        if (mouseX >= centerX - 8 && mouseX <= centerX + 8 &&
                mouseY >= centerY - 8 && mouseY <= centerY + 8) {

            graphics.text(mc.font, ciBookGuiAdd.mainText, mouseX, mouseY, ciBookGuiAdd.colorMain);

            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                graphics.text(mc.font, ciBookGuiAdd.text.get(i), mouseX, mouseY + (i + 1) * 12, ciBookGuiAdd.colorText);
            }
        }
    }
    public boolean has(ItemStack stack){
        for (String string :player.getData(AttReg.itemRecord)) {
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(string));
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }
    public void addText(CIBookGuiAdd ciBookGuiAdd, GuiGraphicsExtractor graphics, int windowLeft, int windowTop, int mouseX, int mouseY){
        Minecraft mc = Minecraft.getInstance();

        int centerX = (int)(
                windowLeft + 252 / 2f
                        + ciBookGuiAdd.vecPos.x
                        + offsetX
        );

        int centerY = (int)(
                windowTop + 140 / 2f
                        + ciBookGuiAdd.vecPos.y
                        + offsetY
        );

        if (mouseX >= centerX - 8 && mouseX <= centerX + 8 &&
                mouseY >= centerY - 8 && mouseY <= centerY + 8) {

            int paddingX = 4;
            int paddingY = 2;

            int mainWidth = mc.font.width(ciBookGuiAdd.mainText);
            int mainHeight = mc.font.lineHeight;
            graphics.fill(
                    mouseX - paddingX,
                    mouseY - paddingY,
                    mouseX + mainWidth + paddingX,
                    mouseY + mainHeight + paddingY,
                    Light.ARGB.color(200,0,0,0)
            );

            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                String line = String.valueOf(ciBookGuiAdd.text.get(i));
                int lineWidth = mc.font.width(line);
                int lineHeight = mc.font.lineHeight;
                int y = mouseY + (i + 1) * 12;
                graphics.fill(
                        mouseX - paddingX,
                        y - paddingY,
                        mouseX + lineWidth + paddingX,
                        y + lineHeight + paddingY,
                        Light.ARGB.color(200,0,0,0)
                );
            }

            graphics.text(mc.font, ciBookGuiAdd.mainText, mouseX, mouseY, ciBookGuiAdd.colorMain);

            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                graphics.text(mc.font, ciBookGuiAdd.text.get(i), mouseX, mouseY + (i + 1) * 12, ciBookGuiAdd.colorText);
            }
        }
    }
    @AddBookPage
    public static class AddPageClass implements RegisterBookPage {
        @Override
        public void addPage(List<CIBookGuiAdd> list) {
            list.add(new CIBookGuiAdd(Items.CHEST,new Vec2(0,0),
                    Component.translatable("chest_item.book.test.main"),
                    List.of(
                            Component.translatable("chest_item.book.test.1")
                    ),
                    Light.ARGB.color(255,255,255,255),
                    Light.ARGB.color(255,150,150,150),
                    ThePage.BASE));
        }
    }
    public record CIBookGuiAdd(Item item, Vec2 vecPos, Component mainText,List<Component> text, int colorMain,int colorText,ThePage thePage){}
    public enum ThePage{
        BASE(Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/base.png")),
        BLACK(Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/black.png")),
        MEAT(Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/meat.png"));
        private final Identifier identifier;
        private ThePage(Identifier identifier){
            this.identifier = identifier;
        }

        public Identifier getIdentifier() {
            return identifier;
        }
    }
}

