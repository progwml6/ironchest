package cpw.mods.ironchest;

import java.util.Properties;

public class Version {
	private static String major, minor, rev, build, mcversion;

	static void init(Properties properties) {
		if (properties != null) {
			major = properties.getProperty("IronChest.build.major.number");
			minor = properties.getProperty("IronChest.build.minor.number");
			rev = properties.getProperty("IronChest.build.revision.number");
			build = properties.getProperty("IronChest.build.number");
			mcversion = properties.getProperty("IronChest.build.mcversion");
		}
	}

	public static String fullVersionString() {
		return String.format("%s.%s.%s build %s", major, minor, rev, build);
	}
}