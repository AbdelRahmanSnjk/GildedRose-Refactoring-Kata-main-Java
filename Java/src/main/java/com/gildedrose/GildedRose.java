package com.gildedrose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GildedRose {
    Item[] items;

    private List<Item> normalItems = new ArrayList<>();
    private List<Item> legendaryItems = new ArrayList<>();
    private List<Item> backStageItems = new ArrayList<>();
    private List<Item> nonDegradableItems = new ArrayList<>();
    private List<Item> conjuredItems = new ArrayList<>();
    private List<String> legendaryItemsNames = new ArrayList<String>(Arrays.asList("Sulfuras, Hand of Ragnaros"));
    private List<String> nonDegradableItemsNames = new ArrayList<String>(Arrays.asList("Aged Brie"));
    private List<String> conjuredItemsNames = new ArrayList<String>(Arrays.asList("Conjured Mana Cake"));
    private List<String> backStageItemsNames = new ArrayList<String>(Arrays.asList("Backstage passes to a TAFKAL80ETC concert"));
    private final int QUALITY_UPPER_LIMIT = 50;
    private final int QUALITY_LOWER_LIMIT = 0;

    public GildedRose(Item[] items) {
        this.items = items;
        sortOutItems();
    }

    private void updateQuality_backstageItems() {
        Item currentItem;

        for (int i = 0; i < backStageItems.size(); i++) {
            currentItem = backStageItems.get(i);

            currentItem.quality += 1;
            currentItem.sellIn -= 1;

            if (currentItem.sellIn < 0) {
                currentItem.quality = 0;
            } else if (currentItem.sellIn < 6) {
                currentItem.quality += 2;
            } else if (currentItem.sellIn < 11) {
                currentItem.quality += 1;
            }

            // cleaning up quality -> 0 <= quality <= 50
            currentItem.quality = Math.min(QUALITY_UPPER_LIMIT, Math.max(QUALITY_LOWER_LIMIT, currentItem.quality));

        }

    }

    private void updateQuality_legendaryItems() {
        // nothing happens in this updateQuality, but the method is there in case new
        // logic will be added
        // a comment to show the intention that legendary items don't get modified
        for (int i = 0; i < legendaryItems.size(); i++) {

        }

    }

    private void updateQuality_nonDegradableItems() {
        Item currentItem;

        for (int i = 0; i < nonDegradableItems.size(); i++) {
            currentItem = nonDegradableItems.get(i);

            currentItem.quality += 1;
            currentItem.sellIn -= 1;

            if (currentItem.sellIn < 0)
                currentItem.quality += 1;

            // cleaning up quality -> 0 <= quality <= 50
            currentItem.quality = Math.min(QUALITY_UPPER_LIMIT, Math.max(QUALITY_LOWER_LIMIT, currentItem.quality));

        }

    }

    private void updateQuality_normalItems() {
        this.updateQuality_normalItems(this.normalItems, 1);

    }

    private void updateQuality_conjuredItems() {
        this.updateQuality_normalItems(this.conjuredItems, 2);

    }

    private void updateQuality_normalItems(List<Item> itemsListToUpdate, int qualityToRemove) {
        Item currentItem;

        for (int i = 0; i < itemsListToUpdate.size(); i++) {
            currentItem = itemsListToUpdate.get(i);

            currentItem.quality -= qualityToRemove;
            currentItem.sellIn -= 1;

            if (currentItem.sellIn < 0)
                currentItem.quality -= qualityToRemove;

            // cleaning up quality -> 0 <= quality <= 50
            currentItem.quality = Math.min(QUALITY_UPPER_LIMIT, Math.max(QUALITY_LOWER_LIMIT, currentItem.quality));

        }

    }

    private void sortOutItems() {
        String itemName;
        Item currentItem;

        for (int i = 0; i < items.length; i++) {
            currentItem = items[i];
            itemName = currentItem.name;

            if (legendaryItemsNames.contains(itemName)) {
                legendaryItems.add(currentItem);
                continue;
            } else if (nonDegradableItemsNames.contains(itemName)) {
                nonDegradableItems.add(currentItem);
                continue;
            } else if (backStageItemsNames.contains(itemName)) {
                backStageItems.add(currentItem);
                continue;
            } else if (conjuredItemsNames.contains(itemName)) {
                conjuredItems.add(currentItem);
                continue;
            }
            normalItems.add(currentItem);
        }

    }

    public void updateQuality() {
        this.updateQuality_normalItems();
        this.updateQuality_backstageItems();
        this.updateQuality_legendaryItems();
        this.updateQuality_nonDegradableItems();
        this.updateQuality_conjuredItems();
    }

}