package com.progwml6.ironchest.common;

import com.progwml6.ironchest.IronChests;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

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

    addTranslatedPages(bookPages, IronChests.MODID + ".book." + key, pageCount);

    book.addTagElement("pages", bookPages);
    book.addTagElement("generation", IntTag.valueOf(3));
    book.addTagElement("author", StringTag.valueOf(BOOK_AUTHOR));
    book.addTagElement("title", StringTag.valueOf("How to use your DirtChest 9000!"));
  }

  public static void addTranslatedPages(ListTag bookPages, String translationKey, int pageCount) {
    for (int i = 1; i <= pageCount; i++)
      bookPages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(translationKey + "." + i))));
  }
}
