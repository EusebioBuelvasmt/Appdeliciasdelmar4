package apps.DeliciasdelMar3;

/* Modelo de la clase para RecyclerView*/
public class ModelRecord {
    //Variables
    String id, name, image, bio, price;

    //Constructor

    public ModelRecord(String id, String name, String image, String bio, String price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.bio = bio;
        this.price = price;

    }

    //Getter y Setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
