package cc.ranmc.login.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.world.level.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class RanmcLoginClient implements ClientModInitializer {

	private boolean shouldSendLogin = false;
	private int tickCounter = 0;
	private List<String> serverAddressList;
	private Map<String, String> pwdMap;
	public static final Logger LOGGER = LoggerFactory.getLogger("ranmc");

	@Override
	public void onInitializeClient() {
		Config config = Config.getInstance();
		serverAddressList = config.getServers();
		pwdMap = config.getAccounts();

		LOGGER.info("init ranmc_auto_login client.");
		ClientPlayConnectionEvents.JOIN.register((_, _, client) -> {
			Minecraft mc = Minecraft.getInstance();
			ServerData server = mc.getCurrentServer();
			String address = (server != null) ? server.ip : null;
			if (address != null && client.player != null) {
				address = address.split(":")[0];
				for (String s : serverAddressList) {
					if (address.endsWith(s)) {
						shouldSendLogin = true;
						break;
					}
				}
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (shouldSendLogin &&
					client.player != null) {
				if (client.player.isDeadOrDying() && client.screen instanceof DeathScreen) {
					client.player.respawn();
				} else {
					tickCounter++;
					if (tickCounter == 10) {
						String playerName = client.player.getName().getString();
						if (pwdMap.containsKey(playerName)) {
							client.player.connection.sendCommand("l " + pwdMap.get(playerName));
						}
					} else if (tickCounter == 15) {
						if (client.player.gameMode() != GameType.CREATIVE) {
							client.player.connection.sendCommand("g 1");
						}
					} else if (tickCounter == 20) {
						client.player.connection.sendCommand("tps");
						tickCounter = 0;
						shouldSendLogin = false;
					}
				}
			}
		});
	}
}
