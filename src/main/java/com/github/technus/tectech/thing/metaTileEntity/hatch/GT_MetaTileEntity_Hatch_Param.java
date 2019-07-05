package com.github.technus.tectech.thing.metaTileEntity.hatch;

import com.github.technus.tectech.CommonValues;
import com.github.technus.tectech.Util;
import com.github.technus.tectech.thing.metaTileEntity.hatch.gui.GT_Container_Param;
import com.github.technus.tectech.thing.metaTileEntity.hatch.gui.GT_Container_ParamAdv;
import com.github.technus.tectech.thing.metaTileEntity.hatch.gui.GT_GUIContainer_Param;
import com.github.technus.tectech.thing.metaTileEntity.hatch.gui.GT_GUIContainer_ParamAdv;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by danie_000 on 15.12.2016.
 */
public class GT_MetaTileEntity_Hatch_Param extends GT_MetaTileEntity_Hatch {
    public int pointer = 0;
    public int param = -1;
    public double value0D = 0;
    public double value1D = 0;
    public double input0D = 0;
    public double input1D = 0;
    private static Textures.BlockIcons.CustomIcon ScreenON;
    private static Textures.BlockIcons.CustomIcon ScreenOFF;

    public GT_MetaTileEntity_Hatch_Param(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "For parametrization of Multiblocks");
        Util.setTier(aTier,this);
    }

    public GT_MetaTileEntity_Hatch_Param(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        super.registerIcons(aBlockIconRegister);
        ScreenOFF = new Textures.BlockIcons.CustomIcon("iconsets/PARAM");
        ScreenON = new Textures.BlockIcons.CustomIcon("iconsets/PARAM_ACTIVE");
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if (mTier > 7) {
            return new GT_Container_ParamAdv(aPlayerInventory, aBaseMetaTileEntity);
        }
        return new GT_Container_Param(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        if (mTier > 7) {
            return new GT_GUIContainer_ParamAdv(aPlayerInventory, aBaseMetaTileEntity);
        }
        return new GT_GUIContainer_Param(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(ScreenON)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(ScreenOFF)};
    }

    //@Override
    //public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
    //    if (aBaseMetaTileEntity.isClientSide() && (aTick % 20L == 0L)) {
    //        //refresh casing on state change
    //        int Xpos = aBaseMetaTileEntity.getXCoord();
    //        int Ypos = aBaseMetaTileEntity.getYCoord();
    //        int Zpos = aBaseMetaTileEntity.getZCoord();
    //        try {
    //            aBaseMetaTileEntity.getWorld().markBlockRangeForRenderUpdate(Xpos , Ypos, Zpos , Xpos , Ypos, Zpos );
    //        } catch (Exception e) {}
    //    }
    //    super.onPostTick(aBaseMetaTileEntity, aTick);
    //}

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GT_MetaTileEntity_Hatch_Param(mName, mTier, mDescription, mTextures);
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                "Parametrizer ID: " + EnumChatFormatting.GREEN + param,
                "Value 0D: " + EnumChatFormatting.AQUA + value0D,
                "Value 1D: " + EnumChatFormatting.BLUE + value1D,
                "Input 0D: " + EnumChatFormatting.GOLD   + input0D,
                "Input 1D: " + EnumChatFormatting.YELLOW + input1D,
        };
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("ePointer", pointer);
        aNBT.setDouble("eValue0D", value0D);
        aNBT.setDouble("eValue1D", value1D);
        aNBT.setDouble("eInput0D", input0D);
        aNBT.setDouble("eInput1D", input1D);
        aNBT.setInteger("eParam", param);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        pointer = aNBT.getInteger("ePointer");
        if(aNBT.hasKey("eFloats") ||
                aNBT.hasKey("eValue0i") ||
                aNBT.hasKey("eValue1i") ||
                aNBT.hasKey("eInput0i") ||
                aNBT.hasKey("eInput1i")){
            boolean usesFloat = aNBT.getBoolean("eFloats");
            if(usesFloat){
                value0D=Float.intBitsToFloat(aNBT.getInteger("eValue0i"));
                value1D=Float.intBitsToFloat(aNBT.getInteger("eValue1i"));
                input0D=Float.intBitsToFloat(aNBT.getInteger("eInput0i"));
                input1D=Float.intBitsToFloat(aNBT.getInteger("eInput1i"));
            }else {
                value0D=aNBT.getInteger("eValue0i");
                value1D=aNBT.getInteger("eValue1i");
                input0D=aNBT.getInteger("eInput0i");
                input1D=aNBT.getInteger("eInput1i");
            }
        }else{
            value0D=aNBT.getDouble("eValue0D");
            value1D=aNBT.getDouble("eValue1D");
            input0D=aNBT.getDouble("eInput0D");
            input1D=aNBT.getDouble("eInput1D");
        }
        param = aNBT.getInteger("eParam");
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return false;
    }

    @Override
    public boolean isLiquidInput(byte aSide) {
        return false;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                CommonValues.TEC_MARK_GENERAL,
                mDescription,
                EnumChatFormatting.AQUA.toString() + EnumChatFormatting.BOLD + "E=mine*craft\u00b2"
        };
    }
}
