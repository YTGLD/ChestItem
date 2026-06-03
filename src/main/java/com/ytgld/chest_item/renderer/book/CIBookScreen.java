package com.ytgld.chest_item.renderer.book;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.BookPageFinder;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
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
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CIBookScreen extends Screen {
    private static final Identifier window =
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/window.png");
    private static final Identifier back =
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/back.png");
    private static final Identifier look_black =
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/look_black.png");


    private static final Component TITLE = Component.translatable("advancements.chest_item.root.title");
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private final Player player;
    private float offsetX = 0;
    private float offsetY = 0;
    private boolean dragging = false;
    private double lastMouseX;
    private double lastMouseY;
    public final List<CIBookGuiAdd> list = new ArrayList<>();

    private float size = 1;

    private boolean isLook = false;
    private int lookAlpha = 0;

//    private float size = 1;
    public CIBookScreen(Player player) {
        super(TITLE);
        this.player = player;
    }

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
    public boolean mouseDragged(@NonNull MouseButtonEvent event, double dx, double dy) {
        if (dragging) {
            offsetX += (float) (event.x() - lastMouseX);
            offsetY += (float) (event.y() - lastMouseY);

            int size = 350;
            offsetX = Math.max(-size, Math.min(offsetX, size));
            offsetY = Math.max(-size, Math.min(offsetY, size));

            lastMouseX = event.x();
            lastMouseY = event.y();

            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }
    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        dragging = false;
        return super.mouseReleased(event);
    }
    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 155 * (s)) / 2);
        graphics.nextStratum();
        graphics.nextStratum();
        this.extractWindow(graphics, xo, yo, mouseX, mouseY);
        if (isLook) {
            if (lookAlpha < 240) {
                lookAlpha += 15;
            }
        }else {
            if (lookAlpha > 0) {
                lookAlpha -= 15;
            }
        }
    }
    public void extractWindow(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
        float s = 1.2f;

        graphics.blit(RenderPipelines.GUI_TEXTURED, back, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s));
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addItem(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
        graphics.blit(RenderPipelines.GUI_TEXTURED, look_black, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s));
        graphics.blit(RenderPipelines.GUI_TEXTURED, window, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s));

        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addText(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
        this.look(xo, yo, mouseX, mouseY);
    }
    public void look (int windowLeft, int windowTop, int mouseX, int mouseY) {
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            int centerX = (int) (
                    windowLeft + 252 / 2f
                            + ciBookGuiAdd.vecPos.x
                            + offsetX
            );

            int centerY = (int) (
                    windowTop + 140 / 2f
                            + ciBookGuiAdd.vecPos.y
                            + offsetY
            );
            if (mouseX >= centerX - 8 && mouseX <= centerX + 8 &&
                    mouseY >= centerY - 8 && mouseY <= centerY + 8) {
                isLook = true;
                return;
            } else {
                isLook = false;
            }
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
        int windowBottom = windowTop + 155;

        int itemSize = 16;

        if (centerX - 24 + itemSize / 2 < windowLeft || centerX - 20 - itemSize / 2 > windowRight ||
                centerY - 24 + itemSize / 2 < windowTop || centerY - 4 - itemSize / 2 > windowBottom) {
            return;
        }

        ItemStack stack = new ItemStack(ciBookGuiAdd.item);


        boolean big  = mouseX >= centerX - 8 && mouseX <= centerX + 8 &&
                mouseY >= centerY - 8 && mouseY <= centerY + 8;

        Matrix3x2fStack pose = graphics.pose();
        //down == 1
        float speed = 20;
        float max = 5 / speed;
        if (big) {
            if (size < max * speed){
                size += max;
            }
        }else {
            if (size > 1) {
                size -= max;
            }
        }

        graphics.blit(RenderPipelines.GUI_TEXTURED,ciBookGuiAdd.thePage.identifier,centerX - 9, centerY - 9,0,0,18,18,18,18);
        int color = ciBookGuiAdd.lightColor;
        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;


        int sss = 64;
        pose.pushMatrix();
        pose.translate(centerX, centerY);
        pose.scale(size,size);
        pose.translate( (float) -sss / 2,  (float) -sss / 2);
        graphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/item_glowing/all.png"),
                0,0,
                0, 0,
                sss, sss, sss, sss,
                Light.ARGB.color((int) (size * 400 - 400), rs, gs, bs));
        graphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/shadow/black_2.png"),
                0,0,
                0, 0,
                sss, sss, sss, sss,
                Light.ARGB.color((int) (size * 400 - 400), rs, gs, bs));
        graphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/shadow/black_3.png"),
                0,0,
                0, 0,
                sss, sss, sss, sss,
                Light.ARGB.color((int) (size * 400 - 400), rs, gs, bs));
        pose.popMatrix();


        pose.pushMatrix();
        pose.translate(centerX, centerY);
        pose.scale(size,size);
        pose.translate( - 8,  - 8);
        graphics.item(stack, 0,0);
        pose.popMatrix();

        if (!has(stack)) {
            if (stack.getItem() instanceof ItemBase) {
                graphics.text(mc.font, Component.translatable("chest_item.item.not_has"), centerX - 12, centerY + 10, Light.ARGB.color(255, 200, 20, 20));
            }
        }else {
            graphics.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item/star.png"),
                    centerX - 12, centerY - 12,0,0,16,16,16,16);
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

        boolean b = mouseX >= centerX - 8 && mouseX <= centerX + 8 &&
                mouseY >= centerY - 8 && mouseY <= centerY + 8;
        if (b) {

            int paddingX = 4;
            int paddingY = 2;

            int mainWidth = (int) (mc.font.width(ciBookGuiAdd.mainText) * 1.25f);
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
            graphics.pose().pushMatrix();
            graphics.pose().translate(mouseX, mouseY);
            graphics.pose().scale(1.25f,1.25f);
            graphics.text(mc.font, ciBookGuiAdd.mainText,0,0 ,ciBookGuiAdd.colorMain);
            graphics.pose().popMatrix();
            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                graphics.text(mc.font, ciBookGuiAdd.text.get(i), mouseX, mouseY + (i + 1) * 12, ciBookGuiAdd.colorText);
            }

            ItemStack stack = new ItemStack(ciBookGuiAdd.item);
            if (stack.getItem() instanceof SkillList element) {
                Map<SkillBase, Identifier> IdentifierMap = element.name();
                if (IdentifierMap != null) {
                    for (int i = 0; i < IdentifierMap.keySet().stream().toList().size(); i++) {
                        SkillBase elt = IdentifierMap.keySet().stream().toList().get(i);
                        Identifier Identifier1 = IdentifierMap.get(elt);
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED,Identifier1,mouseX - 48, mouseY + 32 * i,32,32);
                        if (!(elt instanceof BlackSkill)) {
                            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID, "frame"), mouseX - 48, mouseY + 32 * i, 32, 32);
                        } else {
                            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID, "frame_black"), mouseX - 48, mouseY + 32 * i, 32, 32);
                        }
                    }
                }
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
                            Component.translatable("chest_item.book.test.1"),
                            Component.translatable("chest_item.book.test.2")
                    ),
                    Light.ARGB.color(255,255,255,255),
                    Light.ARGB.color(255,150,150,150),
                    ThePage.BASE,
                    Light.ARGB.color(255,255,255,100)));
        }
    }
    public record CIBookGuiAdd(Item item, Vec2 vecPos, Component mainText,List<Component> text, int colorMain,int colorText,ThePage thePage,int lightColor){}
    public enum ThePage{
        BASE(Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/base.png")),
        BLACK(Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/black.png")),
        MEAT(Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/meat.png"));
        private final Identifier identifier;
        ThePage(Identifier identifier){
            this.identifier = identifier;
        }

        public Identifier getIdentifier() {
            return identifier;
        }
    }
}

