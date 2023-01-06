package dev.dubhe.brace4qq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;

public abstract class BotFile {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Path ROOT = new File("").toPath().toAbsolutePath();
    public static final File CONFIG = ROOT.resolve("config.json").toFile();
    public static final File CONFIG_PATH = ROOT.resolve("configs").toFile();
    public static final File PLUGIN_PATH = ROOT.resolve("plugins").toFile();
    public static final File RESOURCES_PATH = ROOT.resolve("resources_packs").toFile();

    public static boolean check() {
        if (!CONFIG_PATH.isDirectory()) CONFIG_PATH.mkdir();
        if (!PLUGIN_PATH.isDirectory()) PLUGIN_PATH.mkdir();
        if (!RESOURCES_PATH.isDirectory()) RESOURCES_PATH.mkdir();
        if (!CONFIG.isFile()) {
            try {
                CONFIG.createNewFile();
                try (FileOutputStream outputStream = new FileOutputStream(CONFIG);
                     OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
                    GSON.toJson(new QQChatBot.QQConfig(), writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public static QQChatBot.QQConfig getConfig() {
        try (FileInputStream in = new FileInputStream(CONFIG);
             InputStreamReader reader = new InputStreamReader(in)) {
            return GSON.fromJson(reader, QQChatBot.QQConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
