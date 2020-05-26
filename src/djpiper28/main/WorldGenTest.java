package djpiper28.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import djpiper28.block.DefaultBlocks;
import djpiper28.render.WorldToImage;
import djpiper28.main.Messages;
import djpiper28.world.World;

public class WorldGenTest {

	private interface inputValidatorInterface {

		public boolean isValid(String input);

	}

	private static String input(String requestText, String errorText, inputValidatorInterface inputValidator,
			Scanner scanner) {
		System.out.println(requestText);
		String input;

		while (!inputValidator.isValid((input = scanner.nextLine()))) {
			System.out.println(errorText);
		}

		return input;
	}

	private static int inputInt(String requestText, String errorText, Scanner scanner) {
		return Integer.valueOf(input(requestText, errorText, (input -> {
			final String intStuff = Messages.getString("WorldGenTest.intstuff"); //$NON-NLS-1$
			boolean isInt = input.length() <= 7;

			for (String character : input.split("")) { //$NON-NLS-1$
				isInt &= intStuff.contains(character);

				if (!isInt) {
					break;
				}
			}

			return isInt;
		}), scanner));
	}

	private static String inputFileName(String requestText, String errorText, Scanner scanner) {
		return input(requestText, errorText, (input -> {
			final String fileStuffs = Messages.getString("WorldGenTest.filestuff"); //$NON-NLS-1$
			boolean isValid = fileStuffs.length() < 100;

			for (String character : input.split("")) { //$NON-NLS-1$
				isValid &= fileStuffs.contains(character);

				if (!isValid) {
					break;
				}
			}

			return isValid;
		}), scanner);
	}

	public static void main(String[] args) {
		System.out.println(Messages.getString(Messages.getString("WorldGenTest.0"))); //$NON-NLS-1$
		
		//Get generation parameters
		Scanner scanner = new Scanner(System.in);

		int width = inputInt(Messages.getString(Messages.getString("WorldGenTest.1")), //$NON-NLS-1$
				Messages.getString("WorldGenTest.widtherror"), scanner); //$NON-NLS-1$ //$NON-NLS-2$
		int height = inputInt(Messages.getString("WorldGenTest.heightquery"), //$NON-NLS-1$
				Messages.getString("WorldGenTest.heighterror"), scanner); //$NON-NLS-1$
		
		String filename = inputFileName(Messages.getString("WorldGenTest.filequery"), //$NON-NLS-1$
				Messages.getString("WorldGenTest.fileerror") + Messages.getString(Messages.getString("WorldGenTest.2")), //$NON-NLS-1$ //$NON-NLS-2$
				scanner);
		
		//Show user chosen parameters
		Logger logger = Logger.getLogger(Messages.getString(Messages.getString("WorldGenTest.3"))); //$NON-NLS-1$
		logger.log(Level.INFO, Messages.getString("WorldGenTest.4")+width+Messages.getString("WorldGenTest.5")+height+Messages.getString("WorldGenTest.6")+filename); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		//Prepare world
		logger.log(Level.INFO, Messages.getString("WorldGenTest.7")); //$NON-NLS-1$
		World world = new World(width, height, DefaultBlocks.getDefaultBlocks());

		//Generate world
		logger.log(Level.INFO, Messages.getString("WorldGenTest.8")); //$NON-NLS-1$
		Generator generator = new Generator(world, DefaultBlocks.getDefaultOres(), DefaultBlocks.getDirt(), DefaultBlocks.getStone(), DefaultBlocks.getAir());
		
		logger.log(Level.INFO, Messages.getString("WorldGenTest.9")); //$NON-NLS-1$
		generator.generateWorld();

		//Render world
		logger.log(Level.INFO, Messages.getString("WorldGenTest.10")); //$NON-NLS-1$
		WorldToImage renderer = new WorldToImage(world);
		BufferedImage image = renderer.getImage();		

		//Save world
		logger.log(Level.INFO, Messages.getString("WorldGenTest.11")); //$NON-NLS-1$
		try {
			ImageIO.write(image, Messages.getString("WorldGenTest.12"), new File(filename)); //$NON-NLS-1$
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Level.INFO, Messages.getString("WorldGenTest.13")); //$NON-NLS-1$
		}
		
		logger.log(Level.INFO, Messages.getString("WorldGenTest.14")); //$NON-NLS-1$
	}

}
