package djpiper28.world;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import djpiper28.block.AirBlock;
import djpiper28.block.Block;
import djpiper28.errors.BlockAlreadyAddedException;
import djpiper28.errors.CoordinateOutOfBoundsError;

public class World {

	private int width, height;
	private int currentID;
	private int[][] worldBlocks;
	private BiMap<Integer, Block> blockIDMap;

	public World(int width, int height, List<Block> blocks) {
		Logger logger = Logger.getLogger(Messages.getString("World.0")); //$NON-NLS-1$

		logger.log(Level.INFO, Messages.getString("World.1")); //$NON-NLS-1$

		currentID = 0;
		blockIDMap = HashBiMap.create(new HashMap<Integer, Block>());

		// Add base block
		blockIDMap.put(-1, new AirBlock());

		this.setWidth(width);
		this.setHeight(height);

		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException(Messages.getString("World.2") + width //$NON-NLS-1$
					+ Messages.getString("World.3") + height + Messages.getString("World.4")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		worldBlocks = new int[this.getWidth()][this.getHeight()];

		// Initialise empty worldBlocks
		logger.log(Level.INFO, Messages.getString("World.5")); //$NON-NLS-1$

		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				setBlock(x, y, -1);
			}
		}

		// Add blocks
		while (!blocks.isEmpty()) {
			Block block = blocks.remove(0);
			logger.log(Level.INFO, Messages.getString("World.6") + block.getName()); //$NON-NLS-1$
			addBlock(block);
		}

		logger.log(Level.INFO, Messages.getString("World.7")); //$NON-NLS-1$
	}

	public void addBlock(Block block) {
		if (blockIDMap.containsValue(block)) {
			throw new BlockAlreadyAddedException();
		} else {
			this.blockIDMap.put(currentID, block);
			this.currentID++;
		}
	}

	private void checkCoord(int x, int y) {
		if (width < x || x < 0 || y < 0 || height < y) {
			throw new CoordinateOutOfBoundsError();
		}
	}

	public Block getBlock(int x, int y) {
		checkCoord(x, y);
		return getBlockFromID(worldBlocks[x][y]);
	}

	private Block getBlockFromID(int ID) {
		return blockIDMap.get(ID);
	}

	public int getHeight() {
		return height;
	}

	private int getIDFromBlock(Block block) {
		return blockIDMap.inverse().get(block);
	}

	public int getWidth() {
		return width;
	}

	public void setBlock(int x, int y, Block block) {
		this.setBlock(x, y, getIDFromBlock(block));
	}

	public void setBlock(int x, int y, int ID) {
		this.worldBlocks[x][y] = ID;
	}

	private void setHeight(int height) {
		this.height = height;
	}

	private void setWidth(int width) {
		this.width = width;
	}

}
