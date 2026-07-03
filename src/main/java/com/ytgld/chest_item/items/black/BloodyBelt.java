package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.UnstableSpheres;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class BloodyBelt extends ItemBlackShadow implements SkillList {
    public BloodyBelt(Properties properties) {
        super(properties);
    }
    public static void die(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.BloodyBelt_)) {
                        int s = (int) (float) player.getData(AttReg.attachmentTypeBLOOD_Model);
                        if (Mth.nextInt(RandomSource.create(), 1, 100) <= 50) {
                            if (s < 8) {
                                player.setData(AttReg.attachmentTypeBLOOD_Model, s + 1f);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void tick(ItemStackTickEvent event){
        Player player = event.player;
        if (Handler.has(player, InitItems.BloodyBelt_.asItem())) {
            ChestInventory chestInventory = event.chestInventory;
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.BloodyBelt_)) {
                        int time = 40;
                        int s =(int)(float) player.getData(AttReg.attachmentTypeBLOOD_Model);
                        if (s > 0) {
                            time /= s;
                        }
                        if (time < 1) {
                            time = 1;
                        }
                        if (player.tickCount % time == 1&&s>0) {
                            int value = (int)((float)player.getData(AttReg.attachmentTypeBLOOD_Model));
                            float ss = value/10f;
                            Vec3 playerPos = player.position().add(0, 1, 0);
                            int range = 12;
                            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                            float off= (float) (Math.sin(player.tickCount / 10f) / 7f);
                            for (LivingEntity living : entities) {
                                if (living.isAlive()) {
                                    if (!living.is(player)) {
                                        if (living instanceof OwnableEntity ownableEntity) {
                                            if (ownableEntity.getOwner() != null) {
                                                if (ownableEntity.getOwner().is(player)) {
                                                    if (Handler.chestEntity(living,player)) {
                                                        UnstableSpheres unstableSpheres = new UnstableSpheres(Entitys.UnstableSpheres_.get(), player.level());
                                                        unstableSpheres.isAttack = false;
                                                        unstableSpheres.setOwner(player);
                                                        unstableSpheres.setTarget(living);
                                                        unstableSpheres.setPos(playerPos.add(0, 2 - ss + off, 0));
                                                        player.level().addFreshEntity(unstableSpheres);
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                        if (living instanceof Targeting targeting) {
                                            if (targeting.getTarget() != null) {
                                                if (targeting.getTarget().is(player)) {
                                                    if (Handler.chestEntity(living,player)) {
                                                        UnstableSpheres unstableSpheres = new UnstableSpheres(Entitys.UnstableSpheres_.get(), player.level());
                                                        unstableSpheres.setOwner(player);
                                                        unstableSpheres.isAttack = true;
                                                        unstableSpheres.setTarget(living);
                                                        unstableSpheres.setPos(playerPos.add(0, 2 - ss + off, 0));
                                                        player.level().addFreshEntity(unstableSpheres);
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.bloody_belt.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.bloody_belt.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.bloody_belt.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.bloody_belt.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color((int) (255), 250, 150, 50);
    }

    @Nullable
    @Override
    public Map<SkillBase, Identifier> name() {
        Map<SkillBase, Identifier> map = new HashMap<>();
        map.put(pTerriblePotion, SkillList.pTerriblePotion.baneImage());
        map.put(pRotten, SkillList.pRotten.baneImage());
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pTerriblePotion,Component.translatable("item.chest_item.skill."+pTerriblePotion.baneName()));
        map.put(SkillList.pRotten,Component.translatable("item.chest_item.skill."+pRotten.baneName()));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();
        SkillBase.getElementMap(stack,map,pTerriblePotion);
        SkillBase.getElementMap(stack,map,pRotten);
        return map;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
}
