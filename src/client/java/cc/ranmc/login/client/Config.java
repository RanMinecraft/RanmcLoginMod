package cc.ranmc.login.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger("ranmc");
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("ranmclogin.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Map<String, String> accounts = new HashMap<>();
    private List<String> servers = new ArrayList<>();

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public Map<String, String> getAccounts() {
        return accounts;
    }

    public List<String> getServers() {
        return servers;
    }

    private static Config load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                Config config = GSON.fromJson(reader, Config.class);
                if (config != null) {
                    LOGGER.info("已加载配置文件 {}", CONFIG_PATH);
                    return config;
                }
            } catch (IOException e) {
                LOGGER.error("读取配置文件失败", e);
            }
        }

        // 创建默认配置文件
        Config config = new Config();
        config.accounts.put("name", "password");
        config.servers.addAll(List.of("ranmc.cc", "mc9.city"));

        save(config);
        return config;
    }

    private static void save(Config config) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
            LOGGER.info("已生成默认配置文件 {}", CONFIG_PATH);
        } catch (IOException e) {
            LOGGER.error("保存配置文件失败", e);
        }
    }
}
