package controller;

import java.text.SimpleDateFormat;

public abstract class FormattingController {

    public static String millisToDate(long timestamp) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(timestamp);
    }

    public static String beautyMillis(long timestamp) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(timestamp);
    }

    public static String getFormattedFileSize(double count) {
        int factor = 1;
        char format = '\u0000';

        if (count >= 1073741824) {
            format = 'G';
            factor = 1073741824;
        } else if (count >= 1048576) {
            format = 'M';
            factor = 1048576;
        } else if (count >= 1024) {
            format = 'K';
            factor = 1024;
        }
        return String.format("%.2f", count / factor) + " " + format + "B";
    }
}
