package bashpack.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.server.MinecraftServer;
import bashpack.core.forge.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid="bashpack", name="BashPack Bacon Mod", version="1.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class BashPackCore
{
	@Instance("bashpack")
	public static BashPackCore instance;

	@SidedProxy(clientSide="bashpack.core.forge.ClientProxy", serverSide="bashpack.core.forge.CommonProxy")
	public static CommonProxy proxy;
	
	//Creative tab.
	public CreativeTabs tabBashPackBacon;
	
	//Items and Blocks
	public Item itemRawBacon;
	public Item itemRawBaconSword;
	public Item itemRawBaconPickaxe;
	public Item itemRawBaconAxe;
	public Item itemRawBaconShovel;
	public Item itemRawBaconHoe;
	
	public Item itemSoggyBacon;
	public Item itemSoggyBaconSword;
	public Item itemSoggyBaconPickaxe;
	public Item itemSoggyBaconAxe;
	public Item itemSoggyBaconShovel;
	public Item itemSoggyBaconHoe;
	
	public Item itemLimpBacon;
	public Item itemLimpBaconSword;
	public Item itemLimpBaconPickaxe;
	public Item itemLimpBaconAxe;
	public Item itemLimpBaconShovel;
	public Item itemLimpBaconHoe;
	
	public Item itemCrispyBacon;
	public Item itemCrispyBaconSword;
	public Item itemCrispyBaconPickaxe;
	public Item itemCrispyBaconAxe;
	public Item itemCrispyBaconShovel;
	public Item itemCrispyBaconHoe;
	
	public Item itemBurntBacon;
	public Item itemBurntBaconSword;
	public Item itemBurntBaconPickaxe;
	public Item itemBurntBaconAxe;
	public Item itemBurntBaconShovel;
	public Item itemBurntBaconHoe;
	
	public ItemArmor itemRawBaconHelmet;
	public ItemArmor itemRawBaconChestplate;
	public ItemArmor itemRawBaconLeggings;
	public ItemArmor itemRawBaconBoots;
	
	public ItemArmor itemSoggyBaconHelmet;
	public ItemArmor itemSoggyBaconChestplate;
	public ItemArmor itemSoggyBaconLeggings;
	public ItemArmor itemSoggyBaconBoots;
	
	public ItemArmor itemLimpBaconHelmet;
	public ItemArmor itemLimpBaconChestplate;
	public ItemArmor itemLimpBaconLeggings;
	public ItemArmor itemLimpBaconBoots;
	
	public ItemArmor itemCrispyBaconHelmet;
	public ItemArmor itemCrispyBaconChestplate;
	public ItemArmor itemCrispyBaconLeggings;
	public ItemArmor itemCrispyBaconBoots;
	
	public ItemArmor itemBurntBaconHelmet;
	public ItemArmor itemBurntBaconChestplate;
	public ItemArmor itemBurntBaconLeggings;
	public ItemArmor itemBurntBaconBoots;
	
	public Block blockRawBacon;
	public Block blockSoggyBacon;
	public Block blockLimpBacon;
	public Block blockCrispyBacon;
	public Block blockBurntBacon;
	
	private Logger logger = FMLLog.getLogger();
	public  String 	runningDirectory = "";
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
	
	/**
	 * Writes the specified object's string representation to System.out.
	 * 
	 * @param 	obj	The object to write to System.out.
	 */
	public void log(Object obj)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();

		if (obj instanceof Throwable)
		{
			((Throwable)obj).printStackTrace();
		}

		try
		{
			logger.log(Level.FINER, "BashPack " + side.toString() + ": " + obj.toString());
			System.out.println("BashPack " + side.toString() + ": " + obj.toString());

			MinecraftServer server = MinecraftServer.getServer();

			if (server != null)
			{
				if (server.isDedicatedServer())
				{
					MinecraftServer.getServer().logInfo("BashPack: " + obj.toString());
				}
			}
		}

		catch (NullPointerException e)
		{
			logger.log(Level.FINER, "BashPack " + side.toString() + ": null");
			System.out.println("BashPack: null");

			MinecraftServer server = MinecraftServer.getServer();

			if (server != null)
			{
				if (server.isDedicatedServer())
				{
					MinecraftServer.getServer().logDebug("BashPack " + side.toString() + ": null");
				}
			}
		}
	}
	
	/**
	 * Stops the game and writes the error to the Forge crash log.
	 * 
	 * @param 	description	A string providing a short description of the problem.
	 * @param 	e			The exception that caused this method to be called.
	 */
	@SideOnly(Side.CLIENT)
	public void quitWithError(String description, Throwable e)
	{
		Writer stackTrace = new StringWriter();

		PrintWriter stackTraceWriter = new PrintWriter(stackTrace);
		e.printStackTrace(stackTraceWriter);

		logger.log(Level.FINER, "Minecraft Comes Alive: An exception occurred.\n" + stackTrace.toString());
		System.out.println("Minecraft Comes Alive: An exception occurred.\n" + stackTrace.toString());

		CrashReport crashReport = new CrashReport("MCA: " + description, e);
		net.minecraft.client.Minecraft.getMinecraft().crashed(crashReport);
	}
}