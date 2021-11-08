package ru.smipealex;

import javax.swing.*;

public class GameObject {
    private final GameObjectType          _type;
    private Vector2                 _position;
    private Vector2                 _lastPosition;
    private JFrame _window;
    private Vector2 _windowSize;

    public GameObject(GameObjectType type){
        _type = type;
    }

    protected void initializeWindow(int windowWidth, int windowHeight){
        _window = new JFrame();
        _window.setSize(windowWidth, windowHeight);
        _windowSize = new Vector2(windowWidth, windowHeight);
        _window.setIconImage(new ImageIcon(_type == GameObjectType.TAIL ? "snake.png" : "apple.png").getImage());
        _window.setTitle(_type == GameObjectType.TAIL ? "Tail" : "Apple");
        _window.setVisible(true);
    }

    protected void closeWindow(){
        _window.setVisible(false);
    }

    protected GameObjectType get_type() {
        return _type;
    }

    protected Vector2 get_position() {
        return _position;
    }

    protected void set_position(Vector2 _position) {
        _lastPosition = this._position;
        this._position = _position;
        _window.setLocation(_position.getX()*_windowSize.getX(), _position.getY()*_windowSize.getY());
    }

    protected Vector2 get_lastPosition(){
        return _lastPosition;
    }
}
