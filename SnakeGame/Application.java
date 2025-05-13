
public class Application 
{
    public static void main(String[] args)
    {
        int width = 600;
        int height = 600;
        int tileSize = 10;
        
        Renderer renderer = new Renderer(width, height, tileSize);

        Window window = new Window(width, height, renderer);
        window.ProvideJFrame();
        GameLogic gameLogic = new GameLogic(window, renderer);
    }
}