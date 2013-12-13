package bashpack.world.biomes;

import net.minecraft.block.material.Material;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import bashpack.core.BashPackCore;

public class BiomeGenBashurverse extends BiomeGenBase
{
	private WorldGenerator UnDeadworldGeneratorBigTree;
	public final Material blockMaterial;
	public BiomeGenBashurverse(int par1)
	{
		super(par1);
		this.blockMaterial = Material.water;
		this.minHeight = 0.1F;
		this.maxHeight = 0.6F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.topBlock = ((byte)BashPackCore.instance.blockRawBacon.blockID);
		this.fillerBlock = ((byte)BashPackCore.instance.blockBone.blockID);
		this.setBiomeName("The Bashurverse");

		/** this changes the water colour, its set to red now but ggole the java colours **/
		this.waterColorMultiplier = 0x990000;
	}
}