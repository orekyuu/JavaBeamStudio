package net.orekyuu.javatter.core.util;

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {
    @Override
    public int compare(String ver1, String ver2) {
        String[] version1 = ver1.split("\\.");
        String[] version2 = ver2.split("\\.");
        int len = Math.min(version1.length, version2.length);
        for (int i = 0; i < len; i++) {
            String token1 = version1[i];
            String token2 = version2[i];
            if (!token1.matches("^\\d+$") || !token2.matches("^\\d+$")) {
                throw new IllegalArgumentException();
            }
            int num1 = Integer.valueOf(token1);
            int num2 = Integer.valueOf(token2);

            if(num1 == num2) {
                continue;
            }
            return num1 - num2;
        }

        String[] remnant;
        if (version1.length < version2.length) {
            remnant = version2;
        } else {
            remnant = version1;
        }

        for (int i = len; i < remnant.length; i++) {
            String token = remnant[i];
            if (!token.matches("^\\d+$")) {
                throw new IllegalArgumentException();
            }
            if (Integer.valueOf(token) != 0) {
                return version1.length - version2.length;
            }
        }
        return 0;
    }
}
