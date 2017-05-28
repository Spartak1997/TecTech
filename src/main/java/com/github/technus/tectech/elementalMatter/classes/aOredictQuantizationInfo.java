package com.github.technus.tectech.elementalMatter.classes;

import com.github.technus.tectech.elementalMatter.interfaces.iExchangeInfo;
import com.github.technus.tectech.elementalMatter.interfaces.iHasElementalDefinition;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Tec on 23.05.2017.
 */
public class aOredictQuantizationInfo implements iExchangeInfo {
    public final int in;
    public final int amount;
    private final iHasElementalDefinition out;

    public aOredictQuantizationInfo(int id, int qty, iHasElementalDefinition emOut){
        in=id;
        amount=qty;
        out=emOut;
    }

    public aOredictQuantizationInfo(String name, int qty, iHasElementalDefinition emOut){
        in=OreDictionary.getOreID(name);
        amount=qty;
        out=emOut;
    }

    public aOredictQuantizationInfo( OrePrefixes prefix, Materials material, int qty, iHasElementalDefinition emOut){
        in=OreDictionary.getOreID(prefix.name() + material.mName);
        amount=qty;
        out=emOut;
    }

    @Override
    public Integer input() {
        return in;
    }

    @Override
    public iHasElementalDefinition output() {
        return out.clone();
    }

    @Override
    public int hashCode() {
        return in;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof aOredictQuantizationInfo && hashCode() == obj.hashCode();
    }
}