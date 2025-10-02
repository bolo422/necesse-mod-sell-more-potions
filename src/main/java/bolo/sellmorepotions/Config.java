package bolo.sellmorepotions;

import bolo.sellmorepotions.model.PotionConfigData;
import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private final List<PotionConfigData> potions = new ArrayList<>();

    public Config(String configFileName) {
        System.out.println("Loading config for Sell More Potions Mod...");
        String filename = GlobalData.rootPath() + "/settings/sellmorepotions/" + configFileName;
        File file = new File(filename);

        try {
            if (!file.exists()) {
                System.out.println("No config file found, creating a new one.");
                createNewFile(file);
            }
            loadConfig(file);
        } catch (IOException ex) {
            System.err.println("Error handling config file for Sell More Potions Mod.");
            ex.printStackTrace();
        }
    }

    private void loadConfig(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        potions.clear();

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                continue;
            }

            try {
                String[] parts = trimmedLine.split("=", 2);
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Line must contain an '=' separator.");
                }
                String name = parts[0].trim();
                String[] values = parts[1].split(",");
                if (values.length != 4) {
                    throw new IllegalArgumentException("A potion entry must have exactly 4 comma-separated values.");
                }

                int maxStock = Integer.parseInt(values[0].trim());
                int restockPerDay = Integer.parseInt(values[1].trim());
                int bestPrice = Integer.parseInt(values[2].trim());
                int worstPrice = Integer.parseInt(values[3].trim());

                if (!name.isEmpty())
                    potions.add(new PotionConfigData(name, maxStock, restockPerDay, bestPrice, worstPrice));
            } catch (Exception e) {
                System.out.println("(WARN) [SellMorePotions] Skipping invalid config line: \"" + trimmedLine + "\". Reason: " + e.getMessage());
            }
        }


        if (potions.isEmpty()) {
            System.out.println("(WARN) [SellMorePotions] Old or fully invalid config detected. Resetting to default values.");
            createNewFile(file);
            loadConfig(file);
        }
    }

    private void createNewFile(File file) throws IOException {
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("# Lines starting with # are comments and will be ignored.\n");
            writer.write("#\n");
            writer.write("# Potion names should be the item's StringID (e.g., 'healthpotion').\n");
            writer.write("# The format is: potionName=maxStock,restockPerDay,bestPrice,worstPrice\n");
            writer.write("#\n");
            writer.write("# - maxStock:       The maximum amount of this potion the Alchemist will keep in stock.\n");
            writer.write("# - restockPerDay:  How many units of this potion are restocked each day.\n");
            writer.write("# - bestPrice:      The lowest possible price (when the Alchemist is very happy).\n");
            writer.write("# - worstPrice:     The highest possible price (when the Alchemist is unhappy).\n");
            writer.write("#\n");
            writer.write("spelunkerpotion=10,2,50,250\n");
            writer.write("miningpotion=10,2,50,250\n");
            writer.write("treasurepotion=5,1,50,250\n");
            writer.write("battlepotion=10,2,50,250\n");
            writer.write("resistancepotion=10,2,25,150\n");
            writer.write("buildingpotion=20,5,25,150\n");
        }
    }

    public List<PotionConfigData> getPotions() {
        return this.potions;
    }
}