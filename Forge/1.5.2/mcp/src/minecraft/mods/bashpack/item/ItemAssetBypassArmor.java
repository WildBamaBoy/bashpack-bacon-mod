package mods.bashpack.item;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class ItemAssetBypassArmor extends ItemArmor
{
	private String textureLocation;
	
	public ItemAssetBypassArmor(int id, EnumArmorMaterial armorMaterial, int renderIndex, int armorType)
	{
		super(id, armorMaterial, renderIndex, armorType);
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
