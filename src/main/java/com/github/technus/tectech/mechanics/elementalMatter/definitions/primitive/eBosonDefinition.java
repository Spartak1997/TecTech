package com.github.technus.tectech.mechanics.elementalMatter.definitions.primitive;

import com.github.technus.tectech.mechanics.elementalMatter.core.cElementalDecay;
import com.github.technus.tectech.mechanics.elementalMatter.core.stacks.cElementalDefinitionStack;
import com.github.technus.tectech.mechanics.elementalMatter.core.templates.cElementalPrimitive;

/**
 * Created by danie_000 on 22.10.2016.
 */
public final class eBosonDefinition extends cElementalPrimitive {
    public static final eBosonDefinition
            boson_Y__ = new eBosonDefinition("Photon", "\u03b3", 1e-18F, -1, 27),
            boson_H__ = new eBosonDefinition("Higgs", "\u0397", 126.09e9F, -2, 28);
    //deadEnd
    public static final cElementalDecay deadEnd = new cElementalDecay(boson_Y__, boson_Y__);
    public static final cElementalDecay deadEndHalf = new cElementalDecay(boson_Y__);
    public static final cElementalDefinitionStack boson_Y__1=new cElementalDefinitionStack(boson_Y__,1);

    private eBosonDefinition(String name, String symbol, float mass, int color, int ID) {
        super(name, symbol, 0, mass, 0, color, ID);
    }

    public static void run() {
        boson_Y__.init(null, NO_DECAY_RAW_LIFE_TIME, -1, -1, cElementalDecay.noDecay);
        boson_H__.init(null, 1.56e-22F, 0, 0,
                new cElementalDecay(0.96F, new cElementalDefinitionStack(boson_Y__, 4)),
                new cElementalDecay(0.02F, eLeptonDefinition.lepton_t, eLeptonDefinition.lepton_t_),
                new cElementalDecay(0.01F, eQuarkDefinition.quark_b, eQuarkDefinition.quark_b_),
                deadEnd);
    }

    @Override
    public String getName() {
        return "Boson: " + name;
    }

    @Override
    public boolean isTimeSpanHalfLife() {
        return this==boson_H__;
    }
}
