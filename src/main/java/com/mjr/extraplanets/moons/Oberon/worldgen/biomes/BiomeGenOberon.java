package com.mjr.extraplanets.moons.Oberon.worldgen.biomes;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.planetAndMoonBlocks.BlockBasicOberon;
import com.mjr.extraplanets.moons.Oberon.worldgen.OberonBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenOberon extends OberonBiomes {

	public BiomeGenOberon(BiomeProperties properties) {
		super(properties);
		this.topBlock = ExtraPlanets_Blocks.OBERON_BLOCKS.getDefaultState();
		this.fillerBlock = ExtraPlanets_Blocks.OBERON_BLOCKS.getDefaultState().withProperty(BlockBasicOberon.BASIC_TYPE, BlockBasicOberon.EnumBlockBasic.SUB_SURFACE);
	}

	@Override
	public void registerTypes(Biome b) {
		if (Config.REGISTER_BIOME_TYPES)
			BiomeDictionary.addTypes(b, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD);
	}
}
