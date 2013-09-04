package petergriffin.core.forge;

import net.minecraftforge.common.MinecraftForge;
import petergriffin.client.render.RenderPeter;
import petergriffin.entity.EntityPeter;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPeter.class, new RenderPeter());
	}
	
	@Override
	public void registerSound()
	{
		MinecraftForge.EVENT_BUS.register(new EventHooks());
	}
}
