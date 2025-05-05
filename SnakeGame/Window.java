import javax.swing.JFrame;

public class Window
{
    Vector2 _windowSize;
    JFrame _currentJFrame;
    Renderer _renderer;


    public Window(int width, int height, Renderer renderer)
    {
        _windowSize = new Vector2(width, height);
        _renderer = renderer;
    }

    public JFrame ProvideJFrame()
    {
        if (_currentJFrame != null){
            return _currentJFrame;
        }
        _currentJFrame = new JFrame("SnakeGame");
        SetupJFrame(_currentJFrame);
        return _currentJFrame;
    }

    void SetupJFrame(JFrame jFrame)
    {
        jFrame.setVisible(true);
        jFrame.setSize(_windowSize.x, _windowSize.y);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.add(_renderer);
        jFrame.pack();
    }
}