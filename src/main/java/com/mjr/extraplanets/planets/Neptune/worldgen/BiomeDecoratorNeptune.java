package com.mjr.extraplanets.planets.Neptune.worldgen;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.world.features.WorldGenCustomLake;
import com.mjr.extraplanets.world.features.WorldGenFrozenNitrogenPile;

public class BiomeDecoratorNeptune extends BiomeDecorator {

	private WorldGenerator copperGen;
	private WorldGenerator tinGen;
	private WorldGenerator ironGen;
	private WorldGenerator zincGen;
	private WorldGenerator frozenNitrogenGen;
	private WorldGenerator blueGemGen;

	private int LakesPerChunk = 5;

	private World currentWorld;
	protected Random rand;
	protected int chunkX;
	protected int chunkZ;

	private boolean isDecorating = false;

	public BiomeDecoratorNeptune() {
		this.copperGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.neptuneBlocks, 4, 5, true, ExtraPlanets_Blocks.neptuneBlocks, 2);
		this.tinGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.neptuneBlocks, 4, 4, true, ExtraPlanets_Blocks.neptuneBlocks, 2);
		this.ironGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.neptuneBlocks, 8, 3, true, ExtraPlanets_Blocks.neptuneBlocks, 2);
		this.zincGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.neptuneBlocks, 4, 6, true, ExtraPlanets_Blocks.neptuneBlocks, 2);
		this.frozenNitrogenGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.frozenNitrogen, 8, 0, true, ExtraPlanets_Blocks.neptuneBlocks, 0);
		this.blueGemGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.neptuneBlocks, 4, 9, true, ExtraPlanets_Blocks.neptuneBlocks, 2);
		// WorldGenMinableMeta(Block OreBlock, int numberOfBlocks, int OreMeta, boolean usingMetaData, Block StoneBlock, int StoneMeta);
	}

	protected void generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY) {
		for (int var5 = 0; var5 < amountPerChunk; ++var5) {
			final int var6 = this.chunkX + this.rand.nextInt(16);
			final int var7 = this.rand.nextInt(maxY - minY) + minY;
			final int var8 = this.chunkZ + this.rand.nextInt(16);
			worldGenerator.generate(this.currentWorld, this.rand, var6, var7, var8);
		}
	}

	@Override
	public void decorateChunk(World world, Random rand, BiomeGenBase biome, int xChunk, int zChunk) {
		this.currentWorld = world;
		this.rand = rand;
		this.chunk_X = xChunk;
		this.chunk_Z = zChunk;

		if (isDecorating)
			return;
		isDecorating = true;
		this.generateOre(26, this.copperGen, 0, 60);
		this.generateOre(23, this.tinGen, 0, 60);
		this.generateOre(20, this.ironGen, 0, 64);
		this.generateOre(20, this.zincGen, 0, 32);
		this.generateOre(5, this.frozenNitrogenGen, 0, 256);
		this.generateOre(10, this.blueGemGen, 0, 10);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.currentWorld, this.rand, this.chunkX, this.chunkZ));
		for (int i = 0; i < this.LakesPerChunk; i++) {
			if (this.rand.nextInt(10) == 0) {
				int x = this.chunkX + 8;
				// int y = this.rand.nextInt(16) + 16;
				int z = this.chunkZ + 8;
				int y = this.currentWorld.getHeightValue(x, z);
				new WorldGenCustomLake(ExtraPlanets_Fluids.nitrogen).generate(this.currentWorld, this.rand, x, y, z, ExtraPlanets_Blocks.neptuneBlocks);
			}
		}

		if (this.rand.nextInt(20) == 1) {
			int x = this.chunkX + 8;
			int z = this.chunkZ + 8;
			int y = this.currentWorld.getHeightValue(x, z);
			y = y - 1;
			new WorldGenFrozenNitrogenPile().generate(this.currentWorld, this.rand, x, y, z);
		}
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(this.currentWorld, this.rand, this.chunkX, this.chunkZ));

		isDecorating = false;
		// generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY);
	}
}