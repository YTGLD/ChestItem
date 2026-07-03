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
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.NonNull;

import java.util.*;

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
    private float targetOffsetX;
    private float targetOffsetY;
    private static final float DRAG_SPEED = 0.5f;

    public final List<CIBookGuiAdd> list = new ArrayList<>();
    private final HashMap<Vec2,Item> map = new HashMap<>();
    private float size = 1;

    private boolean isMouseClicked = false;
    private Item lastItem = ItemStack.EMPTY.getItem();

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
        for (CIBookGuiAdd ciBookGuiAdd : list){
            map.put(ciBookGuiAdd.vecPos,ciBookGuiAdd.item);
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
            if (!isMouseClicked) {
                dragging = true;
                lastMouseX = event.x();
                lastMouseY = event.y();
                for (CIBookGuiAdd ciBookGuiAdd : list) {
                    float s = 1.2f;
                    int xo = (int) ((this.width - 255 * s) / 2);
                    int yo = (int) ((this.height - 155 * (s)) / 2);
                    int centerX = (int) (
                            xo + 256 / 2f
                                    + ciBookGuiAdd.vecPos.x
                                    + offsetX
                    );

                    int centerY = (int) (
                            yo + 155 / 2f
                                    + ciBookGuiAdd.vecPos.y
                                    + offsetY
                    );

                    for (int i = -10; i < 10; i++) {
                        if (event.x() >= centerX - i && event.x() <= centerX + i &&
                                event.y() + 6 >= centerY - i && event.y() + 6<= centerY + i) {
                            for (Vec2 vec2 : map.keySet()) {
                                if (vec2.distanceToSqr(new Vec2(ciBookGuiAdd.vecPos.x, ciBookGuiAdd.vecPos.y)) == 0) {
                                    isMouseClicked = true;
                                    lastItem = map.get(vec2);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else {
            isMouseClicked = false;
            lastItem = ItemStack.EMPTY.getItem();
        }

        return super.mouseClicked(event,doubleClick);
    }
    @Override
    public boolean mouseDragged(@NonNull MouseButtonEvent event, double dx, double dy) {
        float mouseX = (float) event.x();
        float mouseY = (float) event.y();
        if (dragging) {

            targetOffsetX += (float)(mouseX - lastMouseX) * DRAG_SPEED;
            targetOffsetY += (float)(mouseY - lastMouseY) * DRAG_SPEED;

            int size = 350;
            targetOffsetX = Math.max(-size, Math.min(targetOffsetX, size));
            targetOffsetY = Math.max(-size, Math.min(targetOffsetY, size));

            lastMouseX = mouseX;
            lastMouseY = mouseY;

            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }

    public void targetOffset(){
        offsetX += (targetOffsetX - offsetX) * 0.15f;
        offsetY += (targetOffsetY - offsetY) * 0.15f;
    }
    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        dragging = false;
        return super.mouseReleased(event);
    }
    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        targetOffset();
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 155 * (s)) / 2);
        graphics.nextStratum();
        graphics.nextStratum();
        this.extractWindow(graphics, xo, yo, mouseX, mouseY);
        if (isMouseClicked) {
            graphics.blit(RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/back_small.png"),
                    (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * (s)) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s));

            graphics.blit(RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/book_small.png"),
                    (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * (s)) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s));

            if (!lastItem.getDefaultInstance().isEmpty()) {
                Optional<TooltipComponent> image = lastItem.getDefaultInstance().getTooltipImage();

                List<Component> lines = Screen.getTooltipFromItem(minecraft, lastItem.getDefaultInstance());

                List<ClientTooltipComponent> components = new ArrayList<>();
                image.ifPresent(img -> components.add(ClientTooltipComponent.create(img)));


                for (Component line : lines) {
                    components.add(ClientTooltipComponent.create(line.getVisualOrderText()));
                }
                for (CIBookGuiAdd ciBookGuiAdd :list) {
                    List<Component> component = ciBookGuiAdd.otherText;
                    if (!component.isEmpty()) {
                        for (int i = 0; i < component.size(); i++) {
                            graphics.pose().pushMatrix();
                            graphics.text(Minecraft.getInstance().font, component.get(i),
                                    width / 2 - 72, height / 2 - 48 + i * 10,
                                    Light.ARGB.color(255, 200, 200, 200));
                            graphics.pose().popMatrix();
                        }
                    } else {
                        graphics.text(Minecraft.getInstance().font, Component.translatable("chest_item.book.not"),
                                width / 2 - 72, height / 2 - 48,
                                Light.ARGB.color(255, 200, 200, 200));
                    }
                }
                graphics.item(lastItem.getDefaultInstance(),width / 2  - 7,height / 2 - 74);
                if (mouseX >= width / 2  - 7 - 16 && mouseX <= width / 2  - 7 + 16 &&
                       mouseY >= height / 2 - 74 - 16 && mouseY <= height / 2 - 74 + 16){
                    graphics.tooltip(
                            font,
                            components,
                            mouseX, mouseY,
                            DefaultTooltipPositioner.INSTANCE,
                            null,
                            lastItem.getDefaultInstance()
                    );
                }
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
        if (isMouseClicked){
            return;
        }
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
                if (i == ciBookGuiAdd.text.size() - 1) {
                    graphics.text(mc.font, Component.translatable("chest_item.book.mouse"), mouseX, mouseY + (i + 2) * 12, Light.ARGB.color(255,200,150,50));
                }
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

    public static final class CIBookGuiAdd {
        private final Item item;
        private final Vec2 vecPos;
        private final Component mainText;
        private final List<Component> text;
        private final int colorMain;
        private final int colorText;
        private final ThePage thePage;
        private final int lightColor;
        private final List<Component> otherText;

        public CIBookGuiAdd(Item item, Vec2 vecPos, Component mainText, List<Component> text, int colorMain, int colorText, ThePage thePage, int lightColor) {
            this.item = item;
            this.vecPos = vecPos;
            this.mainText = mainText;
            this.text = text;
            this.colorMain = colorMain;
            this.colorText = colorText;
            this.thePage = thePage;
            this.lightColor = lightColor;
            otherText = new ArrayList<>();
        }
        public CIBookGuiAdd(Item item, Vec2 vecPos, Component mainText, List<Component> text, int colorMain, int colorText, ThePage thePage, int lightColor,List<Component> otherText) {
            this.item = item;
            this.vecPos = vecPos;
            this.mainText = mainText;
            this.text = text;
            this.colorMain = colorMain;
            this.colorText = colorText;
            this.thePage = thePage;
            this.lightColor = lightColor;
            this.otherText = otherText;
        }

        public Item item() {
            return item;
        }

        public Vec2 vecPos() {
            return vecPos;
        }

        public Component mainText() {
            return mainText;
        }

        public List<Component> text() {
            return text;
        }

        public int colorMain() {
            return colorMain;
        }

        public int colorText() {
            return colorText;
        }

        public ThePage thePage() {
            return thePage;
        }

        public int lightColor() {
            return lightColor;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (CIBookGuiAdd) obj;
            return Objects.equals(this.item, that.item) &&
                    Objects.equals(this.vecPos, that.vecPos) &&
                    Objects.equals(this.mainText, that.mainText) &&
                    Objects.equals(this.text, that.text) &&
                    this.colorMain == that.colorMain &&
                    this.colorText == that.colorText &&
                    Objects.equals(this.thePage, that.thePage) &&
                    this.lightColor == that.lightColor;
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, vecPos, mainText, text, colorMain, colorText, thePage, lightColor);
        }

        @Override
        public String toString() {
            return "CIBookGuiAdd[" +
                    "item=" + item + ", " +
                    "vecPos=" + vecPos + ", " +
                    "mainText=" + mainText + ", " +
                    "text=" + text + ", " +
                    "colorMain=" + colorMain + ", " +
                    "colorText=" + colorText + ", " +
                    "thePage=" + thePage + ", " +
                    "lightColor=" + lightColor + ']';
        }
    }
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

