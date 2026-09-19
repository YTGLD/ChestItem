package com.ytgld.chest_item.renderer.book;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.MGuiGraphics;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.BookPageFinder;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
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
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

import java.util.*;

public class CIBookScreen extends Screen {
    private static final ResourceLocation window = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/window.png");
    private static final ResourceLocation back = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/back.png");
    private static final ResourceLocation look_black = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/look_black.png");
    private static final ResourceLocation back_small = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/back_small.png");
    private static final ResourceLocation back_small_black = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/black_all.png");
    private static final ResourceLocation book_small = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/book_small.png");
    private static final ResourceLocation evil_book_small = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_small.png");
    private static final ResourceLocation evil_book_back = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_back.png");
    private static final ResourceLocation glow = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png");
    private static final ResourceLocation shadow_2 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black_2.png");
    private static final ResourceLocation shadow_3 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black_3.png");
    private static final ResourceLocation item_not = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item/not.png");
    private static final ResourceLocation item_star = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item/star.png");
    private static final ResourceLocation frame = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "frame");
    private static final ResourceLocation frame_black = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "frame_black");

    private static final ResourceLocation evil_book_main = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_main.png");
    private static final ResourceLocation evil_book_main_back = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_main_back.png");


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
    public final List<CIBookGuiAdd> list = new ArrayList<>(); /* * 每个条目单独保存缩放值。 */
    private final Map<CIBookGuiAdd, Float> itemSizes = new HashMap<>();
    private boolean isMouseClicked = false; /* * 当前点击的条目。 */
    private CIBookGuiAdd lastGuiAdd = null; /* * 当前点击条目的 Item。 */
    private Item lastItem = ItemStack.EMPTY.getItem();

    public CIBookScreen(Player player) {
        super(TITLE);
        this.player = player;
    }

    @Override
    protected void init() {
        list.clear();
        itemSizes.clear();
        for (RegisterBookPage registerItemConfig : BookPageFinder.getModPlugins()) {
            registerItemConfig.addPage(list);
        }
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            itemSizes.put(ciBookGuiAdd, 1.0f);
        }
        this.layout.addTitleHeader(TITLE, this.font);
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (a) -> this.onClose()).width(200).build());
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
                CIBookGuiAdd clicked = findEntryAt(mouseX, mouseY);
                if (clicked != null) {
                    isMouseClicked = true;
                    lastGuiAdd = clicked;
                    lastItem = clicked.item;
                    lastItemOnUse = clicked.item.getDefaultInstance();
                }
            }
        } else {
            isMouseClicked = false;
            lastGuiAdd = null;
            lastItem = ItemStack.EMPTY.getItem();
        }
        return super.mouseClicked(mouseX, mouseY,button);
    }

    private CIBookGuiAdd findEntryAt(double mouseX, double mouseY) {
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 155 * s) / 2);
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            int centerX = (int) (xo + 252 / 2f + ciBookGuiAdd.vecPos.x + offsetX);
            int centerY = (int) (yo + 140 / 2f + ciBookGuiAdd.vecPos.y + offsetY);
            if (mouseX >= centerX - 10 && mouseX <= centerX + 10 && mouseY >= centerY - 10 && mouseY <= centerY + 10) {
                return ciBookGuiAdd;
            }
        }
        return null;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (dragging) {
            targetOffsetX += (float) (mouseX - lastMouseX) * DRAG_SPEED;
            targetOffsetY += (float) (mouseY - lastMouseY) * DRAG_SPEED;
            int size = 350;
            targetOffsetX = Math.clamp(targetOffsetX, -size, size);
            targetOffsetY = Math.clamp(targetOffsetY, -size, size);
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return true;
        }
        return super.mouseDragged(mouseX,mouseY,button,dragX,dragY);
    }

    public void targetOffset() {
        offsetX += (targetOffsetX - offsetX) * 0.15f;
        offsetY += (targetOffsetY - offsetY) * 0.15f;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return super.mouseReleased(mouseX,mouseY,button);
    }

    public RandomSource source = RandomSource.create();

    public void addPart(int x, int y, BlackKey.ColorImage colorImage) {
    }

    private int smallAlpha = 0;
    public int time = 0;
    private int evilAlpha = 0;
    @Override
    public void tick() {
        super.tick();
        time++;
        if (isMouseClicked && lastGuiAdd != null) {
            if (smallAlpha < 255) {
                smallAlpha += 20;
                smallAlpha = Math.min(255,smallAlpha);
            }
            if (lastGuiAdd.item instanceof IEvil) {
                if (evilAlpha < 255) {
                    evilAlpha += 25;
                    evilAlpha = Math.min(255,evilAlpha);
                }
            }else {
                if (evilAlpha > 0) {
                    evilAlpha -= 25;
                    evilAlpha = Math.max(0,evilAlpha);
                }
            }
        }else {
            if (smallAlpha > 0) {
                smallAlpha -= 20;
                smallAlpha = Math.max(0,smallAlpha);
            }
        }
    }
    public ItemStack lastItemOnUse = ItemStack.EMPTY;

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        super.render(graphics, mouseX, mouseY, a);
        targetOffset();
        addPart(6, 6, new BlackKey.ColorImage(150, (int) (240 * 0.85F), (int) (240 * 0.85F), (int) (100 * 0.85F)));
        addPart(249, 6, new BlackKey.ColorImage(150, (int) (130 * 0.85F), (int) (255 * 0.85F), (int) (100 * 0.85F)));
        addPart(249, 148, new BlackKey.ColorImage(150, (int) (100 * 0.85F), (int) (240 * 0.85F), (int) (255 * 0.85F)));
        addPart(6, 148, new BlackKey.ColorImage(150, (int) (255 * 0.85F), (int) (100 * 0.85F), (int) (255 * 0.85F)));
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 155 * s) / 2);

        this.extractWindow(graphics, xo, yo, mouseX, mouseY);
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, back_small_black,
                (int) ((this.width - 1024 * s) / 2), (int) ((this.height - 1024 * s) / 2),

                0.0F, 0.0F,
                (int) (1024 * s), (int) (1024 * s), (int) (1024 * s), (int) (1024 * s),

                Light.ARGB.color((int) (smallAlpha / 1.5f),255,255,255));


        if (!(lastItemOnUse.getItem() instanceof IEvil)) {
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics, back_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics, book_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
        }else {
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics, evil_book_back, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                    .blit(graphics, evil_book_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
            int color = Light.ARGB.color(255,70,240,210);
            int as = (color >> 24) & 0xFF;
            int rs = ((color >> 16) & 0xFF);
            int gs = ((color >> 8) & 0xFF);
            int bs = (color & 0xFF);
            BlackKey.ColorImage colorImage = new BlackKey.ColorImage(smallAlpha, rs, gs, bs);
            BlackParticlesAdd.markSeen(
                    (int) ((this.width - 118 * s) / 2),
                    (int) ((this.height - 110 * s) / 2),
                    new BlackKey.ImageColorAndRenderPipeline(16,
                            colorImage,
                            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                            new Vector2f(),
                            new Vector2f(0,-0.1f),
                            new Vector2f(), false), 50);
            BlackParticlesAdd.markSeen(
                    (int) ((this.width + 118 * s) / 2),
                    (int) ((this.height - 110 * s) / 2),
                    new BlackKey.ImageColorAndRenderPipeline(16,
                            colorImage,
                            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                            new Vector2f(),
                            new Vector2f(0,-0.1f),
                            new Vector2f(), true), 50);
        }


        if (isMouseClicked && lastGuiAdd != null) {
            ItemStack stack = lastGuiAdd.item.getDefaultInstance();
            if (!stack.isEmpty()) {
                Optional<TooltipComponent> image = stack.getTooltipImage();
                List<Component> lines = Screen.getTooltipFromItem(minecraft, stack);
                List<ClientTooltipComponent> components = new ArrayList<>();
                image.ifPresent(img -> components.add(ClientTooltipComponent.create(img)));
                for (Component line : lines) {
                    components.add(ClientTooltipComponent.create(line.getVisualOrderText()));
                }
                List<Component> component = lastGuiAdd.otherText;
                if (!component.isEmpty()) {
                    for (int i = 0; i < component.size(); i++) {
                        graphics.drawString(Minecraft.getInstance().font, component.get(i), width / 2 - 72, height / 2 - 48 + i * 10, Light.ARGB.color(255, 200, 200, 200));
                    }
                } else {
                    graphics.drawString(Minecraft.getInstance().font, Component.translatable("chest_item.book.not"), width / 2 - 72, height / 2 - 48, Light.ARGB.color(255, 200, 200, 200));
                }
                int itemX = width / 2 - 8;
                int itemY = height / 2 - 74;
                graphics.renderItem(stack, itemX, itemY);
                if (mouseX >= itemX - 16 && mouseX <= itemX + 16 && mouseY >= itemY - 16 && mouseY <= itemY + 16) {
                    graphics.renderTooltip(
                            font,
                            lastItem.getDefaultInstance(),
                            mouseX, mouseY
                    );
                }
            }
        }
    }


    public void extractWindow(GuiGraphics graphics, int xo, int yo, int mouseX, int mouseY) {
        float s = 1.2f;
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, evil_book_main_back, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),
                Light.ARGB.color(evilAlpha ,255,255,255));
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, back, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),
                Light.ARGB.color(255 - evilAlpha, 255,255,255));

        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addItem(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }

        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, look_black, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),Light.ARGB.color(255,255,255,255));
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, evil_book_main, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),
                Light.ARGB.color(evilAlpha ,255,255,255));
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics, window, xo, yo, 0.0F, 0.0F, (int) (255 * s), (int) (155 * s), (int) (256 * s), (int) (256 * s),
                Light.ARGB.color(255 - evilAlpha, 255,255,255));

        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addText(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
    }


    public void addItem(CIBookGuiAdd ciBookGuiAdd, GuiGraphics graphics, int windowLeft, int windowTop, int mouseX, int mouseY) {
        int centerX = (int) (windowLeft + 252 / 2f + ciBookGuiAdd.vecPos.x + offsetX);
        int centerY = (int) (windowTop + 140 / 2f + ciBookGuiAdd.vecPos.y + offsetY);
        int windowRight = windowLeft + 255;
        int windowBottom = windowTop + 155;
        int itemSize = 16;
        if (centerX - 24 + itemSize / 2 < windowLeft || centerX - 20 - itemSize / 2 > windowRight || centerY - 24 + itemSize / 2 < windowTop || centerY - 4 - itemSize / 2 > windowBottom) {
            return;
        }
        ItemStack stack = new ItemStack(ciBookGuiAdd.item);
        boolean big = mouseX >= centerX - 8 && mouseX <= centerX + 8 && mouseY >= centerY - 8 && mouseY <= centerY + 8; /* * 每个条目使用自己的 size。 */
        float size = itemSizes.getOrDefault(ciBookGuiAdd, 1.0f);
        float speed = 20;
        float max = 5 / speed;
        if (big) {
            size += max;
            size = Math.min(size, 1.25f);
        } else {
            size -= max;
            size = Math.max(size, 1.0f);
        }
        itemSizes.put(ciBookGuiAdd, size);
        new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,false)
                .blit(graphics,ciBookGuiAdd.thePage.identifier,centerX - 9, centerY - 9,0,0,18,18,18,18,0xffffffff);
        int color = ciBookGuiAdd.lightColor;
        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;
        int sss = 64;
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(centerX, centerY,0);
        pose.scale(size, size,0);
        pose.translate(-sss / 2f, -sss / 2f,0);
        int alpha = Mth.clamp((int) (size * 400 - 400), 0, 255);
        if (ciBookGuiAdd.thePage != ThePage.EVILMOTHER) {
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,true)
                    .blit(graphics,glow, 0, 0, 0, 0, sss, sss, sss, sss, Light.ARGB.color(alpha, rs, gs, bs));
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,true)
                    .blit(graphics,shadow_2, 0, 0, 0, 0, sss, sss, sss, sss, Light.ARGB.color(alpha, rs, gs, bs));
            new MGuiGraphics.GUI(CIStateShardsHasBlack::getHasBlock,true)
                    .blit(graphics,shadow_3, 0, 0, 0, 0, sss, sss, sss, sss, Light.ARGB.color(alpha, rs, gs, bs));
        }else {
            BlackKey.ColorImage colorImage = new BlackKey.ColorImage(Math.min(255 - smallAlpha,alpha), 70,240,210);

            float cs = 10f;
            int sizeS = 16;
            BlackParticlesAdd.markSeen((int) (centerX + Math.cos(time / cs) * 12), (int) (centerY+ Math.sin(time / cs) * 12),
                    new BlackKey.ImageColorAndRenderPipeline(sizeS,
                            colorImage,
                            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                            new Vector2f(),
                            new Vector2f(0, -0.01f),
                            new Vector2f(), false));

            BlackParticlesAdd.markSeen((int) (centerX + Math.cos(time / cs + 90) * 12), (int) (centerY+ Math.sin(time / cs+ 90) * 12),
                    new BlackKey.ImageColorAndRenderPipeline(sizeS,
                            colorImage,
                            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                            new Vector2f(),
                            new Vector2f(0, -0.01f),
                            new Vector2f(), false));

            BlackParticlesAdd.markSeen((int) (centerX + Math.cos(time / cs + 180) * 12), (int) (centerY+ Math.sin(time / cs+ 180) * 12),
                    new BlackKey.ImageColorAndRenderPipeline(sizeS,
                            colorImage,
                            ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                            new Vector2f(),
                            new Vector2f(0, -0.01f),
                            new Vector2f(), false));
        }
        pose.popPose(); /* * Item。 */
        pose.pushPose();
        pose.translate(centerX, centerY,0);
        pose.scale(size, size,0);
        pose.translate(-8, -8,0);
        graphics.renderItem(stack, 0, 0);
        pose.popPose(); /* * 已获得 / 未获得。 */
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

    public boolean has(ItemStack stack) {
        for (String string : player.getData(AttReg.itemRecord)) {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(string));
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }

    public void addText(CIBookGuiAdd ciBookGuiAdd, GuiGraphics graphics, int windowLeft, int windowTop, int mouseX, int mouseY) {
        if (isMouseClicked) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        int centerX = (int) (windowLeft + 252 / 2f + ciBookGuiAdd.vecPos.x + offsetX);
        int centerY = (int) (windowTop + 140 / 2f + ciBookGuiAdd.vecPos.y + offsetY);
        boolean b = mouseX >= centerX - 8 && mouseX <= centerX + 8 && mouseY >= centerY - 8 && mouseY <= centerY + 8;
        if (b) {
            int paddingX = 4;
            int paddingY = 2;
            int mainWidth = (int) (mc.font.width(ciBookGuiAdd.mainText) * 1.25f);
            int mainHeight = mc.font.lineHeight;
            graphics.fill(mouseX - paddingX, mouseY - paddingY, mouseX + mainWidth + paddingX, mouseY + mainHeight + paddingY, Light.ARGB.color(200, 0, 0, 0));
            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                String line = String.valueOf(ciBookGuiAdd.text.get(i));
                int lineWidth = mc.font.width(line);
                int lineHeight = mc.font.lineHeight;
                int y = mouseY + (i + 1) * 12;
                graphics.fill(mouseX - paddingX, y - paddingY, mouseX + lineWidth + paddingX, y + lineHeight + paddingY, Light.ARGB.color(200, 0, 0, 0));
            }
            graphics.pose().pushPose();
            graphics.pose().translate(mouseX, mouseY,0);
            graphics.pose().scale(1.25f, 1.25f,0);
            graphics.drawString(mc.font, ciBookGuiAdd.mainText, 0, 0, ciBookGuiAdd.colorMain);
            graphics.pose().popPose();
            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                graphics.drawString(mc.font, ciBookGuiAdd.text.get(i), mouseX, mouseY + (i + 1) * 12, ciBookGuiAdd.colorText);
                if (i == ciBookGuiAdd.text.size() - 1) {
                    graphics.drawString(mc.font, Component.translatable("chest_item.book.mouse"), mouseX, mouseY + (i + 2) * 12, Light.ARGB.color(255, 200, 150, 50));
                }
            }
            ItemStack stack = new ItemStack(ciBookGuiAdd.item);
            if (stack.getItem() instanceof SkillList element) {
                Map<SkillBase, ResourceLocation> identifierMap = element.name();
                if (identifierMap != null) {
                    int i = 0;
                    for (Map.Entry<SkillBase, ResourceLocation> entry : identifierMap.entrySet()) {
                        SkillBase elt = entry.getKey();
                        ResourceLocation identifier1 = entry.getValue();
                        graphics.blitSprite(identifier1,mouseX - 48, mouseY + 32 * i,32,32);
                        if (!(elt instanceof BlackSkill)) {
                            graphics.blitSprite(frame, mouseX - 48, mouseY + 32 * i, 32,32);
                        } else {
                            graphics.blitSprite( frame_black, mouseX - 48, mouseY + 32 * i,32,32);
                        }
                        i++;
                    }
                }
            }
        }
    }
    public static void event(ClientTickEvent.Pre event){
    }
    @AddBookPage
    public static class AddPageClass implements RegisterBookPage {
        @Override
        public void addPage(List<CIBookGuiAdd> list) {
            list.add(new CIBookGuiAdd(Items.CHEST, new Vec2(0, 0), Component.translatable("chest_item.book.test.main"), List.of(Component.translatable("chest_item.book.test.1"), Component.translatable("chest_item.book.test.2")), Light.ARGB.color(255, 255, 255, 255), Light.ARGB.color(255, 150, 150, 150), ThePage.BASE, Light.ARGB.color(255, 255, 255, 100)));
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
            this.otherText = new ArrayList<>();
        }

        public CIBookGuiAdd(Item item, Vec2 vecPos, Component mainText, List<Component> text, int colorMain, int colorText, ThePage thePage, int lightColor, List<Component> otherText) {
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

        public List<Component> otherText() {
            return otherText;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (CIBookGuiAdd) obj;
            return Objects.equals(this.item, that.item) && Objects.equals(this.vecPos, that.vecPos) && Objects.equals(this.mainText, that.mainText) && Objects.equals(this.text, that.text) && this.colorMain == that.colorMain && this.colorText == that.colorText && Objects.equals(this.thePage, that.thePage) && this.lightColor == that.lightColor;
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, vecPos, mainText, text, colorMain, colorText, thePage, lightColor);
        }

        @Override
        public String toString() {
            return "CIBookGuiAdd[" + "item=" + item + ", " + "vecPos=" + vecPos + ", " + "mainText=" + mainText + ", " + "text=" + text + ", " + "colorMain=" + colorMain + ", " + "colorText=" + colorText + ", " + "thePage=" + thePage + ", " + "lightColor=" + lightColor + ']';
        }
    }

    public enum ThePage {
        BASE(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/base.png")),
        BLACK(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/black.png")),
        EVILMOTHER(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil.png")),
        MEAT(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/meat.png"));
        private final ResourceLocation identifier;

        ThePage(ResourceLocation identifier) {
            this.identifier = identifier;
        }

        public ResourceLocation ResourceLocation() {
            return identifier;
        }
    }
}

