package com.ytgld.chest_item.renderer.book;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.items.meet.Meat;
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
import org.jspecify.annotations.NonNull;

import java.util.*;

public class CIBookScreen extends Screen {
    private static final Identifier window = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/window.png");
    private static final Identifier back = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/back.png");
    private static final Identifier look_black = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/look_black.png");
    private static final Identifier back_small = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/back_small.png");
    private static final Identifier back_small_black = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/black_all.png");
    private static final Identifier book_small = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/book_small.png");
    private static final Identifier evil_book_small = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_small.png");
    private static final Identifier evil_book_back = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_back.png");
    private static final Identifier glow = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png");
    private static final Identifier shadow_2 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black_2.png");
    private static final Identifier shadow_3 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black_3.png");
    private static final Identifier item_not = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item/not.png");
    private static final Identifier item_star = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item/star.png");
    private static final Identifier frame = Identifier.fromNamespaceAndPath(Chestitem.MODID, "frame");
    private static final Identifier frame_black = Identifier.fromNamespaceAndPath(Chestitem.MODID, "frame_black");

    private static final Identifier evil_book_main = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_main.png");
    private static final Identifier evil_book_main_back = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil_book_main_back.png");

    private static final Identifier meat_book_main = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/meat_book_main.png");
    private static final Identifier meat_book_main_back = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/meat_book_main_back.png");

    private static final Identifier meat_book_small = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/meat_book_small.png");
    private static final Identifier meat_book_back = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/meat_book_back.png");


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
        if (event.button() == 1) {
            if (!isMouseClicked) {
                dragging = true;
                lastMouseX = event.x();
                lastMouseY = event.y();
                CIBookGuiAdd clicked = findEntryAt(event.x(), event.y());
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
        return super.mouseClicked(event, doubleClick);
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
    public boolean mouseDragged(@NonNull MouseButtonEvent event, double dx, double dy) {
        float mouseX = (float) event.x();
        float mouseY = (float) event.y();
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
        return super.mouseDragged(event, dx, dy);
    }

    public void targetOffset() {
        offsetX += (targetOffsetX - offsetX) * 0.15f;
        offsetY += (targetOffsetY - offsetY) * 0.15f;
    }

    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        dragging = false;
        return super.mouseReleased(event);
    }

    public RandomSource source = RandomSource.create();

    public void addPart(int x, int y, BlackKey.ColorImage colorImage) {
    }

    private int smallAlpha = 0;
    public int time = 0;
    private enum BookStyle {
        WINDOW,
        EVIL,
        MEAT
    }

    private BookStyle bookStyle = BookStyle.WINDOW;

    private int evilAlpha = 0;
    private int meatAlpha = 0;

    private int evilTargetAlpha = 0;
    private int meatTargetAlpha = 0;
    private void setBookStyle(BookStyle style) {
        bookStyle = style;

        switch (style) {
            case EVIL -> {
                evilTargetAlpha = 255;
                meatTargetAlpha = 0;
            }
            case MEAT -> {
                evilTargetAlpha = 0;
                meatTargetAlpha = 255;
            }
            case WINDOW -> {
                evilTargetAlpha = 0;
                meatTargetAlpha = 0;
            }
        }
    }
    @Override
    public void tick() {
        super.tick();
        time++;

        if (isMouseClicked && lastGuiAdd != null) {
            if (smallAlpha < 255) {
                smallAlpha += 20;
                smallAlpha = Math.min(255, smallAlpha);
            }

            if (lastGuiAdd.item instanceof IEvil) {
                setBookStyle(BookStyle.EVIL);
            } else if (lastGuiAdd.item instanceof Meat) {
                setBookStyle(BookStyle.MEAT);
            }else {
                setBookStyle(BookStyle.WINDOW);
            }
        } else {
            if (smallAlpha > 0) {
                smallAlpha -= 20;
                smallAlpha = Math.max(0, smallAlpha);
            }
        }

        evilAlpha = smoothAlpha(evilAlpha, evilTargetAlpha);
        meatAlpha = smoothAlpha(meatAlpha, meatTargetAlpha);
    }

    private int smoothAlpha(int current, int target) {
        if (current < target) {
            return Math.min(current + 15, target);
        }

        if (current > target) {
            return Math.max(current - 15, target);
        }

        return current;
    }
    public ItemStack lastItemOnUse = ItemStack.EMPTY;

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        targetOffset();
        addPart(6, 6, new BlackKey.ColorImage(150, (int) (240 * 0.85F), (int) (240 * 0.85F), (int) (100 * 0.85F)));
        addPart(249, 6, new BlackKey.ColorImage(150, (int) (130 * 0.85F), (int) (255 * 0.85F), (int) (100 * 0.85F)));
        addPart(249, 148, new BlackKey.ColorImage(150, (int) (100 * 0.85F), (int) (240 * 0.85F), (int) (255 * 0.85F)));
        addPart(6, 148, new BlackKey.ColorImage(150, (int) (255 * 0.85F), (int) (100 * 0.85F), (int) (255 * 0.85F)));
        float s = 1.2f;
        int xo = (int) ((this.width - 255 * s) / 2);
        int yo = (int) ((this.height - 155 * s) / 2);
        graphics.nextStratum();
        graphics.nextStratum();
        this.extractWindow(graphics, xo, yo, mouseX, mouseY);
        graphics.blit(RenderPipelines.GUI_TEXTURED, back_small_black,
                (int) ((this.width - 1024 * s) / 2), (int) ((this.height - 1024 * s) / 2),

                0.0F, 0.0F,
                (int) (1024 * s), (int) (1024 * s), (int) (1024 * s), (int) (1024 * s),

                Light.ARGB.color((int) (smallAlpha / 1.5f),255,255,255));


        if (lastItemOnUse.getItem() instanceof IEvil) {


            graphics.blit(RenderPipelines.GUI_TEXTURED, evil_book_back, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
            graphics.blit(RenderPipelines.GUI_TEXTURED, evil_book_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
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
                            Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            MRender.RenderPs.GUI_TEXTURED,
                            new Vector2f(),
                            new Vector2f(0,-0.1f),
                            new Vector2f(), false), 50);
            BlackParticlesAdd.markSeen(
                    (int) ((this.width + 118 * s) / 2),
                    (int) ((this.height - 110 * s) / 2),
                    new BlackKey.ImageColorAndRenderPipeline(16,
                            colorImage,
                            Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            MRender.RenderPs.GUI_TEXTURED,
                            new Vector2f(),
                            new Vector2f(0,-0.1f),
                            new Vector2f(), true), 50);
        }else if (lastItemOnUse.getItem() instanceof Meat){
            graphics.blit(RenderPipelines.GUI_TEXTURED, meat_book_back, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
            graphics.blit(RenderPipelines.GUI_TEXTURED, meat_book_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
        }else {
            graphics.blit(RenderPipelines.GUI_TEXTURED, back_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
            graphics.blit(RenderPipelines.GUI_TEXTURED, book_small, (int) ((this.width - 128 * s) / 2), (int) ((this.height - 128 * s) / 2), 0.0F, 0.0F, (int) (128 * s), (int) (128 * s), (int) (128 * s), (int) (128 * s),
                    Light.ARGB.color(smallAlpha, 255, 255, 255));
        }


        if (isMouseClicked && lastGuiAdd != null) {
            ItemStack stack = lastGuiAdd.item.getDefaultInstance();
            if (!stack.isEmpty()) {
//                for (int i = 0; i < 6; i++) {
//                    if (stack.getItem() instanceof ItemBase itemBase && time % 2 == 1) {
//
//                        int color = itemBase.color(stack);
//
//                        int as = (color >> 24) & 0xFF;
//                        int rs = (int) (((color >> 16) & 0xFF) / 1.25f);
//                        int gs = (int) (((color >> 8) & 0xFF) / 1.25f);
//                        int bs = (int) ((color & 0xFF) / 1.25f);
//                        float speed = 80f;
//                        BlackKey.ColorImage colorImage = new BlackKey.ColorImage(smallAlpha, rs, gs, bs);
//                        BlackParticlesAdd.markSeen(
//                                (int) (graphics.guiWidth() / 2f + Math.cos(time / speed + i * 45) * 120),
//                                (int) (graphics.guiHeight() / 2f + Math.sin(time  / speed + i * 45) * 120),
//                                new BlackKey.ImageColorAndRenderPipeline(8,
//                                        colorImage,
//                                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/cube.png"),
//                                        MRender.RenderPs.GUI_TEXTURED,
//                                        new Vector2f(),
//                                        new Vector2f(),
//                                        new Vector2f(), true), 5);
//                    }
//                }
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
                        graphics.text(Minecraft.getInstance().font, component.get(i), width / 2 - 72, height / 2 - 48 + i * 10, Light.ARGB.color(255, 200, 200, 200));
                    }
                } else {
                    graphics.text(Minecraft.getInstance().font, Component.translatable("chest_item.book.not"), width / 2 - 72, height / 2 - 48, Light.ARGB.color(255, 200, 200, 200));
                }
                int itemX = width / 2 - 8;
                int itemY = height / 2 - 74;
                graphics.item(stack, itemX, itemY);
                if (mouseX >= itemX - 16 && mouseX <= itemX + 16 && mouseY >= itemY - 16 && mouseY <= itemY + 16) {
                    graphics.tooltip(font, components, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null, true, stack);
                }
            }
        }
    }


    public void extractWindow(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY) {
        float s = 1.2f;
        int windowAlpha = switch (bookStyle) {
            case WINDOW -> 255;
            case EVIL -> 255 - evilAlpha;
            case MEAT -> 255 - meatAlpha;
        };
        int width = (int) (255 * s);
        int height = (int) (155 * s);
        int texWidth = (int) (256 * s);
        int texHeight = (int) (256 * s);

        graphics.blit(RenderPipelines.GUI_TEXTURED, evil_book_main_back, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight, Light.ARGB.color(evilAlpha, 255, 255, 255));
        graphics.blit(RenderPipelines.GUI_TEXTURED, meat_book_main_back, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight, Light.ARGB.color(meatAlpha, 255, 255, 255));
        int backAlpha = Math.max(0, 255 - Math.max(evilAlpha, meatAlpha));
        graphics.blit(RenderPipelines.GUI_TEXTURED, back, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight, Light.ARGB.color(backAlpha, 255, 255, 255));

        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addItem(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }

        graphics.blit(RenderPipelines.GUI_TEXTURED, look_black, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight);
        graphics.blit(RenderPipelines.GUI_TEXTURED,evil_book_main, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight, Light.ARGB.color(evilAlpha, 255, 255, 255));
        graphics.blit(RenderPipelines.GUI_TEXTURED, meat_book_main, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight, Light.ARGB.color(meatAlpha, 255, 255, 255));

        graphics.blit(RenderPipelines.GUI_TEXTURED, window, xo, yo, 0.0F, 0.0F, width, height, texWidth, texHeight, Light.ARGB.color(windowAlpha, 255, 255, 255));
        for (CIBookGuiAdd ciBookGuiAdd : list) {
            addText(ciBookGuiAdd, graphics, xo, yo, mouseX, mouseY);
        }
    }


    public void addItem(CIBookGuiAdd ciBookGuiAdd, GuiGraphicsExtractor graphics, int windowLeft, int windowTop, int mouseX, int mouseY) {
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
        graphics.blit(RenderPipelines.GUI_TEXTURED, ciBookGuiAdd.thePage.identifier, centerX - 9, centerY - 9, 0, 0, 18, 18, 18, 18);
        int color = ciBookGuiAdd.lightColor;
        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;
        int sss = 64;
        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.translate(centerX, centerY);
        pose.scale(size, size);
        pose.translate(-sss / 2f, -sss / 2f);
        int alpha = Mth.clamp((int) (size * 400 - 400), 0, 255);
        if (ciBookGuiAdd.thePage != ThePage.EVILMOTHER) {
            graphics.blit(MRender.RenderPs.GUI_TEXTURED, glow, 0, 0, 0, 0, sss, sss, sss, sss, Light.ARGB.color(alpha, rs, gs, bs));
            graphics.blit(MRender.RenderPs.GUI_TEXTURED, shadow_2, 0, 0, 0, 0, sss, sss, sss, sss, Light.ARGB.color(alpha, rs, gs, bs));
            graphics.blit(MRender.RenderPs.GUI_TEXTURED, shadow_3, 0, 0, 0, 0, sss, sss, sss, sss, Light.ARGB.color(alpha, rs, gs, bs));
        }else {
            BlackKey.ColorImage colorImage = new BlackKey.ColorImage(Math.min(255 - smallAlpha,alpha), 70,240,210);

            float cs = 10f;
            int sizeS = 16;
            BlackParticlesAdd.markSeen((int) (centerX + Math.cos(time / cs) * 12), (int) (centerY+ Math.sin(time / cs) * 12),
                    new BlackKey.ImageColorAndRenderPipeline(sizeS,
                            colorImage,
                            Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            MRender.RenderPs.GUI_TEXTURED,
                            new Vector2f(),
                            new Vector2f(0, -0.01f),
                            new Vector2f(), false));

            BlackParticlesAdd.markSeen((int) (centerX + Math.cos(time / cs + 90) * 12), (int) (centerY+ Math.sin(time / cs+ 90) * 12),
                    new BlackKey.ImageColorAndRenderPipeline(sizeS,
                            colorImage,
                            Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            MRender.RenderPs.GUI_TEXTURED,
                            new Vector2f(),
                            new Vector2f(0, -0.01f),
                            new Vector2f(), false));

            BlackParticlesAdd.markSeen((int) (centerX + Math.cos(time / cs + 180) * 12), (int) (centerY+ Math.sin(time / cs+ 180) * 12),
                    new BlackKey.ImageColorAndRenderPipeline(sizeS,
                            colorImage,
                            Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"),
                            MRender.RenderPs.GUI_TEXTURED,
                            new Vector2f(),
                            new Vector2f(0, -0.01f),
                            new Vector2f(), false));
        }
        pose.popMatrix(); /* * Item。 */
        pose.pushMatrix();
        pose.translate(centerX, centerY);
        pose.scale(size, size);
        pose.translate(-8, -8);
        graphics.item(stack, 0, 0);
        pose.popMatrix(); /* * 已获得 / 未获得。 */
        if (!has(stack)) {
            if (stack.getItem() instanceof ItemBase) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, item_not, centerX - 12, centerY - 12, 0, 0, 16, 16, 16, 16);
            }
        } else {
            graphics.blit(RenderPipelines.GUI_TEXTURED, item_star, centerX - 12, centerY - 12, 0, 0, 16, 16, 16, 16);
        }
    }

    public boolean has(ItemStack stack) {
        for (String string : player.getData(AttReg.itemRecord)) {
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(string));
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }

    public void addText(CIBookGuiAdd ciBookGuiAdd, GuiGraphicsExtractor graphics, int windowLeft, int windowTop, int mouseX, int mouseY) {
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
            graphics.pose().pushMatrix();
            graphics.pose().translate(mouseX, mouseY);
            graphics.pose().scale(1.25f, 1.25f);
            graphics.text(mc.font, ciBookGuiAdd.mainText, 0, 0, ciBookGuiAdd.colorMain);
            graphics.pose().popMatrix();
            for (int i = 0; i < ciBookGuiAdd.text.size(); i++) {
                graphics.text(mc.font, ciBookGuiAdd.text.get(i), mouseX, mouseY + (i + 1) * 12, ciBookGuiAdd.colorText);
                if (i == ciBookGuiAdd.text.size() - 1) {
                    graphics.text(mc.font, Component.translatable("chest_item.book.mouse"), mouseX, mouseY + (i + 2) * 12, Light.ARGB.color(255, 200, 150, 50));
                }
            }
            ItemStack stack = new ItemStack(ciBookGuiAdd.item);
            if (stack.getItem() instanceof SkillList element) {
                Map<SkillBase, Identifier> identifierMap = element.name();
                if (identifierMap != null) {
                    int i = 0;
                    for (Map.Entry<SkillBase, Identifier> entry : identifierMap.entrySet()) {
                        SkillBase elt = entry.getKey();
                        Identifier identifier1 = entry.getValue();
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier1, mouseX - 48, mouseY + 32 * i, 32, 32);
                        if (!(elt instanceof BlackSkill)) {
                            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, frame, mouseX - 48, mouseY + 32 * i, 32, 32);
                        } else {
                            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, frame_black, mouseX - 48, mouseY + 32 * i, 32, 32);
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
        BASE(Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/base.png")),
        BLACK(Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/black.png")),
        EVILMOTHER(Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/evil.png")),
        MEAT(Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/book/meat.png"));
        private final Identifier identifier;

        ThePage(Identifier identifier) {
            this.identifier = identifier;
        }

        public Identifier getIdentifier() {
            return identifier;
        }
    }

}
