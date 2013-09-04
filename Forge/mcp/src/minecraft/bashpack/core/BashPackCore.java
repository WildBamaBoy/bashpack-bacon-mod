package bashpack.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.EnumHelper;
import bashpack.core.forge.CommonProxy;
import bashpack.core.io.ModPropertiesManager;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
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
	
	//Materials.
	private static EnumToolMaterial toolMaterialRawBacon    = EnumHelper.addToolMaterial("RAW_BACON",    0, 16,  2.0F, 0.0F, 15);
	private static EnumToolMaterial toolMaterialSoggyBacon  = EnumHelper.addToolMaterial("SOGGY_BACON",  1, 100, 2.0F, 0.5F, 5);
	private static EnumToolMaterial toolMaterialLimpBacon   = EnumHelper.addToolMaterial("LIMP_BACON",   2, 150, 2.0F, 1.0F, 14);
	private static EnumToolMaterial toolMaterialCrispyBacon = EnumHelper.addToolMaterial("CRISPY_BACON", 3, 250, 3.0F, 1.5F, 10);
	private static EnumToolMaterial toolMaterialBurntBacon  = EnumHelper.addToolMaterial("BURNT_BACON",  0, 59,  0.0F, 0.0F, 22);
	
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
	public String runningDirectory = "";
	public ModPropertiesManager modPropertiesManager = null;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		
		//Set running directory.
		if (event.getSide() == Side.CLIENT)
		{
			runningDirectory = System.getProperty("user.dir");
		}

		else if (event.getSide() == Side.SERVER)
		{
			runningDirectory = System.getProperty("user.dir");
		}
		
		//Load external data and register things.
		modPropertiesManager = new ModPropertiesManager();
		
		//Register item used for icon.
		itemRawBacon = new ItemFood(modPropertiesManager.modProperties.itemID_RawBacon, 3, 0.3F, true).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:bacon_raw");

		//Register creative tab.
		tabBashPackBacon = new CreativeTabs("tabBashPack")
		{
			public ItemStack getIconItemStack()
			{
				return new ItemStack(itemRawBacon, 1, 0);
			}
		};
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabBashPack", "BashPack Bacon Mod");
		
		//Register items and blocks.
		itemRawBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_RawBaconSword, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon);
		itemRawBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_RawBaconPickaxe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon);
		itemRawBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_RawBaconAxe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon);
		itemRawBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_RawBaconShovel, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon);
		itemRawBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_RawBaconHoe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon);
		itemSoggyBacon = new ItemFood(modPropertiesManager.modProperties.itemID_SoggyBacon, 4, 0.6F, true).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:bacon_soggy");
		itemSoggyBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_SoggyBaconSword, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon);
		itemSoggyBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_SoggyBaconPickaxe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon);
		itemSoggyBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_SoggyBaconAxe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon);
		itemSoggyBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_SoggyBaconShovel, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon);
		itemSoggyBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_SoggyBaconHoe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon);
		itemLimpBacon = new ItemFood(modPropertiesManager.modProperties.itemID_LimpBacon, 5, 0.8F, true).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:bacon_limp");
		itemLimpBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_LimpBaconSword, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon);
		itemLimpBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_LimpBaconPickaxe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon);
		itemLimpBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_LimpBaconAxe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon);
		itemLimpBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_LimpBaconShovel, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon);
		itemLimpBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_LimpBaconHoe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon);
		itemCrispyBacon = new ItemFood(modPropertiesManager.modProperties.itemID_CrispyBacon, 10, 1.2F, true).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:bacon_crispy");
		itemCrispyBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_CrispyBaconSword, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon);
		itemCrispyBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_CrispyBaconPickaxe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon);
		itemCrispyBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_CrispyBaconAxe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon);
		itemCrispyBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_CrispyBaconShovel, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon);
		itemCrispyBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_CrispyBaconHoe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon);
		itemBurntBacon = new ItemFood(modPropertiesManager.modProperties.itemID_BurntBacon, 1, 0.1F, true).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:bacon_burnt");
		itemBurntBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_BurntBaconSword, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon);
		itemBurntBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_BurntBaconPickaxe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon);
		itemBurntBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_BurntBaconAxe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon);
		itemBurntBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_BurntBaconShovel, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon);
		itemBurntBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_BurntBaconHoe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon);
		
		//Register recipes.
		GameRegistry.addRecipe(new ItemStack(itemRawBaconSword, 1), new Object[]
				{
			" B ", " B ", " S ", 'B', itemRawBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconPickaxe, 1), new Object[]
				{
			"BBB", " S ", " S ", 'B', itemRawBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconAxe, 1), new Object[]
				{
			"BB ", "BS ", " S ", 'B', itemRawBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconShovel, 1), new Object[]
				{
			" B ", " S ", " S ", 'B', itemRawBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconHoe, 1), new Object[]
				{
			"BB ", " S ", " S ", 'B', itemRawBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconSword, 1), new Object[]
				{
			" B ", " B ", " S ", 'B', itemSoggyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconPickaxe, 1), new Object[]
				{
			"BBB", " S ", " S ", 'B', itemSoggyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconAxe, 1), new Object[]
				{
			"BB ", "BS ", " S ", 'B', itemSoggyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconShovel, 1), new Object[]
				{
			" B ", " S ", " S ", 'B', itemSoggyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconHoe, 1), new Object[]
				{
			"BB ", " S ", " S ", 'B', itemSoggyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconSword, 1), new Object[]
				{
			" B ", " B ", " S ", 'B', itemLimpBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconPickaxe, 1), new Object[]
				{
			"BBB", " S ", " S ", 'B', itemLimpBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconAxe, 1), new Object[]
				{
			"BB ", "BS ", " S ", 'B', itemLimpBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconShovel, 1), new Object[]
				{
			" B ", " S ", " S ", 'B', itemLimpBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconHoe, 1), new Object[]
				{
			"BB ", " S ", " S ", 'B', itemLimpBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconSword, 1), new Object[]
				{
			" B ", " B ", " S ", 'B', itemCrispyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconPickaxe, 1), new Object[]
				{
			"BBB", " S ", " S ", 'B', itemCrispyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconAxe, 1), new Object[]
				{
			"BB ", "BS ", " S ", 'B', itemCrispyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconShovel, 1), new Object[]
				{
			" B ", " S ", " S ", 'B', itemCrispyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconHoe, 1), new Object[]
				{
			"BB ", " S ", " S ", 'B', itemCrispyBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconSword, 1), new Object[]
				{
			" B ", " B ", " S ", 'B', itemBurntBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconPickaxe, 1), new Object[]
				{
			"BBB", " S ", " S ", 'B', itemBurntBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconAxe, 1), new Object[]
				{
			"BB ", "BS ", " S ", 'B', itemBurntBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconShovel, 1), new Object[]
				{
			" B ", " S ", " S ", 'B', itemBurntBacon, 'S', Item.stick
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconHoe, 1), new Object[]
				{
			"BB ", " S ", " S ", 'B', itemBurntBacon, 'S', Item.stick
				});
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