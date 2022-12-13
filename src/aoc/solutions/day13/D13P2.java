package aoc.solutions.day13;

import aoc.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class D13P2 {

    private static class Item {

        enum ItemType {
            INT,
            LIST
        }

        Item.ItemType type;
        int intValue;
        List<Item> listValue;

        Item(Item.ItemType itemType) {
            this.type = itemType;
            this.intValue = Integer.MIN_VALUE;
            this.listValue = new ArrayList<>();
        }

        Item convertFromIntToList() {

            if(this.type != Item.ItemType.INT) throw new IllegalStateException();

            Item intItem = new Item(Item.ItemType.INT);
            intItem.intValue = this.intValue;

            Item listItem = new Item(ItemType.LIST);
            listItem.listValue.add(intItem);

            return listItem;

        }

        static Item divPack1() {
            Item div = new Item(ItemType.LIST);
            Item listContaining2 = new Item(ItemType.LIST);
            Item int2 = new Item(ItemType.INT);
            int2.intValue = 2;
            listContaining2.listValue.add(int2);
            div.listValue.add(listContaining2);
            return div;
        }

        static Item divPack2() {
            Item div = new Item(ItemType.LIST);
            Item listContaining6 = new Item(ItemType.LIST);
            Item int6 = new Item(ItemType.INT);
            int6.intValue = 6;
            listContaining6.listValue.add(int6);
            div.listValue.add(listContaining6);
            return div;
        }

        @Override
        public boolean equals(Object o) {

            if(this == o) return true;

            if(o instanceof Item i) {
                if(this.type == ItemType.INT && i.type == ItemType.INT) {
                    return this.intValue == i.intValue;
                } else if(this.type == ItemType.LIST && i.type == ItemType.LIST) {
                    if(this.listValue.size() == i.listValue.size()) {
                        for(int k = 0; k < this.listValue.size(); k++) {
                            if(!this.listValue.get(k).equals(i.listValue.get(k))) return false;
                        }
                        return true;
                    }
                    return false;
                }
                return false;
            }

            return false;

        }

    }

    public static void run() {

        List<String> lines = Util.inputLines("day13");
        lines.removeIf((l) -> l.length() <= 0);

        List<Item> items = lines.stream()
                .map((l) -> parseItems(l.toCharArray(), 0, l.length()))
                .collect(Collectors.toList());
        Item div1 = Item.divPack1();
        Item div2 = Item.divPack2();
        items.add(div1);
        items.add(div2);

        items.sort(D13P2::compareOrder);

        int res = 1;
        int idx = 1;
        for(Item item : items) {
            if(item == div1 || item == div2) {
                res *= idx;
            }
            idx++;
        }

        System.out.println(res);

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

                return compareOrder(left, right.convertFromIntToList());

            } else if(left.type == Item.ItemType.INT && right.type == Item.ItemType.LIST) {

                return compareOrder(left.convertFromIntToList(), right);

            }

        }

        throw new IllegalStateException();

    }

}
