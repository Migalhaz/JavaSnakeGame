import java.awt.Color;

public class GameObject 
{
    public Vector2 Position;
    public ObjectRenderer ObjectRenderer;

    public GameObject(Vector2 position, ObjectRenderer renderer)
    {
        Position = position;
        ObjectRenderer = renderer;
    }

    public GameObject(int x, int y, ObjectRenderer renderer)
    {
        Position = new Vector2(x, y);
        ObjectRenderer = renderer;
    }

    public GameObject(Vector2 position, Color objectColor)
    {
        Position = position;
        ObjectRenderer = new ObjectRenderer(objectColor);
    }

    public GameObject(int x, int y, Color objectColor)
    {
        Position = new Vector2(x, y);
        ObjectRenderer = new ObjectRenderer(objectColor);
    }
}
