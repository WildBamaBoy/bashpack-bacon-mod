package mods.bashpack.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import mods.bashpack.block.BlockBarbecueFire;
import mods.bashpack.block.BlockPortal;
import mods.bashpack.core.forge.CommonProxy;
import mods.bashpack.core.io.ModPropertiesManager;
import mods.bashpack.entity.EntityGiantPig;
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
		itemRawBacon = new ItemFood(modPropertiesManager.modProperties.itemID_RawBacon, 1, 0.3F, true).setUnlocalizedName("rawbacon");
		itemSoggyBacon = new ItemFood(modPropertiesManager.modProperties.itemID_SoggyBacon, 2, 0.6F, true).setUnlocalizedName("soggybacon");
		itemLimpBacon = new ItemFood(modPropertiesManager.modProperties.itemID_LimpBacon, 4, 0.8F, true).setUnlocalizedName("limpbacon");
		itemCrispyBacon = new ItemFood(modPropertiesManager.modProperties.itemID_CrispyBacon, 8, 1.2F, true).setUnlocalizedName("crispybacon");
		itemBurntBacon = new ItemFood(modPropertiesManager.modProperties.itemID_BurntBacon, 1, 0.1F, true).setUnlocalizedName("burntbacon");

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

		itemRawBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_RawBaconSword, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("sword");
		itemRawBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_RawBaconPickaxe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("pickaxe");
		itemRawBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_RawBaconAxe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("axe");
		itemRawBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_RawBaconShovel, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("shovel");
		itemRawBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_RawBaconHoe, toolMaterialRawBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("hoe");

		itemSoggyBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_SoggyBaconSword, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("sword");
		itemSoggyBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_SoggyBaconPickaxe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("pickaxe");
		itemSoggyBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_SoggyBaconAxe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("axe");
		itemSoggyBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_SoggyBaconShovel, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("shovel");
		itemSoggyBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_SoggyBaconHoe, toolMaterialSoggyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("hoe");

		itemLimpBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_LimpBaconSword, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_sword");
		itemLimpBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_LimpBaconPickaxe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_pickaxe");
		itemLimpBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_LimpBaconAxe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_axe");
		itemLimpBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_LimpBaconShovel, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_shovel");
		itemLimpBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_LimpBaconHoe, toolMaterialLimpBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_hoe");

		itemCrispyBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_CrispyBaconSword, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("sword");
		itemCrispyBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_CrispyBaconPickaxe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("pickaxe");
		itemCrispyBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_CrispyBaconAxe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("axe");
		itemCrispyBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_CrispyBaconShovel, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("shovel");
		itemCrispyBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_CrispyBaconHoe, toolMaterialCrispyBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("hoe");

		itemBurntBaconSword = new ItemSword(modPropertiesManager.modProperties.itemID_BurntBaconSword, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("sword");
		itemBurntBaconPickaxe = new ItemPickaxe(modPropertiesManager.modProperties.itemID_BurntBaconPickaxe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("pickaxe");
		itemBurntBaconAxe = new ItemAxe(modPropertiesManager.modProperties.itemID_BurntBaconAxe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("axe");
		itemBurntBaconShovel = new ItemSpade(modPropertiesManager.modProperties.itemID_BurntBaconShovel, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("shovel");
		itemBurntBaconHoe = new ItemHoe(modPropertiesManager.modProperties.itemID_BurntBaconHoe, toolMaterialBurntBacon).setCreativeTab(tabBashPackBacon).setUnlocalizedName("hoe");

		//Register bacon armor.
		itemRawBaconHelmet 			= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconHelmet, armorMaterialRawBacon,  0, 0, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("helmet");
		itemRawBaconChestplate 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconChestplate, armorMaterialRawBacon,  0, 1, 2, 0.2F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("chestplate");
		itemRawBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconLeggings, armorMaterialRawBacon,  0, 2, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("leggings");
		itemRawBaconBoots 			= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_RawBaconBoots, armorMaterialRawBacon,  0, 3, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("boots");

		itemSoggyBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconHelmet, armorMaterialSoggyBacon,  0, 0, 2, 0.3F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("helmet");
		itemSoggyBaconChestplate	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconChestplate, armorMaterialSoggyBacon,  0, 1, 3, 0.4F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("chestplate");
		itemSoggyBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconLeggings, armorMaterialSoggyBacon,  0, 2, 2, 0.3F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("leggings");
		itemSoggyBaconBoots 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_SoggyBaconBoots, armorMaterialSoggyBacon,  0, 3, 2, 0.3F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("boots");

		itemLimpBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconHelmet, armorMaterialLimpBacon,  0, 0, 4, 0.5F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_helmet");
		itemLimpBaconChestplate 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconChestplate, armorMaterialLimpBacon,  0, 1, 5, 0.6F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_chestplate");
		itemLimpBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconLeggings, armorMaterialLimpBacon,  0, 2, 4, 0.5F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_leggings");
		itemLimpBaconBoots 			= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_LimpBaconBoots, armorMaterialLimpBacon,  0, 3, 4, 0.5F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_boots");

		itemCrispyBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconHelmet, armorMaterialCrispyBacon,  0, 0, 8, 0.7F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("helmet");
		itemCrispyBaconChestplate 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconChestplate, armorMaterialCrispyBacon,  0, 1, 9, 0.9F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("chestplate");
		itemCrispyBaconLeggings 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconLeggings, armorMaterialCrispyBacon,  0, 2, 8, 0.7F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("leggings");
		itemCrispyBaconBoots 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_CrispyBaconBoots, armorMaterialCrispyBacon,  0, 3, 8, 0.7F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("boots");

		itemBurntBaconHelmet 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconHelmet, armorMaterialBurntBacon,  0, 0, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("helmet");
		itemBurntBaconChestplate 	= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconChestplate, armorMaterialBurntBacon,  0, 1, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("chestplate");
		itemBurntBaconLeggings 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconLeggings, armorMaterialBurntBacon,  0, 2, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("leggings");
		itemBurntBaconBoots 		= new ItemEdibleArmor(modPropertiesManager.modProperties.itemID_BurntBaconBoots, armorMaterialBurntBacon,  0, 3, 1, 0.1F).setCreativeTab(tabBashPackBacon).setUnlocalizedName("boots");

		//Register misc items.
		itemBarbecueLighter = new ItemBarbecueLighter(modPropertiesManager.modProperties.itemID_BarbecueLighter);

		//Register blocks.
		blockRawBacon = new Block(modPropertiesManager.modProperties.blockID_RawBacon, Material.clay).setCreativeTab(tabBashPackBacon).setUnlocalizedName("raw_bacon_block").setResistance(2.5F).setHardness(1.0F);
		blockSoggyBacon = new Block(modPropertiesManager.modProperties.blockID_SoggyBacon, Material.clay).setCreativeTab(tabBashPackBacon).setUnlocalizedName("soggy_bacon_block").setResistance(2.5F).setHardness(1.0F);
		blockLimpBacon = new Block(modPropertiesManager.modProperties.blockID_LimpBacon, Material.clay).setCreativeTab(tabBashPackBacon).setUnlocalizedName("limp_bacon_block").setResistance(2.5F).setHardness(1.0F);
		blockCrispyBacon = new Block(modPropertiesManager.modProperties.blockID_CrispyBacon, Material.clay).setCreativeTab(tabBashPackBacon).setUnlocalizedName("crispy_bacon_block").setResistance(2.5F).setHardness(1.0F);
		blockBurntBacon = new Block(modPropertiesManager.modProperties.blockID_BurntBacon, Material.clay).setCreativeTab(tabBashPackBacon).setUnlocalizedName("burnt_bacon_block").setResistance(2.5F).setHardness(1.0F);
		blockBone = new Block(modPropertiesManager.modProperties.blockID_Bone, Material.rock).setCreativeTab(tabBashPackBacon).setUnlocalizedName("bone").setResistance(10.0F).setHardness(1.5F).setStepSound(Block.soundStoneFootstep);

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