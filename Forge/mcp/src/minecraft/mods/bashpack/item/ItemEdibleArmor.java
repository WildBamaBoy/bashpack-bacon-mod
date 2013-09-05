package mods.bashpack.item;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEdibleArmor extends ItemAssetBypassArmor
{
    public  final int itemUseDuration = 32;
    private final int healAmount;
    private final float saturationModifier;
    private boolean alwaysEdible = false;
    private String armorTextureName;
    private String iconTextureName;
    
	public ItemEdibleArmor(int id, EnumArmorMaterial armorMaterial, String armorTextureName, int renderIndex, int armorType, int healAmount, float saturationModifier)
	{
		super(id, armorMaterial, renderIndex, armorType);
		this.healAmount = healAmount;
		this.saturationModifier = saturationModifier;
		
		if (armorType == 2)
		{
			this.armorTextureName = "/mods/bashpack/textures/armor/" + armorTextureName + "_layer_2.png";
		}
		
		else
		{
			this.armorTextureName = "/mods/bashpack/textures/armor/" + armorTextureName + "_layer_1.png";
		}
	}
	
	@Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.eat;
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (entityPlayer.canEat(this.alwaysEdible))
        {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }

        return itemStack;
    }
    
	@Override
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        --itemStack.stackSize;
        world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        entityPlayer.getFoodStats().addStats(healAmount, saturationModifier);
        return itemStack;
    }
    
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) 
	{
		return armorTextureName;
	}
	
	public Item func_111206_d(String textureLocation)
	{
		this.iconTextureName = textureLocation;
		return this;
	}
	
	public void registerIcons(IconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(iconTextureName);
	}
}
