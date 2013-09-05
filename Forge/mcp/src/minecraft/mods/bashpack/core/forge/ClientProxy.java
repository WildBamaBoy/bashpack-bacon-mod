package mods.bashpack.core.forge;

import mods.bashpack.client.render.RenderGiantPig;
import mods.bashpack.entity.EntityGiantPig;
import net.minecraft.client.model.ModelPig;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy
{
	public void registerRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGiantPig.class, new RenderGiantPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
	}
}
