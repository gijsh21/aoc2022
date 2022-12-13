package aoc.solutions.day13;

import aoc.Util;

import java.util.ArrayList;
import java.util.List;

public class D13P1 {

    private static class Item {

        enum ItemType {
            INT,
            LIST
        }

        ItemType type;
        int intValue;
        List<Item> listValue;

        Item(ItemType itemType) {
            this.type = itemType;
            this.intValue = Integer.MIN_VALUE;
            this.listValue = new ArrayList<>();
        }

        void convertFromIntToList() {

            if(this.type != ItemType.INT) throw new IllegalStateException();

            Item intItem = new Item(ItemType.INT);
            intItem.intValue = this.intValue;

            this.type = ItemType.LIST;
            this.listValue.add(intItem);

        }

    }

    public static void run() {

        List<String> lines = Util.inputLines("day13");

        List<List<String>> pairs = new ArrayList<>();
        for(int i = 0; i < lines.size(); i += 3) {
            List<String> pair = new ArrayList<>();
            pair.add(lines.get(i));
            pair.add(lines.get(i + 1));
            pairs.add(pair);
        }

        int pairsCorrectIndexSum = 0;
        for(int i = 0; i < pairs.size(); i++) {
            if(correctOrder(pairs.get(i))) pairsCorrectIndexSum += (i + 1);
        }

        System.out.println(pairsCorrectIndexSum);

    }

    private static boolean correctOrder(List<String> pair) {

        if(pair.size() != 2) throw new IllegalArgumentException();

        char[] leftArr = pair.get(0).toCharArray();
        char[] rightArr = pair.get(1).toCharArray();

        Item left = parseItems(leftArr, 0, leftArr.length);
        Item right = parseItems(rightArr, 0, rightArr.length);

        assert left != null;
        assert right != null;
        return compareOrder(left, right) < 0;

    }

    private static Item parseItems(char[] in, int start, int end) {

        if(end <= start) return null;
        if(in[start] == '[' && in[start + 1] == ']') return new Item(Item.ItemType.LIST);

        Item item;
        if(in[start] == '[') {

            item = new Item(Item.ItemType.LIST);

            int depth = 0;
            int startMarker = start + 1;
            for(int i = start + 1; i < end; i++) {
                if(in[i] == '[') {
                    depth++;
                } else if(in[i] == ']') {
                    depth--;
                    if(depth < 0) {
                        item.listValue.add(parseItems(in, startMarker, i));
                    }
                } else if(in[i] == ',' && depth == 0) {
                    item.listValue.add(parseItems(in, startMarker, i));
                    startMarker = i + 1;
                }
            }

        } else {

            item = new Item(Item.ItemType.INT);

            StringBuilder sb = new StringBuilder();
            for(int i = start; i < end; i++) {
                sb.append(in[i]);
            }
            item.intValue = Integer.parseInt(sb.toString());

        }

        return item;

    }

    private static int compareOrder(Item left, Item right) {

        if(left.type == Item.ItemType.LIST && right.type == Item.ItemType.LIST) {

            for(int i = 0; i < left.listValue.size() && i < right.listValue.size(); i++) {

                Item leftItem = left.listValue.get(i);
                Item rightItem = right.listValue.get(i);
                int result = compareOrder(leftItem, rightItem);

                if(result < 0) return -1;
                if(result > 0) return 1;

            }

            return Integer.compare(left.listValue.size(), right.listValue.size());

        } else if(left.type == Item.ItemType.INT && right.type == Item.ItemType.INT) {

            return Integer.compare(left.intValue, right.intValue);

        } else {

            if(left.type == Item.ItemType.LIST && right.type == Item.ItemType.INT) {

                right.convertFromIntToList();
                return compareOrder(left, right);

            } else if(left.type == Item.ItemType.INT && right.type == Item.ItemType.LIST) {

                left.convertFromIntToList();
                return compareOrder(left, right);

            }

        }

        throw new IllegalStateException();

    }

}
