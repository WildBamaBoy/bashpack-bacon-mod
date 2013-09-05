package mods.bashpack.item;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemAssetBypassFood extends ItemFood
{
	private String textureLocation;
	
	public ItemAssetBypassFood(int par1, int par2, float par3, boolean par4)
	{
		super(par1, par2, par3, par4);
	}
	
	public Item func_111206_d(String textureLocation)
	{
		this.textureLocation = textureLocation;
		return this;
	}
	
	public void registerIcons(IconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(textureLocation);
	}
}
