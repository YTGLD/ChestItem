package com.ytgld.chest_item.items.tool;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.renderer.particle.other.SwordEnergyOption;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

public class FearGreatsword extends Item {
    public static final String doubleString = "doubleString";

    public FearGreatsword(Properties properties) {
        super(properties.sword(ToolMaterial.NETHERITE,4,-2.6f));
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, @NonNull LivingEntity mob, LivingEntity attacker) {
        CompoundTag compoundTag = itemStack.get(DataReg.tag);
        if (compoundTag == null) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean(doubleString,true);
            itemStack.set(DataReg.tag,tag);
        }
        float limit = 0.1f;
        addPart(itemStack, mob, attacker,isDoubleDamage(mob, limit));
        if (isDoubleDamage(mob, limit)) {
            mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(), Sounds.Kill.value(), SoundSource.PLAYERS, 0.66f, 1);
        }
        super.hurtEnemy(itemStack, mob, attacker);
    }
    public void addPart(ItemStack itemStack, LivingEntity mob, LivingEntity attacker,boolean doubleAttack){
        RandomSource randomSource = attacker.getRandom();
        RandomSource mobRandomSource = mob.getRandom();
        CompoundTag compoundTag = itemStack.get(DataReg.tag);
        if (compoundTag !=null) {
            if (!compoundTag.getBooleanOr(doubleString,false)) {
                addPart(mob, Light.ARGB.color(255, 25, 225, 145), mobRandomSource);
                compoundTag.putBoolean(doubleString,true);
            } else {
                addPart(mob, Light.ARGB.color(255, 255, 100, 255), randomSource);
                compoundTag.putBoolean(doubleString,false);
            }

            if (doubleAttack) {
                addPart(mob, Light.ARGB.color(255, 25, 225, 145), mobRandomSource);
                addPart(mob, Light.ARGB.color(255, 255, 100, 255), randomSource);
            }
        }
    }
    public boolean isDoubleDamage(LivingEntity mob, float limit){
        return mob.getHealth() < mob.getMaxHealth() * limit;
    }
    public void addPart(LivingEntity mob,int color,RandomSource randomSource){
        if (mob.level() instanceof ServerLevel serverLevel) {
            Vec3 axis = new Vec3(randomSource.nextInt(-25, 25), randomSource.nextInt(-360, 360), randomSource.nextInt(-25, 25));
            float size = randomSource.nextInt(25, 30);
            serverLevel.sendParticles(SwordEnergyOption.createSwordEnergyOption(Particles.SwordEnergyOption_.get(),
                            axis, true, color, size),
                    mob.getX(), mob.getEyeY(), mob.getZ() - 0.5f, 1, 0, 0, 0, 0);
        }
    }
}
