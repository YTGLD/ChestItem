package com.ytgld.chest_item.event;

import com.ytgld.chest_item.client_utils.AddUtil;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class CounterHandler {
    public static void event(EntityTickEvent.Post event){
        if (event.getEntity() instanceof Player player) {
            int score = ScoreConsecutiveVictoriesHandler.getNow(player);
            int max = (int) player.getAttributeValue(AttReg.counter);
            int now =   player.getData(AttReg.counter_data);
            int cooldown = player.getData(AttReg.counter_data_cooldown);
            if (max > 0 && cooldown <= 0) {
                if (player.tickCount % 100 == 1) {
                    int add = now + 2;
                    if (add > max) {
                        add = max;
                    }
                    player.setData(AttReg.counter_data.get(),add);
                }
            }
            if (cooldown > 0) {
                int down = score - 1;
                if (down <= 0) {
                    down = 0;
                }else if (player.getLastHurtMob() instanceof LivingEntity living
                        && living.isAlive()
                        && living.level() instanceof ServerLevel serverLevel
                        && living.tickCount % 2 == 1
                        && living.position().distanceTo(player.position()) < 10) {
                    AddUtil.addSwordCounter(player,living,Light.ARGB.color(255,120,220,85));
                    living.damageCooldownTime = 0;
                    living.setInvulnerableTime(0);
                    living.hurtServer(serverLevel,living.damageSources().playerAttack(player),
                            (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2F));
                }
                player.setData(AttReg.score_consecutive_victories.get(),down);
            }
            if (player.tickCount % 200 == 1) {
                int down = score - 1;
                if (down < 0) {
                    down = 0;
                }
                player.setData(AttReg.score_consecutive_victories.get(),down);
            }
            if (cooldown > 0) {
                player.setData(AttReg.counter_data_cooldown.get(),cooldown - 1);
            }
        }
    }
    public static void event(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            int max = (int) player.getAttributeValue(AttReg.counter);
            int now = player.getData(AttReg.counter_data);
            if (max <= 0){
                return;
            }
            float bounces = 1.3f;
            int score = ScoreConsecutiveVictoriesHandler.getNow(player);
            ScoreConsecutiveVictoriesHandler.addScore(player,score);
            bounces = ScoreConsecutiveVictoriesHandler.eventAddDamage(player,bounces,score);

            event.setNewDamage(event.getNewDamage() * bounces);
            int newNow = now - 1;
            if (newNow < 0) {
                player.setData(AttReg.counter_data_cooldown,applyCooldown(player));
                newNow = 0;
            }
            player.setData(AttReg.counter_data,newNow);
        }
    }
    private static int applyCooldown(Player player){
        return 200;
    }

    public static class ScoreConsecutiveVictoriesHandler{
        public static float eventAddDamage(Player player,float damage,int score) {
            if (canAddDamage(player, score)) {
                float damageBaseAdd = 0.1f;
                float damageAdd = (float) Math.sqrt(score - HowValueToAddDamage(player));
                return damage + (damageBaseAdd * damageAdd);
            }
            return damage;
        }
        public static void addScore(Player player, int score){
            int max = maxValue(player);
            int newSize = score + 1;
            if (newSize > max) {
                newSize = max;
            }
            player.setData(AttReg.score_consecutive_victories.get(),newSize);
        }
        public static int HowValueToAddDamage(Player player){
            return 5;
        }
        public static int maxValue(Player player){
            return 21;
        }
        public static int getNow(Player player){
            return player.getData(AttReg.score_consecutive_victories);
        }

        public static int HowDifference(Player player){
            return Math.min(0,getNow(player) - HowValueToAddDamage(player));
        }
        public static boolean canAddDamage(Player player,int score){
            return score > HowValueToAddDamage(player);
        }
    }
}
