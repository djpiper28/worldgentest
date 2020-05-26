package djpiper28.render;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import djpiper28.world.World;

public class WorldToImage {
	
	private World world;
	private BufferedImage image;
	
	public WorldToImage(World world) {
		Logger logger = Logger.getLogger(Messages.getString("WorldToImage.0")); //$NON-NLS-1$
		
		this.world = world;
		this.image = new BufferedImage(this.world.getWidth(), this.world.getHeight(), BufferedImage.TYPE_INT_RGB);
		logger.log(Level.INFO, Messages.getString("WorldToImage.1")); //$NON-NLS-1$
		this.updateImage();
		logger.log(Level.INFO, Messages.getString("WorldToImage.2")); //$NON-NLS-1$
	}
	
	private void updateImage() {
		for (int x = 0; x < this.world.getWidth(); x++) {
			for(int y = 0; y < this.world.getHeight(); y++) {
				this.updateImageBlock(x, y);
			}
		}
	}
	
	private void updateImageBlock(int x, int y) {
		this.image.setRGB(x, (this.world.getHeight() - y - 1), this.world.getBlock(x, y).getColour());
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
}
