package djpiper28.main;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import djpiper28.block.Block;
import djpiper28.world.World;
import fastnoise.Fastnoise;

public class Generator {

	private World world;
	private Map<Block, Float> ores;
	private Block dirt, stone, air;
	private Random random;
	private int seed;

	public Generator(World world, Map<Block, Float> ores, Block dirt, Block stone, Block air) {
		this.world = world;
		this.ores = ores;
		this.random = new Random();
		this.seed = this.random.nextInt();
		this.dirt = dirt;
		this.stone = stone;
		this.air = air;
	}

	private void addCaves() {
		scatterBlock(air, -0.11f, dirt);
	}
	
	private void fillToFloor(int x, int yToFillTo, Block block) {
		for (int y = 0; y < yToFillTo; y++) {
			world.setBlock(x, y, block);
		}
	}

	private void generateBlockLayer(int minHeight, int maxHeight, Block block, int variance) {
		int currentHeight = minHeight + random.nextInt(maxHeight - minHeight);
		int flatFor = 0;

		// Iterate over x
		for (int x = 0; x < this.world.getWidth(); x++) {
			// If still flat
			if (flatFor > 0) {
				// Fill to floor with block
				fillToFloor(x, currentHeight, block);
				// Dec still flat counter
				flatFor--;
			} else {
				// Get new flat for counter
				flatFor = random.nextInt(12);

				// Change y pos
				currentHeight += (int) Math.round(squareRootKeepSign((random.nextInt(variance) - variance / 2) * (random.nextInt(variance) - variance / 2)));

				// Valdiate y pos
				if (currentHeight < minHeight) {
					currentHeight = minHeight;
				} else if (currentHeight > maxHeight) {
					currentHeight = maxHeight;
				}

				// Fill to floor
				fillToFloor(x, currentHeight, block);
			}
		}
	}

	public void generateWorld() {
		Logger logger = Logger.getLogger(Messages.getString("Generator.0")); //$NON-NLS-1$
		logger.log(Level.INFO, Messages.getString("Generator.1")); //$NON-NLS-1$
		generateBlockLayer((int) (0.7 * this.world.getHeight()), (int) (0.9 * this.world.getHeight()), dirt, 6);

		logger.log(Level.INFO, Messages.getString("Generator.2")); //$NON-NLS-1$
		generateBlockLayer((int) (0.6 * this.world.getHeight()), (int) (0.7 * this.world.getHeight()) - 20, stone, 12);

		logger.log(Level.INFO, Messages.getString("Generator.3")); //$NON-NLS-1$
		scatterDirt();

		for (Block ore : ores.keySet()) {
			logger.log(Level.INFO, Messages.getString("Generator.4") + ore.getName() + Messages.getString("Generator.5")); //$NON-NLS-1$ //$NON-NLS-2$
			scatterBlock(ore, -this.ores.get(ore), stone);
		}

		logger.log(Level.INFO, Messages.getString("Generator.6")); //$NON-NLS-1$
		addCaves();

		logger.log(Level.INFO, Messages.getString("Generator.7")); //$NON-NLS-1$
	}

	private void scatterBlock(Block block, float threshold, Block startAt) {
		Fastnoise fastnoise = new Fastnoise(this.seed * random.nextInt());

		for (int x = 0; x < this.world.getWidth(); x++) {
			// Get stone start height
			int startHeight = this.world.getHeight() - 1;
			while (this.world.getBlock(x, startHeight) != startAt) {
				startHeight--;
				if (startHeight <= 0) {
					startHeight = 1;
					break;
				}
			}

			// Add dirt
			for (int y = 0; y <= startHeight; y++) {
				if (fastnoise.GetSimplexFractal(x, y) <= threshold) {
					this.world.setBlock(x, y, block);
				}
			}
		}
	}

	private void scatterDirt() {
		scatterBlock(dirt, -0.15f, stone);
	}

	private double squareRootKeepSign(double number) {
		boolean negative = number < 0;
		double root = Math.sqrt(Math.abs(number));
		
		if (negative) {
			root *= -1;
		}
		
		return root;
	}

}
