package nc.pastek.manhunt;

public class Coordinates {
    int[] coordinates;

    Coordinates(String name, int x,int y,int z){
        this.coordinates = new int[]{x, y, z};
    }
    public String DisplayCoordinates(){
        return "["+String.valueOf(this.coordinates[0])+", "+String.valueOf(this.coordinates[1])+", "+String.valueOf(this.coordinates[2])+"]";
    }
    public String GetStrCoordinates(){
        return String.valueOf(this.coordinates[0])+" "+String.valueOf(this.coordinates[1])+" "+String.valueOf(this.coordinates[2]);
    }
}
