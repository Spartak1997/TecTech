package com.github.technus.tectech.elementalMatter.core;

import com.github.technus.tectech.elementalMatter.core.containers.cElementalDefinitionStack;
import com.github.technus.tectech.elementalMatter.core.containers.cElementalInstanceStack;
import com.github.technus.tectech.elementalMatter.core.interfaces.iElementalDefinition;

/**
 * Created by danie_000 on 22.10.2016.
 */
public final class cElementalDecay {
    public static final cElementalDecay[] noDecay = (cElementalDecay[]) null;
    //DECAY IMPOSSIBLE!!!
    //Do not use regular NULL java will not make it work with varargs!!!
    //Or cast null into ARRAY type but this static is more convenient!!!
    public static final cElementalDecay[] noProduct = new cElementalDecay[0];
    //this in turn can be used to tell that the thing should just vanish
    public final cElementalDefinitionStackMap outputStacks;
    public final float probability;

    public cElementalDecay(iElementalDefinition... outSafe) {
        this(2F, outSafe);
    }

    public cElementalDecay(float probability, iElementalDefinition... outSafe) {
        cElementalDefinitionStack[] outArr = new cElementalDefinitionStack[outSafe.length];
        for (int i = 0; i < outArr.length; i++) {
            outArr[i] = new cElementalDefinitionStack(outSafe[i], 1);
        }
        this.outputStacks = new cElementalDefinitionStackMap(outArr);
        this.probability = probability;
    }

    public cElementalDecay(cElementalDefinitionStack... outSafe) {
        this(2F, outSafe);
    }

    public cElementalDecay(float probability, cElementalDefinitionStack... out) {
        this.outputStacks = new cElementalDefinitionStackMap(out);
        this.probability = probability;
    }

    public cElementalDecay(cElementalDefinitionStackMap tree) {
        this(2F, tree);
    }

    public cElementalDecay(float probability, cElementalDefinitionStackMap tree) {
        this.outputStacks = tree;
        this.probability = probability;
    }

    public cElementalInstanceStackMap getResults(float lifeMult, long age, long energy, long amountDecaying) {
        cElementalInstanceStackMap decayResult = new cElementalInstanceStackMap();
        if (outputStacks == null) return decayResult;//This is to prevent null pointer exceptions.
        //Deny decay code is in instance!
        long qtty = 0;
        for (cElementalDefinitionStack stack : outputStacks.values()) qtty += stack.amount;
        if (qtty <= 0) return decayResult;
        //energy /= qtty;
        //lifeMult /= (float) qtty;
        for (cElementalDefinitionStack stack : outputStacks.values()) {
            decayResult.putUnify(new cElementalInstanceStack(stack.definition,
                    amountDecaying * stack.amount,
                    lifeMult, age/*new products*/, energy / Math.max(1, stack.amount)));//get instances from stack
        }
        return decayResult;
    }
}
