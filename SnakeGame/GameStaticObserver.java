import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameStaticObserver 
{
    public static Supplier<SnakeHead> ProvideSnakeHead = null;
    public static Supplier<GameLogic> ProvideGameLogic = null;
    public static Supplier<Renderer> ProvideRenderer = null;

    public static Consumer<SnakeHead> OnPlayerDies = null;

    public static SnakeHead GetSnakeHead()
    {
        return ProvideSnakeHead.get();
    }

    public static GameLogic GetGameLogic()
    {
        return ProvideGameLogic.get();
    }

    public static Renderer GetRenderer()
    {
        return ProvideRenderer.get();
    }

    public static void PlayerDies(SnakeHead snakeHead){
        OnPlayerDies.accept(snakeHead);
    }

    @Override
    protected void finalize() throws Throwable
    {
        ProvideSnakeHead = null;
        ProvideGameLogic = null;
        ProvideRenderer = null;
        OnPlayerDies = null;
    }
}