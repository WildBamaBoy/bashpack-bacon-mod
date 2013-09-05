package mods.bashpack.block;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class BlockAssetBypass extends Block
{
	private String textureLocation;
	
	public BlockAssetBypass(int par1, Material par2Material) 
	{
		super(par1, par2Material);
	}
	
	public Block func_111022_d(String textureLocation)
	{
		this.textureLocation = textureLocation;
		return this;
	}
	
	public void registerIcons(IconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(textureLocation);
	}
}
