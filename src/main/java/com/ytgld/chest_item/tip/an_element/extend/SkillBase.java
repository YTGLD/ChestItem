package com.ytgld.chest_item.tip.an_element.extend;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class SkillBase {
    public static final String skillBaseXP = "SkillBaseXP";
    public abstract String baneName();
    public abstract boolean isPercentage();
    public abstract float aneLvlForModify();
    public abstract int levelMax(ItemStack stack);
    public Identifier baneImage(){
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,
                baneName()
        );
    }

    /**
     * 用于生成某个物品的map来更改显示
     * <P>
     * @param stack 输入的物品
     * <P>
     * @param map 输入的map集合
     * <P>
     * @param name 需要植入的SkillBase类型
     */
    public static void getElementMap(ItemStack stack, Map<SkillBase, Integer> map,
                                    SkillBase name){
        @Nullable CompoundTag compoundTag = stack.get(DataReg.tag);
        int sa = 0;
        if (compoundTag != null) {
            sa = compoundTag.getIntOr(name.baneName(), 0);
        }
        map.put(name,sa);
    }

    /**
     * 用于查找物品是否有skill数据
     *
     * @param stack 输入的物品
     * @param mustHasElement 检查的类型
     * @return 检查是不是有这个输入的skill
     */

    public static boolean isHasElement(ItemStack stack,SkillBase mustHasElement){
        if (stack.getItem() instanceof SkillList skillList) {
            Map<SkillBase, Integer> skillBaseIntegerMap = skillList.element(stack);
            if (skillBaseIntegerMap != null) {
                for (SkillBase map : skillBaseIntegerMap.keySet()) {
                    if (map == mustHasElement) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查这个物品的skill等级
     * @param stack 输入的物品
     * @param mustHasElement 检查的类型
     * @return 返回等级
     */
    public static int getHasElementLevel(ItemStack stack,SkillBase mustHasElement){
        @Nullable CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            int lvl = compoundTag.getIntOr(mustHasElement.baneName(), 0);
            lvl += compoundTag.getBooleanOr(IBlackLight.blackName,false) ? 1 : 0;
            return lvl;
        }
        return 0;
    }

    /**
     * 为输入的物品增加经验值
     * @param stack 输入的物品
     * @param mustHasElement 检查的类型
     * @param xp 每次增加的经验值
     * @param oneXPForOneLevel 每次升级所需的经验值
     */
    public static void addXP(ItemStack stack,SkillBase mustHasElement,int xp,int oneXPForOneLevel,int maxLvl){
        maxLvl--;
        @Nullable CompoundTag compoundTag = stack.get(DataReg.tag);
        String baneName = mustHasElement.baneName();
        String mixinName = baneName + skillBaseXP;
        if (compoundTag == null){
            stack.set(DataReg.tag,new CompoundTag());
        }
        if (compoundTag != null) {
            compoundTag.putInt(mixinName,compoundTag.getIntOr(mixinName,0)+xp);
        }
        if (getHasElementLevel(stack,mustHasElement)<maxLvl) {
            if (compoundTag != null) {
                int xpNumber = compoundTag.getIntOr(mixinName, 0);
                if (xpNumber % oneXPForOneLevel == 1) {
                    addSkillLevel(stack, mustHasElement);
                }
            }
        }
    }

    /**
     * 为物品增加等级
     */
    private static void addSkillLevel(ItemStack stack,SkillBase base){
        @Nullable CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag == null){
            stack.set(DataReg.tag,new CompoundTag());
        }
        if (compoundTag != null) {
            String lelSting = base.baneName();
            compoundTag.putInt(lelSting,compoundTag.getIntOr(lelSting,0)+ 1);
        }
    }
}
