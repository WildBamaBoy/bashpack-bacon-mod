package bashpack.core;

import net.minecraft.item.Item;
import bashpack.core.forge.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="bashpack", name="BashPack Bacon Mod", version="1.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class BashPackCore
{
	@Instance("bashpack")
	public static BashPackCore instance;
	
	@SidedProxy(clientSide="bashpack.core.forge.ClientProxy", serverSide="bashpack.core.forge.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}