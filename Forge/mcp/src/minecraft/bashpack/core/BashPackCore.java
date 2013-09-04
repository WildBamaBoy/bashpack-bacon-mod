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
		
		//Register food first.
		itemRawBacon = new ItemFood(modPropertiesManager.modProperties.itemID_RawBacon, 1, 0.3F, true).func_111206_d("bashpack:bacon_raw").setUnlocalizedName("rawbacon");
		itemSoggyBacon = new ItemFood(modPropertiesManager.modProperties.itemID_SoggyBacon, 2, 0.6F, true).func_111206_d("bashpack:bacon_soggy").setUnlocalizedName("soggybacon");
		itemLimpBacon = new ItemFood(modPropertiesManager.modProperties.itemID_LimpBacon, 4, 0.8F, true).func_111206_d("bashpack:bacon_limp").setUnlocalizedName("limpbacon");
		itemCrispyBacon = new ItemFood(modPropertiesManager.modProperties.itemID_CrispyBacon, 8, 1.2F, true).func_111206_d("bashpack:bacon_crispy").setUnlocalizedName("crispybacon");
		itemBurntBacon = new ItemFood(modPropertiesManager.modProperties.itemID_BurntBacon, 1, 0.1F, true).func_111206_d("bashpack:bacon_burnt").setUnlocalizedName("burntbacon");
		
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
		itemRawBacon = itemRawBacon.setCreativeTab(tabBashPackBacon);
		itemSoggyBacon = itemSoggyBacon.setCreativeTab(tabBashPackBacon);
		itemLimpBacon = itemLimpBacon.setCreativeTab(tabBashPackBacon);
		itemCrispyBacon = itemCrispyBacon.setCreativeTab(tabBashPackBacon);
		itemBurntBacon = itemBurntBacon.setCreativeTab(tabBashPackBacon);
		
		itemRawBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_RawBaconSword, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:raw_sword").setUnlocalizedName("raw_sword");
		itemRawBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_RawBaconPickaxe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:raw_pickaxe").setUnlocalizedName("raw_pickaxe");
		itemRawBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_RawBaconAxe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:raw_axe").setUnlocalizedName("raw_axe");
		itemRawBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_RawBaconShovel, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:raw_shovel").setUnlocalizedName("raw_shovel");
		itemRawBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_RawBaconHoe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:raw_hoe").setUnlocalizedName("raw_hoe");

		itemSoggyBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_SoggyBaconSword, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:soggy_sword").setUnlocalizedName("soggy_sword");
		itemSoggyBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_SoggyBaconPickaxe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:soggy_pickaxe").setUnlocalizedName("soggy_pickaxe");
		itemSoggyBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_SoggyBaconAxe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:soggy_axe").setUnlocalizedName("soggy_axe");
		itemSoggyBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_SoggyBaconShovel, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:soggy_shovel").setUnlocalizedName("soggy_shovel");
		itemSoggyBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_SoggyBaconHoe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:soggy_hoe").setUnlocalizedName("soggy_hoe");
		
		itemLimpBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_LimpBaconSword, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:limp_sword").setUnlocalizedName("limp_sword");
		itemLimpBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_LimpBaconPickaxe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:limp_pickaxe").setUnlocalizedName("limp_pickaxe");
		itemLimpBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_LimpBaconAxe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:limp_axe").setUnlocalizedName("limp_axe");
		itemLimpBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_LimpBaconShovel, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:limp_shovel").setUnlocalizedName("limp_shovel");
		itemLimpBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_LimpBaconHoe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:limp_hoe").setUnlocalizedName("limp_hoe");
		
		itemCrispyBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_CrispyBaconSword, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:crispy_sword").setUnlocalizedName("crispy_sword");
		itemCrispyBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_CrispyBaconPickaxe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:crispy_pickaxe").setUnlocalizedName("crispy_pickaxe");
		itemCrispyBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_CrispyBaconAxe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:crispy_axe").setUnlocalizedName("crispy_axe");
		itemCrispyBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_CrispyBaconShovel, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:crispy_shovel").setUnlocalizedName("crispy_shovel");
		itemCrispyBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_CrispyBaconHoe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:crispy_hoe").setUnlocalizedName("crispy_hoe");
		
		itemBurntBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_BurntBaconSword, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:burnt_sword").setUnlocalizedName("burnt_sword");
		itemBurntBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_BurntBaconPickaxe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:burnt_pickaxe").setUnlocalizedName("burnt_pickaxe");
		itemBurntBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_BurntBaconAxe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:burnt_axe").setUnlocalizedName("burnt_axe");
		itemBurntBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_BurntBaconShovel, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:burnt_shovel").setUnlocalizedName("burnt_shovel");
		itemBurntBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_BurntBaconHoe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).func_111206_d("bashpack:burnt_hoe").setUnlocalizedName("burnt_hoe");
		
		//Register recipes.
		GameRegistry.addShapelessRecipe(new ItemStack(itemRawBacon, 3), new Object[]{Item.porkRaw});
		
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
		
		//Add smeltings.
		GameRegistry.addSmelting(itemRawBacon.itemID, new ItemStack(itemSoggyBacon, 1), 0.2F);
		GameRegistry.addSmelting(itemSoggyBacon.itemID, new ItemStack(itemLimpBacon, 1), 0.4F);
		GameRegistry.addSmelting(itemLimpBacon.itemID, new ItemStack(itemCrispyBacon, 1), 0.7F);
		GameRegistry.addSmelting(itemCrispyBacon.itemID, new ItemStack(itemBurntBacon, 1), 0.0F);
		
		LanguageRegistry.addName(itemRawBacon, "Raw Bacon");
		LanguageRegistry.addName(itemSoggyBacon, "Soggy Bacon");
		LanguageRegistry.addName(itemLimpBacon, "Limp Bacon");
		LanguageRegistry.addName(itemCrispyBacon, "Crispy Bacon");
		LanguageRegistry.addName(itemBurntBacon, "Burnt Bacon");
		
		LanguageRegistry.addName(itemRawBaconSword, "Raw Bacon Sword");
		LanguageRegistry.addName(itemRawBaconPickaxe, "Raw Bacon Pickaxe");
		LanguageRegistry.addName(itemRawBaconAxe, "Raw Bacon Axe");
		LanguageRegistry.addName(itemRawBaconShovel, "Raw Bacon Shovel");
		LanguageRegistry.addName(itemRawBaconHoe, "Raw Bacon Hoe");
		LanguageRegistry.addName(itemSoggyBaconSword, "Soggy Bacon Sword");
		LanguageRegistry.addName(itemSoggyBaconPickaxe, "Soggy Bacon Pickaxe");
		LanguageRegistry.addName(itemSoggyBaconAxe, "Soggy Bacon Axe");
		LanguageRegistry.addName(itemSoggyBaconShovel, "Soggy Bacon Shovel");
		LanguageRegistry.addName(itemSoggyBaconHoe, "Soggy Bacon Hoe");
		LanguageRegistry.addName(itemLimpBaconSword, "Limp Bacon Sword");
		LanguageRegistry.addName(itemLimpBaconPickaxe, "Limp Bacon Pickaxe");
		LanguageRegistry.addName(itemLimpBaconAxe, "Limp Bacon Axe");
		LanguageRegistry.addName(itemLimpBaconShovel, "Limp Bacon Shovel");
		LanguageRegistry.addName(itemLimpBaconHoe, "Limp Bacon Hoe");
		LanguageRegistry.addName(itemCrispyBaconSword, "Crispy Bacon Sword");
		LanguageRegistry.addName(itemCrispyBaconPickaxe, "Crispy Bacon Pickaxe");
		LanguageRegistry.addName(itemCrispyBaconAxe, "Crispy Bacon Axe");
		LanguageRegistry.addName(itemCrispyBaconShovel, "Crispy Bacon Shovel");
		LanguageRegistry.addName(itemCrispyBaconHoe, "Crispy Bacon Hoe");
		LanguageRegistry.addName(itemBurntBaconSword, "Burnt Bacon Sword");
		LanguageRegistry.addName(itemBurntBaconPickaxe, "Burnt Bacon Pickaxe");
		LanguageRegistry.addName(itemBurntBaconAxe, "Burnt Bacon Axe");
		LanguageRegistry.addName(itemBurntBaconShovel, "Burnt Bacon Shovel");
		LanguageRegistry.addName(itemBurntBaconHoe, "Burnt Bacon Hoe");
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