/*******************************************************************************
 * Copyright (c) 2012 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     cpw - initial API and implementation
 ******************************************************************************/
package cpw.mods.ironchest.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
 public class ModelHelper {
	public static void registerItemInternal(Item item, String[] registryNames, int[] registryMetas) {
		if (registryNames.length != registryMetas.length) { return; }
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (int i = 0; i < registryNames.length; i ++) {
			mesher.register(item, registryMetas[i], new ModelResourceLocation("ironchest:" + registryNames[i], "inventory"));
		}
	}
	
	public static void registerItem(Item item, String[] registryNames, int[] registryMetas) {
		ModelBakery.registerItemVariants(item, generateVariants(registryNames));
		registerItemInternal(item, registryNames, registryMetas);
	}
	
	public static void registerItem(Item item, int meta, String registryName) {
		registerItem(item, new String[] {registryName}, new int[] {meta});
	}
	
	static ResourceLocation[] generateVariants(String[] registryNames) {
		ArrayList<ResourceLocation> ret = new ArrayList<ResourceLocation>();
		for (String aString : registryNames) {
			ret.add(new ResourceLocation("ironchest", aString.contains("ironchest:") ? aString.replace("ironchest:", "") : aString));
		}
		return ret.toArray(new ResourceLocation[] {});
	}
}