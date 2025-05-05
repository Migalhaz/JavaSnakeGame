public class Vector2 
{
    public int x;
    public int y;

    public Vector2()
    {
        x = 0;
        y = 0;
    }

    public Vector2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void Set(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return String.format("%d %d", x, y);
    }
}
