package bashpack.entity;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityGiantPig extends EntityPig
{
	public EntityGiantPig(World par1World)
	{
		super(par1World);
		this.setSize(3F, 3F);
	}
	
    protected void dropFewItems(boolean par1, int par2)
    {
        int j = this.rand.nextInt(9) + 4;

        for (int k = 0; k < j; ++k)
        {
            if (this.isBurning())
            {
                this.dropItem(Item.porkCooked.itemID, 1);
            }
            else
            {
                this.dropItem(Item.porkRaw.itemID, 1);
            }
        }

        if (this.getSaddled())
        {
            this.dropItem(Item.saddle.itemID, 1);
        }
    }
}
