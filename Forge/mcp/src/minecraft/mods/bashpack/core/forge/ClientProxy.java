package bashpack.core.forge;

import net.minecraft.client.model.ModelPig;
import bashpack.client.render.RenderGiantPig;
import bashpack.entity.EntityGiantPig;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy
{
	public void registerRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGiantPig.class, new RenderGiantPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
	}
}
