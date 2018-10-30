
package com.vache.exhibition;



import java.util.List;

public class Exhibit {
    private String title;
    private List<String> images;

    public Exhibit(String title, List<String> images) {
        this.title = title;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public interface ExhibitsLoader {
        List<Exhibit> getExhibitList();
    }

}
