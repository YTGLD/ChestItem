package com.ytgld.chest_item.items.evil_mother.decay;

public class DecayLead extends DecayItem{
    public DecayLead(Properties properties) {
        super(properties);
    }
    @Override
    public int getSanity() {
        return -3;
    }
    
}
