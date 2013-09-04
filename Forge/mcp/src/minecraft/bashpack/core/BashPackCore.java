package petergriffin.core;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import petergriffin.core.forge.CommonProxy;
import petergriffin.entity.EntityPeter;
import petergriffin.item.ItemPeterEgg;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="petergriffin", name="Peter Griffin Mod", version="1.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class PeterGriffin
{
	@Instance("petergriffin")
	public static PeterGriffin instance;
	
	@SidedProxy(clientSide="petergriffin.core.forge.ClientProxy", serverSide="petergriffin.core.forge.CommonProxy")
	public static CommonProxy proxy;

	public Item itemPeterEgg;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		EntityRegistry.registerModEntity(EntityPeter.class, EntityPeter.class.getSimpleName(), 1, this, 50, 2, true);
		EntityRegistry.addSpawn(EntityPeter.class, 2, 0, 1, EnumCreatureType.creature, BiomeGenBase.forest);
		proxy.registerRenderers();
		proxy.registerSound();
		
		itemPeterEgg = new ItemPeterEgg(26698);
		GameRegistry.registerItem(itemPeterEgg, "SpawnPeterGriffin");
		
		LanguageRegistry.instance().addStringLocalization("entity." + EntityPeter.class.getSimpleName() + ".name", "en_US", "Peter Griffin");
		LanguageRegistry.addName(itemPeterEgg, "Spawn Peter Griffin");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}