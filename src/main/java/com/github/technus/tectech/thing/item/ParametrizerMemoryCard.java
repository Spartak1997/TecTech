package com.github.technus.tectech.thing.item;

import com.github.technus.tectech.CommonValues;
import com.github.technus.tectech.Util;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_Param;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

import static com.github.technus.tectech.Reference.MODID;
import static com.github.technus.tectech.thing.CustomItemList.parametrizerMemory;

/**
 * Created by Tec on 15.03.2017.
 */
public final class ParametrizerMemoryCard extends Item {
    public static ParametrizerMemoryCard INSTANCE;
    public static IIcon locked,unlocked;

    private ParametrizerMemoryCard() {
        setMaxStackSize(1);
        setHasSubtypes(true);
        setUnlocalizedName("em.parametrizerMemoryCard");
        setTextureName(MODID + ":itemParametrizerMemoryCardUnlocked");
    }

    @Override
    public boolean onItemUseFirst(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (aPlayer instanceof EntityPlayerMP) {
            aStack.stackSize = 1;
            if (tTileEntity instanceof IGregTechTileEntity) {
                IMetaTileEntity metaTE = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
                if (metaTE != null) {
                    if (metaTE instanceof GT_MetaTileEntity_Hatch_Param) {
                        GT_MetaTileEntity_Hatch_Param parametrizer = (GT_MetaTileEntity_Hatch_Param) metaTE;
                        if (aStack.getTagCompound() == null) {
                            aStack.setTagCompound(new NBTTagCompound());
                        }
                        NBTTagCompound tNBT = aStack.getTagCompound();
                        if (aStack.getItemDamage() == 1) {
                            //write to parametrizer
                            parametrizer.param = tNBT.getInteger("param");
                            parametrizer.value0D = tNBT.getDouble("value0D");
                            parametrizer.value1D = tNBT.getDouble("value1D");
                        } else {
                            //read from parametrizer
                            tNBT.setInteger("param", parametrizer.param);
                            tNBT.setDouble("value0D", parametrizer.value0D);
                            tNBT.setDouble("value1D", parametrizer.value1D);
                        }
                        return true;
                    }else if(metaTE instanceof GT_MetaTileEntity_MultiblockBase_EM){
                        GT_MetaTileEntity_MultiblockBase_EM base = (GT_MetaTileEntity_MultiblockBase_EM) metaTE;
                        if (aStack.getTagCompound() == null) {
                            aStack.setTagCompound(new NBTTagCompound());
                        }
                        NBTTagCompound tNBT = aStack.getTagCompound();
                        if(aStack.getItemDamage()== 1){
                            base.parametrization.trySetParameters(
                                    tNBT.getInteger("param"),
                                    tNBT.getDouble("value0D"),
                                    tNBT.getDouble("value1D"));
                            return true;
                        }
                    }
                }
            }
        }
        return aPlayer instanceof EntityPlayerMP;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (aPlayer instanceof EntityPlayerMP && aPlayer.isSneaking()) {
            aStack.stackSize = 1;
            if(aStack.getItemDamage()==1) {
                aStack.setItemDamage(0);
            }else{
                aStack.setItemDamage(1);
            }
            return aStack;
        }
        return aStack;
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer ep, List aList, boolean boo) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        aList.add(CommonValues.BASS_MARK);
        aList.add("Stores Parameters");

        if(aStack.getItemDamage()==1) {
            aList.add(EnumChatFormatting.BLUE + "Use on Parametrizer/Controller to configure it");
        }else{
            aList.add(EnumChatFormatting.BLUE + "Use on Parametrizer to store parameters");
        }
        aList.add(EnumChatFormatting.BLUE + "Sneak right click to lock/unlock");

        long temp;
        if(tNBT!=null && tNBT.hasKey("param")) {
            aList.add("Hatch ID: "+EnumChatFormatting.AQUA + tNBT.getInteger("param"));
            temp=tNBT.getInteger("value0D");
            aList.add("Value 0D: "+EnumChatFormatting.AQUA + temp);
            aList.add("Value 0B: "+EnumChatFormatting.AQUA + Util.longBitsToShortString(Double.doubleToLongBits(temp)));
            temp=tNBT.getInteger("value1D");
            aList.add("Value 1D: "+EnumChatFormatting.AQUA + temp);
            aList.add("Value 1B: "+EnumChatFormatting.AQUA + Util.longBitsToShortString(Double.doubleToLongBits(temp)));
            aList.add("Uses Floats: "+(tNBT.getBoolean("usesFloats")?EnumChatFormatting.GREEN+"TRUE":EnumChatFormatting.RED+"FALSE"));
        }

    }

    public static void run() {
        INSTANCE = new ParametrizerMemoryCard();
        GameRegistry.registerItem(INSTANCE, INSTANCE.getUnlocalizedName());
        parametrizerMemory.set(INSTANCE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        locked =iconRegister.registerIcon(MODID + ":itemParametrizerMemoryCardLocked");
        unlocked = itemIcon = iconRegister.registerIcon(getIconString());
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        if(damage==1) {
            return locked;
        }else{
            return unlocked;
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        ItemStack that = new ItemStack(this, 1);
        that.setTagCompound(new NBTTagCompound());
        list.add(that);
    }
}
