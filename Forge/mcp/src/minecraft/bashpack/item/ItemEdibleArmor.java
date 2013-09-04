package bashpack.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEdibleArmor extends ItemArmor
{
    public  final int itemUseDuration = 32;
    private final int healAmount;
    private final float saturationModifier;
    private boolean alwaysEdible = false;

	public ItemEdibleArmor(int id, EnumArmorMaterial armorMaterial, int renderIndex, int armorType, int healAmount, float saturationModifier)
	{
		super(id, armorMaterial, renderIndex, armorType);
		this.healAmount = healAmount;
		this.saturationModifier = saturationModifier;
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
        return itemStack;
    }
    
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }
}
