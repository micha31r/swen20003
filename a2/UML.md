# Implementation Changes

I made quite a few changes to the structure and relationship of my classes compared to what I handed in for project 2A. These changes include ones based on the feedback I received for project 2A, as well as to improve readability and implementation feasibility. In addition to class relationships, quite a few attributes and methods were changed as well. Here are some examples of the changes I made.

 - Changing constants to `private static`
 - Removing font/text related attributes (as I made a `Text` class to delegate text rendering)
 - Delegating collision detection/resolution code to each subclass of `GameObject`, as opposed handling all types of collision in the `Player` class and using `switch case`.
	 - Adding a suite of collision detection methods to the `GameObject` class
 - Adding `INITIAL_X` attribute to random move objects to calculate displacement from its initial position

There were other changes made to improve readability and semantics, as well as make implementation easier and more flexible, that I won't go through here. Instead I'll focus on the changes to my classes structures.

Here is the full list of class I implemented:
 - `ShadowMario`
 - `IO`
 - `PropertiesLoader` - new
 - `SceneManager` - new
 - `Camera` - new (although used in Project 1)
 - `IntroScene` - renamed to `StartScene`
 - `LevelScene` - made into abstract class
	 - `Level1` - new
	 - `Level2` - new
	 - `Level3` - new
 - `GameOverScene` - renamed `EndScene`
 - `Text` - new
 - ~~`Collidable`~~ - removed interface
 - ~~`RandomMovable`~~ - removed interface
 - `Collectable`
 - `GameObject`
	 - `Flag`
	 - `Item`
		 - `DoubleScorePowerUp`
		 - `InvinciblePowerUp`
		 - `Coin`
	 - `Fireball`
	 - `Tile` - renamed `Platform`
	 - `FlyingPlatform` - new
	 - `Slime`
	 - `Entity` - new abstract class
		 - `Boss` - same, but extends `Entity`
		 - `Player` - same, but extends `Entity`

The first change is adding `PropertiesLoader`. The reason behind this is to make retrieving properties easier. My previous implementation is to pass in `gameProps` and `messageProps` to every class through the constructor. This made the code more complicated. I also didn't want to pass an instance of `ShadowMario` to every other class, which would make the classes highly coupled. The new class follows a singleton model, only reads the properties files once and allows them to be accessed through a static method.

`SceneManager` to delegate scene switching because `ShadowMario` was not in a package, but other classes were, so they couldn't access the `setScene` method.

`Camera` is used to implement horizontal scrolling. I didn't want to move every object and not the player because it doesn't make sense (player is the only object supposed to move).

`LevelScene` was made into a abstract class based on the feedback I received "'Level' superclass is missing". It still contains gameplay logic, such as updating all the game objects, switch scene when the player has won .etc. I then created `Level1`, `Level2`, and `Level3` which extends LevelScene. Theses subclasses specify which level file to load, and can override `checkGameWon` method to override the win condition (this was only used for `Level3` because the player has to beat the boss enemy as well as reaching the end flag).

`Text` was made to delegate text rendering. This means I don't have to create font related attributes such as FONT, MESSAGE, FONT_SIZE .etc every time I need to display text.

Remove `Collidable` and `RandomMovable` interfaces per feedback "some interfaces are only implemented by one class". `Collidable` was initially used on `GameObject`, but I realised it can just be turned into a method. `RandomMovable` defines a randomMove method, which is only used internally. So it's also removed, and replaced with a private method.

`FlyingPlatform` was added for cohesion because it had extra attributes and methods that an normal platform didn't not use. So it didn't make sense to only have one `Platform` class.

`Entity` abstract class was created after I wrote the the `Player` class and started working on the `Boss` class because I realised the player had too much in common with boss enemy. They both have health, can shoot/collide with fireballs, have death animation, direction property .etc. These common features were coded in the Entity super class, and both `Player` and `Boss` extends from it.