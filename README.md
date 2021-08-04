# A CPU RAYTRACER WRITTEN IN JAVA

## VIDEOS & IMAGES

https://user-images.githubusercontent.com/3200599/128142280-17aca308-8326-4a61-a8e4-23357ac724a4.mp4
![team_jellybean_06_04_2021_12_59_55](https://user-images.githubusercontent.com/3200599/128142365-1a8c9f5b-7fb4-420f-b6ec-35896396cb82.png)
![team_jellybean_29_07_2021_23_18_03](https://user-images.githubusercontent.com/3200599/128142469-9a0cdf90-3e4b-4825-88ef-78f3e6071a86.png)


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
