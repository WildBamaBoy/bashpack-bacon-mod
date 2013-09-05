package mods.bashpack.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import mods.bashpack.block.BlockAssetBypass;
import mods.bashpack.block.BlockBarbecueFire;
import mods.bashpack.block.BlockPortal;
import mods.bashpack.core.forge.CommonProxy;
import mods.bashpack.core.io.ModPropertiesManager;
import mods.bashpack.entity.EntityGiantPig;
import mods.bashpack.item.ItemAssetBypassAxe;
import mods.bashpack.item.ItemAssetBypassFood;
import mods.bashpack.item.ItemAssetBypassHoe;
import mods.bashpack.item.ItemAssetBypassPickaxe;
import mods.bashpack.item.ItemAssetBypassShovel;
import mods.bashpack.item.ItemAssetBypassSword;
import mods.bashpack.item.ItemBarbecueLighter;
import mods.bashpack.item.ItemEdibleArmor;
import mods.bashpack.world.WorldProviderBashurverse;
import mods.bashpack.world.biomes.BiomeGenBashurverse;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
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

	@SidedProxy(clientSide="mods.bashpack.core.forge.ClientProxy", serverSide="mods.bashpack.core.forge.CommonProxy")
	public static CommonProxy proxy;

	//Creative tab.
	public CreativeTabs tabBashPackBacon;

	//Materials.
	private static EnumToolMaterial toolMaterialRawBacon    = EnumHelper.addToolMaterial("RAW_BACON",    0, 16,  2.0F, 1, 15);
	private static EnumToolMaterial toolMaterialSoggyBacon  = EnumHelper.addToolMaterial("SOGGY_BACON",  1, 100, 2.0F, 2, 5);
	private static EnumToolMaterial toolMaterialLimpBacon   = EnumHelper.addToolMaterial("LIMP_BACON",   2, 150, 2.0F, 2, 14);
	private static EnumToolMaterial toolMaterialCrispyBacon = EnumHelper.addToolMaterial("CRISPY_BACON", 3, 250, 3.0F, 3, 10);
	private static EnumToolMaterial toolMaterialBurntBacon  = EnumHelper.addToolMaterial("BURNT_BACON",  0, 59,  0.0F, 1, 22);

	private static EnumArmorMaterial armorMaterialRawBacon = EnumHelper.addArmorMaterial("RAW_BACON", 5, new int[]{1, 2, 1, 1}, 15);
	private static EnumArmorMaterial armorMaterialSoggyBacon = EnumHelper.addArmorMaterial("SOGGY_BACON", 7, new int[]{1, 2, 2, 1}, 15);
	private static EnumArmorMaterial armorMaterialLimpBacon = EnumHelper.addArmorMaterial("LIMP_BACON", 8, new int[]{2, 3, 2, 1}, 15);
	private static EnumArmorMaterial armorMaterialCrispyBacon = EnumHelper.addArmorMaterial("CRISPY_BACON", 10, new int[]{2, 4, 3, 1}, 10);
	private static EnumArmorMaterial armorMaterialBurntBacon = EnumHelper.addArmorMaterial("BURNT_BACON", 12, new int[]{3, 5, 4, 1}, 25);

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

	public Item itemRawBaconHelmet;
	public Item itemRawBaconChestplate;
	public Item itemRawBaconLeggings;
	public Item itemRawBaconBoots;

	public Item itemSoggyBaconHelmet;
	public Item itemSoggyBaconChestplate;
	public Item itemSoggyBaconLeggings;
	public Item itemSoggyBaconBoots;

	public Item itemLimpBaconHelmet;
	public Item itemLimpBaconChestplate;
	public Item itemLimpBaconLeggings;
	public Item itemLimpBaconBoots;

	public Item itemCrispyBaconHelmet;
	public Item itemCrispyBaconChestplate;
	public Item itemCrispyBaconLeggings;
	public Item itemCrispyBaconBoots;

	public Item itemBurntBaconHelmet;
	public Item itemBurntBaconChestplate;
	public Item itemBurntBaconLeggings;
	public Item itemBurntBaconBoots;

	public Item itemBarbecueLighter;

	public Block blockRawBacon;
	public Block blockSoggyBacon;
	public Block blockLimpBacon;
	public Block blockCrispyBacon;
	public Block blockBurntBacon;
	public Block blockBone;
	public BlockBarbecueFire blockBarbecueFire;
	public BlockPortal blockPortal;

	public static int bashurverseID = 5;
	public BiomeGenBase bashurBiome = null;
	private Logger logger = FMLLog.getLogger();
	public String runningDirectory = "";
	public ModPropertiesManager modPropertiesManager = null;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		proxy.registerRenderers();

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
		itemRawBacon = new ItemAssetBypassFood(modPropertiesManager.modProperties.itemID_RawBacon, 1, 0.3F, true).func_111206_d("bashpack:bacon_raw").setUnlocalizedName("rawbacon");
		itemSoggyBacon = new ItemAssetBypassFood(modPropertiesManager.modProperties.itemID_SoggyBacon, 2, 0.6F, true).func_111206_d("bashpack:bacon_soggy").setUnlocalizedName("soggybacon");
		itemLimpBacon = new ItemAssetBypassFood(modPropertiesManager.modProperties.itemID_LimpBacon, 4, 0.8F, true).func_111206_d("bashpack:bacon_limp").setUnlocalizedName("limpbacon");
		itemCrispyBacon = new ItemAssetBypassFood(modPropertiesManager.modProperties.itemID_CrispyBacon, 8, 1.2F, true).func_111206_d("bashpack:bacon_crispy").setUnlocalizedName("crispybacon");
		itemBurntBacon = new ItemAssetBypassFood(modPropertiesManager.modProperties.itemID_BurntBacon, 1, 0.1F, true).func_111206_d("bashpack:bacon_burnt").setUnlocalizedName("burntbacon");

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

		itemRawBaconSword = new ItemAssetBypassSword(modPropertiesManager.modProperties.itemID_RawBaconSword, toolMaterialRawBacon).func_111206_d("bashpack:raw_sword").setUnlocalizedName("raw_sword").setCreativeTab(tabBashPackBacon);
		itemRawBaconPickaxe = new ItemAssetBypassPickaxe(modPropertiesManager.modProperties.itemID_RawBaconPickaxe, toolMaterialRawBacon).func_111206_d("bashpack:raw_pickaxe").setUnlocalizedName("raw_pickaxe").setCreativeTab(tabBashPackBacon);
		itemRawBaconAxe = new ItemAssetBypassAxe(modPropertiesManager.modProperties.itemID_RawBaconAxe, toolMaterialRawBacon).func_111206_d("bashpack:raw_axe").setUnlocalizedName("raw_axe").setCreativeTab(tabBashPackBacon);
		itemRawBaconShovel = new ItemAssetBypassShovel(modPropertiesManager.modProperties.itemID_RawBaconShovel, toolMaterialRawBacon).func_111206_d("bashpack:raw_shovel").setUnlocalizedName("raw_shovel").setCreativeTab(tabBashPackBacon);
		itemRawBaconHoe = new ItemAssetBypassHoe(modPropertiesManager.modProperties.itemID_RawBaconHoe, toolMaterialRawBacon).func_111206_d("bashpack:raw_hoe").setUnlocalizedName("raw_hoe").setCreativeTab(tabBashPackBacon);

		itemSoggyBaconSword = new ItemAssetBypassSword(modPropertiesManager.modProperties.itemID_SoggyBaconSword, toolMaterialSoggyBacon).func_111206_d("bashpack:soggy_sword").setUnlocalizedName("soggy_sword").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconPickaxe = new ItemAssetBypassPickaxe(modPropertiesManager.modProperties.itemID_SoggyBaconPickaxe, toolMaterialSoggyBacon).func_111206_d("bashpack:soggy_pickaxe").setUnlocalizedName("soggy_pickaxe").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconAxe = new ItemAssetBypassAxe(modPropertiesManager.modProperties.itemID_SoggyBaconAxe, toolMaterialSoggyBacon).func_111206_d("bashpack:soggy_axe").setUnlocalizedName("soggy_axe").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconShovel = new ItemAssetBypassShovel(modPropertiesManager.modProperties.itemID_SoggyBaconShovel, toolMaterialSoggyBacon).func_111206_d("bashpack:soggy_shovel").setUnlocalizedName("soggy_shovel").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconHoe = new ItemAssetBypassHoe(modPropertiesManager.modProperties.itemID_SoggyBaconHoe, toolMaterialSoggyBacon).func_111206_d("bashpack:soggy_hoe").setUnlocalizedName("soggy_hoe").setCreativeTab(tabBashPackBacon);

		itemLimpBaconSword = new ItemAssetBypassSword(modPropertiesManager.modProperties.itemID_LimpBaconSword, toolMaterialLimpBacon).func_111206_d("bashpack:limp_sword").setUnlocalizedName("limp_sword").setCreativeTab(tabBashPackBacon);
		itemLimpBaconPickaxe = new ItemAssetBypassPickaxe(modPropertiesManager.modProperties.itemID_LimpBaconPickaxe, toolMaterialLimpBacon).func_111206_d("bashpack:limp_pickaxe").setUnlocalizedName("limp_pickaxe").setCreativeTab(tabBashPackBacon);
		itemLimpBaconAxe = new ItemAssetBypassAxe(modPropertiesManager.modProperties.itemID_LimpBaconAxe, toolMaterialLimpBacon).func_111206_d("bashpack:limp_axe").setUnlocalizedName("limp_axe").setCreativeTab(tabBashPackBacon);
		itemLimpBaconShovel = new ItemAssetBypassShovel(modPropertiesManager.modProperties.itemID_LimpBaconShovel, toolMaterialLimpBacon).func_111206_d("bashpack:limp_shovel").setUnlocalizedName("limp_shovel").setCreativeTab(tabBashPackBacon);
		itemLimpBaconHoe = new ItemAssetBypassHoe(modPropertiesManager.modProperties.itemID_LimpBaconHoe, toolMaterialLimpBacon).func_111206_d("bashpack:limp_hoe").setUnlocalizedName("limp_hoe").setCreativeTab(tabBashPackBacon);

		itemCrispyBaconSword = new ItemAssetBypassSword(modPropertiesManager.modProperties.itemID_CrispyBaconSword, toolMaterialCrispyBacon).func_111206_d("bashpack:crispy_sword").setUnlocalizedName("crispy_sword").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconPickaxe = new ItemAssetBypassPickaxe(modPropertiesManager.modProperties.itemID_CrispyBaconPickaxe, toolMaterialCrispyBacon).func_111206_d("bashpack:crispy_pickaxe").setUnlocalizedName("crispy_pickaxe").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconAxe = new ItemAssetBypassAxe(modPropertiesManager.modProperties.itemID_CrispyBaconAxe, toolMaterialCrispyBacon).func_111206_d("bashpack:crispy_axe").setUnlocalizedName("crispy_axe").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconShovel = new ItemAssetBypassShovel(modPropertiesManager.modProperties.itemID_CrispyBaconShovel, toolMaterialCrispyBacon).func_111206_d("bashpack:crispy_shovel").setUnlocalizedName("crispy_shovel").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconHoe = new ItemAssetBypassHoe(modPropertiesManager.modProperties.itemID_CrispyBaconHoe, toolMaterialCrispyBacon).func_111206_d("bashpack:crispy_hoe").setUnlocalizedName("crispy_hoe").setCreativeTab(tabBashPackBacon);

		itemBurntBaconSword = new ItemAssetBypassSword(modPropertiesManager.modProperties.itemID_BurntBaconSword, toolMaterialBurntBacon).func_111206_d("bashpack:burnt_sword").setUnlocalizedName("burnt_sword").setCreativeTab(tabBashPackBacon);
		itemBurntBaconPickaxe = new ItemAssetBypassPickaxe(modPropertiesManager.modProperties.itemID_BurntBaconPickaxe, toolMaterialBurntBacon).func_111206_d("bashpack:burnt_pickaxe").setUnlocalizedName("burnt_pickaxe").setCreativeTab(tabBashPackBacon);
		itemBurntBaconAxe = new ItemAssetBypassAxe(modPropertiesManager.modProperties.itemID_BurntBaconAxe, toolMaterialBurntBacon).func_111206_d("bashpack:burnt_axe").setUnlocalizedName("burnt_axe").setCreativeTab(tabBashPackBacon);
		itemBurntBaconShovel = new ItemAssetBypassShovel(modPropertiesManager.modProperties.itemID_BurntBaconShovel, toolMaterialBurntBacon).func_111206_d("bashpack:burnt_shovel").setUnlocalizedName("burnt_shovel").setCreativeTab(tabBashPackBacon);
		itemBurntBaconHoe = new ItemAssetBypassHoe(modPropertiesManager.modProperties.itemID_BurntBaconHoe, toolMaterialBurntBacon).func_111206_d("bashpack:burnt_hoe").setUnlocalizedName("burnt_hoe").setCreativeTab(tabBashPackBacon);

		//Register bacon armor.
		itemRawBaconHelmet 			= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconHelmet, armorMaterialRawBacon, "raw", 0, 0, 1, 0.1F).func_111206_d("bashpack:raw_helmet").setUnlocalizedName("raw_helmet").setCreativeTab(tabBashPackBacon);
		itemRawBaconChestplate 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconChestplate, armorMaterialRawBacon, "raw", 0, 1, 2, 0.2F).func_111206_d("bashpack:raw_chestplate").setUnlocalizedName("raw_chestplate").setCreativeTab(tabBashPackBacon);
		itemRawBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconLeggings, armorMaterialRawBacon, "raw", 0, 2, 1, 0.1F).func_111206_d("bashpack:raw_leggings").setUnlocalizedName("raw_leggings").setCreativeTab(tabBashPackBacon);
		itemRawBaconBoots 			= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconBoots, armorMaterialRawBacon, "raw", 0, 3, 1, 0.1F).func_111206_d("bashpack:raw_boots").setUnlocalizedName("raw_boots").setCreativeTab(tabBashPackBacon);

		itemSoggyBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconHelmet, armorMaterialSoggyBacon, "soggy", 0, 0, 2, 0.3F).func_111206_d("bashpack:soggy_helmet").setUnlocalizedName("soggy_helmet").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconChestplate	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconChestplate, armorMaterialSoggyBacon, "soggy", 0, 1, 3, 0.4F).func_111206_d("bashpack:soggy_chestplate").setUnlocalizedName("soggy_chestplate").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconLeggings, armorMaterialSoggyBacon, "soggy", 0, 2, 2, 0.3F).func_111206_d("bashpack:soggy_leggings").setUnlocalizedName("soggy_leggings").setCreativeTab(tabBashPackBacon);
		itemSoggyBaconBoots 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconBoots, armorMaterialSoggyBacon, "soggy", 0, 3, 2, 0.3F).func_111206_d("bashpack:soggy_boots").setUnlocalizedName("soggy_boots").setCreativeTab(tabBashPackBacon);

		itemLimpBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconHelmet, armorMaterialLimpBacon, "limp", 0, 0, 4, 0.5F).func_111206_d("bashpack:limp_helmet").setUnlocalizedName("limp_helmet").setCreativeTab(tabBashPackBacon);
		itemLimpBaconChestplate 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconChestplate, armorMaterialLimpBacon, "limp", 0, 1, 5, 0.6F).func_111206_d("bashpack:limp_chestplate").setUnlocalizedName("limp_chestplate").setCreativeTab(tabBashPackBacon);
		itemLimpBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconLeggings, armorMaterialLimpBacon, "limp", 0, 2, 4, 0.5F).func_111206_d("bashpack:limp_leggings").setUnlocalizedName("limp_leggings").setCreativeTab(tabBashPackBacon);
		itemLimpBaconBoots 			= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconBoots, armorMaterialLimpBacon, "limp", 0, 3, 4, 0.5F).func_111206_d("bashpack:limp_boots").setUnlocalizedName("limp_boots").setCreativeTab(tabBashPackBacon);

		itemCrispyBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconHelmet, armorMaterialCrispyBacon, "crispy", 0, 0, 8, 0.7F).func_111206_d("bashpack:crispy_helmet").setUnlocalizedName("crispy_helmet").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconChestplate 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconChestplate, armorMaterialCrispyBacon, "crispy", 0, 1, 9, 0.9F).func_111206_d("bashpack:crispy_chestplate").setUnlocalizedName("crispy_chestplate").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconLeggings 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconLeggings, armorMaterialCrispyBacon, "crispy", 0, 2, 8, 0.7F).func_111206_d("bashpack:crispy_leggings").setUnlocalizedName("crispy_leggings").setCreativeTab(tabBashPackBacon);
		itemCrispyBaconBoots 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconBoots, armorMaterialCrispyBacon, "crispy", 0, 3, 8, 0.7F).func_111206_d("bashpack:crispy_boots").setUnlocalizedName("crispy_boots").setCreativeTab(tabBashPackBacon);

		itemBurntBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconHelmet, armorMaterialBurntBacon, "burnt", 0, 0, 1, 0.1F).func_111206_d("bashpack:burnt_helmet").setUnlocalizedName("burnt_helmet").setCreativeTab(tabBashPackBacon);
		itemBurntBaconChestplate 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconChestplate, armorMaterialBurntBacon, "burnt", 0, 1, 1, 0.1F).func_111206_d("bashpack:burnt_chestplate").setUnlocalizedName("burnt_chestplate").setCreativeTab(tabBashPackBacon);
		itemBurntBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconLeggings, armorMaterialBurntBacon, "burnt", 0, 2, 1, 0.1F).func_111206_d("bashpack:burnt_leggings").setUnlocalizedName("burnt_leggings").setCreativeTab(tabBashPackBacon);
		itemBurntBaconBoots 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconBoots, armorMaterialBurntBacon, "burnt", 0, 3, 1, 0.1F).func_111206_d("bashpack:burnt_boots").setUnlocalizedName("burnt_boots").setCreativeTab(tabBashPackBacon);

		//Register misc items.
		itemBarbecueLighter = new ItemBarbecueLighter(modPropertiesManager.modProperties.itemID_BarbecueLighter);

		//Register blocks.
		blockRawBacon = new BlockAssetBypass(modPropertiesManager.modProperties.blockID_RawBacon, Material.clay).func_111022_d("bashpack:bacon_raw").setUnlocalizedName("raw_bacon_block").setResistance(2.5F).setHardness(1.0F).setCreativeTab(tabBashPackBacon);
		blockSoggyBacon = new BlockAssetBypass(modPropertiesManager.modProperties.blockID_SoggyBacon, Material.clay).func_111022_d("bashpack:bacon_soggy").setUnlocalizedName("soggy_bacon_block").setResistance(2.5F).setHardness(1.0F).setCreativeTab(tabBashPackBacon);
		blockLimpBacon = new BlockAssetBypass(modPropertiesManager.modProperties.blockID_LimpBacon, Material.clay).func_111022_d("bashpack:bacon_limp").setUnlocalizedName("limp_bacon_block").setResistance(2.5F).setHardness(1.0F).setCreativeTab(tabBashPackBacon);
		blockCrispyBacon = new BlockAssetBypass(modPropertiesManager.modProperties.blockID_CrispyBacon, Material.clay).func_111022_d("bashpack:bacon_crispy").setUnlocalizedName("crispy_bacon_block").setResistance(2.5F).setHardness(1.0F).setCreativeTab(tabBashPackBacon);
		blockBurntBacon = new BlockAssetBypass(modPropertiesManager.modProperties.blockID_BurntBacon, Material.clay).func_111022_d("bashpack:bacon_burnt").setUnlocalizedName("burnt_bacon_block").setResistance(2.5F).setHardness(1.0F).setCreativeTab(tabBashPackBacon);
		blockBone = new BlockAssetBypass(modPropertiesManager.modProperties.blockID_Bone, Material.rock).func_111022_d("bashpack:bone").setUnlocalizedName("bone").setResistance(10.0F).setHardness(1.5F).setStepSound(Block.soundStoneFootstep).setCreativeTab(tabBashPackBacon);

		blockBarbecueFire = new BlockBarbecueFire(modPropertiesManager.modProperties.blockID_BarbecueFire);
		blockPortal = new BlockPortal(modPropertiesManager.modProperties.blockID_Portal);

		GameRegistry.registerBlock(blockRawBacon, ItemBlock.class, "Raw Bacon Block");
		GameRegistry.registerBlock(blockSoggyBacon, ItemBlock.class, "Soggy Bacon Block");
		GameRegistry.registerBlock(blockLimpBacon, ItemBlock.class, "Limp Bacon Block");
		GameRegistry.registerBlock(blockCrispyBacon, ItemBlock.class, "Crispy Bacon Block");
		GameRegistry.registerBlock(blockBurntBacon, ItemBlock.class, "Burnt Bacon Block");
		GameRegistry.registerBlock(blockBone, ItemBlock.class, "Bone Block");

		//Register biomes.
		bashurBiome = new BiomeGenBashurverse(33);

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

		//Add armor recipes.
		GameRegistry.addRecipe(new ItemStack(itemRawBaconHelmet, 1), new Object[]
				{
			"BBB", "B B", 'B', itemRawBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconChestplate, 1), new Object[]
				{
			"B B", "BBB", "BBB", 'B', itemRawBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconLeggings, 1), new Object[]
				{
			"BBB", "B B", "B B", 'B', itemRawBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemRawBaconBoots, 1), new Object[]
				{
			"B B", "B B", 'B', itemRawBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconHelmet, 1), new Object[]
				{
			"BBB", "B B", 'B', itemSoggyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconChestplate, 1), new Object[]
				{
			"B B", "BBB", "BBB", 'B', itemSoggyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconLeggings, 1), new Object[]
				{
			"BBB", "B B", "B B", 'B', itemSoggyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemSoggyBaconBoots, 1), new Object[]
				{
			"B B", "B B", 'B', itemSoggyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconHelmet, 1), new Object[]
				{
			"BBB", "B B", 'B', itemLimpBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconChestplate, 1), new Object[]
				{
			"B B", "BBB", "BBB", 'B', itemLimpBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconLeggings, 1), new Object[]
				{
			"BBB", "B B", "B B", 'B', itemLimpBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemLimpBaconBoots, 1), new Object[]
				{
			"B B", "B B", 'B', itemLimpBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconHelmet, 1), new Object[]
				{
			"BBB", "B B", 'B', itemCrispyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconChestplate, 1), new Object[]
				{
			"B B", "BBB", "BBB", 'B', itemCrispyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconLeggings, 1), new Object[]
				{
			"BBB", "B B", "B B", 'B', itemCrispyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemCrispyBaconBoots, 1), new Object[]
				{
			"B B", "B B", 'B', itemCrispyBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconHelmet, 1), new Object[]
				{
			"BBB", "B B", 'B', itemBurntBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconChestplate, 1), new Object[]
				{
			"B B", "BBB", "BBB", 'B', itemBurntBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconLeggings, 1), new Object[]
				{
			"BBB", "B B", "B B", 'B', itemBurntBacon
				});
		GameRegistry.addRecipe(new ItemStack(itemBurntBaconBoots, 1), new Object[]
				{
			"B B", "B B", 'B', itemBurntBacon
				});

		//Add block recipes.
		GameRegistry.addShapelessRecipe(new ItemStack(itemRawBacon, 9), new Object[]{blockRawBacon});
		GameRegistry.addShapelessRecipe(new ItemStack(itemSoggyBacon, 9), new Object[]{blockSoggyBacon});
		GameRegistry.addShapelessRecipe(new ItemStack(itemLimpBacon, 9), new Object[]{blockLimpBacon});
		GameRegistry.addShapelessRecipe(new ItemStack(itemCrispyBacon, 9), new Object[]{blockCrispyBacon});
		GameRegistry.addShapelessRecipe(new ItemStack(itemBurntBacon, 9), new Object[]{blockBurntBacon});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 9, 15), new Object[]{blockBone});

		GameRegistry.addRecipe(new ItemStack(blockRawBacon, 1), new Object[]
				{
			"BBB", "BBB", "BBB", 'B', itemRawBacon
				});
		GameRegistry.addRecipe(new ItemStack(blockSoggyBacon, 1), new Object[]
				{
			"BBB", "BBB", "BBB", 'B', itemSoggyBacon
				});
		GameRegistry.addRecipe(new ItemStack(blockLimpBacon, 1), new Object[]
				{
			"BBB", "BBB", "BBB", 'B', itemLimpBacon
				});
		GameRegistry.addRecipe(new ItemStack(blockCrispyBacon, 1), new Object[]
				{
			"BBB", "BBB", "BBB", 'B', itemCrispyBacon
				});
		GameRegistry.addRecipe(new ItemStack(blockBurntBacon, 1), new Object[]
				{
			"BBB", "BBB", "BBB", 'B', itemBurntBacon
				});
		GameRegistry.addRecipe(new ItemStack(blockBone, 1), new Object[]
				{
			"DDD", "DDD", "DDD", 'D', new ItemStack(Item.dyePowder, 1, 15)
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
		LanguageRegistry.addName(itemBarbecueLighter, "Barbecue Lighter");
		
		LanguageRegistry.addName(blockRawBacon, "Raw Bacon Block");
		LanguageRegistry.addName(blockSoggyBacon, "Soggy Bacon Block");
		LanguageRegistry.addName(blockLimpBacon, "Limp Bacon Block");
		LanguageRegistry.addName(blockCrispyBacon, "Crispy Bacon Block");
		LanguageRegistry.addName(blockBurntBacon, "Burnt Bacon Block");
		LanguageRegistry.addName(blockBone, "Bone Block");

		LanguageRegistry.addName(itemRawBaconHelmet, "Raw Bacon Helmet");
		LanguageRegistry.addName(itemRawBaconChestplate, "Raw Bacon Chestplate");
		LanguageRegistry.addName(itemRawBaconLeggings, "Raw Bacon Leggings");
		LanguageRegistry.addName(itemRawBaconBoots, "Raw Bacon Boots");
		LanguageRegistry.addName(itemSoggyBaconHelmet, "Soggy Bacon Helmet");
		LanguageRegistry.addName(itemSoggyBaconChestplate, "Soggy Bacon Chestplate");
		LanguageRegistry.addName(itemSoggyBaconLeggings, "Soggy Bacon Leggings");
		LanguageRegistry.addName(itemSoggyBaconBoots, "Soggy Bacon Boots");
		LanguageRegistry.addName(itemLimpBaconHelmet, "Limp Bacon Helmet");
		LanguageRegistry.addName(itemLimpBaconChestplate, "Limp Bacon Chestplate");
		LanguageRegistry.addName(itemLimpBaconLeggings, "Limp Bacon Leggings");
		LanguageRegistry.addName(itemLimpBaconBoots, "Limp Bacon Boots");
		LanguageRegistry.addName(itemCrispyBaconHelmet, "Crispy Bacon Helmet");
		LanguageRegistry.addName(itemCrispyBaconChestplate, "Crispy Bacon Chestplate");
		LanguageRegistry.addName(itemCrispyBaconLeggings, "Crispy Bacon Leggings");
		LanguageRegistry.addName(itemCrispyBaconBoots, "Crispy Bacon Boots");
		LanguageRegistry.addName(itemBurntBaconHelmet, "Burnt Bacon Helmet");
		LanguageRegistry.addName(itemBurntBaconChestplate, "Burnt Bacon Chestplate");
		LanguageRegistry.addName(itemBurntBaconLeggings, "Burnt Bacon Leggings");
		LanguageRegistry.addName(itemBurntBaconBoots, "Burnt Bacon Boots");
		
		//Register Dimension
		DimensionManager.registerProviderType(bashurverseID, WorldProviderBashurverse.class, true);
		DimensionManager.registerDimension(bashurverseID, bashurverseID);

		//Register Spawns.
		EntityRegistry.registerModEntity(EntityGiantPig.class, "Giant Pig", 2, this, 50, 2, true);
		EntityRegistry.addSpawn(EntityPig.class, 80, 12, 32, EnumCreatureType.creature, bashurBiome);
		EntityRegistry.addSpawn(EntityGiantPig.class, 10, 1, 1, EnumCreatureType.creature, bashurBiome);
		EntityRegistry.addSpawn(EntityPigZombie.class, 10, 3, 6, EnumCreatureType.creature, bashurBiome);
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

		logger.log(Level.FINER, "BashPack: An exception occurred.\n" + stackTrace.toString());
		System.out.println("BashPack: An exception occurred.\n" + stackTrace.toString());

		CrashReport crashReport = new CrashReport("BashPack: " + description, e);
		net.minecraft.client.Minecraft.getMinecraft().crashed(crashReport);
	}
}