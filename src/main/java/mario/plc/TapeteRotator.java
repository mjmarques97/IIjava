package mario.plc;

public class TapeteRotator extends TapeteDireitaEsquerda {
    public TapeteRotator(int plcCellName) {
        super(plcCellName, "Top_Rotator_Horizontal");
    }

    public void moveDown(){
        this.stopMovingRight();

    }

}
