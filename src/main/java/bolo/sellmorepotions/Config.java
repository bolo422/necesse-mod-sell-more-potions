package bolo.sellmorepotions;

import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Config {

    private Map<String, Integer> potions = new HashMap<>();

    public Config() {
        this(new HashMap<>());
    }

    public Config(Map<String, Integer> potions) {
        this.potions = potions;
    }

    public Config(String configFileName) {
        System.out.println("Loading config for Sell More Potions Mod...");
        String filename = GlobalData.rootPath() + "/settings/sellmorepotions/" + configFileName;
        try {
            File file = new File(filename);
            if (!file.exists()) createNewFile(file);

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            loadConfig(br);
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadConfig(BufferedReader br) throws IOException {
        String line;
        potions = new HashMap<>();
        while ((line = br.readLine()) != null) {
            if (line.length() != 0 && !line.startsWith("#")) {
                String[] temp = line.split("=");
                addPotion(temp[0], Integer.parseInt(temp[1]));
            }
        }
    }

    private void createNewFile(File file) throws IOException {
        if (!file.getParentFile().mkdirs() && !file.createNewFile()) {
            throw new IOException("Error creating file: " + file.toPath());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("# lines starting with # are comments and will be ignored");
            writer.write("\n#");
            writer.write("\n# potion names should be according to the game's item names, check github for the list of working potions");
            writer.write("\n# potionName=price");
            writer.write("\nspelunkerpotion=100");
            writer.write("\nminingpotion=100");
            writer.write("\ntreasurepotion=100");
            writer.write("\nbuildingpotion=100");
        }
    }

    private void addPotion(String name, Integer price) {
        if(name != null && price != null) {
            this.potions.put(name, price);
        }
    }

    public Map<String, Integer> getPotions() {
        return this.potions;
    }
}
