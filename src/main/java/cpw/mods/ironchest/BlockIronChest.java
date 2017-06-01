/*******************************************************************************
 * Copyright (c) 2012 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Contributors:
 * cpw - initial API and implementation
 ******************************************************************************/
package cpw.mods.ironchest;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockIronChest extends Block
{
    public static final PropertyEnum<IronChestType> VARIANT_PROP = PropertyEnum.create("variant", IronChestType.class);

    public BlockIronChest()
    {
        super(Material.IRON);
        this.setRegistryName(new ResourceLocation(IronChest.MOD_ID, "iron_chest"));

        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, IronChestType.IRON));

        this.setHardness(3.0F);
        this.setUnlocalizedName("IronChest");
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return Blocks.ENDER_CHEST.getBoundingBox(state, source, pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof TileEntityIronChest && !worldIn.isRemote && !worldIn.isSideSolid(pos.add(0, 1, 0), EnumFacing.DOWN))
        {
        	TileEntityIronChest teic = (TileEntityIronChest) te;
        	playerIn.openGui(IronChest.instance, teic.getType().ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return state.getValue(VARIANT_PROP).makeEntity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (IronChestType type : IronChestType.VALUES)
        {
            if (type.isValidForCreativeMode())
            {
                list.add(new ItemStack(itemIn, 1, type.ordinal()));
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT_PROP, IronChestType.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState blockState)
    {
        return blockState.getValue(VARIANT_PROP).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT_PROP);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(world, pos, state);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof TileEntityIronChest)
        {
            TileEntityIronChest teic = (TileEntityIronChest) te;
            teic.wasPlaced(placer, stack);
            teic.setFacing(placer.getHorizontalFacing().getOpposite());
            worldIn.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT_PROP).ordinal();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof TileEntityIronChest)
        {
        	TileEntityIronChest teic = (TileEntityIronChest) te;
            teic.removeAdornments();
            InventoryHelper.dropInventoryItems(worldIn, pos, teic);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
    {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TileEntityIronChest)
        {
            TileEntityIronChest teic = (TileEntityIronChest) te;

            if (teic.getType().isExplosionResistant())
            {
                return 10000F;
            }
        }

        return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
    {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    private static final EnumFacing[] validRotationAxes = new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };

    @Override
    public EnumFacing[] getValidRotations(World worldObj, BlockPos pos)
    {
        return validRotationAxes;
    }

    @Override
    public boolean rotateBlock(World worldObj, BlockPos pos, EnumFacing axis)
    {
        if (worldObj.isRemote)
        {
            return false;
        }
        if (axis == EnumFacing.UP || axis == EnumFacing.DOWN)
        {
            TileEntity te = worldObj.getTileEntity(pos);

            if (te instanceof TileEntityIronChest)
            {
                TileEntityIronChest icte = (TileEntityIronChest) te;
                icte.rotateAround();
            }
            return true;
        }
        return false;
    }

    @Override
    @Deprecated
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity te = worldIn.getTileEntity(pos);
        return te != null && te.receiveClientEvent(id, param);
    }
}
