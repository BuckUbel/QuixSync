package models;

public class FileSize {

    private double count;

    public FileSize(double count) {
        this.set(count);
    }

    public String getFormattedString() {
        int factor = 1;
        char format = '\u0000';

        if (this.count >= 1073741824) {

            format = 'G';
            factor = 1073741824;

        } else if (this.count >= 1048576) {

            format = 'M';
            factor = 1048576;

        } else if (this.count >= 1024) {

            format = 'K';
            factor = 1024;

        }

        return String.format("%.2f", this.count / factor) + " " + format + "B";
    }

    public void set(double count) {
        this.count = count;
    }

    public double getBytes() {
        return this.count;
    }


}
