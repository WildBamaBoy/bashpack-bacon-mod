package bashpack.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

public class RenderGiantPig extends RenderPig
{
	public RenderGiantPig(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par2ModelBase, par3);
	}
	
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        float f1 = 3F;
        GL11.glScalef(f1 + 1, f1, f1);
    }
}
