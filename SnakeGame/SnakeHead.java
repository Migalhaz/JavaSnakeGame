import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class SnakeHead extends GameObject implements ActionListener
{
    Vector2 _moveDirection;
    int _snakeSize;
    ArrayList<SnakeBody> _body;
    boolean _moveInputEnabled;

    public SnakeHead(int x, int y, Color objectColor) {
        _moveDirection = new Vector2(1, 0);
        super(x, y, objectColor);
        _snakeSize = 0;
        _body = new ArrayList<SnakeBody>();
        _moveInputEnabled = true;
        GameStaticObserver.ProvideSnakeHead = () -> this;
    }

    boolean DeadLogic()
    {
        if (_body != null && _body.size() > 0)
        {
            GameLogic gameLogic = GameStaticObserver.GetGameLogic();
            for(SnakeBody body : _body)
            {
                Vector2 bodyPosition = body.Position;
                boolean InCollision = gameLogic.InCollision(bodyPosition, Position);
                if (InCollision)
                {
                    return true;    
                }
            }
        }
        if (Position.x < 0)
        {
            return true;
        }
        if (Position.y < 0)
        {
            return true;
        }

        Renderer renderer = GameStaticObserver.GetRenderer();
        if (renderer == null)
        {
            return false;
        }
        Vector2 boardSize = renderer.GetBoardSize();
        int maxX = boardSize.x / renderer.GetTileSize();
        int maxY = boardSize.y / renderer.GetTileSize();

        if (Position.x >= maxX)
        {
            return true;
        }

        if (Position.y >= maxY)
        {
            return true;
        }

        return false;
    }

    public void IncreaseSize()
    {
        if (_snakeSize < 0)
        {
            return;
        }

        Vector2 newPosition = new Vector2(Position.x, Position.y);
        if(_snakeSize >= 1)
        {
            newPosition = new Vector2(_body.get(_snakeSize-1).Position.x, _body.get(_snakeSize-1).Position.y);         
        }
        
        
        _snakeSize++;
        SnakeBody newBody = new SnakeBody(newPosition.x, newPosition.y, ObjectRenderer.ColorRenderer);
        _body.add(newBody);

        GameLogic gameLogic = GameStaticObserver.GetGameLogic();
        gameLogic.AddGameObject(newBody);
    }

    void Move(){
        _moveInputEnabled = true;
        UpdateBodyPosition(0, Position);
        Position.x += _moveDirection.x;
        Position.y -= _moveDirection.y;
    }


    void UpdateBodyPosition(int index, Vector2 newPosition)
    {
        if (_snakeSize <= 0){
            return;
        }
        if (index < 0 || index >= _body.size()){
            return;
        }
        SnakeBody body = _body.get(index);
        Vector2 lastPosition = body.Position;        
        UpdateBodyPosition(index+1, lastPosition);
        body.Position.Set(newPosition.x, newPosition.y);
    }

    void SetMoveDirection(int x, int y)
    {
        if (!_moveInputEnabled)
        {
            return;
        }
        _moveInputEnabled = false;
        SetXMove(x);
        SetYMove(y);
    }

    void SetXMove(int xMove)
    {
        if (_moveDirection.x != 0 && xMove != 0)
        {
            return;
        }

        _moveDirection.x = xMove;
    }

    void SetYMove(int yMove)
    {
        if (_moveDirection.y != 0 && yMove != 0)
        {
            return;
        }

        _moveDirection.y = yMove;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
        if (DeadLogic())
        {
            System.out.println("Morri");
            GameStaticObserver.PlayerDies(this);
        }
    }
}

