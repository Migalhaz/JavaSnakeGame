import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Renderer extends JPanel implements ActionListener
{
    Vector2 _boardSize;
    int _tileSize;

    ArrayList<GameObject> _gameObjects;

    Color _backgroundColor = Color.black;

    public Renderer(int width, int height, int tileSize)
    {
        _boardSize = new Vector2(width, height);
        _tileSize = tileSize;
        _gameObjects = new ArrayList<GameObject>();
        GameStaticObserver.ProvideRenderer = () -> this;
        SetupRenderer();
    }

    public Vector2 GetBoardSize() 
    {
        return _boardSize;
    }

    public int GetTileSize() 
    {
        return _tileSize;
    }

    public void AddGameObject(GameObject newObjectRenderer)
    {
        _gameObjects.add(newObjectRenderer);
    }

    public void RemoveGameObject(GameObject gameObject)
    {
        _gameObjects.remove(gameObject);
    }

    void SetupRenderer()
    {
        setPreferredSize(new Dimension(_boardSize.x, _boardSize.y));
        setBackground(_backgroundColor);
        setFocusable(true);
        requestFocusInWindow();
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e){
                requestFocusInWindow();
            }
        });
        SetupKeyBindings();
    }

    void SetupKeyBindings()
    {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        
        String[] keysStrings = { "W", "A", "S", "D", "SPACE", "ESCAPE"};
        int[] keyCodes = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};

        for (int i = 0; i < keyCodes.length; ++i)
        {
            final int index = i;
            KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCodes[i], 0);
            inputMap.put(keyStroke, keysStrings[i]);
            actionMap.put(keysStrings[i], new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e){
                    HandleKeyPress(keyCodes[index]);
                }
            });
        }
    }

    void HandleKeyPress(int keyCode)
    {
        System.out.println("Input Listened");
        SnakeHead snakeHead = GameStaticObserver.GetSnakeHead();
        if (snakeHead == null) return;
    
        if (keyCode == KeyEvent.VK_D) {
            snakeHead.SetMoveDirection(1, 0); // Direita
        } else if (keyCode == KeyEvent.VK_W) {
            snakeHead.SetMoveDirection(0, 1); // Cima
        } else if (keyCode == KeyEvent.VK_A) {
            snakeHead.SetMoveDirection(-1, 0); // Esquerda
        } else if (keyCode == KeyEvent.VK_S) {
            snakeHead.SetMoveDirection(0, -1); // Baixo
        } else if (keyCode == KeyEvent.VK_SPACE) {
            snakeHead.IncreaseSize();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Draw(g);
    }

    void Draw(Graphics graphics)
    {
        int iterations = _boardSize.x/_tileSize;
        for (int i = 0; i < iterations; i++)
        {
            graphics.drawLine(i * _tileSize, 0, i*_tileSize, _boardSize.y);
            graphics.drawLine(0, i * _tileSize, _boardSize.x, i * _tileSize);
        }

        for (GameObject obj : _gameObjects) 
        {
            Color color = obj.ObjectRenderer.ColorRenderer;
            graphics.setColor(color);

            int xPosition = obj.Position.x * _tileSize;
            int yPosition = obj.Position.y * _tileSize;

            graphics.fillRect(xPosition, yPosition, _tileSize, _tileSize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
