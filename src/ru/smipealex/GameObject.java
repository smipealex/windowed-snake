package ru.smipealex;

public class GameObject {
    private final GameObjectType          _type;
    private Vector2                 _position;
    private Vector2                 _lastPosition;

    public GameObject(GameObjectType type){
        _type = type;
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
    }

    protected Vector2 get_lastPosition(){
        return _lastPosition;
    }
}
