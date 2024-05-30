package no.uib.inf101.sem2.game.model.entities.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

import no.uib.inf101.sem2.game.view.Inf101Graphics;

/**
 * Tile manager for the game.
 * Creates the whole platform consisting of {@link Tile}.
 * 
 * @author Lilly Thi bui
 */
public class TileManager {
    Tile[] tileList;
    int mapTileNum[][];
    int tileSize = 36;
    int tileArraySize = 20;

    // map values
    private int maxMapCol = 17;
    private int maxMapRow = 48;

    /**
     * Creates a new {@link TileManager}.
     */
    public TileManager() {
        this.tileList = new Tile[tileArraySize];
        this.mapTileNum = new int[maxMapRow][maxMapCol];
        loadMap();
        getTiles();
    }

    /**
     * Renders the list of {@link Tile} based on platform map.
     * 
     * @param g The Graphics component where we draw {@link Tile}
     */
    public void render(Graphics g) {
        for (int row = 0; row < maxMapRow; row++) {
            for (int col = 0; col < maxMapCol; col++) {
                int tileNumb = mapTileNum[row][col];
                // draw the tile based on the correct tile number
                g.drawImage(tileList[tileNumb].getImage(), (int) col * tileSize, (int) row * tileSize,
                        tileSize, tileSize, null);
            }
        }
    }

    /** Get all tile images. */
    public void getTiles() {
        BufferedImage backgroundImg = Inf101Graphics.loadImageFromResources("/tiles/sky.png");
        tileList[0] = new Tile(0, 0, tileSize, tileSize, backgroundImg, false, false);
        BufferedImage wallImg = Inf101Graphics.loadImageFromResources("/tiles/wall.png");
        tileList[1] = new Tile(0, 0, tileSize, tileSize, wallImg, true, false);
        BufferedImage starImg = Inf101Graphics.loadImageFromResources("/tiles/dog_house.png");
        tileList[2] = new Tile(0, 0, tileSize, tileSize, starImg, false, true);

        // house background images
        BufferedImage redHouseImg = Inf101Graphics.loadImageFromResources("/tiles/house_red_1.png");
        tileList[3] = new Tile(0, 0, tileSize, tileSize, redHouseImg, false, false);
        BufferedImage redHouseImg2 = Inf101Graphics.loadImageFromResources("/tiles/house_red_2.png");
        tileList[4] = new Tile(0, 0, tileSize, tileSize, redHouseImg2, false, false);

        BufferedImage whiteHouseImg = Inf101Graphics.loadImageFromResources("/tiles/house_white_1.png");
        tileList[5] = new Tile(0, 0, tileSize, tileSize, whiteHouseImg, false, false);
        BufferedImage whiteHouseImg2 = Inf101Graphics.loadImageFromResources("/tiles/house_white_2.png");
        tileList[6] = new Tile(0, 0, tileSize, tileSize, whiteHouseImg2, false, false);

        BufferedImage orangeHouseImg = Inf101Graphics.loadImageFromResources("/tiles/house_orange_1.png");
        tileList[7] = new Tile(0, 0, tileSize, tileSize, orangeHouseImg, false, false);
        BufferedImage orangeHouseImg2 = Inf101Graphics.loadImageFromResources("/tiles/house_orange_2.png");
        tileList[8] = new Tile(0, 0, tileSize, tileSize, orangeHouseImg2, false, false);

        BufferedImage yellowHouseImg = Inf101Graphics.loadImageFromResources("/tiles/house_yellow_1.png");
        tileList[9] = new Tile(0, 0, tileSize, tileSize, yellowHouseImg, false, false);
        BufferedImage yellowHouseImg2 = Inf101Graphics.loadImageFromResources("/tiles/house_yellow_2.png");
        tileList[10] = new Tile(0, 0, tileSize, tileSize, yellowHouseImg2, false, false);

        BufferedImage darkRedHouseImg = Inf101Graphics.loadImageFromResources("/tiles/house_dark_red_1.png");
        tileList[11] = new Tile(0, 0, tileSize, tileSize, darkRedHouseImg, false, false);
        BufferedImage darkRedHouseImg2 = Inf101Graphics.loadImageFromResources("/tiles/house_dark_red_2.png");
        tileList[12] = new Tile(0, 0, tileSize, tileSize, darkRedHouseImg2, false, false);

        // tree
        BufferedImage treeImg = Inf101Graphics.loadImageFromResources("/tiles/tree.png");
        tileList[13] = new Tile(0, 0, tileSize, tileSize, treeImg, false, false);
        // big tree
        BufferedImage bigTreeImg = Inf101Graphics.loadImageFromResources("/tiles/big_tree_1.png");
        tileList[14] = new Tile(0, 0, tileSize, tileSize, bigTreeImg, false, false);
        BufferedImage bigTreeImg2 = Inf101Graphics.loadImageFromResources("/tiles/big_tree_2.png");
        tileList[15] = new Tile(0, 0, tileSize, tileSize, bigTreeImg2, false, false);

        // shop
        BufferedImage shopImg = Inf101Graphics.loadImageFromResources("/tiles/shop_1.png");
        tileList[16] = new Tile(0, 0, tileSize, tileSize, shopImg, false, false);
        BufferedImage shopImg2 = Inf101Graphics.loadImageFromResources("/tiles/shop_2.png");
        tileList[17] = new Tile(0, 0, tileSize, tileSize, shopImg2, false, false);
        BufferedImage shopImg3 = Inf101Graphics.loadImageFromResources("/tiles/shop_3.png");
        tileList[18] = new Tile(0, 0, tileSize, tileSize, shopImg3, false, false);
        BufferedImage shopImg4 = Inf101Graphics.loadImageFromResources("/tiles/shop_4.png");
        tileList[19] = new Tile(0, 0, tileSize, tileSize, shopImg4, false, false);
    }

    /** Load map and update mapTileNumb based on the numbers from the map. */
    public void loadMap() {
        try {
            // read the file
            File mapFile = new File("src/main/java/no/uib/inf101/sem2/game/model/entities/tile/map.txt");
            BufferedReader reader = new BufferedReader(new FileReader(mapFile, StandardCharsets.UTF_8));
            List<String> lines = reader.lines().toList();
            reader.close();
            // go through the whole map and get map numbers
            for (int row = 0; row < maxMapRow; row++) {
                String stringRow = lines.get(row);
                String[] rowArray = stringRow.split(" ");
                for (int col = 0; col < maxMapCol; col++) {
                    mapTileNum[row][col] = Integer.parseInt(rowArray[col]);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load file");
        }

    }

    /** @return list of {@link Tile} */
    public List<Tile> getTileList() {
        List<Tile> list = new ArrayList<Tile>();
        for (int row = 0; row < maxMapRow; row++) {
            for (int col = 0; col < maxMapCol; col++) {
                int tileNumb = mapTileNum[row][col];
                Tile tile = new Tile(col * tileSize, row * tileSize, tileSize, tileSize, tileList[tileNumb].getImage(),
                        tileList[tileNumb].isSolid(), tileList[tileNumb].isDogHouse());
                list.add(tile);
            }
        }
        return list;
    }
}
