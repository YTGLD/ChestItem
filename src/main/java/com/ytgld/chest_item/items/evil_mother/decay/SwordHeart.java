package com.ytgld.chest_item.items.evil_mother.decay;

import com.ytgld.chest_item.items.evil_mother.EvilMother;

/**
 * 腐堕剑心
 * <p>
 * 无法再制造出飞散的剑气
 * <p>
 * 作为补偿，剑气造成伤害时有概率再次凝聚能量
 * <p>
 * 增加100%连斩次数
 */
public class SwordHeart extends EvilMother {
    public SwordHeart(Properties properties) {
        super(properties);
    }


    @Override
    public int getSanity() {
        return 0;
    }
}
