package com.github.technus.tectech.thing.metaTileEntity.multi;

import com.github.technus.tectech.CommonValues;
import com.github.technus.tectech.Reference;
import com.github.technus.tectech.mechanics.dataTransport.InventoryDataPacket;
import com.github.technus.tectech.thing.metaTileEntity.IConstructable;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_InputDataItems;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_OutputDataItems;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_Container_MultiMachineEM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_GUIContainer_MultiMachineEM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.IHatchAdder;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedTexture;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DataAccess;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

import static com.github.technus.tectech.CommonValues.V;
import static com.github.technus.tectech.Util.StructureBuilderExtreme;
import static com.github.technus.tectech.recipe.TT_recipeAdder.nullItem;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.textureOffset;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;

public class GT_MetaTileEntity_EM_dataBank extends GT_MetaTileEntity_MultiblockBase_EM implements IConstructable {
    private final ArrayList<GT_MetaTileEntity_Hatch_OutputDataItems> eStacksDataOutputs = new ArrayList<>();
    private final ArrayList<GT_MetaTileEntity_Hatch_DataAccess> eDataAccessHatches = new ArrayList<>();

    //region Structure
    private static final String[][] shape = new String[][]{
            {"0   0","0 . 0","0   0",},
            {"0!!!0","01110","0!!!0",},
            {"0!!!0","0!!!0","0!!!0",},
    };
    private static final Block[] blockType = new Block[]{sBlockCasingsTT,sBlockCasingsTT};
    private static final byte[] blockMeta = new byte[]{2,1};
    private final IHatchAdder[] addingMethods = new IHatchAdder[]{this::addClassicToMachineList,this::addDataBankHatchToMachineList};
    private static final short[] casingTextures = new short[]{textureOffset,textureOffset+1};
    private static final Block[] blockTypeFallback = new Block[]{sBlockCasingsTT,sBlockCasingsTT};
    private static final byte[] blockMetaFallback = new byte[]{0,1};
    private static final String[] description = new String[]{
            EnumChatFormatting.AQUA+"Hint Details:",
            "1 - Classic Hatches or high power casing",
            "2 - Data Access/Data Bank Master Hatches or computer casing",
    };
    //endregion

    public GT_MetaTileEntity_EM_dataBank(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_dataBank(String aName) {
        super(aName);
    }

    public final static ResourceLocation activitySound=new ResourceLocation(Reference.MODID+":fx_hi_freq");

    @Override
    @SideOnly(Side.CLIENT)
    protected ResourceLocation getActivitySound(){
        return activitySound;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_dataBank(mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.casingTexturePages[texturePage][1], new TT_RenderedTexture(aActive ? GT_MetaTileEntity_MultiblockBase_EM.ScreenON : GT_MetaTileEntity_MultiblockBase_EM.ScreenOFF)};
        }
        return new ITexture[]{Textures.BlockIcons.casingTexturePages[texturePage][1]};
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiMachineEM(aPlayerInventory, aBaseMetaTileEntity, true, false, true);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachineEM(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "EMDisplay.png", true, false, true);//todo texture
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        eDataAccessHatches.clear();
        eStacksDataOutputs.clear();
        return structureCheck_EM(shape, blockType, blockMeta, addingMethods, casingTextures, blockTypeFallback, blockMetaFallback, 2, 1, 0);
    }

    @Override
    public void construct(int stackSize, boolean hintsOnly) {
        StructureBuilderExtreme(shape, blockType, blockMeta,2, 1, 0, getBaseMetaTileEntity(),this,hintsOnly);
    }

    @Override
    public String[] getStructureDescription(int stackSize) {
        return description;
    }

    @Override
    public boolean checkRecipe_EM(ItemStack itemStack) {
        if (eDataAccessHatches.size() > 0 && eStacksDataOutputs.size() > 0) {
            mEUt = -(int)V[4];
            eAmpereFlow = 1 + eStacksDataOutputs.size()*eDataAccessHatches.size();
            mMaxProgresstime = 20;
            mEfficiencyIncrease = 10000;
            return true;
        }
        return false;
    }

    @Override
    public void outputAfterRecipe_EM() {
        ArrayList<ItemStack> stacks=new ArrayList<>();
        for(GT_MetaTileEntity_Hatch_DataAccess dataAccess:eDataAccessHatches){
            int count=dataAccess.getSizeInventory();
            for(int i=0;i<count;i++){
                ItemStack stack=dataAccess.getStackInSlot(i);
                if(stack!=null){
                    stacks.add(stack);
                }
            }
        }
        if(stacks.size()>0){
            ItemStack[] arr=stacks.toArray(nullItem);
            for(GT_MetaTileEntity_Hatch_OutputDataItems hatch:eStacksDataOutputs){
                hatch.q=new InventoryDataPacket(arr);
            }
        }else{
            for(GT_MetaTileEntity_Hatch_OutputDataItems hatch:eStacksDataOutputs){
                hatch.q=null;
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                CommonValues.TEC_MARK_EM,
                "Remote assembly data delivery",
                EnumChatFormatting.AQUA.toString() + EnumChatFormatting.BOLD + "Apply directly to the forehead"
        };
    }

    //NEW METHOD
    public final boolean addDataBankHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_OutputDataItems) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return eStacksDataOutputs.add((GT_MetaTileEntity_Hatch_OutputDataItems) aMetaTileEntity);
        }else if(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_DataAccess && !(aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_InputDataItems)){
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return eDataAccessHatches.add((GT_MetaTileEntity_Hatch_DataAccess) aMetaTileEntity);
        }
        return false;
    }
}
