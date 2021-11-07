package ru.smipealex;

public class Apple extends GameObject {
    public Apple(Vector2 position){
        super(GameObjectType.APPLE);
        set_position(position);
    }
}
