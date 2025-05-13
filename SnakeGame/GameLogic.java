import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class GameLogic implements ActionListener, KeyListener
{
    Window _window;
    Renderer _renderer;

    ArrayList<GameObject> _allGameObjects;
    ArrayList<ActionListener> _listeners;
    SnakeHead _snakeHead;
    GameObject _food; 

    boolean _playing;
    Timer _gameLoop;

    public GameLogic(Window window, Renderer renderer)
    {
        GameStaticObserver.ProvideGameLogic = () -> this;
        GameStaticObserver.OnPlayerDies = (snakeHead) -> OnPlayerDies(snakeHead);

        _window = window;
        _renderer = renderer;
        _allGameObjects = new ArrayList<GameObject>();

        SetupGame();
        SetListeners();

        _playing = false;
        _gameLoop = new Timer(100, this);
        _gameLoop.start();

    }


    void SetListeners(){
        _listeners = new ArrayList<ActionListener>();
        _listeners.add(_snakeHead);
        _listeners.add(_renderer);
    }

    void SetupGame()
    {
        _snakeHead = new SnakeHead(10, 10, Color.green);
        AddGameObject(_snakeHead);
        PlaceFood();
    }

    void PlaceFood()
    {
        Random random = new Random();

        int maxWidth = _renderer.GetBoardSize().x/_renderer.GetTileSize();
        int maxHeight = _renderer.GetBoardSize().y/_renderer.GetTileSize();

        int nextX = random.nextInt(maxWidth);
        int nextY = random.nextInt(maxHeight);
        Vector2 newPosition = new Vector2(nextX, nextY);

        for (GameObject gameObject : _allGameObjects) {
            if (InCollision(newPosition, gameObject.Position))
            {
                PlaceFood();
                return;
            }
        }

        if (_food != null)
        {
            RemoveGameObject(_food);
        }
        _food = new GameObject(newPosition, Color.red);
        AddGameObject(_food);
    }

    boolean InCollision(Vector2 positionA, Vector2 positionB)
    {
        return positionA.x == positionB.x && positionA.y == positionB.y;
    }

    void CheckEatFood(){
        Vector2 snakePos = _snakeHead.Position;
        Vector2 foodPos = _food.Position;
        if (!InCollision(snakePos, foodPos)){
            return;
        }

        PlaceFood();
        _snakeHead.IncreaseSize();
    }

    public void AddGameObject(GameObject newGameObject)
    {
        System.out.println("adding new game object");
        _allGameObjects.add(newGameObject);

        _renderer.AddGameObject(newGameObject);
    }

    void RemoveGameObject(GameObject gameObject)
    {
        _allGameObjects.remove(gameObject);
        _renderer.RemoveGameObject(gameObject);
    }

    void OnPlayerDies(SnakeHead snakeHead)
    {
        GameOver();
    }

    public void GameOver()
    {
        _gameLoop.stop();
        System.out.println(String.format("Obrigado por jogar! Você fez: %d pontos!", _snakeHead._snakeSize));
        _window.CloseWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(ActionListener action : _listeners)
        {
            action.actionPerformed(e);
        }
        CheckEatFood();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE)
        {
            GameOver();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
