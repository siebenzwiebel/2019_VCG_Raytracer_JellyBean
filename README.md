# A CPU RAYTRACER WRITTEN IN JAVA

## WHAT IT DOES

* Shapes
  * Plane, Sphere, Square, Triangle
* Shaders
  * Lambert
  * Phong
* Lights
  * Area
  * Point
* OBJ File Import
  * Shading of Polygons/Triangles is still broken
* Reflection, Refraction
* Global Illumination
* Random Lights (color, position, intensity)
* Random Spheres (material, position, size)
* Minimal physics engine for animation
  * apply forces (as vectors) to objects for e.g. a bouncing ball inside of Cornell box (see video)
  * render out frames as image sequence
* Threading (multicore Rendering, uses maximum possible threads , based on given CPU)

## TODO

* add tree (preferrably kd-tree or octotree) for performance
* fix polygon shading
* add a gui
* implement ffmpeg to output videos instead of image sequences
