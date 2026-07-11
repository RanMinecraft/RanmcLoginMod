package cc.ranmc.login;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RanmcLogin implements ModInitializer {
	public static final String MOD_ID = "ran_login";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("init ranmc_auto_login.");
	}
}