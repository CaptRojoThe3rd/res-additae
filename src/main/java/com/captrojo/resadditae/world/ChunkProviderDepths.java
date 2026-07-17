package com.captrojo.resadditae.world;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.world.biome.ModBiomes;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

/* Because netherrack and other crap is hardcoded into ChunkProviderHell. */
public class ChunkProviderDepths implements IChunkProvider
{
	private Random rand;

	private NoiseGeneratorOctaves noise_gen_1;
	private NoiseGeneratorOctaves noise_gen_2;
	private NoiseGeneratorOctaves noise_gen_3;
	private NoiseGeneratorOctaves noise_gen_4;
	private NoiseGeneratorOctaves noise_gen_5;
	
	private NoiseGeneratorOctaves noise_gen_s;
	private NoiseGeneratorOctaves noise_gen_l;

	private World world;

	private double[] noise_field;

	private double[] noise_data_1;
	private double[] noise_data_2;
	private double[] noise_data_3;
	private double[] noise_data_4;
	private double[] noise_data_5;
	
	private double[] noise_data_s;
//	private double[] noise_data_l;

	public ChunkProviderDepths(World world, long seed)
	{
		this.world = world;
		this.rand = new Random(seed);

		this.noise_gen_1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noise_gen_2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noise_gen_3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noise_gen_4 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noise_gen_5 = new NoiseGeneratorOctaves(this.rand, 16);
		
		this.noise_gen_s = new NoiseGeneratorOctaves(this.rand, 4);
//		this.noise_gen_l = new NoiseGeneratorOctaves(this.rand, 2);
	}

	public void doBlockPlacementStuff(int chunk_x, int chunk_z, Block[] blocks, byte[] metas)
	{
		int size_x = 5;
		int size_y = 25;
		int size_z = 5;
		this.noise_field = this.initializeNoiseField(this.noise_field, chunk_x * 4, 0, chunk_z * 4, size_x, size_y, size_z);

		byte b0 = 4;
		byte sea_level = 40;

		for (int i1 = 0; i1 < b0; ++i1) {
			for (int j1 = 0; j1 < b0; ++j1) {
				for (int y2 = 0; y2 < 24; ++y2) {
					double d0 = 0.125D;
					double d1 = this.noise_field[((i1 + 0) * size_z + j1 + 0) * size_y + y2 + 0];
					double d2 = this.noise_field[((i1 + 0) * size_z + j1 + 1) * size_y + y2 + 0];
					double d3 = this.noise_field[((i1 + 1) * size_z + j1 + 0) * size_y + y2 + 0];
					double d4 = this.noise_field[((i1 + 1) * size_z + j1 + 1) * size_y + y2 + 0];
					double d5 = (this.noise_field[((i1 + 0) * size_z + j1 + 0) * size_y + y2 + 1] - d1) * d0;
					double d6 = (this.noise_field[((i1 + 0) * size_z + j1 + 1) * size_y + y2 + 1] - d2) * d0;
					double d7 = (this.noise_field[((i1 + 1) * size_z + j1 + 0) * size_y + y2 + 1] - d3) * d0;
					double d8 = (this.noise_field[((i1 + 1) * size_z + j1 + 1) * size_y + y2 + 1] - d4) * d0;
					
					for (int l1 = 0; l1 < 8; ++l1) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 4; ++i2) {
							int blk_idx = (i2 + i1 * 4 << 12) | (0 + j1 * 4 << 8) | (y2 * 8 + l1);
							short column_size = 256;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int k2 = 0; k2 < 4; ++k2) {
								Block block = null;
								byte meta = 0;

								if (y2 * 8 + l1 < sea_level) {
									block = Blocks.water;
									meta = 0;
								}
								
								double gmin = 36;
								double gmax = 48;
								
								if (d15 > gmin && d15 < gmax) {
									block = ModBlocks.depth_stones_special;
									meta = 2;
//									block = Blocks.air;
//									meta = 0;
								} else if (d15 > 0) {
									block = ModBlocks.depth_stones;
									meta = 0;
//									block = Blocks.wool;
//									meta = (byte) Math.min(Math.max((int) d15 / 2, 0), 15);
								}
								
//								if (y2 > 16) {
//									block = Blocks.air;
//									meta = 0;
//								}

								blocks[blk_idx] = block;
								metas[blk_idx] = meta;
								blk_idx += column_size;
								d15 += d16;
							}
							
							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	public void replaceBiomeBlocks(int chunk_x, int chunk_z, Block[] blocks, byte[] metas, BiomeGenBase[] biomes)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunk_x, chunk_z, blocks, metas, biomes, this.world);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return;

		this.noise_data_s = this.noise_gen_s.generateNoiseOctaves(this.noise_data_s, chunk_x << 4, 0, chunk_z << 4, 16, 256, 16, 0.125, 0.0625, 0.125);
//		this.noise_data_l = this.noise_gen_l.generateNoiseOctaves(this.noise_data_l, chunk_x << 4, 0, chunk_z << 4, 16, 1, 16, 0.0078125, 1, 0.0078125);
		
		for (int cx = 0; cx < 16; cx++) {
			for (int cz = 0; cz < 16; cz++) {
				int blk_idx_start = (cx * 16 + cz) * 256;

				BiomeGenBase biome = biomes[cz * 16 + cx];
				Block alt_stone_blk = ModBlocks.depth_stones;
				byte alt_stone_meta = 0;
				if (biome == ModBiomes.depths_amber) {
					alt_stone_meta = 1;
				} else if (biome == ModBiomes.depths_jade) {
					alt_stone_meta = 2;
				} else if (biome == ModBiomes.depths_ruby) {
					alt_stone_meta = 3;
				} else if (biome == ModBiomes.depths_sapphire) {
					alt_stone_meta = 4;
				} else if (biome == ModBiomes.depths_topaz) {
					alt_stone_meta = 5;
				}
				
//				double d1 = this.noise_data_l[cz + (cx << 4)];
//				double d1a = Math.pow(d1 * 2, 2);
//				double d1b = Math.abs(d1a) * -1 + 1;
//				double d1c = (d1b < 0) ? 0 : d1b;
//				{
//					int cy = MathHelper.floor_double(d1c * 48);
//					if (biome == ModBiomes.depths_ruby) {
//						cy = (int) (d1b * 20);
//					} else {
//						cy = (int) (d1b * 10);
//					}
//					
//					if (cy < 0) {
//						cy = 0;
//					}
//					
//					for (int y = 0; y < cy; y++) {
//						blocks[blk_idx_start + y] = ModBlocks.depth_stones;
//					}
//					if (d1 < 0) {
//						for (int y = cy; y < 40; y++) {
//							Block existing = blocks[blk_idx_start + y];
//							if (existing == null || existing.isOpaqueCube()) {
//								continue;
//							}
//							blocks[blk_idx_start + y] = Blocks.lava;
//						}
//					}
//				}

				for (int cy = 191; cy >= 0; cy--) {
					int blk_idx = blk_idx_start + cy;

					if (blocks[blk_idx] == ModBlocks.depth_stones) {
						if (blocks[blk_idx + 1] == null || blocks[blk_idx + 1] == Blocks.air) {
							blocks[blk_idx] = ModBlocks.depth_soil;
						} else if (blocks[blk_idx + 1] == ModBlocks.depth_soil && blocks[blk_idx + 3 + rand.nextInt(3)] != ModBlocks.depth_soil) {
							blocks[blk_idx] = ModBlocks.depth_soil;
						}
					}
					
					{
						int csi = cy + (cz << 8) + (cx << 12);
						double d0 = this.noise_data_s[csi];
						if (Math.abs(d0) < 2.5) {
							if (blocks[blk_idx] == ModBlocks.depth_stones) {
								blocks[blk_idx] = alt_stone_blk;
								metas[blk_idx] = alt_stone_meta;
							}
						}
					}

					if (cy < 1 + this.rand.nextInt(5) || cy > 188 - this.rand.nextInt(5)) {
						blocks[blk_idx] = ModBlocks.depth_stones_special;
						metas[blk_idx] = 0;
					}
				}
				for (int cy = 192; cy < 256; cy++) {
					blocks[blk_idx_start + cy] = ModBlocks.depth_stones_special;
					metas[blk_idx_start + cy] = 0;
				}
			}
		}
	}

	public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		return this.provideChunk(p_73158_1_, p_73158_2_);
	}

	public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		this.rand.setSeed((long) p_73154_1_ * 341873128712L + (long) p_73154_2_ * 132897987541L);
		Block[] ablock = new Block[65536];
		byte[] ameta = new byte[ablock.length];
		BiomeGenBase[] abiomegenbase = this.world.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16); //Forge Move up to allow for passing to replaceBiomeBlocks
		this.doBlockPlacementStuff(p_73154_1_, p_73154_2_, ablock, ameta);
		this.replaceBiomeBlocks(p_73154_1_, p_73154_2_, ablock, ameta, abiomegenbase);
		Chunk chunk = new Chunk(this.world, ablock, ameta, p_73154_1_, p_73154_2_);
		byte[] abiome = chunk.getBiomeArray();

		for (int k = 0; k < abiome.length; ++k) {
			abiome[k] = (byte) abiomegenbase[k].biomeID;
		}

		chunk.resetRelightChecks();
		return chunk;
	}

	private double[] initializeNoiseField(double[] field, int pos_x, int pos_y, int pos_z, int size_x, int size_y, int size_z)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, field, pos_x, pos_y, pos_z, size_x, size_y, size_z);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return event.noisefield;

		if (field == null) {
			field = new double[size_x * size_y * size_z];
		}

//		double horz_scale = 684.412D;
//		double vert_scale = 2053.236D;
		double horz_scale = 161;
		double vert_scale = 161;

		this.noise_data_4 = this.noise_gen_4.generateNoiseOctaves(this.noise_data_4, pos_x, pos_y, pos_z, size_x, 1, size_z, 32, 0, 32);
		this.noise_data_5 = this.noise_gen_5.generateNoiseOctaves(this.noise_data_5, pos_x, pos_y, pos_z, size_x, 1, size_z, 100, 0, 100);
		this.noise_data_1 = this.noise_gen_3.generateNoiseOctaves(this.noise_data_1, pos_x, pos_y, pos_z, size_x, size_y, size_z, horz_scale / 80, vert_scale / 60, horz_scale / 80);
		this.noise_data_2 = this.noise_gen_1.generateNoiseOctaves(this.noise_data_2, pos_x, pos_y, pos_z, size_x, size_y, size_z, horz_scale, vert_scale, horz_scale);
		this.noise_data_3 = this.noise_gen_2.generateNoiseOctaves(this.noise_data_3, pos_x, pos_y, pos_z, size_x, size_y, size_z, horz_scale, vert_scale, horz_scale);

		int k1 = 0;
		int l1 = 0;
		double[] adouble1 = new double[size_y];
		int i2;

		for (i2 = 0; i2 < size_y; ++i2) {
			adouble1[i2] = Math.cos((double) i2 * Math.PI * 6.0D / (double) size_y) * 2.0D;
			double d2 = (double) i2;

			if (i2 > size_y / 2) {
				d2 = (double) (size_y - 1 - i2);
			}

			if (d2 < 4.0D) {
				d2 = 4.0D - d2;
				adouble1[i2] -= d2 * d2 * d2 * 10.0D;
			}
		}

		for (i2 = 0; i2 < size_x; ++i2) {
			for (int k2 = 0; k2 < size_z; ++k2) {
				double d3 = (this.noise_data_4[l1] + 512d) / 256d;

				if (d3 > 1.0D) {
					d3 = 1.0D;
				}

				double d4 = 0.0D;
				double d5 = this.noise_data_5[l1] / 8000.0D;

				if (d5 < 0.0D) {
					d5 = -d5;
				}

				d5 = d5 * 3.0D - 3.0D;

				if (d5 < 0.0D) {
					d5 /= 2.0D;

					if (d5 < -1.0D) {
						d5 = -1.0D;
					}

					d5 /= 1.4D;
					d5 /= 2.0D;
					d3 = 0.0D;
				} else {
					if (d5 > 1.0D) {
						d5 = 1.0D;
					}

					d5 /= 6.0D;
				}

				d3 += 0.5D;
				d5 = d5 * (double) size_y / 16.0D;
				++l1;

				for (int j2 = 0; j2 < size_y; ++j2) {
					double d6 = 0.0D;
					double d7 = adouble1[j2];
					double d8 = this.noise_data_2[k1] / 512.0D;
					double d9 = this.noise_data_3[k1] / 512.0D;
					double d10 = (this.noise_data_1[k1] / 10.0D + 1.0D) / 2.0D;

					if (d10 < 0.0D) {
						d6 = d8;
					} else if (d10 > 1.0D) {
						d6 = d9;
					} else {
						d6 = d8 + (d9 - d8) * d10;
					}

					d6 -= d7;
					double d11;

					if (j2 > size_y - 4) {
						d11 = (double) ((float) (j2 - (size_y - 4)) / 3.0F);
						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					if ((double) j2 < d4) {
						d11 = (d4 - (double) j2) / 4.0D;

						if (d11 < 0.0D) {
							d11 = 0.0D;
						}

						if (d11 > 1.0D) {
							d11 = 1.0D;
						}

						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					field[k1] = d6;
					++k1;
				}
			}
		}

		return field;
	}

	public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}

	public void populate(IChunkProvider chunk_prov, int chunk_x, int chunk_z)
	{
		int x = chunk_x << 4;
		int z = chunk_z << 4;
		BiomeGenBase biome = this.world.getBiomeGenForCoords(x + 16, z + 16);
		
		this.rand.setSeed(this.world.getSeed());
		long rn1 = this.rand.nextLong() / 2L * 2L + 1L;
		long rn2 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long) chunk_x * rn1 + (long) chunk_z * rn2 ^ this.world.getSeed());

		BlockFalling.fallInstantly = true;
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunk_prov, this.world, this.rand, chunk_x, chunk_z, false));
		
		biome.decorate(world, rand, chunk_x, chunk_z);

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunk_prov, this.world, this.rand, chunk_x, chunk_z, false));
		BlockFalling.fallInstantly = false;
	}

	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}

	public void saveExtraData()
	{
	}

	public boolean unloadQueuedChunks()
	{
		return false;
	}

	public boolean canSave()
	{
		return true;
	}

	public String makeString()
	{
		return "HellRandomLevelSource";
	}

	public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
	{
		BiomeGenBase biomegenbase = this.world.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return biomegenbase.getSpawnableList(p_73155_1_);
	}

	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
	{
		return null;
	}

	public int getLoadedChunkCount()
	{
		return 0;
	}

	public void recreateStructures(int p_82695_1_, int p_82695_2_)
	{
	}
}
