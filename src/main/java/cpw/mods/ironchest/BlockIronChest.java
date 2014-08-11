package cpw.mods.ironchest;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIronChest extends BlockContainer {
	@SideOnly(Side.CLIENT)
    public static IIcon icon[] = new IIcon[IronChestType.values().length];

    public BlockIronChest()
    {
        super(Material.iron);
        setBlockName("IronChest");
        setHardness(3.0F);
        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
    	return IronChestType.makeEntity(meta);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return 22;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister r)
    {
    	for (IronChestType type : IronChestType.values())
    	{
    		if (type.isValidForCreativeMode())
    		{
    			icon[type.ordinal()] = r.registerIcon("ironchest:" + type.name().toLowerCase());
    		}
    	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return icon[meta];
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> items = Lists.newArrayList();
        ItemStack stack = new ItemStack(this,1,metadata);
        IronChestType.values()[IronChestType.validateMeta(metadata)].adornItemDrop(stack);
        items.add(stack);
        return items;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3)
    {
        TileEntity te = world.getTileEntity(i, j, k);

        if (te == null || !(te instanceof TileEntityIronChest) || world.isSideSolid(i, j + 1, k, ForgeDirection.DOWN) || world.isRemote)
        {
            return true;
        }

        player.openGui(IronChest.instance, ((TileEntityIronChest) te).getType().ordinal(), world, i, j, k);
        return true;
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        world.markBlockForUpdate(i, j, k);
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack)
    {
        byte chestFacing = 0;
        int facing = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if (facing == 0)
        {
            chestFacing = 2;
        }
        if (facing == 1)
        {
            chestFacing = 5;
        }
        if (facing == 2)
        {
            chestFacing = 3;
        }
        if (facing == 3)
        {
            chestFacing = 4;
        }
        TileEntity te = world.getTileEntity(i, j, k);
        if (te != null && te instanceof TileEntityIronChest)
        {
            TileEntityIronChest teic = (TileEntityIronChest) te;
            teic.wasPlaced(entityliving, itemStack);
            teic.setFacing(chestFacing);
            world.markBlockForUpdate(i, j, k);
        }
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @Override
    public void breakBlock(World world, int i, int j, int k, Block i1, int i2)
    {
        TileEntityIronChest tileentitychest = (TileEntityIronChest) world.getTileEntity(i, j, k);
        if (tileentitychest != null)
        {
            tileentitychest.removeAdornments();
            dropItems(0, tileentitychest, world, tileentitychest.xCoord, tileentitychest.yCoord, tileentitychest.zCoord);
        }
        super.breakBlock(world, i, j, k, i1, i2);
    }

    public void dropItems(int newSize, IInventory chest, World world, int i, int j, int k)
    {
    	for (int i1 = 0; i1 < chest.getSizeInventory(); ++i1)
        {
    		Random rand = new Random();
            ItemStack is = chest.getStackInSlot(i1);

            if (is != null)
            {
                EntityItem entityitem;

                for (float f = rand.nextFloat() * 0.8F + 0.1F; is.stackSize > 0; world.spawnEntityInWorld(entityitem))
                {
                    int j1 = rand.nextInt(21) + 10;

                    if (j1 > is.stackSize)
                    {
                        j1 = is.stackSize;
                    }

                    is.stackSize -= j1;
                    entityitem = new EntityItem(world, (double)((float)i + f), (double)((float)j + f), (double)((float)k + f), new ItemStack(is.getItem(), j1, is.getItemDamage()));
                    float f2 = 0.05F;
                    entityitem.motionX = (double)((float)rand.nextGaussian() * f2);
                    entityitem.motionY = (double)((float)rand.nextGaussian() * f2 + 0.2F);
                    entityitem.motionZ = (double)((float)rand.nextGaussian() * f2);

                    if (is.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)is.getTagCompound().copy());
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (IronChestType type : IronChestType.values())
        {
            if (type.isValidForCreativeMode())
            {
                par3List.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
       TileEntity te = world.getTileEntity(x, y, z);
       if (te instanceof TileEntityIronChest)
       {
           TileEntityIronChest teic = (TileEntityIronChest) te;
           if (teic.getType().isExplosionResistant())
           {
               return 10000f;
           }
       }
       return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        TileEntity te = par1World.getTileEntity(par2, par3, par4);
        if (te instanceof IInventory)
        {
            return Container.calcRedstoneFromInventory((IInventory)te);
        }
        return 0;
    }

    @Override
    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z)
    {
        return new ForgeDirection[] { UP, DOWN };
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis)
    {
        if (worldObj.isRemote)
        {
            return false;
        }
        if (axis == UP || axis == DOWN)
        {
            TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityIronChest) {
                TileEntityIronChest icte = (TileEntityIronChest) tileEntity;
                icte.rotateAround(axis);
            }
            return true;
        }
        return false;
    }

}
