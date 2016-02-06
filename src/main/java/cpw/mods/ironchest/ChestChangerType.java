/*******************************************************************************
 * Copyright (c) 2012 cpw. All rights reserved. This program and the accompanying materials are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 *
 * Contributors: cpw - initial API and implementation
 ******************************************************************************/
package cpw.mods.ironchest;

import static cpw.mods.ironchest.IronChestTypeSimple.COPPER;
import static cpw.mods.ironchest.IronChestTypeSimple.CRYSTAL;
import static cpw.mods.ironchest.IronChestTypeSimple.DIAMOND;
import static cpw.mods.ironchest.IronChestTypeSimple.GOLD;
import static cpw.mods.ironchest.IronChestTypeSimple.IRON;
import static cpw.mods.ironchest.IronChestTypeSimple.OBSIDIAN;
import static cpw.mods.ironchest.IronChestTypeSimple.SILVER;
import static cpw.mods.ironchest.IronChestTypeSimple.WOOD;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import cpw.mods.ironchest.client.ModelHelper;

public enum ChestChangerType {
	WOOD_UPGRADE(WOOD, new IronChestTypeSimple[] {IRON, GOLD, DIAMOND, OBSIDIAN}, "woodUpgrade", new String[][] {{"ttt", "tst", "ttt"}, {"ttt", "tst", "ttt"}, {"GGG", "tst", "GGG"}, {"tst", "tGt", "ttt"}}),
	IRON_UPGRADE(IRON, new IronChestTypeSimple[] {GOLD, DIAMOND, OBSIDIAN}, "ironUpgrade", new String[][] {{"ttt", "tst", "ttt"}, {"GGG", "tst", "GGG"}, {"tst", "tGt", "ttt"}}),
	GOLD_UPGRADE(GOLD, new IronChestTypeSimple[] {DIAMOND, OBSIDIAN}, "goldUpgrade", new String[][] {{"GGG", "tst", "GGG"}, {"tst", "tGt", "ttt"}}),
	DIAMOND_OBSIDIAN_UPGRADE(DIAMOND, new IronChestTypeSimple[] {OBSIDIAN}, "diamondObsidianUpgrade", new String[][] {{"tst", "tGt", "ttt"}}),
	DIAMOND_CRYSTAL_UPGRADE(DIAMOND, new IronChestTypeSimple[] {CRYSTAL}, "diamondCrystalUpgrade", new String[][] {{"GsG", "GOG", "GGG"}}),
	COPPER_UPGRADE(COPPER, new IronChestTypeSimple[] {IRON, GOLD, DIAMOND, OBSIDIAN}, "copperUpgrade", new String[][] {{"tGt", "GsG", "tGt"}, {"ttt", "tst", "ttt"}, {"GGG", "tst", "GGG"}, {"tst", "tGt", "ttt"}}),
	SILVER_UPGRADE(SILVER, new IronChestTypeSimple[] {GOLD, DIAMOND, OBSIDIAN}, "silverUpgrade", new String[][] {{"tGt", "GsG", "tGt"}, {"GGG", "tst", "GGG"}, {"tst", "tGt", "ttt"}});
	
	private IronChestTypeSimple source;
	IronChestTypeSimple[] upgradeChain;
	public ItemChestChanger item;
	public String itemName;
	private String[][] recipe;
	
	private ChestChangerType(IronChestTypeSimple source, IronChestTypeSimple[] upgradeChain, String itemName, String[][] recipes) {
		this.source = source;
		this.upgradeChain = upgradeChain;
		this.itemName = itemName;
		this.recipe = recipes;
	}
	
	public IronChestTypeSimple getSource() {
		return source;
	}
	
	public boolean canUpgrade(IronChestTypeSimple from) {
		return from == this.source;
	}
	
	public int getTarget(int meta) {
		return this.upgradeChain[meta].ordinal();
	}
	
	public String getTargetName(int meta) {
		return this.upgradeChain[meta].getName();
	}
	
	public ItemChestChanger buildItem() {
		item = new ItemChestChanger(this);
		GameRegistry.registerItem(item, itemName);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			for (int i = 0; i < this.upgradeChain.length; i ++) {
				String targetName = this.upgradeChain[i].getName().substring(0, 1).toUpperCase() + this.upgradeChain[i].getName().substring(1);
				ModelHelper.registerItem(item, i, itemName + targetName);
			}
		}
		return item;
	}
	
	public void addRecipes() {
		Object sourceMaterial = this.source.toObject();
		Object targetMaterial = this.upgradeChain[0].toObject();
		IronChestType.addRecipe(new ItemStack(item), recipe[0], 's', sourceMaterial, 't', targetMaterial, 'G', "blockGlass", 'O', Blocks.obsidian);
		for (int i = 1; i < this.upgradeChain.length; i ++) {
			targetMaterial = this.upgradeChain[i].toObject();
			IronChestType.addRecipe(new ItemStack(item, 1, i), recipe[i], 's', new ItemStack(this.item, 1, i - 1), 't', targetMaterial, 'G', "blockGlass", 'O', Blocks.obsidian);
		}
	}
	
	public static void buildItems() {
		for (ChestChangerType type : values()) {
			type.buildItem();
		}
	}
	
	public static void generateRecipes() {
		for (ChestChangerType item : values()) {
			item.addRecipes();
		}
	}
}
