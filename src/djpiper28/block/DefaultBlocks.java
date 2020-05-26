package djpiper28.block;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefaultBlocks {

	private static Block oreBlock = new OreBlock();
	private static Block oreBlock2 = new OreBlock2();
	
	private static Block dirt = new DirtBlock();
	private static Block stone = new StoneBlock();
	private static Block air = new AirBlock();
	
	public static Block getAir() {
		return air;
	}

	public static List<Block> getDefaultBlocks() {
		List<Block> output = new LinkedList<Block>();
		
		output.add(air);
		output.add(dirt);
		output.add(stone);
		output.add(oreBlock);
		output.add(oreBlock2);
		
		return output;
	}

	public static Map<Block, Float> getDefaultOres() {
		HashMap<Block, Float> ores =  new HashMap<Block, Float>();
		
		ores.put(oreBlock, 0.45f);
		ores.put(oreBlock2, 0.5f);
		
		return ores;
	}

	public static Block getDirt() {
		return dirt;
	}

	public static Block getOreBlock() {
		return oreBlock;
	}
	
	public static Block getStone() {
		return stone;
	}
	
	private DefaultBlocks() {
		//Utils class
	}
	
}
