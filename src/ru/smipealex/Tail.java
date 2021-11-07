package ru.smipealex;

public class Tail extends GameObject {
    private final   TailType    _tailType;
    private final Tail                _nextTail;


    public Tail getNextTail() {
        return _nextTail;
    }

    public Tail(TailType tailType, Tail nextTail){
        super(GameObjectType.TAIL);
        _tailType = tailType;
        _nextTail = nextTail;
    }

    public TailType getTailType() {
        return _tailType;
    }
}
