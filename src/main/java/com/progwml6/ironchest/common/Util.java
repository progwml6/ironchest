package com.progwml6.ironchest.common;

import com.progwml6.ironchest.IronChests;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

  static String BOOK_AUTHOR = "cpw";

  public static String toEnglishName(String internalName) {
    return Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_"))
      .map(StringUtils::capitalize)
      .collect(Collectors.joining(" "));
  }

  public static ItemStack createDirtGuideBook() {
    ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
    addBookInformationStatic(book, new ListTag(), "dirtchest9000", 5);
    return book;
  }

  public static void addBookInformationStatic(ItemStack book, ListTag bookPages, @Nullable String name, int pageCount) {
    String key = name == null ? "unknown" : name;

    Function<Integer, Filterable<Component>> pageGenerationFunc = index -> Filterable.passThrough(Component.translatable(IronChests.MODID + ".book." + key + "." + (index + 1)));

    List<Filterable<Component>> list = Stream.iterate(0, index -> index + 1)
      .limit(pageCount)
      .map(pageGenerationFunc)
      .toList();

    book.set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(
      Filterable.passThrough("How to use your DirtChest 9000!"),
      BOOK_AUTHOR,
      3,
      list,
      true
    ));
  }
}
