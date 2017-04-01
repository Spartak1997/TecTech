package com.github.technus.tectech.thing.metaTileEntity.multi;

import com.github.technus.tectech.elementalMatter.CommonValues;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

/**
 * Created by danie_000 on 17.12.2016.
 */
public class GT_MetaTileEntity_EM_decay extends GT_MetaTileEntity_MultiblockBase_EM {
    private static final String[][] shape = new String[][]{
            {"",//left to right top
                    "",
                    ""},//front
            {},//behind front
            {} //behind
    };
    private static final Block[] blockType = new Block[]{};
    private static final byte[] blockMeta = new byte[]{};

    public GT_MetaTileEntity_EM_decay(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_decay(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_decay(this.mName);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        return false;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                CommonValues.tecMark,
                "Is life time too long?",
                EnumChatFormatting.AQUA.toString() + EnumChatFormatting.BOLD + "Make ia half-life (3) instead!"
        };
    }
}