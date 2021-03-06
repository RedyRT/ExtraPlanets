package com.mjr.extraplanets.planets.Saturn.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.Constants;
import com.mjr.extraplanets.planets.Saturn.worldgen.biomes.BiomeGenSaturn;
import com.mjr.extraplanets.planets.Saturn.worldgen.village.*;
import com.mjr.mjrlegendslib.util.MessageUtilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenVillageSaturn extends MapGenStructure {
	public static List<Biome> villageSpawnBiomes = Arrays.asList(new Biome[] { BiomeGenSaturn.saturn });
	private final int terrainType;
	private static boolean initialized;

	static {
		try {
			MapGenVillageSaturn.initiateStructures();
		} catch (Throwable e) {

		}
	}

	public static void initiateStructures() throws Throwable {
		if (!MapGenVillageSaturn.initialized) {
			MapGenStructureIO.registerStructure(StructureVillageStartSaturn.class, "SaturnVillage");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageField.class, "SaturnField1");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageField2.class, "SaturnField2");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageHouse.class, "SaturnHouse");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageRoadPiece.class, "SaturnRoadPiece");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillagePathGen.class, "SaturnPath");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageTorch.class, "SaturnTorch");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageStartPiece.class, "SaturnWell");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageWoodHut.class, "SaturnWoodHut");
		}

		MapGenVillageSaturn.initialized = true;
	}

	public MapGenVillageSaturn() {
		this.terrainType = 0;
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int i, int j) {
		final byte numChunks = 32;
		final byte offsetChunks = 8;
		final int oldi = i;
		final int oldj = j;

		if (i < 0) {
			i -= numChunks - 1;
		}

		if (j < 0) {
			j -= numChunks - 1;
		}

		int randX = i / numChunks;
		int randZ = j / numChunks;
		final Random var7 = this.world.setRandomSeed(i, j, 10387312);
		randX *= numChunks;
		randZ *= numChunks;
		randX += var7.nextInt(numChunks - offsetChunks);
		randZ += var7.nextInt(numChunks - offsetChunks);

		return oldi == randX && oldj == randZ;

	}

	@Override
	public BlockPos getNearestStructurePos(World world, BlockPos pos, boolean findUnexplored) {
		this.world = world;
		return findNearestStructurePosBySpacing(world, this, pos, 32, 8, 10387312, false, 100, findUnexplored);
	}

	@Override
	protected StructureStart getStructureStart(int par1, int par2) {
		if (Config.DEBUG_MODE)
			MessageUtilities.debugMessageToLog(Constants.modID, "Generating Saturn Village at x" + par1 * 16 + " z" + par2 * 16);
		return new StructureVillageStartSaturn(this.world, this.rand, par1, par2, this.terrainType);
	}

	@Override
	public String getStructureName() {
		return "SaturnVillage";
	}
}
