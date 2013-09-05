package mods.bashpack.item;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;

public class ItemAssetBypassPickaxe extends ItemPickaxe
{
	private String textureLocation;
	
	public ItemAssetBypassPickaxe(int par1, EnumToolMaterial par2EnumToolMaterial) 
	{
		super(par1, par2EnumToolMaterial);
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
