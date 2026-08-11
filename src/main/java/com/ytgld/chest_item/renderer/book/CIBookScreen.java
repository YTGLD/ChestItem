package com.ytgld.chest_item.renderer.book;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.MGuiGraphics;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.BookPageFinder;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;

import java.util.*;

public class CIBookScreen extends Screen {
    private static final ResourceLocation window =
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/window.png");
    private static final ResourceLocation back =
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/back.png");
    private static final ResourceLocation look_black =
            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/look_black.png");


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
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, asd -> this.onClose()).width(200).build());
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (!isMouseClicked) {
                dragging = true;

                lastMouseX = mouseX;
                lastMouseY = mouseY;


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
                        if (mouseX >= centerX - i && mouseX <= centerX + i &&
                                mouseY + 6 >= centerY - i && mouseY + 6<= centerY + i) {
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

        return super.mouseClicked(mouseX,mouseY,button);
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
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
        return super.mouseDragged(mouseX,mouseY,button,dragX,dragY);
    }

    public void targetOffset(){
        offsetX += (targetOffsetX - offsetX) * 0.15f;
        offsetY += (targetOffsetY - offsetY) * 0.15f;
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return super.mouseReleased(mouseX, mouseY, button);

    }
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        targetOffset();

        if (minecraft != null && minecraft.level == null) {
            return;
        }
        if (minecraft == null){
            return;
        }
        super.render(graphics, mouseX, mouseY, a);
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 155 * (s)) / 2);
        this.extractWindow(graphics, xo, yo, mouseX, mouseY);
        graphics.pose().pushPose();
        graphics.pose().translate(0,0,500);
        if (isMouseClicked) {
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/back_small.png"),
                    (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * (s)) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),0xffffffff);

            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/book_small.png"),
                    (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * (s)) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),0xffffffff);

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
                            graphics.pose().pushPose();
                            graphics.drawString(Minecraft.getInstance().font, component.get(i),
                                    width / 2 - 72, height / 2 - 48 + i * 10,
                                    Light.ARGB.color(255, 200, 200, 200));
                            graphics.pose().popPose();
                        }
                    } else {
                        graphics.drawString(Minecraft.getInstance().font, Component.translatable("chest_item.book.not"),
                                width / 2 - 72, height / 2 - 48,
                                Light.ARGB.color(255, 200, 200, 200));
                    }
                }
                graphics.renderItem(lastItem.getDefaultInstance(),width / 2  - 7,height / 2 - 74);
                if (mouseX >= width / 2  - 7 - 16 && mouseX <= width / 2  - 7 + 16 &&
                       mouseY >= height / 2 - 74 - 16 && mouseY <= height / 2 - 74 + 16){
                    graphics.renderTooltip(
                            font,
                            lastItem.getDefaultInstance(),
                            mouseX, mouseY
                    );
                }
            }
        }
        graphics.pose().popPose();
    }
    public void extractWindow(GuiGraphics graphics, int xo, int yo, int mouseX, int mouseY) {
        float s = 1.2f;
        graphics.pose().pushPose();
        graphics.pose().translate(0,0,50);
       new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
               .blit(graphics, back, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),Light.ARGB.color(255,255,255,255));
        graphics.pose().popPose();

        graphics.pose().pushPose();
        graphics.pose().translate(0,0,50);
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addItem(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
        graphics.pose().popPose();

        graphics.pose().pushPose();
        graphics.pose().translate(0,0,70);
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, look_black, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),0xffffffff);
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, window, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),0xffffffff);
        graphics.pose().popPose();


        graphics.pose().pushPose();
        graphics.pose().translate(0,0,100);
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addText(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
        graphics.pose().popPose();
    }
    public void addItem(CIBookGuiAdd ciBookGuiAdd, GuiGraphics graphics, int windowLeft, int windowTop, int mouseX, int mouseY){
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

        PoseStack pose = graphics.pose();
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

        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics,ciBookGuiAdd.thePage.identifier,centerX - 9, centerY - 9,0,0,18,18,18,18,0xffffffff);
        int color = ciBookGuiAdd.lightColor;
        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;


        int sss = 64;
        pose.pushPose();
        pose.translate(centerX, centerY,0);
        pose.scale(size,size,0);
        pose.translate( (float) -sss / 2,  (float) -sss / 2,0);
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,true)
                .blit(graphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/item_glowing/all.png"),
                0,0,
                0, 0,
                sss, sss, sss, sss,
                Light.ARGB.color((int) (size * 400 - 400), rs, gs, bs));
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,true)
                .blit(graphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/shadow/black_2.png"),
                0,0,
                0, 0,
                sss, sss, sss, sss,
                Light.ARGB.color((int) (size * 400 - 400), rs, gs, bs));
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,true)
                .blit(graphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/shadow/black_3.png"),
                0,0,
                0, 0,
                sss, sss, sss, sss,
                Light.ARGB.color((int) (size * 400 - 400), rs, gs, bs));
        pose.popPose();


        pose.pushPose();
        pose.translate(centerX, centerY,0);
        pose.scale(size,size,0);
        pose.translate( - 8,  - 8,0);
        graphics.renderItem(stack, 0,0);
        pose.popPose();

        if (!has(stack)) {
            if (stack.getItem() instanceof ItemBase) {
                new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                        .blit(graphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item/not.png"),
                                centerX - 12, centerY - 12,0,0,16,16,16,16,0xffffffff);
            }
        }else {
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item/star.png"),
                    centerX - 12, centerY - 12,0,0,16,16,16,16,0xffffffff);
        }

    }
    public boolean has(ItemStack stack){
        for (String string :player.getData(AttReg.itemRecord)) {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(string));
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }
    public void addText(CIBookGuiAdd ciBookGuiAdd, GuiGraphics graphics, int windowLeft, int windowTop, int mouseX, int mouseY){
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
            graphics.pose().pushPose();
            graphics.pose().translate(mouseX, mouseY,0);
            graphics.pose().scale(1.25f,1.25f,0);
            graphics.drawString(mc.font, ciBookGuiAdd.mainText,0,0 ,ciBookGuiAdd.colorMain);
            graphics.pose().popPose();
            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                graphics.drawString(mc.font, ciBookGuiAdd.text.get(i), mouseX, mouseY + (i + 1) * 12, ciBookGuiAdd.colorText);
                if (i == ciBookGuiAdd.text.size() - 1) {
                    graphics.drawString(mc.font, Component.translatable("chest_item.book.mouse"), mouseX, mouseY + (i + 2) * 12, Light.ARGB.color(255,200,150,50));
                }
            }

            ItemStack stack = new ItemStack(ciBookGuiAdd.item);
            if (stack.getItem() instanceof SkillList element) {
                Map<SkillBase, ResourceLocation> ResourceLocationMap = element.name();
                if (ResourceLocationMap != null) {
                    for (int i = 0; i < ResourceLocationMap.keySet().stream().toList().size(); i++) {
                        SkillBase elt = ResourceLocationMap.keySet().stream().toList().get(i);
                        ResourceLocation ResourceLocation1 = ResourceLocationMap.get(elt);
                        graphics.blitSprite(ResourceLocation1,mouseX - 48, mouseY + 32 * i,32,32);
                        if (!(elt instanceof BlackSkill)) {
                            graphics.blitSprite( ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "frame"), mouseX - 48, mouseY + 32 * i, 32,32);
                        } else {
                            graphics.blitSprite( ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "frame_black"), mouseX - 48, mouseY + 32 * i,32,32);
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
        BASE(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/base.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/black.png")),
        MEAT(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/meat.png")),
        EVILMOTHER(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/book/evil.png"));
        private final ResourceLocation identifier;
        ThePage(ResourceLocation identifier){
            this.identifier = identifier;
        }

        public ResourceLocation getResourceLocation() {
            return identifier;
        }
    }
}

