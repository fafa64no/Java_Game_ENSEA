# Java Game ENSEA

## Basic description

This is a project made to learn OOP at ENSEA.

Terrifying red cubes are everywhere, and they want to kill humanity.

Your mission : Attrition defense.

## Keybinds

 Z,Q,S,D to move around.

R to swap Tank.

Left Click to fire.

Scroll to zoom in/out.

## Features

### - Tanks

Tanks shells : Goes through enemies while dealing damage, stops on walls.

Turret : Follows mouse while keeping Tank base inertia.

Health bars : Swapping tank swaps which health pool the health bar is reading.
Each tank has a different health pool/ movement speed/ rotation speed/ etc ...

The white Tank is nearly invicible and has increased reload speed,
it is used to test enemy patterns without dying.

### - Enemies

Basic cube : does damage when you touch it.

Gatling cube : deploys when a player is nearby and shoots at him with spread.

Gatling cube with wheels : Just like Gatling cube but also moves towards its target.

Artillery cube : Just like Gatling cube but with shells that are animated
and have a different behaviour towards terrain. Also has a different range than other cubes.

Beacon cube : Spawns other cubes. Can be configured to spawn any cube, 
including himself (don't do it).
Can also spawn a version of himself which spawns different cubes 
(this one can be done without crashing).

### - Auto Generated Terrain

Generates structures from txt files, either placing them manually (like the spawn)
or randomly (like all other structures).

Pre-computes which texture goes where.

Tile map collider : The tile map has a single collider which only tests collisions around 
other colliders.

### - Camera

One camera per tank, each having their own parameters.

Only displays what's inside the range of the current camera.

### - Other stuffs

Made some vector classes to suit the needs of the project.

Most important values can be changed through the Config class.

Used some debug functions to test which method increased the launch time the most.


## Didn't have time for that

### - Currently broken circle colliders

### - Rotating colliders

### - Main menu and pause menu

### - Other vehicles for the player

### - Audio

### - Multiplayer ???
