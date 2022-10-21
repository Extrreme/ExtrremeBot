package dev.extrreme.extrremebot.utils;

public class StringUtility {
    public static String concatenate(String[] args, int start, int end, String between) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length && i <= end; i++) {
            sb.append(args[i]);
            if (i != end) {
                sb.append(between);
            }
        }

        return sb.toString();
    }

    public static String concatenate(String[] args, String between) {
        return concatenate(args, 0, args.length-1, between);
    }

    public static String concatenate(String[] args) {
        return concatenate(args, 0, args.length-1, "");
    }
}
