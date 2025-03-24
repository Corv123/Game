package io.github.some_example_name.lwjgl3.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ✅ Abstract Entity class implementing move()
public abstract class Entity {
    protected float x, y;
    protected float speed;
    protected Texture texture;
    protected SpriteBatch batch;
    protected float scale;

    public Entity(Texture texture, float x, float y, float speed,float scale, SpriteBatch batch)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = texture;
        this.batch = batch;
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public abstract void update();

    public abstract void draw(SpriteBatch batch);

    // ✅ Ensure all subclasses can define movement behavior
    public void move(){
    }

    // ✅ Dispose should be the last method for cleanup
    public void dispose()
    {
        if (texture != null)
        {
            texture.dispose();
            texture = null;
        }
    }
}
