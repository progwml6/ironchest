package cpw.mods.ironchest;

import net.minecraft.init.Blocks;


public enum IronChestTypeSimple {
	IRON("ingotIron"),
	GOLD("ingotGold"),
	DIAMOND("gemDiamond"),
	COPPER("ingotCopper"),
	SILVER("ingotSilver"),
	CRYSTAL("blockGlass"),
	OBSIDIAN("obsidian"),
	DIRTCHEST9000("dirt"),
	WOOD("plankWood");
	
	private String material;
	
	IronChestTypeSimple(String material) {
		this.material = material;
	}
	
	public String getMaterial() { return this.material; }
	public Object toObject() {
		if (this == OBSIDIAN) return Blocks.obsidian;
		if (this == DIRTCHEST9000) return Blocks.dirt;
		return this.material;
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}